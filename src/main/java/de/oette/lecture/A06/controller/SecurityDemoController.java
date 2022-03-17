package de.oette.lecture.A06.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/secure-demo")
public class SecurityDemoController {

    @GetMapping(value = "/public")  //öffentlicher Zugang, der nicht geprüft wird
    public String publicDemo() {
        return "public access";
    }

    @GetMapping(value = "/private")    //ein Zugang mit Password --> siehe Class CustomWebSecurityConfi...
    public String privateDemo() {
        return "private demo";
    }

}
