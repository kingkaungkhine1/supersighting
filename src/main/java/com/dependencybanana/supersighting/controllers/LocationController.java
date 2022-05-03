package com.dependencybanana.supersighting.controllers;

import com.dependencybanana.supersighting.dao.LocationRepo;
import com.dependencybanana.supersighting.models.Location;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LocationController {

    @Autowired
    LocationRepo locationRepo;

    @GetMapping("/locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationRepo.findAll();
        model.addAttribute("locations", locations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String rawLongitude = request.getParameter("longitude");
        BigDecimal longitude = null;
        if(rawLongitude != null) {
            longitude = new BigDecimal(rawLongitude);
        }
        String rawLatitude = request.getParameter("latitude");
        BigDecimal latitude = null;
        if(rawLatitude != null) {
            latitude = new BigDecimal(rawLatitude);
        }

        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        location.setAddress(address);
        location.setLongitude(longitude);
        location.setLatitude(latitude);

        locationRepo.save(location);

        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationRepo.deleteById(id);
        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationRepo.getById(id);
        model.addAttribute("location", location);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String editLocation(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String rawLongitude = request.getParameter("longitude");
        BigDecimal longitude = null;
        if(rawLongitude != null) {
            longitude = new BigDecimal(rawLongitude);
        }
        String rawLatitude = request.getParameter("latitude");
        BigDecimal latitude = null;
        if(rawLatitude != null) {
            latitude = new BigDecimal(rawLatitude);
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationRepo.getById(id);
        location.setName(name);
        location.setDescription(description);
        location.setAddress(address);
        location.setLongitude(longitude);
        location.setLatitude(latitude);

        locationRepo.save(location);

        return "redirect:/locations";
    }

}
