package br.com.desbravador.projetoacelera.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources
			.tokenServices(tokenServices())
			.resourceId("restService");
	}
	
	@Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        return converter;
    }
	
	@Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
	
	@Override
	public void configure(HttpSecurity http) throws Exception {		
		http.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.and().authorizeRequests()
			.antMatchers(HttpMethod.POST, "/api/users").permitAll()
			.antMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("ADMIN")
			.antMatchers("/api/consumidores").authenticated() 
			.antMatchers("/api/dicionarios").authenticated() 
			.antMatchers("/api/fontededados").authenticated() 
			.antMatchers("/api/produtos").authenticated() 
			.antMatchers("/api/relatorios").authenticated() 
			.anyRequest().denyAll();	
	}
	
}
