package fr.diginamic.hello.services;

import fr.diginamic.hello.exceptions.CityNotFoundException;
import fr.diginamic.hello.model.City;
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
    private CityRepository cityRepository;

    public Page<City> findAllPageable(Pageable pageable) {
        return this.cityRepository.findAll(pageable);
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

    public City update(City city) {
        return this.cityRepository.save(city);
    }

    public City create(City city) {
        return this.cityRepository.save(city);
    }

    public List<City> findByNameStartingWith(String prefix){
        return this.cityRepository.findByNameStartingWith(prefix);
    }

    public List<City> findByNbInhabitantsAfter(long min){
        return this.cityRepository.findByNbInhabitantsAfter(min);
    }

    public List<City> findByNbInhabitantsBetween(long min, long max){
        return this.cityRepository.findByNbInhabitantsBetween(min, max);
    }

    public List<City> findByDepartement_IdAndNbInhabitantsAfter(long idDept, long min){
        return this.cityRepository.findByDepartement_IdAndNbInhabitantsAfter(idDept, min);
    }

    public List<City> findByDepartement_IdAndNbInhabitantsBetween(long dept, long min, long max){
        return this.cityRepository.findByDepartement_IdAndNbInhabitantsBetween(dept, min, max);
    }

    public Page<City> findByDepartement_IdOrderByNbInhabitantsDesc(Pageable pageable, long deptId){
        return this.cityRepository.findByDepartement_IdOrderByNbInhabitantsDesc(pageable, deptId);
    }
}