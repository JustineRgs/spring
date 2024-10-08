package fr.diginamic.hello.controllers;

import fr.diginamic.hello.model.Ville;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VilleControleur {

    private List<Ville> villes = new ArrayList<>(List.of(
            new Ville("Paris", 2148000),
            new Ville("Marseille", 861635),
            new Ville("Lyon", 513275)
    ));

    @GetMapping("/villes")
    public List<Ville> getVilles() {
        return villes;
    }

    @PostMapping("/villes")
    public ResponseEntity<String> ajouterVille(@RequestBody Ville nouvelleVille) {
        for (Ville ville : villes) {
            if (ville.getNom().equalsIgnoreCase(nouvelleVille.getNom())) {
                return new ResponseEntity<>("La ville existe déjà", HttpStatus.BAD_REQUEST);
            }
        }
        villes.add(nouvelleVille);
        return new ResponseEntity<>("Ville insérée avec succès", HttpStatus.OK);
    }
}
