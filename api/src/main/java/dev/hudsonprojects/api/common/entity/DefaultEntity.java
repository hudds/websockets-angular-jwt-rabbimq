package dev.hudsonprojects.api.common.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

import java.time.Instant;
import java.time.LocalDateTime;


@MappedSuperclass
public abstract class DefaultEntity {

    @Embedded
    private RegistryData registryData = new RegistryData();

    protected DefaultEntity(){}

    protected DefaultEntity(DefaultEntity defaultEntity) {
        if(defaultEntity.getRegistryData() != null) {
            this.registryData = new RegistryData(defaultEntity.getRegistryData());
        }
    }

    public RegistryData getRegistryData() {
        if(registryData == null){
            registryData = new RegistryData();
        }
        return registryData;
    }


    public void setRegistryData(RegistryData registryData) {
        this.registryData = registryData;
    }

    public LocalDateTime getCreatedAt() {
        return getRegistryData().getCreatedAt();
    }

    public void setCreatedAt(LocalDateTime createdAt){
        getRegistryData().setCreatedAt(createdAt);
    }

    public LocalDateTime getUpdatedAt() {
        return getRegistryData().getUpdatedAt();
    }

    public void setUpdatedAt(LocalDateTime updatedAt){
        getRegistryData().setUpdatedAt(updatedAt);
    }



}
