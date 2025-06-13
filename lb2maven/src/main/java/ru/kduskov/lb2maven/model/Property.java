package ru.kduskov.lb2maven.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import ru.kduskov.lb2maven.enums.PropertyType;
import ru.kduskov.lb2maven.enums.RentalPeriod;
import ru.kduskov.lb2maven.model.PropertyPhoto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
  @Builder.Default
  private List<PropertyPhoto> photos = new ArrayList<>();
}
