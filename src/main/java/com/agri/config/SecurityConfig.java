package com.agri.config;

import com.agri.services.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    public GoogleOAuth2SuccesHandler googleOAuth2SuccesHandler;
    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/","/register","/login"   ).permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(form->form.loginPage("/login").permitAll().failureUrl("/login?error=true").defaultSuccessUrl("/").usernameParameter("email").passwordParameter("password"))
                // Disable form login
                .oauth2Login(oauth->oauth.loginPage("/login").successHandler(googleOAuth2SuccesHandler)).
                logout(logout->logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("JSESSIONID"))


                .csrf(csrf->csrf.disable()); // Disable CSRF protection

        return http.build();



    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**", "/static/**","/images/**","/productImage","/css/**","/js/**");
    }

}
