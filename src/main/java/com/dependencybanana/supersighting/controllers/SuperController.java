package com.dependencybanana.supersighting.controllers;

import com.dependencybanana.supersighting.dao.AbilityRepo;
import com.dependencybanana.supersighting.dao.SuperRepo;
import com.dependencybanana.supersighting.models.Ability;
import com.dependencybanana.supersighting.models.Super;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SuperController {

    @Autowired
    SuperRepo superRepo;

    @Autowired
    AbilityRepo abilityRepo;

    @GetMapping("/supers")
    public String displaySupers(Model model) {
        List<Super> supers = superRepo.findAll();
        model.addAttribute("supers", supers);
        List<Ability> abilities = abilityRepo.findAll();
        model.addAttribute("abilities", abilities);
        return "supers";
    }

    @PostMapping("addSuper")
    public String addSuper(HttpServletRequest request) {
        String alias = request.getParameter("name");
        String alignment = request.getParameter("alignment");
        String description = request.getParameter("description");

        String[] abilityIds = request.getParameterValues("abilityId");
        List<Ability> abilities = new ArrayList<>();
        if (abilityIds != null && abilityIds.length != 0) {
            for (String abilityId: abilityIds) {
                abilities.add(abilityRepo.getById(Integer.valueOf(abilityId)));
            }
        }

        Super sup = new Super();
        sup.setName(alias);
        sup.setAlignment(alignment);
        sup.setDescription(description);
        sup.setAbilities(abilities);

        superRepo.save(sup);

        return "redirect:/supers";
    }

    @GetMapping("deleteSuper")
    public String deleteSuper(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superRepo.deleteById(id);

        return "redirect:/supers";
    }

    @GetMapping("editSuper")
    public String editSuper(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));

        Super sup = superRepo.getById(id);
        model.addAttribute("sup", sup);

        List<Ability> abilities = abilityRepo.findAll();
        model.addAttribute("abilities", abilities);
        return "editSuper";
    }

    @PostMapping("editSuper")
    public String editSuper(HttpServletRequest request) {
        String alias = request.getParameter("name");
        String alignment = request.getParameter("alignment");
        String description = request.getParameter("description");

        String[] abilityIds = request.getParameterValues("abilityId");
        List<Ability> abilities = new ArrayList<>();
        if (abilityIds != null && abilityIds.length != 0) {
            for (String abilityId: abilityIds) {
                abilities.add(abilityRepo.getById(Integer.valueOf(abilityId)));
            }
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Super sup = superRepo.getById(id);
        sup.setName(alias);
        sup.setAlignment(alignment);
        sup.setDescription(description);
        sup.setAbilities(abilities);

        superRepo.save(sup);

        return "redirect:/supers";
    }

}
