package ru.kduskov.lb2maven.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.kduskov.lb2maven.model.Property;
import ru.kduskov.lb2maven.model.PropertyPhoto;
import ru.kduskov.lb2maven.repository.PropertyPhotoRepository;
import ru.kduskov.lb2maven.repository.PropertyRepository;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyPhotoRepository photoRepository;

    public List<Property> findAll() {
        return propertyRepository.findAllWithPhotos();
    }

    public Property save(Property property) {
        return propertyRepository.save(property);
    }

    public void deleteById(Long id) {
        propertyRepository.deleteById(id);
    }

    public Property findById(Long id) {
        return propertyRepository.findById(id).orElse(null);
    }

    public PropertyPhoto findPhotoById(Long id) {
        return photoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void saveWithPhoto(Property property, MultipartFile photoFile) {
        Property savedProperty = propertyRepository.save(property);

        if (photoFile != null && !photoFile.isEmpty()) {
            try {
                PropertyPhoto photo = PropertyPhoto.builder()
                        .fileName(photoFile.getOriginalFilename())
                        .data(photoFile.getBytes())
                        .property(savedProperty)
                        .build();

                photoRepository.save(photo);
            } catch (IOException e) {
                throw new RuntimeException("Не удалось сохранить фото", e);
            }
        }
    }
}
