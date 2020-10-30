package br.com.desbravador.projetoacelera.auth.config;

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
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import br.com.desbravador.projetoacelera.auth.service.UserDetailServiceImpl;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	/* credenciais aplicação react */
	private final String CLIENT = "my-react-app";
	private final String SECRET_KEY = "@321";
	
	/* tempo de expiração dos tokens */
	private final int ACCES_TOKEN_VALIDITY_SECONDS = 60 * 60;
	private final int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60;	
	
	private AuthenticationManager authenticationManager;
	private UserDetailServiceImpl userDetailServiceImpl;	
	private TokenStore tokenStore;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients();
	}


	@Autowired
	public AuthorizationServerConfig(AuthenticationManager auth, UserDetailServiceImpl service) {
		this.authenticationManager = auth;
		this.userDetailServiceImpl = service;
		this.tokenStore = new InMemoryTokenStore();
	}
	
	
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
			.withClient(CLIENT)
			.secret(toEncrypt(SECRET_KEY))
			.scopes("read", "write")
			.authorizedGrantTypes("password", "authorization_code", "refresh_token")
			.resourceIds("restService")
			.accessTokenValiditySeconds(ACCES_TOKEN_VALIDITY_SECONDS)
			.refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
	}
	

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setTokenStore(tokenStore);
		return tokenServices;
	}
	
	private String toEncrypt(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}

}
