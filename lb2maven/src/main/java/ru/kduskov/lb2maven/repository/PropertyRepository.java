package ru.kduskov.lb2maven.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ru.kduskov.lb2maven.model.Property;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("SELECT DISTINCT p FROM Property p LEFT JOIN FETCH p.photos")
    List<Property> findAllWithPhotos();
}
