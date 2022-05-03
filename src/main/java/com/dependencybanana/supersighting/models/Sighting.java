package com.dependencybanana.supersighting.models;

import java.time.LocalDate;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
public class Sighting
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sightingId;

    @Column
    @Size(max = 500)
    private String description;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;


    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    @NotNull
    private Location location;
    //do i need to figure out how to join columns and join tables later?

    @ManyToMany
    @JoinTable(
            name = "sighting_super",
            joinColumns =
            {
                @JoinColumn(name = "sighting_id")
            },
            inverseJoinColumns =
            {
                @JoinColumn(name = "super_id")
            }
    )
    @NotEmpty
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
