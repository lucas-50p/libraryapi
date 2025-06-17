package com.cursospring.libraryapi.config.Database;

import com.cursospring.libraryapi.security.CustomUserDetailsService;
import com.cursospring.libraryapi.security.JwtCustomAuthenticationFilter;
import com.cursospring.libraryapi.security.LoginSocialSucessHandler;
import com.cursospring.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, LoginSocialSucessHandler loginSocialSucessHandler, JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter) throws  Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(configurer ->{
                    configurer.loginPage("/login").permitAll();
                })
//                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST,"/usuarios/**").permitAll();// Qualque usuario
                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 ->
                                oauth2
                                        .loginPage("/login")
                                        .successHandler(loginSocialSucessHandler)
                                        .redirectionEndpoint(redirectionEndpointConfig ->
                                                redirectionEndpointConfig.baseUri("/oauth2/code/*")))
                .oauth2ResourceServer(oauth2RS -> oauth2RS.jwt(Customizer.withDefaults()))
                .addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class)
                .build();
    }

    // COFIGURA O PREFIXO ROLE
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        // Vai ignorar o prefixo reole
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){

        var authoritiesConvert = new JwtGrantedAuthoritiesConverter();
        authoritiesConvert.setAuthorityPrefix("");

        var convert = new JwtAuthenticationConverter();
        convert.setJwtGrantedAuthoritiesConverter(authoritiesConvert);

        return convert;
    }
}
