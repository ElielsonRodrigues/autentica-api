package br.gov.pa.saude.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.pa.saude.api.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
	
	public boolean existsPermissaoByDescricao(String nome);
	
	@Query(value = "SELECT ps.descricao as descricao FROM usuarios u INNER JOIN servico s ON u.id = s.usuarios_id INNER JOIN perfil p ON p.servico_id = s.id INNER JOIN permissoes ps ON ps.perfil_id = p.id WHERE u.login = :login", nativeQuery = true)
	public List<String> permissaoForUser(String login);

}
