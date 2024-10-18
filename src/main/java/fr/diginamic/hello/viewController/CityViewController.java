package fr.diginamic.hello.viewController;

import fr.diginamic.hello.model.City;
import fr.diginamic.hello.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//http://localhost:8080/city
@Controller
public class CityViewController {
    @Autowired
    private CityService cityService;

    @GetMapping("/city")
    public String getIndex(Model model) {
        model.addAttribute("title", "Cities");
        model.addAttribute("cities", cityService.findAll());
        return "city";
    }

    @GetMapping("/city/{id}")
    public String getCity(
            Model model,
            @PathVariable long id) {
        City city = cityService.findById(id);
        model.addAttribute("title", city.getName());
        model.addAttribute("city", city);
        return "city-info";
    }

    @GetMapping("/city/delete/{id}")
    public String delete(
            Model model,
            @PathVariable long id) {
        City city = cityService.findById(id);
        this.cityService.delete(city);
        return "redirect:/city";
    }
}
