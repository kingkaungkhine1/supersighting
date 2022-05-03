package com.dependencybanana.supersighting.controllers;

import com.dependencybanana.supersighting.dao.SightingRepo;
import com.dependencybanana.supersighting.models.Sighting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private static final int MOST_RECENT_LIMIT = 10;

    @Autowired
    SightingRepo sRepo;

    @GetMapping("/")
    public String getIndexPage(Model model) {
        List<Sighting> sightings = sRepo.findMostRecentSightings(MOST_RECENT_LIMIT);
        model.addAttribute("sightings", sightings);
        return "index";
    }

}
