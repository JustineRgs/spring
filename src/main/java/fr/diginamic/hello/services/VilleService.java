package fr.diginamic.hello.services;

import fr.diginamic.hello.model.Ville;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VilleService {

    private List<Ville> villes = new ArrayList<>(List.of(
            new Ville(1, "Paris", 2148000),
            new Ville(2, "Marseille", 861635),
            new Ville(3, "Lyon", 513275)
    ));

    public List<Ville> getVilles() {
        return villes;
    }

    public Ville getVilleById(int id) {
        return villes.stream()
                .filter(ville -> ville.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public String ajouterVille(Ville nouvelleVille) {
        for (Ville ville : villes) {
            if (ville.getNom().equalsIgnoreCase(nouvelleVille.getNom()) || ville.getId() == nouvelleVille.getId()) {
                return "La ville existe déjà ou l'identifiant n'est pas unique";
            }
        }
        villes.add(nouvelleVille);
        return "Ville insérée avec succès";
    }

    public String updateVille(int id, Ville updatedVille) {
        for (int i = 0; i < villes.size(); i++) {
            Ville ville = villes.get(i);
            if (ville.getId() == id) {
                ville.setNom(updatedVille.getNom());
                ville.setNbHabitants(updatedVille.getNbHabitants());
                return "Ville mise à jour avec succès";
            }
        }
        return "Ville non trouvée";
    }

    public String deleteVille(int id) {
        if (villes.removeIf(ville -> ville.getId() == id)) {
            return "Ville supprimée avec succès";
        }
        return "Ville non trouvée";
    }
}
