package fr.diginamic.hello.DAO;

import fr.diginamic.hello.model.City;
import fr.diginamic.hello.model.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object for managing Departement entities.
 */
@Repository
public class DepartementDao {

    @PersistenceContext
    private EntityManager em;

    /**
     * Persists a new Departement entity.
     *
     * @param dep the Departement entity to persist
     * @return the persisted Departement entity
     */
    @Transactional
    public Departement create(Departement dep) {
        this.em.persist(dep);
        return dep;
    }

    /**
     * Retrieves all Departement entities.
     *
     * @return a List of Departement entities
     */
    public List<Departement> findAll() {
        return this.em.createQuery("FROM Departement", Departement.class).getResultList();
    }

    /**
     * Finds a Departement entity by its ID.
     *
     * @param id the ID of the Departement
     * @return the Departement entity if found, null otherwise
     */
    public Departement findById(int id) {
        return this.em.find(Departement.class, id);
    }

    /**
     * Updates an existing Departement entity.
     *
     * @param dep the Departement entity to update
     * @return the updated Departement entity
     */
    @Transactional
    public Departement update(Departement dep) {
        return this.em.merge(dep);
    }

    /**
     * Deletes a Departement entity.
     *
     * @param dep the Departement entity to delete
     */
    @Transactional
    public void delete(Departement dep) {
        if (this.em.contains(dep)) {
            this.em.remove(dep);
        } else {
            Departement foundDepartment = this.em.find(Departement.class, dep.getId());
            if (foundDepartment != null) {
                this.em.remove(foundDepartment);
            }
        }
    }

    /**
     * Finds the biggest cities in a specific Departement.
     *
     * @param id the ID of the Departement
     * @param limit the maximum number of cities to return
     * @return a List of the biggest cities
     */
    public List<City> findBiggestCities(int id, int limit) {
        return this.em.createQuery(
                        "SELECT c FROM City c WHERE c.departement.id = :id ORDER BY c.nbInhabitants DESC", City.class)
                .setParameter("id", id)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * Finds cities within a specified population range in a specific Departement.
     *
     * @param id the ID of the Departement
     * @param min the minimum population
     * @param max the maximum population
     * @return a List of cities within the specified population bounds
     */
    public List<City> findCitiesByPopulationBounds(int id, int min, int max) {
        return this.em.createQuery(
                        "SELECT c FROM City c WHERE c.departement.id = :id AND c.nbInhabitants BETWEEN :min AND :max ORDER BY c.nbInhabitants DESC", City.class)
                .setParameter("id", id)
                .setParameter("min", min)
                .setParameter("max", max)
                .getResultList();
    }
}
