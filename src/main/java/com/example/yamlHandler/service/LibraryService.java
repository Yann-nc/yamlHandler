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
    private final static Map<Integer, Tune> LIBRARY = new HashMap<>();

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
        return LIBRARY.get(id);
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
