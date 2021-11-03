package br.gov.pa.saude.api;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import br.gov.pa.saude.api.config.property.SespaApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(SespaApiProperty.class)
public class AutenticaApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AutenticaApiApplication.class, args);
		// TODO Rever as permissoes dos recursos em resourceserver.
		
		// TODO Auditoria: recuperar o usuário que esta fazendo a requisição, em algum recurso. Usar para auditoria? https://www.algaworks.com/forum/topicos/58712/boa-tarde-como-eu-faria-pra-recuperar-o-usuario-que-esta-fazendo-a-requisicao-em-um-dos-metodos
		// TODO Definir origin para o profile de produção. Usar config.setAllowedOriginPatterns?
		// TODO Rate access limit
		// TODO Cache 2 nivel JPA
		
		// TODO Documentar as classes, métodos envolvidos na autenticação e autorização.		
		// TODO OpenAPI: estudar como usar com autenticação.
		// TODO Flyway: remover em produção.

		// TODO Resposta de erro das requisições padronizadas. Ver video algaworks no instagran.
		// TODO Decidir regra negocio: Trazer todos os telefones de uma vez ou não? Se não trouxer terá a paginação e isso implica que a população não conhece os setores então não faz sentido trazer paginado com lazy. Nesse caso teria que trazer tudo e aplicar rate access limit e cache de 2 nivel. 
		
	}

	@PostConstruct
	public void init() {
		// Atribuindo TimeZone no Spring Boot. Força timezone UTC em todo back-end. Os horários de timezones para outros locais só podem ser definidos nos clientes web (Angular por exemplo).
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}
