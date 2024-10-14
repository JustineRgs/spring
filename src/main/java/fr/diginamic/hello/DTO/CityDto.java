package fr.diginamic.hello.DTO;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for City entities.
 */
@Setter
@Getter
public class CityDto {

    /**
     * The number of inhabitants in the city.
     */
    private long nbInhabitants;

    /**
     * The code of the department to which the city belongs.
     */
    private String deptCode;

    /**
     * The name of the department to which the city belongs.
     */
    private String deptName;

    /**
     * The code of the city.
     */
    private String cityCode;
}
