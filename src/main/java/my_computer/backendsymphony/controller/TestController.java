package my_computer.backendsymphony.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/hello") // Một URL cực kỳ đơn giản
    public String sayHello() {
        return "HELLO WORLD! The MAPPING IS WORKING!";
    }

}
