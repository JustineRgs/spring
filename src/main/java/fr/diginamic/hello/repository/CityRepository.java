package fr.diginamic.hello.repository;

import fr.diginamic.hello.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByName(String name);

    List<City> findByNameStartingWith(String prefix);
    List<City> findByNbInhabitantsAfter(long min);
    List<City> findByNbInhabitantsBetween(long min, long max);
    List<City> findByDepartement_CodeAndNbInhabitantsAfter(String dept, long min);
    List<City> findByDepartement_CodeAndNbInhabitantsBetween(String dept, long min, long max);
    Page<City> findByDepartement_CodeOrderByNbInhabitantsDesc(Pageable pageable, String dept);


}