package com.dependencybanana.supersighting.controllers;

import com.dependencybanana.supersighting.dao.AbilityRepo;
import com.dependencybanana.supersighting.models.Ability;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AbilityController {

    @Autowired
    AbilityRepo abilityRepo;

    @GetMapping("/abilities")
    public String displayAbilities(Model model) {
        List<Ability> abilities = abilityRepo.findAll();
        model.addAttribute("abilities", abilities);
        return "abilities";
    }

    @PostMapping("addAbility")
    public String addAbility(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        Ability ability = new Ability();
        ability.setName(name);
        ability.setDescription(description);

        abilityRepo.save(ability);

        return "redirect:/abilities";
    }

    @GetMapping("deleteAbility")
    public String deleteAbility(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        abilityRepo.deleteById(id);
        return "redirect:/abilities";
    }

    @GetMapping("editAbility")
    public String editAbility(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Ability ability = abilityRepo.getById(id);
        model.addAttribute("ability", ability);
        return "editAbility";
    }

    @PostMapping("editAbility")
    public String editAbility(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int id = Integer.parseInt(request.getParameter("id"));

        Ability ability = abilityRepo.getById(id);
        ability.setName(name);
        ability.setDescription(description);

        abilityRepo.save(ability);

        return "redirect:/abilities";
    }

}
