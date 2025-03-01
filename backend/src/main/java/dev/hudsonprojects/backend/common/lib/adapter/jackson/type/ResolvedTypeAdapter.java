package dev.hudsonprojects.backend.common.lib.adapter.jackson.type;

import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.common.lib.JavaClassType;

public class ResolvedTypeAdapter {

    public ResolvedType adapt(JavaClassType type, ObjectMapper objectMapper) {
        if(type.getCollectionType() != null){
            return objectMapper.getTypeFactory().constructCollectionType(type.getCollectionType(), type.getType());
        }
        return objectMapper.getTypeFactory().constructType(type.getType());
    }

}
