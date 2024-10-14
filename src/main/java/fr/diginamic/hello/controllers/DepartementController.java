package fr.diginamic.hello.controllers;

import fr.diginamic.hello.model.City;
import fr.diginamic.hello.services.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departement")
public class DepartementController {

    @Autowired
    private DepartementService departementService;

    @GetMapping("/{id}/largest-cities")
    public ResponseEntity<Page<City>> getLargestCities(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<City> cities = departementService.getLargestCities(id, pageable);
        return ResponseEntity.ok(cities);
    }
}
