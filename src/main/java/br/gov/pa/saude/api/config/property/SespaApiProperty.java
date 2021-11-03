package br.gov.pa.saude.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("sespa")
public class SespaApiProperty {

	private String exemploPropriedade = "exemplo"; // Exemplo demonstrando que pode ser usado sem agrupar por uma classe
	private final Seguranca seguranca = new Seguranca();

	public String getExemploPropriedade() {
		return exemploPropriedade;
	}

	public void setExemploPropriedade(String exemploPropriedade) {
		this.exemploPropriedade = exemploPropriedade;
	}

	public Seguranca getSeguranca() {
		return seguranca;
	}

	public static class Seguranca {

		/**
		 * Configura o https da API. Quando não informado o valor false é atribuido por padrão.
		 */
		private boolean enableHttps; // false é o default deste tipo primitivo.

		/**
		 * Configura as origens permitidas para requisições cross-origin na API. Quando não informado o valor "http://localhost" é o padrão. Ex.:
		 * http://dominio1.com,http://dominio2.com,http://dominio3.com
		 */
		private String originPermitida = "http://localhost:8080,http://localhost:8081";

		/**
		 * Define o password padrão que será utilizado para resetar senhas.
		 */
		private String passwordPadrao = "123456";

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}

		public String getOriginPermitida() {
			return originPermitida;
		}

		public void setOriginPermitida(String originPermitida) {
			this.originPermitida = originPermitida;
		}

		public String getPasswordPadrao() {
			return passwordPadrao;
		}

		public void setPasswordPadrao(String passwordPadrao) {
			this.passwordPadrao = passwordPadrao;
		}

	}

}
