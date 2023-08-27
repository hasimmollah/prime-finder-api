package com.nw.primefinder.security;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.Policy;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public AntiSamy antiSamy(final ApplicationContext ctx) {
        try {
            final Policy policy = Policy.getInstance(getClass().getResourceAsStream("/configuration/antisamy.xml"));
            return new AntiSamy(policy);
        } catch (final Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                 .authorizeRequests().anyRequest().permitAll()
                .and()
                .headers()
                .httpStrictTransportSecurity().includeSubDomains(true)
                .maxAgeInSeconds(31536000).and().contentTypeOptions().and().frameOptions()
        ;
        http.csrf().disable();
    }

    @Bean
    public FilterRegistrationBean antiSamyFilterRegistration(final AntiSamyFilterService filterService) {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new XssFilter(filterService));
        registration.addUrlPatterns("*");

        registration.setName("antiSamyFilter");
        return registration;
    }

}
