package java.com.geds.security;

import java.com.geds.model.Usuario;
import java.com.geds.repository.UsuarioRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.beans.BeanProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.value;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.*;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
class GedsuserDetailsService implements UserDetailsService{

    private final UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado" + email));
        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())));
    }
}

@Component
@RequiredArgsConstructor
class JwtAuthFilter extends OncePerRecuestFilter{

    private final JwtUtil jwtUtil;
    private final GedsUserDetailsService uds;

    @Override
    protectd void doFilterInternal(HttpServerRequest req,HttpServletReponse res, FilterChain chain) throws ServletException, IQException{
        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer")){
            String token + header.substring(7);
            if (jwUtil.validar(token)){
                String email = jwUtil.getEmail(token);
                    UserDetails ud = uds.loaduserByUsername (email);
                    var auth = new UsernamePasswordAuthenticationToken(ud,null,ud.getAuthotities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(req,res);
    }
}
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor

public class SecurityConfig{
    private final JwAuthFilter jwtAuthFilter;

    @value("${geds.cors.allowed-origins}")
    private String[] allowedorigins;

    @Bean
    public SecurityFilterChain filterChain ( HttpSecurity http) throws Exception{
        http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfig()))
        .sessionManagement (sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELES))
        .authorizeHttpRequests (auth -> auth
            //esta sson rutas que son publicas 
        .requestMatchers(HttpMethod.POST, "/auth/**").permiAll()
        .requestMatchers(HttpMethod.GET, "/recintos/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/newsletter").permitAll()
            //estas son rutas que son exclusivas para el admin
            .requestMatchers("/admin/**").hasRole("ADMIN")
            //rutas que requieren de autentificacion
            .requestmatchers().authenticated()
        )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return  new BCryptPasswordEncoder(12);
    }
    @Bean 
    public AuthenticationManager authenticationManager (AuthenticationConfiguration cfg) 
    throws Exception {
        return cfg.getAuthenticationManager();
    }

    private CorsConfigurationSource corsConfig(){
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of(allowedorigins));
        
    }
}