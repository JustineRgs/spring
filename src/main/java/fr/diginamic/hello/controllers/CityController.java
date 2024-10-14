package fr.diginamic.hello.controllers;

import fr.diginamic.hello.exceptions.CityNotFoundException;
import fr.diginamic.hello.exceptions.FunctionalException;
import fr.diginamic.hello.mapper.CityMapper;
import fr.diginamic.hello.model.City;
import fr.diginamic.hello.DTO.CityDto;
import fr.diginamic.hello.services.CityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    CityMapper cityMapper;

    @Operation(summary = "Get paginated list of cities", description = "Retrieve a list of cities with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of cities returned", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied")
    })
    @GetMapping
    public Page<CityDto> getCities(
            @Parameter(description = "Page number", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.cityService.findAllPageable(pageable);
    }

    @Operation(summary = "Get city by ID", description = "Retrieve a city by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "City found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "City not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCity(
            @Parameter(description = "ID of the city to retrieve", required = true) @PathVariable int id) {
        try {
            City city = cityService.findById(id);
            return new ResponseEntity<>(this.cityMapper.toDto(city), HttpStatus.OK);
        } catch (CityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Find city by name", description = "Retrieve a city by its name")
    @GetMapping("/findByName")
    public ResponseEntity<CityDto> findByName(@RequestParam String name) {
        City city = this.cityService.findByName(name);
        return new ResponseEntity<>(this.cityMapper.toDto(city), HttpStatus.OK);
    }

    @Operation(summary = "Create a new city", description = "Add a new city to the database")
    @PostMapping
    public City create(
            @Parameter(description = "City object to be created", required = true) @Valid @RequestBody City city) throws FunctionalException {
        return this.cityService.create(city);
    }

    @Operation(summary = "Update an existing city", description = "Update an existing city in the database")
    @PutMapping
    public City update(
            @Parameter(description = "City object with updated information", required = true) @Valid @RequestBody City city) throws FunctionalException {
        return this.cityService.update(city);
    }

    @Operation(summary = "Delete a city by ID", description = "Delete an existing city by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        City existingCity = this.cityService.findById(id);
        if (existingCity == null) {
            return ResponseEntity.badRequest().body("Resource not found");
        }
        return this.cityService.delete(existingCity);
    }

    @Operation(summary = "Find cities by name prefix", description = "Retrieve cities whose names start with a given prefix")
    @GetMapping("/findby-name-starting-with")
    public List<CityDto> findByNameStartingWith(@RequestParam String prefix) throws FunctionalException {
        List<City> cities = this.cityService.findByNameStartingWith(prefix);
        return cities.stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Find cities by minimum inhabitants", description = "Retrieve cities with a population greater than the specified number")
    @GetMapping("/findby-nb-inhabitants-after")
    public List<CityDto> findByNbInhabitantsAfter(@RequestParam long min) throws FunctionalException {
        List<City> cities = this.cityService.findByNbInhabitantsAfter(min);
        return cities.stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Find cities by population range", description = "Retrieve cities with population between the specified minimum and maximum")
    @GetMapping("/findby-nb-inhabitants-between")
    public List<CityDto> findByNbInhabitantsBetween(
            @RequestParam long min,
            @RequestParam long max
    ) throws FunctionalException {
        List<City> cities = this.cityService.findByNbInhabitantsBetween(min, max);
        return cities.stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Find cities by department and population after a minimum", description = "Retrieve cities in a department with a population greater than the specified number")
    @GetMapping("/findby-departement_id-and-nb-inhabitants-after")
    public List<CityDto> findByDepartement_IdAndNbInhabitantsAfter(
            @RequestParam String codeDept,
            @RequestParam long min
    ) throws FunctionalException {
        List<City> cities = this.cityService.findByDepartement_IdAndNbInhabitantsAfter(codeDept, min);
        return cities.stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Find cities by department and population range", description = "Retrieve cities in a department with population between the specified minimum and maximum")
    @GetMapping("/findby-departement-and-nbinhabitants-between")
    public List<CityDto> findByDepartement_IdAndNbInhabitantsBetween(
            @RequestParam String codeDept,
            @RequestParam long min,
            @RequestParam long max
    ) throws FunctionalException {
        List<City> cities = this.cityService.findByDepartementAndNbInhabitantsBetween(codeDept, min, max);
        return cities.stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Find largest cities in a department", description = "Retrieve the largest cities in a department with pagination")
    @GetMapping("/find-departement-biggest-cities")
    public Page<CityDto> findDepartementBiggestCities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String codeDept
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return this.cityService.findDepartementBiggestCities(pageable, codeDept);
    }
}
