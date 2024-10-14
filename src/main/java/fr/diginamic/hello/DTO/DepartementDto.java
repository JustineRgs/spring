package fr.diginamic.hello.DTO;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for Departement entities.
 */
@Getter
@Setter
public class DepartementDto {

    /**
     * The code of the department.
     */
    private String code;

    /**
     * The name of the department.
     */
    private String name;

    /**
     * The number of inhabitants in the department.
     */
    private long nbInhabitants;
}
