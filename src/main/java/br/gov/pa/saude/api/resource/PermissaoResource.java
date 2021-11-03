package br.gov.pa.saude.api.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.gov.pa.saude.api.model.Permissao;
import br.gov.pa.saude.api.repository.PermissaoRepository;
import br.gov.pa.saude.api.service.PermissaoService;

@RestController
@RequestMapping("/permissoes")
public class PermissaoResource {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private PermissaoService permissaoService;

	@GetMapping
	public List<Permissao> listar() {
		return permissaoRepository.findAll(Sort.by(Sort.Direction.ASC, "descricao"));
	}
	
	@PostMapping
	public ResponseEntity<Permissao> criar(@Valid @RequestBody Permissao permissao) {
		Permissao permissaoSalvar = permissaoService.criar(permissao);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(permissaoSalvar.getId()).toUri();
		return ResponseEntity.created(uri).body(permissaoSalvar);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Permissao> atualizar(@PathVariable Long codigo, @Valid @RequestBody Permissao permissao) {
		Permissao permissaoSalvar = permissaoService.atualizar(codigo, permissao);
		return ResponseEntity.ok(permissaoSalvar);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		permissaoRepository.deleteById(codigo);
	}

	
	
}
