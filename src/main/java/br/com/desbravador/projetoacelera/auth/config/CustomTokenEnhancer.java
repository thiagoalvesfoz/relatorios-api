package br.com.desbravador.projetoacelera.auth.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import br.com.desbravador.projetoacelera.auth.service.UserDetailServiceImpl;


public class CustomTokenEnhancer implements TokenEnhancer {
	
	@Autowired
	private UserDetailServiceImpl service;
	
	@Override
    public OAuth2AccessToken enhance(
      OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("user", service.getUser(authentication.getName()));
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(
          additionalInfo);
        return accessToken;
    }
}
