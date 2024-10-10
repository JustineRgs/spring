package fr.diginamic.hello.controllers;

import fr.diginamic.hello.model.City;
import fr.diginamic.hello.model.Departement;
import fr.diginamic.hello.services.DepartementService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departement")
public class DepartementController {

    @Autowired
    private DepartementService departementService;

    @GetMapping
    public List<Departement> getDepartements(){
        return this.departementService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartement(@PathVariable int id) {
        Departement existingCity = this.departementService.findById(id);
        if (existingCity == null) {
            return ResponseEntity.badRequest().body("Ressource not found");
        }
        return new ResponseEntity<Departement>(existingCity, HttpStatus.OK);
    }

    @PostMapping
    public Departement create(@Valid @RequestBody Departement city) {
        return this.departementService.create(city);
    }

    @PutMapping
    public Departement update(
            @Valid @RequestBody Departement city) {
        return this.departementService.update(city);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Departement existingCity = this.departementService.findById(id);
        if (existingCity == null) {
            return ResponseEntity.badRequest().body("Ressource not found");
        }
        return this.departementService.delete(existingCity);
    }

    @GetMapping("/{id}/largest-cities")
    public List<City> getLargestCities(
            @PathVariable int id,
            @NotNull @RequestParam int limit){
        return this.departementService.getLargestCities(id, limit);
    }

    @GetMapping("/{id}/population-between-limit")
    public List<City> getCitiesPopulationBetweenLimit(
            @PathVariable int id,
            @NotNull @RequestParam int min,
            @NotNull @RequestParam int max){
        return this.departementService.getCitiesPopulationBetweenLimit(id, min, max);
    }
}