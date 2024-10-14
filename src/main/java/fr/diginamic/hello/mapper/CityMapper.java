package fr.diginamic.hello.mapper;

import fr.diginamic.hello.DTO.CityDto;
import fr.diginamic.hello.model.City;
import fr.diginamic.hello.services.DepartementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between City and CityDto objects.
 */
@Component
public class CityMapper {

    @Autowired
    private DepartementService departementService;

    /**
     * Converts a City entity to a CityDto.
     *
     * @param city the City entity to convert
     * @return the converted CityDto
     */
    public CityDto toDto(City city) {
        CityDto dto = new CityDto();
        dto.setNbInhabitants(city.getNbInhabitants());
        dto.setDeptCode(city.getDepartement() != null ? city.getDepartement().getCode() : null);
        dto.setDeptName(city.getDepartement() != null ? city.getDepartement().getName() : null);
        dto.setCityCode(city.getName());
        return dto;
    }

    /**
     * Converts a CityDto to a City entity.
     *
     * @param dto the CityDto to convert
     * @return the converted City entity
     */
    public City toEntity(CityDto dto) {
        City city = new City();
        city.setNbInhabitants(dto.getNbInhabitants());
        city.setDepartement(departementService.findByCode(dto.getDeptCode()));
        return city;
    }
}
