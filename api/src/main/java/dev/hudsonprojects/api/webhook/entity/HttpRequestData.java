package dev.hudsonprojects.api.webhook.entity;

import dev.hudsonprojects.api.common.entity.DefaultEntity;
import dev.hudsonprojects.api.common.entity.RegistryData;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "http_request_data")
public class HttpRequestData extends DefaultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "http_request_data_id")
    private Long httpRequestDataId;
    @Column(name = "method", nullable = false)
    private String method;
    @Column(name = "url", nullable = false)
    private String url;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "http_request_data_id", referencedColumnName = "http_request_data_id")
    private List<HttpHeader> headers;

    public HttpRequestData() {}

    public HttpRequestData(HttpRequestData httpRequestData) {
        super(httpRequestData);
        httpRequestDataId = httpRequestData.httpRequestDataId;
        method = httpRequestData.method;
        url = httpRequestData.url;
        headers = httpRequestData.headers != null ? new ArrayList<>(httpRequestData.headers.stream()
                .map(header -> new HttpHeader(this, header))
                .toList()) : null;
    }

    public HttpRequestData createCopy() {
        return new HttpRequestData(this);
    }


    public Long getHttpRequestDataId() {
        return httpRequestDataId;
    }

    public void setHttpRequestDataId(Long httpRequestDataId) {
        this.httpRequestDataId = httpRequestDataId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<HttpHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HttpHeader> headers) {
        this.headers = headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequestData that = (HttpRequestData) o;
        return Objects.equals(httpRequestDataId, that.httpRequestDataId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(httpRequestDataId);
    }

}
