package com.example.yamlHandler.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@SpringBootTest("basic GET")
@AutoConfigureMockMvc
public class LibraryControllerTest {
//    mockMvc.perform(get("/library/music")
//        .contentType(MediaType.APPLICATION_JSON))
//        .andExpect(status().isOk())
//        .andExpect(content().json(
//            "[{'title':'Summer','author':'Calvin Harris'}," +
//            "{'title':'Jubel (Radio Edit)','author':'Klingande'}," +
//            "{'title':'Broken','author':'Jacob Tillberg'}," +
//            "{'title':'Body (feat. Brando)','author':'Loud Luxury'}," +
//            "{'title':'Can't take it from me','author':'Major Lazer'}]"));
}
