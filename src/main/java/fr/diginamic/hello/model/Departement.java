package fr.diginamic.hello.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entity class representing a Department.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Department name cannot be null")
    private String name;

    @NotNull(message = "Department code cannot be null")
    private String code;

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<City> cities;

    public Departement(String name, String code, List<City> cities) {
        this.name = name;
        this.code = code;
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "Departement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", cities=" + (cities != null ? cities.size() : 0) + " cities" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departement)) return false;
        Departement that = (Departement) o;
        return id == that.id;
    }
}
