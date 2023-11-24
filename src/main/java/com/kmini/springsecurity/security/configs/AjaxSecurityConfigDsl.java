package com.kmini.springsecurity.security.configs;

import com.kmini.springsecurity.security.common.AjaxLoginAuthenticationEntryPoint;
import com.kmini.springsecurity.security.handler.AjaxAccessDeniedHandler;
import com.kmini.springsecurity.security.handler.AjaxAuthenticationFailureHandler;
import com.kmini.springsecurity.security.handler.AjaxAuthenticationSuccessHandler;
import com.kmini.springsecurity.security.provider.AjaxAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

//@Order(0)
//@Configuration
//@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class AjaxSecurityConfigDsl {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    // dsl 방식으로 설정하기
    @Bean
    public SecurityFilterChain ajaxConfigureDsl(HttpSecurity http) throws Exception {

        customConfigureAjax(http);

        return http.build();
    }

    private void customConfigureAjax(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**")
                .authorizeRequests()
                .antMatchers("/api/messages").hasRole("MANAGER")
                .anyRequest().authenticated()
                .and()
                .apply(new AjaxLoginConfigurer<>())
                .successHandlerAjax(ajaxAuthenticationSuccessHandler())
                .failureHandlerAjax(ajaxAuthenticationFailureHandler())
                .setAuthenticationManager(ajaxAuthenticationManager())
                .authenticationEntryPointAjax(ajaxAuthenticationEntryPoint())
                .accessDeniedHandler(ajaxAccessDeniedHandler())
                .loginProcessingUrl("/api/login");

        http.csrf().disable();
    }

    @Bean
    public AuthenticationProvider ajaxAuthenticationProvider() {
        return new AjaxAuthenticationProvider(userDetailsService, passwordEncoder);
    }

    @Bean
    public AuthenticationManager ajaxAuthenticationManager() throws Exception {
        ProviderManager authenticationManager = (ProviderManager) authenticationConfiguration.getAuthenticationManager();
        authenticationManager.getProviders().add(ajaxAuthenticationProvider());
        return authenticationManager;
    }

    @Bean
    public AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        return new AjaxAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
        return new AjaxAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationEntryPoint ajaxAuthenticationEntryPoint() {
        return new AjaxLoginAuthenticationEntryPoint();
    }
    @Bean
    public AccessDeniedHandler ajaxAccessDeniedHandler() {
        return new AjaxAccessDeniedHandler();
    }

}
