/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.dependencybanana.supersighting.dao;

import javax.transaction.Transactional;
import com.dependencybanana.supersighting.models.Organization;
import com.dependencybanana.supersighting.models.Super;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
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
public class OrganizationRepoTest
{
    @Autowired
    OrganizationRepo orgRepo;
    
    @Autowired
    SuperRepo supRepo;
    
    public OrganizationRepoTest()
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

    @Test
    public void testSupersInOrg()
    {
        Organization org = new Organization();
        org.setOrganizationId(1);
        org.setAlignment("Test Hero");
        org.setDescription("Test Desc");
        org.setName("Test Name");
        org.setLocation(null);

        List<Super> tempSup = supRepo.findAll();
        tempSup.clear();
        
        
        Super sup = new Super();
        sup.setAlignment("Hero");
        sup.setDescription("Test Super Desc");
        sup.setName("Test Sup Name");
        sup.setSuperId(1);
        sup.setOrganizations(orgRepo.findAll());
        
        sup = supRepo.save(sup);
        
        tempSup.add(sup);

        org.setSupers(tempSup);

        org = orgRepo.save(org);

        Organization fromRepo = orgRepo.getById(org.getOrganizationId());
        assertEquals(org, fromRepo);
        
        Super supFromAbRepo = orgRepo.getById(org.getOrganizationId()).getSupers().get(0);
        
        assertEquals(sup, supFromAbRepo);
        
        Super supFromSupRepo = supRepo.getById(sup.getSuperId());
        
        assertEquals(sup, supFromSupRepo);
    }

    //reports back all org a specific hero is in
    @Test
    public void testFindOrg()
    {
        List<Super> tempSup = supRepo.findAll();
        tempSup.clear();
        
        Organization org = new Organization();
        org.setOrganizationId(1);
        // org.setAlignment("Test Hero");
        // org.setDescription("Test Desc");
        org.setName("Test Name");
//        org = orgRepo.save(org);

        Super sup = new Super();
        sup.setAlignment("Hero");
        sup.setDescription("Test Super Desc");
        sup.setName("Test Sup Name");
        sup.setSuperId(1);
        sup.setOrganizations(orgRepo.findAll());
        
        sup = supRepo.save(sup);

        tempSup.add(sup);
        org.setSupers(tempSup);
        org = orgRepo.save(org);

        List<Organization> temp = orgRepo.findBySupers(sup);

        assertEquals(org, temp.get(0));
    }
    
}
