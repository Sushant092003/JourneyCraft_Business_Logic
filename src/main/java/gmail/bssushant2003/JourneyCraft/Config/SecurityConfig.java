package gmail.bssushant2003.JourneyCraft.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration


@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
//        return httpSecurity.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers("/api/users/register").permitAll()
//                        .requestMatchers("/api/guides/register").permitAll()
//                        .requestMatchers("/api/restaurant/register").permitAll()
//                        .requestMatchers("/api/guides/register/guide/**").permitAll()
//                        .requestMatchers("/api/restaurant/**").permitAll()
//                        .requestMatchers("/api/users/all-users").permitAll()
//                        .requestMatchers("/api/users/find").authenticated()
//                .anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
//                .build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Allow all requests
                .build();
    }
}
