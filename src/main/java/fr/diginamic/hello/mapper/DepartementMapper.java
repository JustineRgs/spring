package fr.diginamic.hello.mapper;

import fr.diginamic.hello.DTO.DepartementDto;
import fr.diginamic.hello.model.City;
import fr.diginamic.hello.model.Departement;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between Departement and DepartementDto objects.
 */
@Component
public class DepartementMapper {

    /**
     * Converts a Departement entity to a DepartementDto.
     *
     * @param departement the Departement entity to convert
     * @return the converted DepartementDto
     */
    public DepartementDto toDto(Departement departement) {
        DepartementDto departementDto = new DepartementDto();
        if (departement != null) {
            departementDto.setCode(departement.getCode());
            departementDto.setName(departement.getName());
            departementDto.setNbInhabitants(getDepartementPopulation(departement));
        }
        return departementDto;
    }

    /**
     * Converts a DepartementDto to a Departement entity.
     *
     * @param departementDto the DepartementDto to convert
     * @return the converted Departement entity
     */
    public Departement toEntity(DepartementDto departementDto) {
        Departement departement = new Departement();
        if (departementDto != null) {
            departement.setCode(departementDto.getCode());
            departement.setName(departementDto.getName());
        }
        return departement;
    }

    /**
     * Calculates the total population of a Departement based on its cities.
     *
     * @param departement the Departement whose population is to be calculated
     * @return the total population of the Departement
     */
    private long getDepartementPopulation(Departement departement) {
        return (departement != null)
                ? departement.getCities().stream().mapToLong(City::getNbInhabitants).sum()
                : 0;
    }
}
