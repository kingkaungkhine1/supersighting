package com.dependencybanana.supersighting.controllers;

import com.dependencybanana.supersighting.dao.LocationRepo;
import com.dependencybanana.supersighting.dao.OrganizationRepo;
import com.dependencybanana.supersighting.dao.SuperRepo;
import com.dependencybanana.supersighting.models.Location;
import com.dependencybanana.supersighting.models.Organization;
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
public class OrganizationController
{

    @Autowired
    OrganizationRepo orgRepo;

    @Autowired
    LocationRepo locationRepo;

    @Autowired
    SuperRepo superRepo;

    @GetMapping("/organizations")
    public String displayOrganizations(Model model)
    {
        List<Organization> organizations = orgRepo.findAll();
        model.addAttribute("organizations", organizations);

        List<Location> locations = locationRepo.findAll();
        model.addAttribute("locations", locations);

        List<Super> supers = superRepo.findAll();
        model.addAttribute("supers", supers);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(HttpServletRequest request)
    {
        String name = request.getParameter("name");
        String alignment = request.getParameter("alignment");
        String description = request.getParameter("description");

        String rawLocation = request.getParameter("locationId");
        Location location = null;
        if (rawLocation != null)
        {
            int locationId = Integer.parseInt(request.getParameter("locationId"));
            location = locationRepo.getById(locationId);
        }

        String[] superIds = request.getParameterValues("superId");
        List<Super> supers = new ArrayList<>();
        if (superIds != null)
        {
            for (String suoerId : superIds)
            {
                supers.add(superRepo.getById(Integer.valueOf(suoerId)));
            }
        }

        Organization organization = new Organization();
        organization.setName(name);
        organization.setAlignment(alignment);
        organization.setDescription(description);
        organization.setLocation(location);
        organization.setSupers(supers);

        orgRepo.save(organization);

        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request)
    {
        int id = Integer.parseInt(request.getParameter("id"));
        orgRepo.deleteById(id);
        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(HttpServletRequest request, Model model)
    {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization organization = orgRepo.getById(id);
        model.addAttribute("organization", organization);
        List<Location> locations = locationRepo.findAll();
        model.addAttribute("locations", locations);

        List<Super> supers = superRepo.findAll();
        model.addAttribute("supers", supers);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String editOrganization(HttpServletRequest request)
    {
        String name = request.getParameter("name");
        String alignment = request.getParameter("alignment");
        String description = request.getParameter("description");

        String rawLocation = request.getParameter("locationId");
        Location location = null;
        if (rawLocation != null)
        {
            int locationId = Integer.parseInt(request.getParameter("locationId"));
            location = locationRepo.getById(locationId);
        }

        String[] superIds = request.getParameterValues("superId");
        List<Super> supers = new ArrayList<>();
        if (superIds != null)
        {
            for (String suoerId : superIds)
            {
                supers.add(superRepo.getById(Integer.valueOf(suoerId)));
            }
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Organization organization = orgRepo.getById(id);
        organization.setName(name);
        organization.setAlignment(alignment);
        organization.setDescription(description);
        organization.setLocation(location);
        organization.setSupers(supers);
        
        orgRepo.save(organization);

        return "redirect:/organizations";
    }

}
