package br.com.desbravador.projetoacelera.auth.config;

import br.com.desbravador.projetoacelera.auth.JWTAuthenticationFilter;
import br.com.desbravador.projetoacelera.auth.JWTAuthorizationFilter;
import br.com.desbravador.projetoacelera.auth.JWTUtil;
import br.com.desbravador.projetoacelera.auth.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment env;
    private final UserDetailsServiceImpl userDetailsService;
    private final JWTUtil jwtUtil;

    @Autowired
    public SecurityConfig(Environment env, UserDetailsServiceImpl userDetailsService, JWTUtil jwtUtil) {
        this.env = env;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    private static final String[] PUBLIC_MATCHERS = {
            "/",
            "/h2-console/**",
            "/reset_password/**"
    };

    private static final String[] PUBLIC_MATCHERS_POST = {
            "/api/users",
            "/auth/forgot_password/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
           http.headers().frameOptions().disable();
        }

        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated();
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /*
     * Define a bean, giving basic access
     * from multiple sources
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
