package dev.hudsonprojects.api.webhook.entity;

import dev.hudsonprojects.api.common.entity.DefaultEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
@Table(schema = "public", name = "http_header")
public class HttpHeader extends DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long httpHeaderId;
    @Column(name = "http_request_data_id", nullable = false, updatable = false)
    private Long httpRequestDataId;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "http_request_data_id", referencedColumnName = "http_request_data_id", updatable = false, insertable = false)
    private HttpRequestData httpRequestData;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "value", nullable = false)
    private String value;

    public HttpHeader(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public HttpHeader(){}

    public HttpHeader(HttpRequestData httpRequestData, String name, String value) {
        this(name, value);
        this.httpRequestData = httpRequestData;
        this.httpRequestDataId = httpRequestData != null ? httpRequestData.getHttpRequestDataId() : null;
    }

    public HttpHeader(HttpRequestData httpRequestData, HttpHeader httpHeader) {
        super(httpHeader);
        httpHeaderId = httpHeader.httpHeaderId;
        httpRequestDataId = httpHeader.httpRequestDataId;
        name = httpHeader.name;
        value = httpHeader.value;
        this.httpRequestData = httpRequestData;
        this.httpRequestDataId = httpRequestData != null ? httpRequestData.getHttpRequestDataId() : null;
    }

    public Long getHttpHeaderId() {
        return httpHeaderId;
    }

    public void setHttpHeaderId(Long httpHeaderId) {
        this.httpHeaderId = httpHeaderId;
    }

    public HttpRequestData getHttpRequestData() {
        return httpRequestData;
    }

    public void setHttpRequestData(HttpRequestData httpRequestData) {
        this.httpRequestData = httpRequestData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpHeader that = (HttpHeader) o;
        return Objects.equals(httpHeaderId, that.httpHeaderId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(httpHeaderId);
    }

    public Long getHttpRequestDataId() {
        return httpRequestDataId;
    }

    public void setHttpRequestDataId(Long httpRequestDataId) {
        this.httpRequestDataId = httpRequestDataId;
    }
}
