package dev.hudsonprojects.api.common.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class GenericJpaRepositoryImpl<T> implements GenericJpaRepository<T> {

    @PersistenceContext
    private final EntityManager entityManager;

    public GenericJpaRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void detach(T entity) {
        entityManager.detach(entity);
    }
}
