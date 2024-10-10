package fr.diginamic.hello.DAO;


import fr.diginamic.hello.model.City;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public City create(City city) {
        this.em.persist(city);
        return city;
    }

    public List<City> findAll() {
        return this.em.createQuery("from City").getResultList();
    }

    public Optional<City> findById(int id) {
        City city = this.em.find(City.class, id);
        return Optional.ofNullable(city);
    }

    public City findByName(String name) {
        return this.em.createQuery("Select c from City c WHERE c.name = :name", City.class).setParameter("name", name).getSingleResult();
    }

    @Transactional
    public City update(City city) {
        return this.em.merge(city);
    }

    @Transactional
    public void delete(City city) {
        this.em.remove(city);
    }


}