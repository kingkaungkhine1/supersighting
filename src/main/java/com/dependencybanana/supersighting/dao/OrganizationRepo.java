package com.dependencybanana.supersighting.dao;

import java.util.List;
import com.dependencybanana.supersighting.models.Organization;
import com.dependencybanana.supersighting.models.Super;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Integer> {
    //List<Super> findByName(String orgName);
    List<Organization> findBySupers(Super supers);
    
}