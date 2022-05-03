package com.dependencybanana.supersighting.controllers;

import com.dependencybanana.supersighting.dao.LocationRepo;
import com.dependencybanana.supersighting.dao.SightingRepo;
import com.dependencybanana.supersighting.dao.SuperRepo;
import com.dependencybanana.supersighting.models.Location;
import com.dependencybanana.supersighting.models.Sighting;
import com.dependencybanana.supersighting.models.Super;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SightingController {

    @Autowired
    SightingRepo sightingRepo;

    @Autowired
    LocationRepo locationRepo;

    @Autowired
    SuperRepo superRepo;

    @GetMapping("/sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingRepo.findAll();
        model.addAttribute("sightings", sightings);

        List<Location> locations = locationRepo.findAll();
        model.addAttribute("locations", locations);

        List<Super> supers = superRepo.findAll();
        model.addAttribute("supers", supers);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        String description = request.getParameter("description");

        String rawLocation = request.getParameter("locationId");
        Location location = null;
        if (rawLocation != null) {
            int locationId = Integer.parseInt(request.getParameter("locationId"));
            location = locationRepo.getById(locationId);
        }

        String[] superIds = request.getParameterValues("superId");
        List<Super> supers = new ArrayList<>();
        if (superIds!= null) {
            for (String suoerId: superIds) {
                supers.add(superRepo.getById(Integer.valueOf(suoerId)));
            }
        }

        LocalDate date = LocalDate.parse(request.getParameter("date"));

        Sighting sighting = new Sighting();
        sighting.setDescription(description);
        sighting.setLocation(location);
        sighting.setSupers(supers);
        sighting.setDate(date);

        sightingRepo.save(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        sightingRepo.deleteById(id);
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingRepo.getById(id);
        model.addAttribute("sighting", sighting);

        List<Location> locations = locationRepo.findAll();
        model.addAttribute("locations", locations);

        List<Super> supers = superRepo.findAll();
        model.addAttribute("supers", supers);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String editSighting(HttpServletRequest request) {
        String description = request.getParameter("description");

        String rawLocation = request.getParameter("locationId");
        Location location = null;
        if (rawLocation != null) {
            int locationId = Integer.parseInt(request.getParameter("locationId"));
            location = locationRepo.getById(locationId);
        }

        String[] superIds = request.getParameterValues("superId");
        List<Super> supers = new ArrayList<>();
        if (superIds!= null) {
            for (String suoerId: superIds) {
                supers.add(superRepo.getById(Integer.valueOf(suoerId)));
            }
        }

        LocalDate date = LocalDate.parse(request.getParameter("date"));

        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingRepo.getById(id);
        sighting.setDescription(description);
        sighting.setLocation(location);
        sighting.setSupers(supers);
        sighting.setDate(date);

        sightingRepo.save(sighting);

        return "redirect:/sightings";
    }

}
