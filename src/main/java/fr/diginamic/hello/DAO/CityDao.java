package fr.diginamic.hello.DAO;

import fr.diginamic.hello.model.City;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for managing City entities.
 */
@Repository
public class CityDao {

    @PersistenceContext
    private EntityManager em;

    /**
     * Persists a new City entity.
     *
     * @param city the City entity to persist
     * @return the persisted City entity
     */
    @Transactional
    public City create(City city) {
        this.em.persist(city);
        return city;
    }

    /**
     * Retrieves all City entities.
     *
     * @return a List of City entities
     */
    public List<City> findAll() {
        return this.em.createQuery("from City", City.class).getResultList();
    }

    /**
     * Finds a City entity by its ID.
     *
     * @param id the ID of the City
     * @return an Optional containing the City entity if found, or empty if not
     */
    public Optional<City> findById(int id) {
        City city = this.em.find(City.class, id);
        return Optional.ofNullable(city);
    }

    /**
     * Finds a City entity by its name.
     *
     * @param name the name of the City
     * @return the City entity if found
     * @throws if no City is found with the given name
     */
    public City findByName(String name) {
        return this.em.createQuery("SELECT c FROM City c WHERE c.name = :name", City.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    /**
     * Updates an existing City entity.
     *
     * @param city the City entity to update
     * @return the updated City entity
     */
    @Transactional
    public City update(City city) {
        return this.em.merge(city);
    }

    /**
     * Deletes a City entity.
     *
     * @param city the City entity to delete
     */
    @Transactional
    public void delete(City city) {
        if (this.em.contains(city)) {
            this.em.remove(city);
        } else {
            City managedCity = this.em.find(City.class, city.getId());
            if (managedCity != null) {
                this.em.remove(managedCity);
            }
        }
    }
}
