package br.gov.pa.saude.api.service;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.gov.pa.saude.api.config.property.SespaApiProperty;
import br.gov.pa.saude.api.exceptionhandler.NegocioException;
import br.gov.pa.saude.api.model.Usuario;
import br.gov.pa.saude.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private SespaApiProperty sespaApiProperty;

	public Usuario criar(Usuario usuario) {

		if (existeUsuarioComLogin(usuario.getLogin())) {
			throw new NegocioException("Já existe um usuário cadastrado com este e-mail.");
		}

		usuario.setSenha(encoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}

	public Usuario atualizar(@PathVariable Long codigo, @RequestBody Usuario usuario) {
		Usuario usuarioSalvo = buscarUsuarioPeloCodigo(codigo);

		if (!usuario.getLogin().equalsIgnoreCase(usuarioSalvo.getLogin()) && existeUsuarioComLogin(usuario.getLogin())) {
			throw new NegocioException("Já existe um usuário cadastrado com este e-mail.");
		}
		
		System.out.println("senha fron= " + usuario.getSenha());
		System.out.println("senha back= " + usuarioSalvo.getSenha());

		//usuario.setSenha(encoder.encode(usuario.getSenha()));
		//BeanUtils.copyProperties(usuario, usuarioSalvo, "id");
		BeanUtils.copyProperties(usuario, usuarioSalvo, "id", "senha");
		return usuarioRepository.save(usuarioSalvo);
	}

	// TODO Usar JPQL para atualizar somente uma propriedade do objeto?
	public void atualizarPropriedadePasswordResetar(Long codigo) {
		Usuario usuarioSalvo = buscarUsuarioPeloCodigo(codigo);

		usuarioSalvo.setSenha(encoder.encode(sespaApiProperty.getSeguranca().getPasswordPadrao()));
		usuarioRepository.save(usuarioSalvo);
	}

	// TODO Usar JPQL para atualizar somente uma propriedade do objeto?
	public void atualizarPropriedadePasswordAlterar(Long codigo, Map<String, String> senhaMap) {
		String senhaAntiga = senhaMap.get("senhaAntiga");
		String novaSenha1 = senhaMap.get("novaSenha1");
		String novaSenha2 = senhaMap.get("novaSenha2");

		Usuario usuarioSalvo = buscarUsuarioPeloCodigo(codigo);

		if (!BCrypt.checkpw(senhaAntiga, usuarioSalvo.getSenha()) || !novaSenha1.equals(novaSenha2)) {
			throw new NegocioException("Verificar campos");
		}

		usuarioSalvo.setSenha(encoder.encode(novaSenha1));
		usuarioRepository.save(usuarioSalvo);
	}

	// TODO Usar JPQL para atualizar somente uma propriedade do objeto?
	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Usuario usuarioSalvo = buscarUsuarioPeloCodigo(codigo);
		usuarioSalvo.setAtivo(ativo);
		usuarioRepository.save(usuarioSalvo);
	}

	private Usuario buscarUsuarioPeloCodigo(Long codigo) {
		return usuarioRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

	private boolean existeUsuarioComLogin(String login) {
		return usuarioRepository.existsUsuarioByLogin(login);
	}

}
