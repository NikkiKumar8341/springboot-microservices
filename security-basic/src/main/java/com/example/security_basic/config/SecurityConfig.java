package com.example.security_basic.config;

import com.example.security_basic.filter.JwtFilter;
import com.example.security_basic.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;



    @Autowired
    public UserDetailsService userDetailsService(){
        return new RegisterService();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable();
        httpSecurity.authorizeHttpRequests().requestMatchers("/auth/**","/api/teacher/**","/eureka/**","/api/coursePost/**").permitAll()
                .and()
                .authorizeHttpRequests()
//                .requestMatchers("/api/teacher/**").hasRole("TEACHER")
//                .and().authorizeHttpRequests().requestMatchers("/api/student/**").hasRole("STUDENT")
                .anyRequest().authenticated()
                .and().sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
//        return  httpSecurity.csrf(csrf->csrf.disable())
//                .authorizeHttpRequests(auth->auth.
//                        requestMatchers("/auth*","/auth/addUser","/auth/login")
//                        .permitAll()
//                        .anyRequest().authenticated()
//                ).sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
    }


//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService());
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }





    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
