package br.gov.pa.saude.api.resource;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.gov.pa.saude.api.model.Usuario;
import br.gov.pa.saude.api.repository.UsuarioRepository;
import br.gov.pa.saude.api.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public Page<Usuario> pesquisar(@RequestParam(defaultValue = "") String login, Pageable pageable) {
		return usuarioRepository.findByLoginContainingIgnoreCaseOrderByLoginAsc(login, pageable);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Usuario> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Usuario> usuario = usuarioRepository.findById(codigo);
		return usuario.isPresent() ? ResponseEntity.ok(usuario.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) {
		Usuario usuarioSalvo = usuarioService.criar(usuario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(usuarioSalvo.getId()).toUri();
		return ResponseEntity.created(uri).body(usuarioSalvo);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long codigo, @Valid @RequestBody Usuario usuario) {
		Usuario usuarioSalvo = usuarioService.atualizar(codigo, usuario);
		return ResponseEntity.ok(usuarioSalvo);
	}

	@PutMapping("/{codigo}/resetar-password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadePasswordResetar(@PathVariable Long codigo) {
		usuarioService.atualizarPropriedadePasswordResetar(codigo);
	}

	@PutMapping("/{codigo}/alterar-password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadePasswordAlterar(@PathVariable Long codigo, @RequestBody Map<String, String> senhaMap) {
		usuarioService.atualizarPropriedadePasswordAlterar(codigo, senhaMap);
	}

	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		usuarioService.atualizarPropriedadeAtivo(codigo, ativo);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		usuarioRepository.deleteById(codigo);
	}

}
