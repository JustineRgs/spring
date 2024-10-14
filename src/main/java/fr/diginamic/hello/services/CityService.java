package fr.diginamic.hello.services;

import fr.diginamic.hello.DTO.CityDto;
import fr.diginamic.hello.exceptions.CityNotFoundException;
import fr.diginamic.hello.exceptions.FunctionalException;
import fr.diginamic.hello.mapper.CityMapper;
import fr.diginamic.hello.model.City;
import fr.diginamic.hello.model.Departement;
import fr.diginamic.hello.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    CityMapper cityMapper;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private DepartementService departementService;

    public Page<CityDto> findAllPageable(Pageable pageable) {
        Page<City> cityPage = this.cityRepository.findAll(pageable);
        return cityPage.map(cityMapper::toDto);
    }

    public City findById(long id) {
        return this.cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException("City with id " + id + " not found"));
    }

    public City findByName(String name) {
        return this.cityRepository.findByName(name).orElseThrow(() -> new CityNotFoundException("City with name " + name + " not found"));
    }

    public ResponseEntity<?> delete(City city) {
        this.cityRepository.delete(city);
        return ResponseEntity.ok().build();
    }

    public City update(City city) throws FunctionalException {
        this.validateCity(city);
        return this.cityRepository.save(city);
    }

    public City create(City city) throws FunctionalException {
        Departement departement = departementService.findByCode(city.getDepartement().getCode());
        if (departement != null) {
            city.setDepartement(departement);
        }
        this.validateCity(city);
        return this.cityRepository.save(city);
    }

    private void validateCity(City city) throws FunctionalException {
        if (city.getNbInhabitants() < 10) {
            throw new FunctionalException("The number of inhabitants must exceed 10");
        }
        if (city.getName().length() < 2) {
            throw new FunctionalException("The name length must be at least 2 characters");
        }
        if (city.getDepartement().getCode().length() != 2) {
            throw new FunctionalException("The departement code length must be exactly 2 characters");
        }
        if (departementService.checkCityExistence(city, city.getDepartement())) {
            throw new FunctionalException("This city name already exists in this departement");
        }
    }

    public List<City> findByNameStartingWith(String prefix) throws FunctionalException {
        List<City> cities = this.cityRepository.findByNameStartingWith(prefix);
        if (cities.isEmpty()) {
            throw new FunctionalException("City with name " + prefix + " not found");
        }
        return cities;
    }

    public List<City> findByNbInhabitantsAfter(long min) throws FunctionalException {
        List<City> cities = this.cityRepository.findByNbInhabitantsAfter(min);
        if (cities.isEmpty()) {
            throw new FunctionalException("No cities with at least" + min + " inhabitants found");
        }
        return cities;
    }

    public List<City> findByNbInhabitantsBetween(long min, long max) throws FunctionalException {
        List<City> cities = this.cityRepository.findByNbInhabitantsBetween(min, max);
        if (cities.isEmpty()) {
            throw new FunctionalException("No city has a population between " + min + " and " + max);
        }
        return cities;
    }

    public List<City> findCitiesByDepartmentIdAndMinInhabitants(String codeDept, long min) throws FunctionalException {
        List<City> cities = this.cityRepository.findByDepartement_CodeAndNbInhabitantsAfter(codeDept, min);
        if (cities.isEmpty()) {
            throw new FunctionalException("No town has a population of over " + min + " in the " + codeDept + " département");
        }
        return cities;
    }

    public List<City> findByDepartementAndNbInhabitantsBetween(String codeDept, long min, long max) throws FunctionalException {
        List<City> cities = this.cityRepository.findByDepartement_CodeAndNbInhabitantsBetween(codeDept, min, max);
        if (cities.isEmpty()) {
            throw new FunctionalException("No town has a population between " + min + " and " + max + " in the " + codeDept + " département");
        }
        return cities;
    }

    public Page<CityDto> findDepartementBiggestCities(Pageable pageable, String deptId) {
        Page<City> cityPage = this.cityRepository.findByDepartement_CodeOrderByNbInhabitantsDesc(pageable, deptId);
        return cityPage.map(cityMapper::toDto);

    }
}