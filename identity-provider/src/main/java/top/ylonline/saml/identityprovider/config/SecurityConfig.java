package top.ylonline.saml.identityprovider.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml.provider.SamlServerConfiguration;
import org.springframework.security.saml.provider.config.NetworkConfiguration;
import org.springframework.security.saml.provider.identity.config.LocalIdentityProviderConfiguration;
import org.springframework.security.saml.provider.identity.config.SamlIdentityProviderSecurityConfiguration;
import org.springframework.security.saml.provider.identity.config.SamlIdentityProviderSecurityDsl;
import org.springframework.security.saml.provider.service.config.LocalServiceProviderConfiguration;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AppConfig appConfig;

    @Bean
    public SamlServerConfiguration samlServerConfiguration() {
        LocalIdentityProviderConfiguration identityProvider = appConfig.getIdentityProvider();
        LocalServiceProviderConfiguration serviceProvider = appConfig.getServiceProvider();
        NetworkConfiguration network = appConfig.getNetwork();

        SamlServerConfiguration serverConfiguration = new SamlServerConfiguration();
        serverConfiguration.setIdentityProvider(identityProvider);
        serverConfiguration.setServiceProvider(serviceProvider);
        serverConfiguration.setNetwork(network);
        return serverConfiguration;
    }

    @Configuration
    @Order(1)
    public class SamlSecurity extends SamlIdentityProviderSecurityConfiguration {

        private final BeanConfig beanConfig;

        public SamlSecurity(BeanConfig beanConfig, @Qualifier("appConfig") AppConfig appConfig) {
            super("/saml/idp/", beanConfig);
            this.beanConfig = beanConfig;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            super.configure(http);

            http.userDetailsService(beanConfig.userDetailsService())
                    .formLogin();

            http.apply(SamlIdentityProviderSecurityDsl.identityProvider())
                    .configure(samlServerConfiguration());
        }
    }

    @Configuration
    public class AppSecurity extends WebSecurityConfigurerAdapter {

        private final BeanConfig beanConfig;

        public AppSecurity(BeanConfig beanConfig) {
            this.beanConfig = beanConfig;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/**")
                    .authorizeRequests()
                    .antMatchers("/**").authenticated()
                    .and()
                    .userDetailsService(beanConfig.userDetailsService()).formLogin();
        }
    }
}
