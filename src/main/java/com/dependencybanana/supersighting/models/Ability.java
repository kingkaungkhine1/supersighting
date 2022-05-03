package com.dependencybanana.supersighting.models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author kaung
 */
@Data
@Entity
public class Ability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int abilityId;

    @Column
//    @NotEmpty (message = "You need to insert a name")
//    @NotBlank (message = "Inserted name cannot be blank")
    @Size(max = 50, message = "Inserted name is too long (50 chars max)")
    private String name;

    @Column
    @Size(max = 150, message = "Inserted desc is too long (150 chars max)")
    private String description;

//    //do i need a list of supers here?
//    //what would the annotations for this look like
    @ManyToMany
    @JoinTable(
        name = "ability_super",
        joinColumns = {@JoinColumn(name = "ability_id")},
        inverseJoinColumns = {@JoinColumn(name = "super_id")}
    )
    @NotEmpty
    private List<Super> supers;
}
