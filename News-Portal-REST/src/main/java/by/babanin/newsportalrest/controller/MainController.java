package by.babanin.newsportalrest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {
    @GetMapping("/api/foos")
    public Principal foos(Principal user) {
        return user;
    }

    @GetMapping("/api/admin")
    public String admin() {
        return "Hello Administrator";
    }
}
