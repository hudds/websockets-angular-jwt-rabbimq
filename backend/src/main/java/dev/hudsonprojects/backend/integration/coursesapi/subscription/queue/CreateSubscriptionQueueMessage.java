package dev.hudsonprojects.backend.integration.coursesapi.subscription.queue;


import dev.hudsonprojects.backend.integration.coursesapi.subscription.CreateSubscriptionDTO;

import java.util.Objects;

public class CreateSubscriptionQueueMessage {

    private Long userId;
    private CreateSubscriptionDTO createSubscriptionDTO;

    public CreateSubscriptionQueueMessage() {}

    public CreateSubscriptionQueueMessage(Long userId, CreateSubscriptionDTO createSubscriptionDTO) {
        this.userId = userId;
        this.createSubscriptionDTO = createSubscriptionDTO;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public CreateSubscriptionDTO getCreateSubscriptionDTO() {
        return createSubscriptionDTO;
    }

    public void setCreateSubscriptionDTO(CreateSubscriptionDTO createSubscriptionDTO) {
        this.createSubscriptionDTO = createSubscriptionDTO;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CreateSubscriptionQueueMessage that = (CreateSubscriptionQueueMessage) object;
        return Objects.equals(userId, that.userId) && Objects.equals(createSubscriptionDTO, that.createSubscriptionDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, createSubscriptionDTO);
    }
}
