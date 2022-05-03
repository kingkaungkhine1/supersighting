package com.dependencybanana.supersighting.dao;

import com.dependencybanana.supersighting.models.Location;
import com.dependencybanana.supersighting.models.Sighting;
import com.dependencybanana.supersighting.models.Super;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class SightingRepoTest
{

    private static final int MOST_RECENT_LIMIT = 10;

    @Autowired
    SightingRepo sRepo;

    @Autowired
    LocationRepo lRepo;

    @Autowired
    SuperRepo supRepo;

    public SightingRepoTest()
    {
    }

    @BeforeEach
    @Sql("classpath:setup_test_db.sql")
    public void setUp()
    {
    }

    @Test
    public void testFindMostRecentSightings()
    {
        LocalDate date = LocalDate.now();
        createMoreSightingsThanMostRecentLimit();

        List<Sighting> mostRecent = sRepo.findMostRecentSightings(MOST_RECENT_LIMIT);

        Assertions.assertNotNull(mostRecent);
        Assertions.assertEquals(MOST_RECENT_LIMIT, mostRecent.size());

        LocalDate latestDate = mostRecent.stream()
                .map(Sighting::getDate)
                .sorted()
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(latestDate);
        Assertions.assertEquals(date.minusMonths(MOST_RECENT_LIMIT - 1), latestDate);
    }

    private void createMoreSightingsThanMostRecentLimit()
    {
        Location loc = new Location();
        loc.setName("New Location");
        loc.setAddress("A new address");
        loc.setLatitude(BigDecimal.ONE);
        loc.setLongitude(BigDecimal.ONE);
        loc = lRepo.save(loc);

        LocalDate date = LocalDate.now();

        for (int i = 0; i < MOST_RECENT_LIMIT + 5; i++)
        {
            Sighting s = new Sighting();
            s.setDate(date.minusMonths(i));
            s.setDescription("desc " + i);
            s.setLocation(loc);
            sRepo.save(s);
        }
    }

    @Test
    public void whenSavingMultipleSightingsWithSameLocation_thenLocationMatchesInRepo()
    {
        Location loc = new Location();
        loc.setName("New Location");
        loc.setAddress("A new address");
        loc.setLatitude(BigDecimal.ONE);
        loc.setLongitude(BigDecimal.ONE);
        loc.setDescription("Temp");
        loc = lRepo.save(loc);

        LocalDate date = LocalDate.now();

        Sighting s = new Sighting();
        s.setDate(date);
        s.setDescription("desc s1");
        s.setLocation(loc);
        s = sRepo.save(s);

        Sighting s2 = new Sighting();
        s2.setDate(date);
        s2.setDescription("desc s2");
        s2.setLocation(loc);
        s2 = sRepo.save(s);

        Assertions.assertEquals(s.getLocation(), s2.getLocation());
    }

    @Test
    public void whenSettingSupersOnMultipleSightings_thenSupersMatchInrepo()
    {
        List<Super> supersInvolved = new ArrayList<>();
        Super sup = new Super();
        sup.setAlignment("Hero");
        sup.setName("A super");
        sup.setAbilities(new ArrayList<>());
        sup = supRepo.save(sup);
        supersInvolved.add(sup);

        Location loc = new Location();
        loc.setName("New Location");
        loc.setAddress("A new address");
        loc.setDescription("Temp");
        loc.setLatitude(BigDecimal.ONE);
        loc.setLongitude(BigDecimal.ONE);
        loc = lRepo.save(loc);

        LocalDate date = LocalDate.now();

        Sighting s = new Sighting();
        s.setDate(date);
        s.setDescription("desc s1");
        s.setLocation(loc);
        s.setSupers(supersInvolved);
        s = sRepo.save(s);

        Sighting s2 = new Sighting();
        s2.setDate(date);
        s2.setDescription("desc s2");
        s2.setLocation(loc);
        s2.setSupers(supersInvolved);
        s2 = sRepo.save(s);

        Assertions.assertFalse(s.getSupers().isEmpty());
        Assertions.assertFalse(s2.getSupers().isEmpty());
        Assertions.assertEquals(s.getSupers().get(0), s2.getSupers().get(0));
    }

    @Test
    public void testFindByDate()
    {
        List<Super> supersInvolved = new ArrayList<>();
        Super sup = new Super();
        sup.setAlignment("Hero");
        sup.setName("A super");
        sup.setAbilities(new ArrayList<>());
        sup = supRepo.save(sup);
        supersInvolved.add(sup);

        Location loc = new Location();
        loc.setName("New Location");
        loc.setAddress("A new address");
        loc.setDescription("Temp");
        loc.setLatitude(BigDecimal.ONE);
        loc.setLongitude(BigDecimal.ONE);
        loc = lRepo.save(loc);

        LocalDate date = LocalDate.now();

        Sighting s = new Sighting();
        s.setDate(date);
        s.setDescription("desc s1");
        s.setLocation(loc);
        s.setSupers(supersInvolved);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String new_date = "04/06/2022";

        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(new_date, formatter);
        s.setDate(localDate);
        s = sRepo.save(s);

        List<Sighting> sightings = sRepo.findByDate(localDate);

        assertEquals(s, sightings.get(0));
    }

}
