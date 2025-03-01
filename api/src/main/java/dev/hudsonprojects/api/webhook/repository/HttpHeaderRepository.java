package dev.hudsonprojects.api.webhook.repository;

import dev.hudsonprojects.api.webhook.entity.HttpHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HttpHeaderRepository extends JpaRepository<HttpHeader, Long> {

    @Modifying
    @Query("DELETE FROM HttpHeader httpHeader WHERE httpHeader.httpRequestDataId = :httpRequestDataId")
    void deleteByHttpRequestDataId(@Param("httpRequestDataId") Long httpRequestDataId);
}
