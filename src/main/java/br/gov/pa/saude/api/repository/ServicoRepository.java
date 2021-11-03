package br.gov.pa.saude.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.pa.saude.api.model.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
	
	@Query(value = "SELECT s.nome FROM usuarios u INNER JOIN servico s ON u.id = s.usuarios_id WHERE u.login = :login", nativeQuery = true)
	public List<String> servicosForUser(String login);

}
