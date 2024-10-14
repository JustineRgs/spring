package fr.diginamic.hello.services;

import fr.diginamic.hello.DAO.DepartementDao;
import fr.diginamic.hello.exceptions.DepartementNotFoundException;
import fr.diginamic.hello.exceptions.FunctionalException;
import fr.diginamic.hello.model.City;
import fr.diginamic.hello.model.Departement;
import fr.diginamic.hello.repository.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing departments.
 */
@Service
public class DepartementService {

    @Autowired
    private DepartementDao departementDao;

    @Autowired
    private DepartementRepository departementRepository;

    public List<Departement> getAll() {
        return this.departementRepository.findAll();
    }

    public Departement findById(long id) {
        return this.departementRepository.findById(id)
                .orElseThrow(() -> new DepartementNotFoundException("Department with ID " + id + " not found."));
    }

    public ResponseEntity<?> delete(Departement dep) {
        this.departementRepository.delete(dep);
        return ResponseEntity.ok().build();
    }

    public Departement update(Departement dep) {
        return this.departementRepository.save(dep);
    }

    public Departement create(Departement dep) throws FunctionalException {
        if (dep.getCode().length() < 2 || dep.getCode().length() > 3) {
            throw new FunctionalException("Department code must be exactly 2 or 3 characters.");
        }
        if (dep.getName() == null || dep.getName().length() <= 2) {
            throw new FunctionalException("Department name must be longer than 2 characters.");
        }

        return this.departementRepository.save(dep);
    }

    /**
     * Retrieves the largest cities in a department with pagination.
     *
     * @param id      the ID of the department
     * @param pageable the pagination information
     * @return a Page of largest cities in the department
     */
    public Page<City> getLargestCities(long id, Pageable pageable) {
        return this.departementRepository.findLargestCitiesInDepartment(id, pageable);
    }

    public List<City> getCitiesByPopulationBounds(int id, int min, int max) {
        return this.departementDao.findCitiesByPopulationBounds(id, min, max);
    }

    public Departement findByCode(String code) {
        return this.departementRepository.findByCode(code)
                .orElseThrow(() -> new DepartementNotFoundException("Department with code " + code + " not found."));
    }

    public boolean checkCityExistence(City city, Departement dept) {
        if (dept != null) {
            return dept.getCities().stream().anyMatch(c -> c.getName().equals(city.getName()));
        }
        return false;
    }
}
