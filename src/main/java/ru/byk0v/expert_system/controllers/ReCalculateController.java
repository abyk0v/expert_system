package ru.byk0v.expert_system.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.byk0v.expert_system.calculations.ReCalculateService;
import ru.byk0v.expert_system.models.ReCalculateResponce;

@RestController
@RequestMapping("/recalculate")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class ReCalculateController {

    private ReCalculateService reCalculateService;

    @GetMapping("/diagnoses")
    public ReCalculateResponce reCalculate() {
        Double accuracy = reCalculateService.reCalculate();
        return new ReCalculateResponce(accuracy);
    }

    @GetMapping("/NS/diagnoses")
    public ReCalculateResponce reCalculateNS() {
        Double accuracy = reCalculateService.reCalculateNS();
        return new ReCalculateResponce(accuracy);
    }

    @GetMapping("/decision-tree/diagnoses")
    public ReCalculateResponce reCalculateDecisionTree() {
        Double accuracy = reCalculateService.reCalculateDecisionTree();
        return new ReCalculateResponce(accuracy);
    }
}
