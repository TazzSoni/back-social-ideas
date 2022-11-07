package com.backsocialideas.controller;

import com.backsocialideas.controller.handler.Handler;
import com.backsocialideas.dto.AskForCooworkDTO;
import com.backsocialideas.dto.UserOutDTO;
import com.backsocialideas.model.AsksForCooworker;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/social-ideas")
@RequiredArgsConstructor
@Api(value = "API REST")
@CrossOrigin(origins = "*")
public class AskCooworkerController {

    private final Handler handler;

    @GetMapping("/getall-asks-cooworkker")
    public ResponseEntity<List<AskForCooworkDTO>> getAll() {
        return new ResponseEntity<>(handler.getAllAskCoorworker(), HttpStatus.OK);
    }

    @DeleteMapping("/delete-ask-cooworker/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id) {
        handler.deleteAksCooworker(id);
    }
}
