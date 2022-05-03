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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 *
 * @author kaung
 */
@Data
@Entity
public class Super
{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int superId;

    @Column
    @NotBlank(message = "Inserted name cannot be blank")
    @Size(max = 50, message = "Inserted name is too long (50 chars max)")
    private String name;

    @Column
    private String alignment;

    @Size(max = 150, message = "Inserted desc is too long (150 chars max)")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "ability_super",
            joinColumns =
            {
                @JoinColumn(name = "super_id")
            },
            inverseJoinColumns =
            {
                @JoinColumn(name = "ability_id")
            }
    )
    private List<Ability> abilities;

//    @ManyToMany
//    @JoinTable(
//            name = "organization_super",
//            joinColumns =
//            {
//                @JoinColumn(name = "organization_id")
//            },
//            inverseJoinColumns =
//            {
//                @JoinColumn(name = "super_id")
//            }
//    )
//    private List<Organization> organizations;
    @ManyToMany(mappedBy = "supers")
    private List<Organization> organizations;

//    @ManyToMany
//    @JoinTable(
//            name = "sighting_super",
//            joinColumns =
//            {
//                @JoinColumn(name = "super_id")
//            },
//            inverseJoinColumns =
//            {
//                @JoinColumn(name = "sighting_id")
//            }
//    )
    @ManyToMany(mappedBy = "supers")
    private List<Sighting> sightings;
}
