package fr.diginamic.hello.controllers;

import fr.diginamic.hello.exceptions.CityNotFoundException;
import fr.diginamic.hello.model.City;
import fr.diginamic.hello.services.CityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public Page<City> getCities(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return this.cityService.findAllPageable(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCity(@PathVariable int id) {
        try {
            City city = cityService.findById(id);
            return new ResponseEntity<>(city, HttpStatus.OK);
        } catch (CityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByName")
    public ResponseEntity<?> findByName(@RequestParam String name) {
        City existingCity = this.cityService.findByName(name);
        return new ResponseEntity<City>(existingCity, HttpStatus.OK);
    }

    @PostMapping
    public City create(@Valid @RequestBody City city) {
        return this.cityService.create(city);
    }

    @PutMapping
    public City update(
            @Valid @RequestBody City city) {
        return this.cityService.update(city);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        City existingCity = this.cityService.findById(id);
        if (existingCity == null) {
            return ResponseEntity.badRequest().body("Ressource not found");
        }
        return this.cityService.delete(existingCity);
    }

    @GetMapping("/findby-name-starting-with")
    public List<City> findByNameStartingWith(@RequestParam String prefix) {
        return this.cityService.findByNameStartingWith(prefix);
    }

    @GetMapping("/findby-nb-inhabitants-after")
    public List<City> findByNbInhabitantsAfter(@RequestParam long min) {
        return this.cityService.findByNbInhabitantsAfter(min);
    }

    @GetMapping("/findby-nb-inhabitants-between")
    public List<City> findByNbInhabitantsBetween(
            @RequestParam long min,
            @RequestParam long max
    ) {
        return this.cityService.findByNbInhabitantsBetween(min, max);
    }

    @GetMapping("/findby-departement_id-and-nb-inhabitants-after")
    public List<City> findByDepartement_IdAndNbInhabitantsAfter(
            @RequestParam long idDept,
            @RequestParam long min
    ) {
        return this.cityService.findByDepartement_IdAndNbInhabitantsAfter(idDept, min);
    }

    @GetMapping("/findby-departement_id-and-nb-inhabitants-between")
    public List<City> findByDepartement_IdAndNbInhabitantsBetween(
            @RequestParam long idDept,
            @RequestParam long min,
            @RequestParam long max
    ) {
        return this.cityService.findByDepartement_IdAndNbInhabitantsBetween(idDept, min, max);
    }

    @GetMapping("/findby-departement_id-orderby-nbinhabitants-desc")
    public Page<City> findByDepartement_IdOrderByNbInhabitantsDesc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam long idDept
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return this.cityService.findByDepartement_IdOrderByNbInhabitantsDesc(pageable, idDept);
    }
}