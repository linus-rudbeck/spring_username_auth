package se.distansakademin.username_auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import se.distansakademin.username_auth.services.UserService;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests()
                .requestMatchers("/", "/sign-in", "/sign-up").permitAll()
                .anyRequest().authenticated();

        var authenticationManager = authenticationManager(http);

        http.authenticationManager(authenticationManager);

        http.formLogin()
                .loginPage("/sign-in")
                .loginProcessingUrl("/sign-in")
                .defaultSuccessUrl("/")
                .failureUrl("/sign-in?error=1");

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        var builder =  http.getSharedObject(AuthenticationManagerBuilder.class);

        builder.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder());

        return builder.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
