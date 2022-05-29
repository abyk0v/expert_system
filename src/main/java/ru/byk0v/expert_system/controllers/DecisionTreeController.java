package ru.byk0v.expert_system.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.byk0v.expert_system.calculations.DecisionTreeServices;
import ru.byk0v.expert_system.models.CalculateRequest;
import ru.byk0v.expert_system.models.CalculateResponce;

@RequestMapping("/decision-tree")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
public class DecisionTreeController {

    private DecisionTreeServices decisionTreeServices;

    @PostMapping("/calculate")
    public CalculateResponce calculate(@RequestBody CalculateRequest request) {
        return decisionTreeServices.calculate(request);
    }
}
