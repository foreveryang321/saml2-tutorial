package top.ylonline.saml.identityprovider.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.saml.provider.SamlServerConfiguration;
import org.springframework.security.saml.provider.identity.AssertionEnhancer;
import org.springframework.security.saml.provider.identity.config.SamlIdentityProviderServerBeanConfiguration;
import org.springframework.security.saml.saml2.attribute.Attribute;

@Configuration
@RequiredArgsConstructor
public class BeanConfig extends SamlIdentityProviderServerBeanConfiguration {
    private final SamlServerConfiguration samlServerConfiguration;

    @Override
    protected SamlServerConfiguration getDefaultHostSamlServerConfiguration() {
        return samlServerConfiguration;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("123456")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(userDetails);
    }

    @Override
    @Bean(name = "samlAssertionEnhancer")
    public AssertionEnhancer samlAssertionEnhancer() {
        return assertion -> {
            Attribute attribute = new Attribute();
            attribute.setName("email");
            attribute.addValues("test@email.com");
            assertion.addAttribute(attribute);
            return assertion;
        };
    }
}
