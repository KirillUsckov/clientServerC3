package ru.kduskov.lb2maven.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kduskov.lb2maven.model.PropertyPhoto;

@Repository
public interface PropertyPhotoRepository extends JpaRepository<PropertyPhoto, Long> {
}