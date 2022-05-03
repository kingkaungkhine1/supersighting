/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.dependencybanana.supersighting.dao;

import com.dependencybanana.supersighting.models.Ability;
import com.dependencybanana.supersighting.models.Location;
import com.dependencybanana.supersighting.models.Organization;
import com.dependencybanana.supersighting.models.Sighting;
import com.dependencybanana.supersighting.models.Super;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author kaung
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class SuperRepoTest
{

    @Autowired
    SuperRepo supRepo;

    @Autowired
    AbilityRepo abRepo;

    @Autowired
    OrganizationRepo orgRepo;

    @Autowired
    SightingRepo sightRepo;

    @Autowired
    LocationRepo locRepo;

    public SuperRepoTest()
    {
    }

    @BeforeAll
    public static void setUpClass()
    {
    }

    @AfterAll
    public static void tearDownClass()
    {
    }

    @BeforeEach
    @Sql("classpath:setup_test_db.sql")
    public void setUp()
    {
    }

    @AfterEach
    public void tearDown()
    {
    }

    //ManyToMany testing
    @Test
    public void testAbInSuper()
    {
        Super sup = new Super();
        sup.setAlignment("Hero");
        sup.setDescription("Test Super Desc");
        sup.setName("Test Sup Name");
        sup.setSuperId(1);

        List<Ability> tempAb = abRepo.findAll();
        tempAb.clear();

        Ability ab = new Ability();
        ab.setAbilityId(1);
        ab.setName("Test Ability Name");
        ab.setDescription("Test Ability Description");

        ab = abRepo.save(ab);

        tempAb.add(ab);

        sup.setAbilities(tempAb);

        sup = supRepo.save(sup);

        Super fromRepo = supRepo.getById(sup.getSuperId());

        assertEquals(sup, fromRepo);

        Ability abFromSupRepo = supRepo.getById(sup.getSuperId()).getAbilities().get(0);

        assertEquals(ab, abFromSupRepo);

        Ability abFromAbRepo = abRepo.getById(ab.getAbilityId());

        assertEquals(ab, abFromAbRepo);
    }

    @Test
    public void testOrgInSuper()
    {
        Super sup = new Super();
        sup.setAlignment("Hero");
        sup.setDescription("Test Super Desc");
        sup.setName("Test Sup Name");
        sup.setSuperId(1);

        List<Organization> tempOrg = orgRepo.findAll();
        tempOrg.clear();

        Organization org = new Organization();
        org.setOrganizationId(1);
        org.setAlignment("Test Hero");
        org.setDescription("Test Desc");
        org.setName("Test Name");
        org.setLocation(null);

        org = orgRepo.save(org);

        tempOrg.add(org);

        sup.setOrganizations(tempOrg);

        sup = supRepo.save(sup);
        Super fromRepo = supRepo.getById(sup.getSuperId());

        assertEquals(sup, fromRepo);

        Organization orgFromSupRepo = supRepo.getById(sup.getSuperId()).getOrganizations().get(0);

        assertEquals(org, orgFromSupRepo);

        Organization orgFromOrgRepo = orgRepo.getById(org.getOrganizationId());
        assertEquals(org, orgFromOrgRepo);
    }

    @Test
    public void testSightingInSuper()
    {
        Super sup = new Super();
        sup.setAlignment("Hero");
        sup.setDescription("Test Super Desc");
        sup.setName("Test Sup Name");
        sup.setSuperId(1);

        List<Sighting> tempSight = sightRepo.findAll();
        tempSight.clear();

        // Sighting s = new Sighting();
        // s.setDescription("Sight Desc Test");
        Location loc = new Location();
        loc.setName("New Location");
        loc.setAddress("A new address");
        loc.setDescription("test description");
        loc.setLatitude(BigDecimal.ONE);
        loc.setLongitude(BigDecimal.ONE);
        loc = locRepo.save(loc);

        LocalDate date = LocalDate.now();

        Sighting s = new Sighting();
        s.setDate(date);
        s.setDescription("Sight Desc Test");
        s.setLocation(loc);

        s = sightRepo.save(s);

        tempSight.add(s);

        sup.setSightings(tempSight);

        sup = supRepo.save(sup);
        Super fromRepo = supRepo.getById(sup.getSuperId());

        assertEquals(sup, fromRepo);

        Sighting sightFromSupRepo = supRepo.getById(sup.getSuperId()).getSightings().get(0);

        assertEquals(s, sightFromSupRepo);

        Sighting sightFromSightRepo = sightRepo.getById(s.getSightingId());

        assertEquals(s, sightFromSightRepo);

    }

    //reports back all members of an organzation
    @Test
    public void testFindSupers()
    {
        Super sup = new Super();
        sup.setAlignment("Hero");
        sup.setDescription("Test Super Desc");
        sup.setName("Test Sup Name");
        sup = supRepo.save(sup);

        Location loc = new Location();
        loc.setName("Boston");
        loc.setAddress("A new address");
        loc.setDescription("Temp");
        loc.setLatitude(BigDecimal.ONE);
        loc.setLongitude(BigDecimal.ONE);
        loc = locRepo.save(loc);

        Organization org = new Organization();
        org.setAlignment("Test Hero");
        org.setDescription("Test Desc");
        org.setName("Test Name");
        org.setLocation(loc);
        org.setSupers(Arrays.asList(sup));
        org = orgRepo.save(org);

        List<Super> temp = supRepo.findByOrganizations(org);

        assertTrue(temp.contains(sup));
    }

    //reports all heroes at a location
    @Test
    public void testFindSupersBySightingsLocation() {
        Location loc = new Location();
        loc.setName("Boston");
        loc.setAddress("A new address");
        loc.setLatitude(BigDecimal.ONE);
        loc.setLongitude(BigDecimal.ONE);
        loc.setDescription("Temp");
        loc = locRepo.save(loc);

        Super sup = new Super();
        sup.setAlignment("Hero");
        sup.setDescription("Test Sup Desc");
        sup.setName("Test Sup Name");
        sup = supRepo.save(sup);

        Super sup2 = new Super();
        sup2.setAlignment("Villian");
        sup2.setDescription("Test Sup2 Desc");
        sup2.setName("Test Sup2 Name");
        sup2 = supRepo.save(sup2);


        Sighting s = new Sighting();
        s.setDate(LocalDate.now());
        s.setDescription("desc ");
        s.setLocation(loc);
        s.setSupers(Arrays.asList(sup));
        sightRepo.save(s);

        Sighting s2 = new Sighting();
        s2.setDate(LocalDate.now());
        s2.setDescription("desc ");
        s2.setLocation(loc);
        s2.setSupers(Arrays.asList(sup2));
        sightRepo.save(s2);

        List<Super> supersByLoc = supRepo.findBySightingsLocation(loc);

        Assertions.assertNotNull(supersByLoc);
        Assertions.assertEquals(2, supersByLoc.size());
        Assertions.assertTrue(supersByLoc.contains(sup));
        Assertions.assertTrue(supersByLoc.contains(sup2));
    }
}
