package org.springframework.samples.petclinic.web;

import org.hibernate.annotations.Check;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/health")
public class HealthCheckController {


    @GetMapping(value = "checkHealth")
    public String healthStatus(ModelMap model){
        model.addAttribute("health", Health.up().build().getStatus());
        return "health/checkHealth";
    }

    
}
