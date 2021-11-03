package br.gov.pa.saude.api.config.token;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import br.gov.pa.saude.api.repository.PerfilRepository;
import br.gov.pa.saude.api.repository.ServicoRepository;
import br.gov.pa.saude.api.security.UsuarioSistema;

public class CustomTokenEnhancer implements TokenEnhancer {
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
		
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) authentication.getPrincipal();
		Map<String, Object> addInfo = new HashMap<>();
		addInfo.put("id", usuarioSistema.getUsuario().getId());
		addInfo.put("sgp", usuarioSistema.getUsuario().getSgpId());
		addInfo.put("perfis", perfilRepository.perfilForUser(usuarioSistema.getUsername()));
		addInfo.put("servicos", servicoRepository.servicosForUser(usuarioSistema.getUsername()));
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(addInfo);
		return accessToken;
	}

}
