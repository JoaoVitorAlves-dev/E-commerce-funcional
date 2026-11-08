package ecom.merce.ecommerce.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TokenFilter tokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(ms -> ms.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.POST, "/clientes/registrar", "/clientes/logar").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/clientes/atualizar").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/clientes/deletar").authenticated()
                        .requestMatchers(HttpMethod.GET, "/clientes/buscar").authenticated()
                        .requestMatchers(HttpMethod.GET,  "/clientes/buscar/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/categorias/listar", "/categorias/buscar/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/categorias/adicionar").hasAnyRole("ADMIN", "SELLER")
                        .requestMatchers(HttpMethod.PATCH, "/categorias/atualizar/{id}").hasAnyRole("ADMIN", "SELLER")
                        .requestMatchers(HttpMethod.DELETE, "/categorias/deletar/{id}").hasAnyRole("ADMIN", "SELLER")

                        .requestMatchers(HttpMethod.GET, "/enderecos/listar").authenticated()
                        .requestMatchers(HttpMethod.GET, "/enderecos/buscar/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/enderecos/adicionar").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/enderecos/atualizar/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/enderecos/deletar/{id}").authenticated()

                        .requestMatchers(HttpMethod.GET, "/itempedidos/listar").authenticated()
                        .requestMatchers(HttpMethod.GET, "/itempedidos/buscar/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/itempedidos/adicionar").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/itempedidos/atualizar/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/itempedidos/deletar/{id}").authenticated()

                        .requestMatchers(HttpMethod.GET, "/pagamentos/listar").authenticated()
                        .requestMatchers(HttpMethod.GET,"/pagamentos/buscar/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/pagamentos/adicionar").hasAnyRole("ADMIN", "SELLER")
                        .requestMatchers(HttpMethod.PATCH, "/pagamentos/atualizar/{id}").hasAnyRole("ADMIN", "SELLER")
                        .requestMatchers(HttpMethod.DELETE, "/pagamentos/deletar/{id}").hasAnyRole("ADMIN", "SELLER")

                        .requestMatchers(HttpMethod.GET, "/produtos/listar", "/produtos/buscar/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/produtos/adicionar").hasAnyRole("ADMIN", "SELLER")
                        .requestMatchers(HttpMethod.PATCH, "/produtos/atualizar/{id}").hasAnyRole("ADMIN", "SELLER")
                        .requestMatchers(HttpMethod.DELETE, "/produtos/deletar/{id}").hasAnyRole("ADMIN", "SELLER")

                        .requestMatchers(HttpMethod.GET, "/pedidos/listar").authenticated()
                        .requestMatchers(HttpMethod.GET, "/pedidos/buscar/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/pedidos/adicionar").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/pedidos/atualizar/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/pedidos/deletar/{id}").authenticated()

                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(
                        (request, response, exception) ->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                ))
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

}
