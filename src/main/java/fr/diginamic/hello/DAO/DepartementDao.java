package fr.diginamic.hello.DAO;

import fr.diginamic.hello.model.City;
import fr.diginamic.hello.model.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Departement create(Departement dep) {
        this.em.persist(dep);
        return dep;
    }

    public List<Departement> findAll() {
        return this.em.createQuery("from Departement").getResultList();
    }

    public Departement findById(int id){
        return this.em.find(Departement.class, id);
    }

    @Transactional
    public Departement update(Departement dep) {
        return this.em.merge(dep);
    }

    @Transactional
    public void delete(Departement dep) {
        this.em.remove(dep);
    }

    public List<City> getLargestCities(int id, int limit){
        return this.em.createQuery(
                        "Select c " +
                                "from City c " +
                                "WHERE c.departement.id = :id " +
                                "ORDER BY c.nbInhabitants DESC"
                        , City.class)
                .setParameter("id", id)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<City> getCitiesPopulationBetweenLimit(int id, int min, int max){
        return this.em.createQuery(
                        "Select c " +
                                "from City c " +
                                "WHERE c.departement.id = :id " +
                                "AND c.nbInhabitants BETWEEN :min and :max " +
                                "ORDER BY c.nbInhabitants DESC"
                        , City.class)
                .setParameter("id", id)
                .setParameter("min", min)
                .setParameter("max", max)
                .getResultList();
    }

}