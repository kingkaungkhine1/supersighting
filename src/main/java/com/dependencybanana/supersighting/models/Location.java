package com.dependencybanana.supersighting.models;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationId;

    @Column
    @Size(max = 50, message = "Inserted name is too long (50 chars max)")
    private String name;

    @Column
    @Size(max = 50, message = "Inserted description is too long (50 chars max)")
    private String description;

    @Column
    @Size(max = 50, message = "Inserted description is too long (50 chars max)")
    private String address;

    @Column
    BigDecimal latitude;

    @Column
    BigDecimal longitude;

    @OneToMany(mappedBy = "location")
    List<Sighting> sightings;
}
