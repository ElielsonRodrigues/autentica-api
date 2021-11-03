package br.gov.pa.saude.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.pa.saude.api.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

	@Query(value = "SELECT CONCAT(s.nome,' - ', p.descricao) AS perfil FROM usuarios u INNER JOIN servico s ON u.id = s.usuarios_id INNER JOIN perfil p ON p.servico_id = s.id WHERE u.login = :login", nativeQuery = true)
	public List<String> perfilForUser(String login);
}
