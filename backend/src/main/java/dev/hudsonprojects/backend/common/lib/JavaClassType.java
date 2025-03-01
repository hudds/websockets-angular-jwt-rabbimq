package dev.hudsonprojects.backend.common.lib;

import java.util.Collection;

public class JavaClassType {
    private final Class<?> type;
    private final JavaClassType javaClassType;
    private final Class<? extends Collection<?>> collectionType;

    public JavaClassType(JavaClassType javaClassType) {
        this.javaClassType = javaClassType;
        this.type = javaClassType.type;
        collectionType = null;
    }

    public JavaClassType(Class<?> type) {
        this.javaClassType = new JavaClassType(type);
        this.type = type;
        collectionType = null;
    }

    public JavaClassType(Class<? extends Collection<?>> wrappingCollectionType, Class<?> type) {
        this.javaClassType = new JavaClassType(type);
        this.type = type;
        this.collectionType = wrappingCollectionType;
    }

    public JavaClassType(Class<? extends Collection<?>> collectionType, JavaClassType type) {
        this.javaClassType = new JavaClassType(type);
        this.type = type.getType();
        this.collectionType = collectionType;
    }

    public Class<? extends Collection<?>> getCollectionType() {
        return collectionType;
    }

    public Class<?> getType() {
        return type;
    }

}
