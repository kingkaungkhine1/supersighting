package com.dependencybanana.supersighting.dao;

import com.dependencybanana.supersighting.models.Location;
import com.dependencybanana.supersighting.models.Organization;
import com.dependencybanana.supersighting.models.Super;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperRepo extends JpaRepository<Super, Integer> {

    List<Super> findBySightingsLocation(Location location);

    List<Super> findByOrganizations(Organization organizations);
}
