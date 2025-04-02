package dev.hudsonprojects.backend.integration.coursesapi.subscription.status;

import dev.hudsonprojects.backend.appuser.AppUser;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(schema = "public", name = "subscription_status", uniqueConstraints = @UniqueConstraint(columnNames = {"app_user_id", "course_id"}))
public class SubscriptionStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionStatusId;
    @Column(name = "user_id", updatable = false)
    private Long userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private AppUser user;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "subscription_id")
    private Long subscriptionId;
    @Column(name = "course_id", nullable = false, updatable = false)
    private Long courseId;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Long getSubscriptionStatusId() {
        return subscriptionStatusId;
    }

    public void setSubscriptionStatusId(Long subscriptionStatusId) {
        this.subscriptionStatusId = subscriptionStatusId;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public enum Status {
        PENDING,
        SUCCESS,
        CANCELED,
        ERROR
    }

}
