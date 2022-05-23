package ru.byk0v.expert_system.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.byk0v.expert_system.calculations.NeuralNetworkService;

@RequestMapping("/neural-network")
@RestController
@AllArgsConstructor
public class NeuralNetworkController {

    private NeuralNetworkService neuralNetworkService;

    @GetMapping()
    public void neuralCalculation() {

    }
}
