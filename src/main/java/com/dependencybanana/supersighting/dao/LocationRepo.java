package com.dependencybanana.supersighting.dao;

import com.dependencybanana.supersighting.models.Location;
import com.dependencybanana.supersighting.models.Super;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepo extends JpaRepository<Location, Integer> {

    List<Location> findBySightingsSupers(Super sup);
    
}
