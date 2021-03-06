package br.gov.pa.saude.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import br.gov.pa.saude.api.config.property.SespaApiProperty;


@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private SespaApiProperty sespaApiProperty;

	// TODO rever as permissões
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors()
			.and()
				.authorizeRequests()
				.antMatchers("/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() // TODO Comentar em produção
				/*.antMatchers(HttpMethod.GET, "/permissoes").hasAuthority("ROLE_PESQUISAR_USUARIO")
				.antMatchers(HttpMethod.GET, "/usuarios/**").hasAuthority("ROLE_PESQUISAR_USUARIO")
				.antMatchers(HttpMethod.POST, "/usuarios/**").hasAuthority("ROLE_CADASTRAR_USUARIO")
				.antMatchers(HttpMethod.PUT, "/usuarios/{\\d+}/alterar-password").hasAnyAuthority("ROLE_CADASTRAR_USUARIO", "ROLE_ALTERAR_PASSWORD")
				.antMatchers(HttpMethod.PUT, "/usuarios/**").hasAuthority("ROLE_CADASTRAR_USUARIO")
				.antMatchers(HttpMethod.DELETE, "/usuarios/**").hasAuthority("ROLE_REMOVER_USUARIO")*/
				.anyRequest().authenticated()
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.csrf().disable();
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(true);
	}
	
	@Bean
	public MethodSecurityExpressionHandler createExpressionHandler() {
		return new OAuth2MethodSecurityExpressionHandler();
	}
	
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowCredentials(true);
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setMaxAge(3600L);
		config.setAllowedOrigins(Arrays.asList(sespaApiProperty.getSeguranca().getOriginPermitida().split(",")));
		//config.setAllowedOriginPatterns(Arrays.asList("*")); // Habilita o cors para qualquer origem. Testar
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return new CorsFilter(source);
	}
	
}
