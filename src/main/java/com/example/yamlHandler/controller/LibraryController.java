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
