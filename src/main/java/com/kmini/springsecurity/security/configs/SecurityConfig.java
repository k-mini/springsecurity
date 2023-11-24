package com.kmini.springsecurity.security.configs;

import com.kmini.springsecurity.security.handler.FormAccessDeniedHandler;
import com.kmini.springsecurity.security.handler.FormAuthenticationFailureHandler;
import com.kmini.springsecurity.security.handler.FormAuthenticationSuccessHandler;
import com.kmini.springsecurity.security.provider.FormAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

import static org.springframework.boot.autoconfigure.security.StaticResourceLocation.*;

@Order(1)
@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

    private final FormAuthenticationSuccessHandler formAuthenticationSuccessHandler;
    private final FormAuthenticationFailureHandler formAuthenticationFailureHandler;
    private final UserDetailsService userDetailsService;
    private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    String[] resources = Stream.of(CSS, JAVA_SCRIPT, IMAGES, WEB_JARS, FAVICON)
            .flatMap(StaticResourceLocation::getPatterns)
            .toArray(String[]::new);

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new FormAuthenticationProvider(userDetailsService, passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public FormAccessDeniedHandler formAccessDeniedHandler() {
        FormAccessDeniedHandler accessDeniedHandler = new FormAccessDeniedHandler();
        accessDeniedHandler.setErrorPage("/denied");
        return accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(resources).permitAll()
                .antMatchers("/", "/user/login/**", "/users", "/login**").permitAll()
                .antMatchers("/mypage").hasRole("USER")
                .antMatchers("/messages").hasRole("MANAGER")
                .antMatchers("/config").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login_proc")
                .authenticationDetailsSource(authenticationDetailsSource)
                .defaultSuccessUrl("/")
                .successHandler(formAuthenticationSuccessHandler)
                .failureHandler(formAuthenticationFailureHandler)
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(formAccessDeniedHandler());

//        http.csrf().disable();
//        http.userDetailsService(userDetailsService);
        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

    //    @Bean
    public UserDetailsService users() {
        String password = passwordEncoder().encode("1111");
        UserDetails user = User.builder()
                .username("user")
                .password(password)
                .roles("USER").build();
        UserDetails manager = User.builder()
                .username("manager")
                .password(password)
                .roles("USER", "MANAGER").build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(password)
                .roles("USER", "MANAGER", "ADMIN").build();
        return new InMemoryUserDetailsManager(user, manager, admin);
    }

    //    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

//    @Bean
//    public WebSecurityCustomizer configure() {
//        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }
}
