package com.tlu.khoacntt.controller;

import com.tlu.khoacntt.dto.response.TrainingProgramResponse;
import com.tlu.khoacntt.service.TrainingProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/training-programs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class TrainingProgramPublicController {

    private final TrainingProgramService trainingProgramService;
    @GetMapping
    public ResponseEntity<List<TrainingProgramResponse>> list() {
        return ResponseEntity.ok(trainingProgramService.getAllForPublic());
    }
    @GetMapping("/{id}")
    public ResponseEntity<TrainingProgramResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(trainingProgramService.getById(id));
    }
}
