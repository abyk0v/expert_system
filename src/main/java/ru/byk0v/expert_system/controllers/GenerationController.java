package ru.byk0v.expert_system.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.byk0v.expert_system.GenerationService;
import ru.byk0v.expert_system.models.Responce;

@RestController
@RequestMapping("/generating")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class GenerationController {

    private GenerationService generationService;

    @GetMapping("/patientsForAllDiagnoses")
    public Responce generatingPatients(@RequestParam("countForDiagnosis") Integer count) {
        try {
            generationService.patientsForAllDiagnoses(count);
            return new Responce("SUCCESS");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Responce("FAIL");
        }
    }
}
