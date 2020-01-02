package com.example.mongodbwithexplicittomcat.controllers;

import com.example.mongodbwithexplicittomcat.models.Geo;
import com.example.mongodbwithexplicittomcat.models.Park;
import com.example.mongodbwithexplicittomcat.services.ParkDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@Validated
@RequestMapping("/parken")
public class ParkenController {
    @Autowired
    private ParkDAO parkDataService;

    @GetMapping({"", "/"})
    private ArrayList<Park> findAllParks(@RequestParam String name){
        if (name == null){
            return parkDataService.findAll();
        } else {
            return parkDataService.findByName(name);
        }
    }

    @PostMapping({"", "/"})
    private ArrayList<Park> ClosestParks(@Valid @RequestParam Geo geo){
        return parkDataService.findNearBy(geo);
    }
}
