package com.dependencybanana.supersighting.dao;

import java.time.LocalDate;
import java.util.List;

import com.dependencybanana.supersighting.models.Sighting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SightingRepo extends JpaRepository<Sighting, Integer>{

    @Query(value = ""
            + "SELECT * FROM sighting s "
            + "ORDER BY s.date DESC "
            + "LIMIT ?1",
            nativeQuery = true)
    List<Sighting> findMostRecentSightings(int limit);

    List<Sighting> findByDate(LocalDate date);
}
