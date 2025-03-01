package dev.hudsonprojects.backend.common.lib.instance;

import java.util.Objects;
import java.util.UUID;

public class InstanceId {

    private static InstanceId instanceId;
    private final String id;

    private InstanceId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static synchronized InstanceId get(){
        if(instanceId == null){
            instanceId = new InstanceId(UUID.randomUUID().toString());
        }
        return instanceId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        InstanceId that = (InstanceId) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstanceId{" +
                "id='" + id + '\'' +
                '}';
    }
}
