package fr.diginamic.hello.controllers;

import fr.diginamic.hello.model.Ville;
import fr.diginamic.hello.services.VilleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
public class VilleControleur {

    private final VilleService villeService;

    @Autowired
    public VilleControleur(VilleService villeService) {
        this.villeService = villeService;
    }

    @GetMapping("/villes")
    public List<Ville> getVilles() {
        return villeService.getVilles();
    }

    @GetMapping("/villes/{id}")
    public ResponseEntity<Ville> getVilleById(@PathVariable int id) {
        Ville ville = villeService.getVilleById(id);
        if (ville != null) {
            return new ResponseEntity<>(ville, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/villes")
    public ResponseEntity<String> ajouterVille(@RequestBody Ville nouvelleVille) {
        String message = villeService.ajouterVille(nouvelleVille);
        if (message.equals("Ville insérée avec succès")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/villes/{id}")
    public ResponseEntity<String> updateVille(@PathVariable int id, @RequestBody Ville updatedVille) {
        String message = villeService.updateVille(id, updatedVille);
        if (message.equals("Ville mise à jour avec succès")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/villes/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {
        String message = villeService.deleteVille(id);
        if (message.equals("Ville supprimée avec succès")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
