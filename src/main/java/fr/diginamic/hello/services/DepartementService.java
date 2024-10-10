package fr.diginamic.hello.services;

import fr.diginamic.hello.DAO.DepartementDao;
import fr.diginamic.hello.model.City;
import fr.diginamic.hello.model.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {

    @Autowired
    private DepartementDao departementDao;

    public List<Departement> getAll() {
        return this.departementDao.findAll();
    }

    public Departement findById(int id) {
        return this.departementDao.findById(id);
    }

    public ResponseEntity<?> delete(Departement dep) {
        this.departementDao.delete(dep);
        return ResponseEntity.ok().build();
    }

    public Departement update(Departement dep) {
        return this.departementDao.update(dep);
    }

    public Departement create(Departement dep) {
        return this.departementDao.create(dep);
    }

    public List<City> getLargestCities(int id, int limit){
        return this.departementDao.getLargestCities(id, limit);
    }

    public List<City> getCitiesPopulationBetweenLimit(int id, int min, int max){
        return this.departementDao.getCitiesPopulationBetweenLimit(id, min, max);
    }

}