package top.ylonline.saml.identityprovider.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml.provider.config.NetworkConfiguration;
import org.springframework.security.saml.provider.identity.config.LocalIdentityProviderConfiguration;
import org.springframework.security.saml.provider.service.config.LocalServiceProviderConfiguration;

@ConfigurationProperties(prefix = "spring.security.saml2")
@Configuration
@Data
// @EqualsAndHashCode(callSuper = true)
public class AppConfig /*extends SamlServerConfiguration*/ {
    @NestedConfigurationProperty
    private LocalServiceProviderConfiguration serviceProvider;
    @NestedConfigurationProperty
    private LocalIdentityProviderConfiguration identityProvider;
    @NestedConfigurationProperty
    private NetworkConfiguration network;
}
