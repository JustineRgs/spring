package fr.diginamic.hello.repository;

import fr.diginamic.hello.model.City;
import fr.diginamic.hello.model.Departement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository interface for managing {@link Departement} entities.
 */
public interface DepartementRepository extends JpaRepository<Departement, Long> {

    Optional<Departement> findByCode(String code);

    /**
     * Get the largest cities in a specific Departement, ordered by population.
     *
     * @param departementId the ID of the Departement
     * @param pageable      the pagination information
     * @return a Page of the largest cities in the specified Departement
     */
    @Query("SELECT c FROM City c WHERE c.departement.id = :departementId ORDER BY c.nbInhabitants DESC")
    Page<City> findLargestCitiesInDepartment(@Param("departementId") Long departementId, Pageable pageable);
}
