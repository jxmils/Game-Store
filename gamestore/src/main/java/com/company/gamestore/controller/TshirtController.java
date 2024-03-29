package com.company.gamestore.controller;

import com.company.gamestore.model.Tshirt;
import com.company.gamestore.repository.TshirtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TshirtController {

    @Autowired
    TshirtRepository tshirtRepository;

    @GetMapping("/tshirts")
    @ResponseStatus(HttpStatus.OK)
    public List<Tshirt> getAllTshirts() {
        return tshirtRepository.findAll();
    }

    @GetMapping("/tshirts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tshirt getTshirtById(@PathVariable Integer id){
        Optional<Tshirt> returnVal = tshirtRepository.findById(id);
        return returnVal.isPresent() ? returnVal.get() : null;
    }

    @GetMapping("/tshirts/color/{color}")
    @ResponseStatus(HttpStatus.OK)
    public List<Tshirt> getTshirtsByColor(@PathVariable String color){
        return tshirtRepository.findTshirtByColor(color);
    }

    @GetMapping("/tshirts/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<Tshirt> getTshirtsBySize(@PathVariable String size){
        return tshirtRepository.findTshirtBySize(size);
    }

    @PostMapping("/tshirt")
    @ResponseStatus(HttpStatus.CREATED)
    public Tshirt addTshirt(@RequestBody Tshirt tshirt){
        return tshirtRepository.save(tshirt);
    }

    @PutMapping("/tshirt")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTshirt(@RequestBody Tshirt tshirt){
        tshirtRepository.save(tshirt);
    }

    @DeleteMapping("/tshirt/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTshirtById(@PathVariable Integer id){
        if (tshirtRepository.findById(id).isEmpty()) {}

        else if (tshirtRepository.existsById(id)){
            tshirtRepository.deleteById(id);
        }
    }
}
