package ru.byk0v.expert_system.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.byk0v.expert_system.calculations.NeuralNetworkService;
import ru.byk0v.expert_system.models.CalculateRequest;
import ru.byk0v.expert_system.models.CalculateResponce;

@RequestMapping("/neural-network")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
public class NeuralNetworkController {

    private NeuralNetworkService neuralNetworkService;

    @GetMapping()
    public void neuralCalculation() {

    }

    @PostMapping("/calculate")
    public CalculateResponce calculate(@RequestBody CalculateRequest request) {
        return neuralNetworkService.calculate(request);
    }
}
