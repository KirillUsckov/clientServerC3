package ru.kduskov.lb1maven.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import ru.kduskov.lb1maven.enums.PropertyType;
import ru.kduskov.lb1maven.enums.RentalPeriod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "properties")
public class Property implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private long price;

    @Enumerated(EnumType.STRING)
    private PropertyType type;

    private String address;
    private String description;

    @Enumerated(EnumType.STRING)
    private RentalPeriod period;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PropertyPhoto> photos = new ArrayList<>();
}
