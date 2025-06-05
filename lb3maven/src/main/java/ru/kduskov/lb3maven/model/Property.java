package ru.kduskov.lb3maven.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import ru.kduskov.lb3maven.enums.PropertyType;
import ru.kduskov.lb3maven.enums.RentalPeriod;

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

  @NotBlank(message = "Название обязательно")
  private String name;

  @Min(value = 0, message = "Цена не может быть отрицательной")
  private long price;

  @NotNull(message = "Тип обязателен")
  @Enumerated(EnumType.STRING)
  private PropertyType type;

  @NotBlank(message = "Адрес обязателен")
  private String address;

  @NotBlank(message = "Описание обязательно")
  private String description;

  @NotNull(message = "Период аренды обязателен")
  @Enumerated(EnumType.STRING)
  private RentalPeriod period;

  @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  @Builder.Default
  private List<PropertyPhoto> photos = new ArrayList<>();
}
