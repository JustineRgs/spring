package fr.diginamic.hello.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;
import fr.diginamic.hello.model.City;
import fr.diginamic.hello.model.Departement;
import fr.diginamic.hello.services.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

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

    //    http://localhost:8080/departement/61/export-pdf
    @GetMapping("/{code}/export-pdf")
    @Operation(
            summary = "Export Department Details to PDF",
            description = "Generates a PDF file containing the department's name, code, and a list of cities with their populations.",
            parameters = {
                    @Parameter(name = "code", description = "Department code", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "PDF generated successfully",
                            content = @Content(mediaType = "application/pdf")),
                    @ApiResponse(responseCode = "404", description = "Department not found",
                            content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content)
            }
    )

    public ResponseEntity<InputStreamResource> exportDepartmentToPDF(
            @Parameter(description = "Department code", required = true) @PathVariable String code) throws DocumentException, IOException {

        Departement departement = departementService.findByCode(code);
        List<City> cities = departement.getCities();

        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);

        document.open();
        document.add(new Paragraph("Department: " + departement.getName()));
        document.add(new Paragraph("Department Code: " + departement.getCode()));
        document.add(new Paragraph("Cities:"));

        for (City city : cities) {
            document.add(new Paragraph(city.getName() + " - Population: " + city.getNbInhabitants()));
        }

        document.close();

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=department_" + code + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    //   http://localhost:8080/departement/export-departments-csv
    @Operation(summary = "Export departments to CSV",
            description = "Exports a list of all departments including their codes and names.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful export of departments to CSV"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/export-departments-csv")
    public ResponseEntity<InputStreamResource> exportDepartmentsToCSV() throws Exception {

        List<Departement> departements = departementService.getAll();

        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer);

        String[] header = {"Department Code", "Department Name"};
        csvWriter.writeNext(header);

        for (Departement departement : departements) {
            String[] deptData = {
                    departement.getCode(),
                    departement.getName()
            };
            csvWriter.writeNext(deptData);
        }

        csvWriter.close();

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(writer.toString().getBytes()));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=departments.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }
}
