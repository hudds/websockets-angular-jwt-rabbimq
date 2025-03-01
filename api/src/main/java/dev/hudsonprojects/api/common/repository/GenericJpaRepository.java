package dev.hudsonprojects.api.common.repository;

public interface GenericJpaRepository<T> {

    void detach(T entity);

}
