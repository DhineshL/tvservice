package com.dhinesh.tvservice.controller;

import com.dhinesh.tvservice.model.UserModel;
import com.dhinesh.tvservice.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/")
public class RegistrationController {

    private ApplicationService applicationService;

    @Autowired
    public RegistrationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserModel userModel) {

        applicationService.createUser(userModel);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value="shuffle")
    public int [] shuffle(@RequestParam int [] arr){
        for (int i = 0; i < arr.length; i++) {
            Random random = new Random();
            int next = random.nextInt(arr.length-1);
            int temp = arr[next];
            arr[next] = arr[i];
            arr[i] = temp;
        }

        return arr;

    }

}
