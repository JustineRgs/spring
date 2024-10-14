package fr.diginamic.hello.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a City.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "City name cannot be null")
    private String name;

    @NotNull(message = "Number of inhabitants cannot be null")
    private long nbInhabitants;

    @JsonIgnore
    private Double idRegion;

    @ManyToOne
    @JoinColumn(name = "departement_id", nullable = false)
    private Departement departement;

    public City(String name, long nbInhabitants, Double idRegion, Departement departement) {
        this.name = name;
        this.nbInhabitants = nbInhabitants;
        this.idRegion = idRegion;
        this.departement = departement;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nbInhabitants=" + nbInhabitants +
                ", idRegion=" + idRegion +
                ", departement=" + departement.getCode() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return id == city.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
