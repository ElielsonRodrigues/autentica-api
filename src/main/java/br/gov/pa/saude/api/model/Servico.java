package br.gov.pa.saude.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;


@Entity
@Data
@Table(name = "servico")
public class Servico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 10, max = 100)
	private String nome;
	
	private String descricao;
	
	@NotBlank
	@Size(min = 10, max = 255)
	private String link;
	
	@NotEmpty
	@ManyToOne()
	@JoinColumn(name = "usuarios_id", referencedColumnName = "id")
	private Usuario usuario;

	
}
