package ru.kduskov.lb1maven.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ru.kduskov.lb1maven.model.Property;
import ru.kduskov.lb1maven.model.PropertyPhoto;

import java.util.List;

public class PropertyDao {

    public void save(Property property) {
        executeInTransaction(em -> em.persist(property));
    }

    public void deleteById(Long id) {
        executeInTransaction(em -> {
            Property property = em.find(Property.class, id);
            if (property != null) {
                em.remove(property);
            }
        });
    }

    public List<Property> findAll() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT DISTINCT p FROM Property p LEFT JOIN FETCH p.photos", Property.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public PropertyPhoto findPhotoById(Long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(PropertyPhoto.class, id);
        } finally {
            em.close();
        }
    }

    public Property findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Property.class, id);
        } finally {
            em.close();
        }
    }

    private void executeInTransaction(java.util.function.Consumer<EntityManager> action) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}

