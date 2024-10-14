package fr.diginamic.hello.controllers;

import fr.diginamic.hello.model.City;
import fr.diginamic.hello.services.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/departement")
public class DepartementController {

    @Autowired
    private DepartementService departementService;

    @Operation(summary = "Get Largest Cities in a Department",
            description = "Retrieve a paginated list of the largest cities in a department by population.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of largest cities returned successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = City.class))),
            @ApiResponse(responseCode = "404",
                    description = "Department not found",
                    content = @Content)
    })
    @GetMapping("/{id}/largest-cities")
    public ResponseEntity<Page<City>> getLargestCities(
            @Parameter(description = "ID of the department", required = true)
            @PathVariable Long id,
            @Parameter(description = "Page number for pagination", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size for pagination", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<City> cities = departementService.getLargestCities(id, pageable);
        return ResponseEntity.ok(cities);
    }
}
