package br.gov.pa.saude.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pa.saude.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByLogin(String login);

	public boolean existsUsuarioByLogin(String email);

	public Page<Usuario> findByLoginContainingIgnoreCaseOrderByLoginAsc(String login, Pageable pageable);
	
}
