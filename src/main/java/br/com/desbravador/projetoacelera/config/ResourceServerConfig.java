package br.com.desbravador.projetoacelera.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("restService");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {		
		http.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.and().authorizeRequests()
			.antMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
			.antMatchers(HttpMethod.GET, "/api/usuarios").hasAnyRole("ADMIN")
			.antMatchers("/api/consumidores").authenticated() 
			.antMatchers("/api/dicionarios").authenticated() 
			.antMatchers("/api/fontededados").authenticated() 
			.antMatchers("/api/produtos").authenticated() 
			.antMatchers("/api/relatorios").authenticated() 
			.anyRequest().denyAll();	
	}

	
}
