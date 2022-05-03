package com.dependencybanana.supersighting.dao;

import com.dependencybanana.supersighting.models.Ability;
import com.dependencybanana.supersighting.models.Super;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AbilityRepoTest {
    
    @Autowired
    AbilityRepo abRepo;
    
    @Autowired
    SuperRepo supRepo;
    
    public AbilityRepoTest() {
    }

    
    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }
    
    @Sql("classpath:setup_test_db.sql")
    @BeforeEach
    public void setUp() {
        //clear out abilities
//        List<Ability> abilities = abRepo.findAll();
//        for(Ability ab : abilities)
//        {
//            abRepo.delete(ab);
//        }
//        
//        //clear out supers
//        List<Super> supers = supRepo.findAll();
//        for(Super sup : supers)
//        {
//            supRepo.delete(sup);
//        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testSupersInAb() {
        Ability ab = new Ability();
        ab.setAbilityId(1);
        ab.setName("Test Ability Name");
        ab.setDescription("Test Ability Description");
        
        List<Super> tempSup = supRepo.findAll();
        tempSup.clear();
        
        Super sup = new Super();
        sup.setAlignment("Hero");
        sup.setDescription("Test Super Desc");
        sup.setName("Test Sup Name");
        sup.setSuperId(1);
        sup.setAbilities(abRepo.findAll());
        
        sup = supRepo.save(sup);
        
        tempSup.add(sup);
        
        ab.setSupers(tempSup);
        
        ab = abRepo.save(ab);
        
        
        Ability fromRepo = abRepo.getById(ab.getAbilityId());
        assertEquals(ab, fromRepo);
        
        Super supFromAbRepo = abRepo.getById(ab.getAbilityId()).getSupers().get(0);
        
        assertEquals(sup, supFromAbRepo);
        
        Super supFromSupRepo = supRepo.getById(sup.getSuperId());
        
        assertEquals(sup, supFromSupRepo);
    }
    
//    @Test
//    public void deleteAb()
//    {
//        Ability ab = new Ability();
//        ab.setAbilityId(1);
//        ab.setName("Test Ability Name");
//        ab.setDescription("Test Ability Description");
//        
//        List<Super> tempSup = supRepo.findAll();
//        tempSup.clear();
//        
//        Super sup = new Super();
//        sup.setAlignment("Hero");
//        sup.setDescription("Test Super Desc");
//        sup.setName("Test Sup Name");
//        sup.setSuperId(1);
//        sup.setAbilities(abRepo.findAll());
//        
//        sup = supRepo.save(sup);
//        
//        tempSup.add(sup);
//        ab.setSupers(tempSup);
//        
//        ab = abRepo.save(ab);
//
//        int abId = ab.getAbilityId();
//
//        abRepo.delete(ab);
//
//        try {
//            Ability nullAb = abRepo.getById(abId);
//
//        } catch (Exception e) {
//            //TODO: handle exception
//            return;
//        }
//        fail("Deleted ability still exists");
//    }

}
