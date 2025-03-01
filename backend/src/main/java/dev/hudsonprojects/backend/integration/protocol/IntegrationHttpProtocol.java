package dev.hudsonprojects.backend.integration.protocol;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class IntegrationHttpProtocol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long protocolId;
    @Enumerated(EnumType.STRING)
    private IntegrationStatus integrationStatus;
    @Column(columnDefinition="TEXT")
    private String exceptionMessage;
    @Column(columnDefinition="TEXT")
    private String url;
    @Column(columnDefinition="TEXT")
    private String content;
    @Column(columnDefinition="TEXT")
    private String response;
    private Integer responseStatus;
    private LocalDateTime sentAt;
    private LocalDateTime responseReceivedAt;


    public Long getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(Long protocolId) {
        this.protocolId = protocolId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

    public LocalDateTime getResponseReceivedAt() {
        return responseReceivedAt;
    }

    public void setResponseReceivedAt(LocalDateTime responseReceivedAt) {
        this.responseReceivedAt = responseReceivedAt;
    }


    public IntegrationStatus getIntegrationStatus() {
        return integrationStatus;
    }

    public void setIntegrationStatus(IntegrationStatus integrationStatus) {
        this.integrationStatus = integrationStatus;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
