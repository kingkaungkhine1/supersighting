package com.dependencybanana.supersighting.dao;

import com.dependencybanana.supersighting.models.Location;
import com.dependencybanana.supersighting.models.Sighting;
import com.dependencybanana.supersighting.models.Super;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class LocationRepoTest {

    @Autowired
    LocationRepo locRepo;

    @Autowired
    SuperRepo supRepo;

    @Autowired
    SightingRepo  sightingRepo;

    public LocationRepoTest() {
    }

    @BeforeEach
    @Sql("classpath:setup_test_db.sql")
    public void setUp() {
    }

    @Test
    public void whenSaveLocation_thenCountIncrases() {
        Location loc = new Location();
        loc.setName("Boston");
        loc.setAddress("A new address");
        loc.setLatitude(BigDecimal.ONE);
        loc.setLongitude(BigDecimal.ONE);
        locRepo.save(loc);

        Assertions.assertEquals(1L, locRepo.count());

        Location loc2 = new Location();
        loc2.setName("Chicago");
        loc2.setAddress("A new address");
        loc2.setLatitude(BigDecimal.ONE);
        loc2.setLongitude(BigDecimal.ONE);
        locRepo.save(loc2);

        Assertions.assertEquals(2L, locRepo.count());

        Location loc3 = new Location();
        loc3.setName("Boston");
        loc3.setAddress("A new address");
        loc3.setLatitude(BigDecimal.ONE);
        loc3.setLongitude(BigDecimal.ONE);
        locRepo.save(loc3);

        Assertions.assertEquals(3L, locRepo.count());
    }

    @Test
    public void whenCreateLocation_thenCanGetLocationById() {
        Location loc = new Location();
        loc.setName("Boston");
        loc.setAddress("A new address");
        loc.setLatitude(BigDecimal.ONE);
        loc.setLongitude(BigDecimal.ONE);
        loc = locRepo.save(loc);

        Assertions.assertNotNull(loc);
        Assertions.assertNotNull(loc.getLocationId());

        Location createdLoc = locRepo.getById(loc.getLocationId());

        Assertions.assertNotNull(createdLoc);
        Assertions.assertEquals(loc, createdLoc);
    }

    @Test
    public void whenEditLocation_thenEditCorrectlySaved() {
        Location loc = new Location();
        loc.setName("Boston");
        loc.setAddress("A new address");
        loc.setLatitude(BigDecimal.ONE);
        loc.setLongitude(BigDecimal.ONE);
        loc = locRepo.save(loc);

        loc.setName("Chicago");
        loc = locRepo.save(loc);

        Location locFromRepo = locRepo.getById(loc.getLocationId());

        Assertions.assertNotNull(locFromRepo);
        Assertions.assertNotEquals("Boston", locFromRepo.getName());
        Assertions.assertEquals("Chicago", locFromRepo.getName());
    }

    @Test
    public void whenDeleteLocation_thenGetByIdThrowsEntityNotFound() {
        Location loc = new Location();
        loc.setName("Boston");
        loc.setAddress("A new address");
        loc.setLatitude(BigDecimal.ONE);
        loc.setLongitude(BigDecimal.ONE);
        loc = locRepo.save(loc);

        int locId = loc.getLocationId();

        locRepo.delete(loc);

        try {
            locRepo.getById(locId);
        } catch (JpaObjectRetrievalFailureException ex) {
            return;
        }
        Assertions.fail("Deleted location still exists");
    }

    //all locations where a hero has been
    @Test
    public void testFindLocationsBySightingsSupersSuperId() {
        Location loc = new Location();
        loc.setName("Boston");
        loc.setAddress("A new address");
        loc.setLatitude(BigDecimal.ONE);
        loc.setLongitude(BigDecimal.ONE);
        loc = locRepo.save(loc);

        Location loc2 = new Location();
        loc2.setName("New York");
        loc2.setAddress("A new address");
        loc2.setLatitude(BigDecimal.ONE);
        loc2.setLongitude(BigDecimal.ONE);
        loc2 = locRepo.save(loc2);

        Super sup = new Super();
        sup.setAlignment("Hero");
        sup.setDescription("Test Super Desc");
        sup.setName("Test Sup Name");
        sup = supRepo.save(sup);

        Sighting s = new Sighting();
        s.setDate(LocalDate.now());
        s.setDescription("desc ");
        s.setLocation(loc);
        s.setSupers(Arrays.asList(sup));
        sightingRepo.save(s);

        Sighting s2 = new Sighting();
        s2.setDate(LocalDate.now());
        s2.setDescription("desc ");
        s2.setLocation(loc2);
        s2.setSupers(Arrays.asList(sup));
        sightingRepo.save(s2);

        List<Location> locations = locRepo.findBySightingsSupers(sup);

        Assertions.assertNotNull(locations);
        Assertions.assertEquals(2, locations.size());
        Assertions.assertTrue(locations.contains(loc));
        Assertions.assertTrue(locations.contains(loc2));
    }

}
