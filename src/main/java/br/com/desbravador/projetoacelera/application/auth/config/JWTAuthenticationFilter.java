package br.com.desbravador.projetoacelera.application.auth.config;

import br.com.desbravador.projetoacelera.application.auth.dto.CredentialsDTO;
import br.com.desbravador.projetoacelera.application.auth.stream.UserSecurity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            var creds = new ObjectMapper()
                    .readValue(req.getInputStream(), CredentialsDTO.class);

            var authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>());

            return authenticationManager.authenticate(authToken);
        }catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException {
        var user = ((UserSecurity) authResult.getPrincipal());
        var token = jwtUtil.generateToken(user);

        String body = "{\"token\": \"" + token + "\"}";
        res.setContentType("Application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(body);
    }

    private static class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
                throws IOException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(prepareMessageToJson(ex.getMessage()));
        }

        private String prepareMessageToJson(String message) {
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            var date = formatter.format(OffsetDateTime.now());

            return "{\"timestamp\": \"" + date + "\", "
                    + "\"status\": 401, "
                    + "\"error\": \"Not authorized\", "
                    + "\"message\": \"" + message + "\", "
                    + "\"path\": \"/login\"}";
        }
    }
}
