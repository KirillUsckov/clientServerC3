package ru.kduskov.lb3maven.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "property_photos")
public class PropertyPhoto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String fileName;

  @Lob
  private byte[] data;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "property_id")
  @JsonBackReference
  private Property property;
}
