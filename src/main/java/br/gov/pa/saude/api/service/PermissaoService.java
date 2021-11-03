package br.gov.pa.saude.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import br.gov.pa.saude.api.exceptionhandler.NegocioException;
import br.gov.pa.saude.api.model.Permissao;
import br.gov.pa.saude.api.repository.PermissaoRepository;

@Service
public class PermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	
	public Permissao criar(Permissao permissao) {
		if (existePermissao(permissao.getDescricao())) {
			throw new NegocioException("Esta permissão ja esta cadastrada.");
		}
		return permissaoRepository.save(permissao);
	}
	public Permissao atualizar(Long codigo, Permissao permissao) {
		Permissao permissaoSalva = buscarPeloCodigo(codigo);
		if (!permissao.getDescricao().equalsIgnoreCase(permissaoSalva.getDescricao()) && existePermissao(permissao.getDescricao())) {
			throw new NegocioException("Não é possivel cadastrar esta permissão, pois ela ja é existente.");
		}
		BeanUtils.copyProperties(permissao, permissaoSalva, "id");
		return permissaoRepository.save(permissaoSalva);
	}
	
	private boolean existePermissao(String nome) {
		return permissaoRepository.existsPermissaoByDescricao(nome);
	}
	private Permissao buscarPeloCodigo(Long codigo) {
		return permissaoRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}
}
