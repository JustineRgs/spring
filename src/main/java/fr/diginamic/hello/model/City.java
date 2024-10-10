package fr.diginamic.hello.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class City {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String name;

    private long nbInhabitants;

    @JsonIgnore
    private Double idRegion;

    @ManyToOne
    private Departement departement;

    public City() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNbInhabitants() {
        return nbInhabitants;
    }

    public void setNbInhabitants(long nbInhabitants) {
        this.nbInhabitants = nbInhabitants;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public double getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(double ID_REGION) {
        this.idRegion = ID_REGION;
    }
}