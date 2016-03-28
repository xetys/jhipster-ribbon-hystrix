package de.stytex.foobar.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import de.stytex.foobar.security.AuthoritiesConstants;
import de.stytex.foobar.security.jwt.JWTConfigurer;
import de.stytex.foobar.security.jwt.TokenProvider;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MicroserviceSecurityConfiguration extends ResourceServerConfigurerAdapter {

    @Inject
    JHipsterProperties jHipsterProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()

            .antMatchers("/api/logs/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/api/**").authenticated()
            //.antMatchers("/api/**").permitAll()         //for testing
            .antMatchers("/metrics/**").permitAll()
            .antMatchers("/health/**").permitAll()
            .antMatchers("/trace/**").permitAll()
            .antMatchers("/dump/**").permitAll()
            .antMatchers("/shutdown/**").permitAll()
            .antMatchers("/beans/**").permitAll()
            .antMatchers("/configprops/**").permitAll()
            .antMatchers("/info/**").permitAll()
            .antMatchers("/autoconfig/**").permitAll()
            .antMatchers("/env/**").permitAll()
            .antMatchers("/mappings/**").permitAll()
            .antMatchers("/liquibase/**").permitAll()
            .antMatchers("/v2/api-docs/**").permitAll()
            .antMatchers("/configuration/security").permitAll()
            .antMatchers("/configuration/ui").permitAll()
            .antMatchers("/protected/**").authenticated();

    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret());

        return converter;
    }
}
