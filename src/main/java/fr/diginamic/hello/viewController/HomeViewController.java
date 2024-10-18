package fr.diginamic.hello.viewController;

import fr.diginamic.hello.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {
    @Autowired
    private CityService cityService;

    @GetMapping
    public String getIndex(Model model) {
        model.addAttribute("title", "Home");
        return "index";
    }
}
