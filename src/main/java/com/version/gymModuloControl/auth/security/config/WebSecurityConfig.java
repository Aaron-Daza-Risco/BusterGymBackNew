package com.version.gymModuloControl.auth.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.version.gymModuloControl.auth.security.UserDetailsServiceImpl;
import com.version.gymModuloControl.auth.security.jwt.AuthTokenFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthTokenFilter authTokenFilter;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/register").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/auth/usuarios").hasRole("ADMIN")
                        .requestMatchers("/api/auth/usuarios/seguridad").hasRole("ADMIN")
                        .requestMatchers("/api/auth/usuarios/*/estado").hasRole("ADMIN")
                        .requestMatchers("/api/auth/usuarios/*/rol").hasRole("ADMIN")
                        .requestMatchers("/api/auth/me").authenticated()

                        // ENDPOINTS RESTRINGIDOS SOLO A ADMIN Y RECEPCIONISTA
                        .requestMatchers("/api/persona/registrar-cliente").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/persona/registrar-empleado").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/persona/listar-clientes").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/persona/listar-empleados").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/personas/empleados").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/personas/clientes").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        
                        // PLANES - Solo ADMIN puede modificar, ambos pueden listar
                        .requestMatchers("/api/plan/guardar").hasRole("ADMIN")
                        .requestMatchers("/api/plan/listar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/plan/actualizar").hasRole("ADMIN")
                        .requestMatchers("/api/plan/*/estado").hasRole("ADMIN")
                        .requestMatchers("/api/plan/eliminar/**").hasRole("ADMIN")
                        
                        // HORARIOS DE EMPLEADOS
                        .requestMatchers("/api/horario-empleado/agregar/**").hasRole("ADMIN")
                        .requestMatchers("/api/horario-empleado/listar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/horario-empleado/actualizar/**").hasRole("ADMIN")
                        .requestMatchers("/api/horario-empleado/eliminar/**").hasRole("ADMIN")
                        .requestMatchers("/api/horario-empleado/*/estado").hasRole("ADMIN")
                        .requestMatchers("/api/horario-empleado/empleado/*/dia/*").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        
                        // CATEGORÍAS
                        .requestMatchers("/api/categoria/guardar").hasRole("ADMIN")
                        .requestMatchers("/api/categoria/actualizar").hasRole("ADMIN")
                        .requestMatchers("/api/categoria/listar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/categoria/eliminar/**").hasRole("ADMIN")
                        .requestMatchers("/api/categoria/*/estado").hasRole("ADMIN")
                        
                        // PRODUCTOS
                        .requestMatchers("/api/producto/guardar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/producto/actualizar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/producto/*/estado").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/producto/listar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/producto/eliminar/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        
                        // VENTAS
                        .requestMatchers("/api/venta/listar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/venta/guardar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/venta/cambiar-estado/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/venta/detalle/agregar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/venta/detalle/listar/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/venta/detalle/eliminar/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/venta/pago/registrar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/venta/cancelar/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        
                        // EQUIPOS/PIEZAS
                        .requestMatchers("/api/pieza/listar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/pieza/guardar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/pieza/actualizar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/pieza/eliminar/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/pieza/*/estado").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        
                        // ASISTENCIA
                        .requestMatchers("/api/asistencia/marcar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/asistencia/listar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/asistencia/registrar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        
                        // INSCRIPCIONES
                        .requestMatchers("/api/inscripciones/registrar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/inscripciones/instructores-disponibles/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/inscripciones/horarios-instructor/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/inscripciones/pago/registrar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/inscripciones/listar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/inscripciones/cancelar/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/inscripciones/cliente/*/inscripciones").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/inscripciones/inscripciones/*/detalle").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/inscripciones/planes-inscritos/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/inscripciones/historial-planes/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        
                        // DESEMPEÑO
                        .requestMatchers("/api/desempeno/registrar").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/desempeno/actualizar/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/desempeno/eliminar/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/desempeno/cliente/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/desempeno/historial/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        .requestMatchers("/api/desempeno/inscripcion/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                        
                        // DASHBOARDS
                        .requestMatchers("/api/dashboard-recepcionista").hasRole("RECEPCIONISTA")
                        .requestMatchers("/api/dashboard-admin/piezas-bajo-stock").hasRole("ADMIN")
                        
                        // ENTRENADORES (endpoints administrativos)
                        .requestMatchers("/api/entrenador/**").hasAnyRole("ADMIN", "RECEPCIONISTA")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

