package org.lgraf.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;


@KeycloakConfiguration
class KeycloakConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder auth ) {
        auth.authenticationProvider( keycloakAuthenticationProvider() );
    }


    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        super.configure( http );
        //@formatter:off
        http.authorizeRequests()
                .anyRequest().authenticated();
        //@formatter:on
    }


    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy( new SessionRegistryImpl() );
    }


    @Bean
    public KeycloakConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }


    @Bean
    @Override
    @ConditionalOnMissingBean( HttpSessionManager.class )
    protected HttpSessionManager httpSessionManager() {
        return new HttpSessionManager();
    }


    @Bean
    public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(
            KeycloakAuthenticationProcessingFilter filter ) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean( filter );
        registrationBean.setEnabled( false );
        return registrationBean;
    }


    @Bean
    public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean( KeycloakPreAuthActionsFilter filter ) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean( filter );
        registrationBean.setEnabled( false );
        return registrationBean;
    }


    @Bean
    public FilterRegistrationBean keycloakAuthenticatedActionsFilterBean( KeycloakAuthenticatedActionsFilter filter ) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean( filter );
        registrationBean.setEnabled( false );
        return registrationBean;
    }


    @Bean
    public FilterRegistrationBean keycloakSecurityContextRequestFilterBean(
            KeycloakSecurityContextRequestFilter filter ) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean( filter );
        registrationBean.setEnabled( false );
        return registrationBean;
    }

}
