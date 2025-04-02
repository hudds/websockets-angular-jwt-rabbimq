package dev.hudsonprojects.backend.appuser.notification.token.repository;

import dev.hudsonprojects.backend.appuser.notification.token.entity.UserNotificationTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserNotificationTokenRepository extends JpaRepository<UserNotificationTokenEntity, Long> {

    @Query("""
    SELECT notificationToken.refreshTokenFamily.appUser.username
        FROM UserNotificationTokenEntity notificationToken
        WHERE notificationToken.token = :token AND notificationToken.valid
    """)
    Optional<String> findUsernameByValidToken(@Param("token") String token);

    @Modifying
    @Transactional
    @Query("""
            UPDATE UserNotificationTokenEntity userToken
                SET userToken.valid = false, userToken.updatedAt = NOW()
                WHERE userToken.refreshTokenFamilyId = :refreshTokenFamilyId AND userToken.valid
            """)
    void invalidateByRefreshTokenFamilyId(@Param("refreshTokenFamilyId") Long refreshTokenFamilyId);
}
