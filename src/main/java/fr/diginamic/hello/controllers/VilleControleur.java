package fr.diginamic.hello.controllers;

import fr.diginamic.hello.model.Ville;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class VilleControleur {
    @GetMapping("/villes")
    public List<Ville> getVilles() {
        return Arrays.asList(
                new Ville("Paris", 2148000),
                new Ville("Marseille", 861635),
                new Ville("Lyon", 513275)
        );
    }
}
