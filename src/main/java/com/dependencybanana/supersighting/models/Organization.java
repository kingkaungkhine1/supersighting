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
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Organization
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int organizationId;

    @Column
    @NotEmpty(message = "You need to insert a name")
    @NotBlank(message = "Inserted name cannot be blank")
    @Size(max = 50, message = "Inserted name is too long (50 chars max)")
    private String name;

    @Column
    @Size(max = 500, message = "Inserted desc is too long (500 chars max).")
    private String description;

    //do we need this?
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column
    private String alignment;
    
    @ManyToMany
    @JoinTable(
            name = "organization_super",
            joinColumns =
            {
                @JoinColumn(name = "organization_id")
            },
            inverseJoinColumns =
            {
                @JoinColumn(name = "super_id")
            }
    )
    private List<Super> supers;

    public boolean containsSuper(Super superhero)
    {
        return supers.stream().anyMatch(s -> s.getSuperId() == superhero.getSuperId());
    }

    public boolean hasLocation(Location loc)
    {
        return location.getLocationId() == loc.getLocationId();
    }
}
