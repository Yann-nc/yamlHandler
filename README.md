# yamlHandler


## Ticket :

**ETQDev, je sais charger un fichier yaml dans du code Java**

[https://trello.com/c/3aqxDcVT/309-2-etqdev-je-sais-charger-un-fichier-yaml-dans-du-code-java](https://trello.com/c/3aqxDcVT/309-2-etqdev-je-sais-charger-un-fichier-yaml-dans-du-code-java)

## Stratégie technique détaillée


- Créer un projet Java vide qui build et run (j’ai nommé le mien : yamlHandler)

- Importer l’Actuator
    - Il suffit de d’ajouter cette dépendance dans le fichier `pom.xml`
        
        ```xml
        <dependency>
        	<groupId>org.springframework.boot</groupId>
        	<artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        ```
        
    - L’Actuator permet de faire des Health Check classique en tapant sur la route : [`http://localhost:8080/actuator/health`](http://localhost:8080/actuator/health)  par défaut.

- Dans le fichier `src/main/resources/application.properties` , ajouter le code suivant :
    
    ```yaml
    management:
      endpoints:
        web:
          base-path: /
          exposure:
            include: "*"
      endpoint:
        metrics:
          enabled: true
    
    server:
      error.whitelabel.enabled: false
      servlet:
        context-path: /yaml-handler
    ```
    
    Cela permettra de donner le préfixe `/yaml-handler` à toutes nos routes
    

- Importer la librairie Jackson qui permet de lire et écrire des fichiers yaml depuis du code java, pour ce faire, il faut ajouter ces dépendances dans le fichier `pom.xml`
    
    ```xml
    <dependency>
    	<groupId>com.fasterxml.jackson.dataformat</groupId>
    	<artifactId>jackson-dataformat-yaml</artifactId>
    	<version>2.13.1</version>
    </dependency>
    
    <dependency>
    	<groupId>com.fasterxml.jackson.core</groupId>
    	<artifactId>jackson-databind</artifactId>
    </dependency>
    ```
    

- Maintenant passons à l’API en elle-même.
L’API que j’ai créée consiste à GET des musiques et leur auteur.
    - Créer un nouveau type de données : `Tune`
        
        `src/main/java/com/example/yamlHandler/model/Tune.java`
        
        ```java
        package com.example.yamlHandler.model;
        
        import lombok.AllArgsConstructor;
        import lombok.Getter;
        import lombok.Setter;
        
        @AllArgsConstructor
        public class Tune {
            @Getter @Setter
            private int id;
        
            @Getter @Setter
            private String title;
        
            @Getter @Setter
            private String author;
        
            public Tune() {
                this.id = 0;
                this.title = "defaut title";
                this.author = "default author";
            }
        
            @Override
            public String toString(){
                return "Music " + getId()
                        + ": " + getTitle()
                        + " by: " + getAuthor()
                        + "\n";
            }
        
        //    public Tune(int id, String title, String author) {
        //        this.id = id;
        //        this.title = title;
        //        this.author = author;
        //    }
        }
        ```
        
        Même en utilisant Lombok, il est nécessaire d’ajouter un constructeur par défaut (nécessité de la librairie Jackson)
        
    - Ajouter un nouveau controller : `LibraryController`
        
        `src/main/java/com/example/yamlHandler/controller/LibraryController.java`
        
        ```java
        package com.example.yamlHandler.controller;
        
        import com.example.yamlHandler.model.Tune;
        import com.example.yamlHandler.service.LibraryService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;
        import java.util.Collection;
        
        @RestController
        @RequestMapping("/library")
        public class LibraryController {
            @Autowired
            private LibraryService myService;
        
            @GetMapping("/music")
            public Collection<Tune> getMusic()  {
                return myService.getAll();
            };
        
            @GetMapping("/music/{id}")
            public Tune getMusic(@PathVariable("id") int musicId) {
                return myService.getOne(musicId);
            }
        
            @GetMapping("/music-yaml")
            public Collection<Tune> getMusicsFromYamlFile()  {
                return myService.getYaml();
            };
        }
        
        ```
        
    - Ajouter le Service qui va avec le Controller : `LibraryService`
        
        `src/main/java/com/example/yamlHandler/service/LibraryService.java`
        
        ```java
        package com.example.yamlHandler.service;
        
        import com.example.yamlHandler.model.Tune;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
        import org.springframework.stereotype.Service;
        
        import java.io.File;
        import java.io.IOException;
        import java.util.*;
        
        @Service
        public class LibraryService {
            private final static Map<Integer, Tune>LIBRARY= new HashMap<>();
        
            static {
        LIBRARY.put(1, new Tune(1, "Summer", "Calvin Harris"));
        LIBRARY.put(2, new Tune(2, "Jubel (Radio Edit)", "Klingande"));
        LIBRARY.put(3, new Tune(3, "Broken", "Jacob Tillberg"));
        LIBRARY.put(4, new Tune(4, "Body (feat. Brando)", "Loud Luxury"));
        LIBRARY.put(5, new Tune(5, "Can't take it from me", "Major Lazer"));
            }
        
            public Collection<Tune> getAll() {
                return new ArrayList<Tune>(LIBRARY.values());
            }
        
            public Tune getOne(int id) {
                returnLIBRARY.get(id);
            }
        
            public Collection<Tune> getYaml() {
                // Loading the YAML file from the /resources folder
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                File file = new File(classLoader.getResource("music.yml").getFile());
        
                // Instantiating a new ObjectMapper as a YAMLFactory
                ObjectMapper om = new ObjectMapper(new YAMLFactory());
        
                List<Tune> tuneList = new ArrayList<>(Collections.emptyList());
        
                // Mapping the employee from the YAML file to the Employee class
                Tune tune = null;
                try {
                    tune = om.readValue(file, Tune.class);
                    tuneList.add(tune);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        
                // Printing out the information
                assert tune != null;
                System.out.println(tune);
        
                return tuneList;
            }
        }
        
        ```
        
        L’objet `LIBRARY` est uniquement utilisé pour tester les fonctions getOne et getAll.
        
- Enfin, on peut ajouter le fichier yaml comprenant une musique : `music.yml`
    
    ```yaml
    ---
    id: 1
    title: Ciao Adios
    author: Anne Marie
    
    ```
    

## Résultat

- L’API build et run bien
- Depuis Postman on est capable de récupérer la musique présente dans le fichier yaml
    
    <img width="1009" alt="Screenshot_2022-03-06_at_16 19 46" src="https://user-images.githubusercontent.com/32078770/156930729-86a65572-dedf-4c48-ad18-64c0e2df22e9.png">
    

## Étape suivante

- Comme on a pu le voir, on est uniquement capable d’importer une seule musique
- Ajouter plusieurs musiques avec le fichier yaml suivant n’est pas possible :
    
    ```yaml
    ---
    - title: Ciao Adios
      author: Anne Marie
    - title: No Money
      author: Galantis
    - title: Solo (feat. Demi Lovato)
      author: Clean Bandit
    - title: I See You
      author: PLUM
    - title: Chakra
      author: Fakear
    ```
    
    Cette impossibilité semble être dû à l’incapacité de la librairie Jackson de correctement parcourir et interpréter le fichier yaml
    
- En revanche, en créant une abstraction supérieure comme dans le code suivant, cela semble être possible.
    
    ```yaml
    ---
    playlistId: 1
    playlist:
        - title: Ciao Adios
          author: Anne Marie
        - title: No Money
          author: Galantis
        - title: Solo (feat. Demi Lovato)
          author: Clean Bandit
        - title: I See You
          author: PLUM
        - title: Chakra
          author: Fakear
    ```
    

## Ressources

- ****Reading and Writing YAML Files in Java with Jackson (****[stackabuse.com](http://stackabuse.com/))
    
    [Reading and Writing YAML Files in Java with Jackson](https://stackabuse.com/reading-and-writing-yaml-files-in-java-with-jackson/)
    
- **Convert JSON to YAML online (**[json2yaml.com](https://www.json2yaml.com/)**)**
    
    [JSON to YAML](https://www.json2yaml.com/)
    
- GitHub project with all my source code ([github.com](http://github.com/))
[https://github.com/Yann-nc/yamlHandler](https://github.com/Yann-nc/yamlHandler)
