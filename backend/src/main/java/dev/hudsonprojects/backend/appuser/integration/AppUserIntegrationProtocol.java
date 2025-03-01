package dev.hudsonprojects.backend.appuser.integration;

import dev.hudsonprojects.backend.appuser.AppUser;
import dev.hudsonprojects.backend.integration.protocol.IntegrationHttpProtocol;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class AppUserIntegrationProtocol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIntegrationProtocol;
    @ManyToOne(optional = false)
    private AppUser appUser;
    @OneToOne(optional = false)
    private IntegrationHttpProtocol integrationHttpProtocol;

    public Long getUserIntegrationProtocol() {
        return userIntegrationProtocol;
    }

    public void setUserIntegrationProtocol(Long userIntegrationProtocol) {
        this.userIntegrationProtocol = userIntegrationProtocol;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public IntegrationHttpProtocol getIntegrationHttpProtocol() {
        return integrationHttpProtocol;
    }

    public void setIntegrationHttpProtocol(IntegrationHttpProtocol integrationHttpProtocol) {
        this.integrationHttpProtocol = integrationHttpProtocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUserIntegrationProtocol that = (AppUserIntegrationProtocol) o;
        return Objects.equals(userIntegrationProtocol, that.userIntegrationProtocol);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userIntegrationProtocol);
    }
}
