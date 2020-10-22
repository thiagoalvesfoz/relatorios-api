package br.com.desbravador.projetoacelera.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import br.com.desbravador.projetoacelera.users.service.UserDetailServiceImpl;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
	private TokenStore tokenStore= new InMemoryTokenStore();
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.tokenStore(tokenStore)
			.authenticationManager(authenticationManager)
			.userDetailsService(userDetailServiceImpl);
	}	

	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//CONFIGURA APLICAÇÕES AUTORIZAÇÃO COMO O ANGULAR/REACT/ETC
		clients
			.inMemory()
			.withClient("my-react-app")
			.secret(new BCryptPasswordEncoder().encode("@321"))
			.scopes("read", "write")
			.authorizedGrantTypes("password", "authorization_code", "refresh_token")
			.resourceIds("restService")
			.accessTokenValiditySeconds(15)
			.refreshTokenValiditySeconds(3600);
	}
	
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setTokenStore(tokenStore);
		return tokenServices;
	}
}
