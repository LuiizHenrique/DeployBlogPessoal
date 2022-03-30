package org.Generation.blogPessoal.controller;

import java.util.List;

import javax.validation.Valid;

import org.Generation.blogPessoal.model.Postagem;
import org.Generation.blogPessoal.repositary.PostagemRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/postagens")
@Tag(name = "Recursos de Postagem", description = "Administração das Postagens")
@CrossOrigin("*")

public class PostagemController {
	@Autowired
	private PostagemRepositary repositoty;

	@Operation(summary = "Busca lista de postagens")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retorna todas as postagens"),
			@ApiResponse(responseCode = "400", description = "Retorno sem postagens"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor") })

	@GetMapping
	public ResponseEntity<List<Postagem>> GetAll() {

		return ResponseEntity.ok(repositoty.findAll());

	}

	@Operation(summary = "Busca postagem por id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retorna postagem existente"),
			@ApiResponse(responseCode = "400", description = "Postagem inexistente"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor") })

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> GetById(@PathVariable long id) {
		return repositoty.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Busca postagem pelo titulo")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retorna postagem de acordo com o titulo"),
			@ApiResponse(responseCode = "400", description = "Retorno sem postagem"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor") })

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> GetByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(repositoty.findAllByTituloContainingIgnoreCase(titulo));

	}

	@Operation(summary = "Cria nova Postagem")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Postagem criado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição"),
			@ApiResponse(responseCode = "422", description = "Postagem já existente"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor") })

	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repositoty.save(postagem));

	}

	@Operation(summary = "Atualiza Postagem existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Retorna Postagem Atualizada"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor") })

	@PutMapping
	public ResponseEntity<Postagem> pust(@RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.OK).body(repositoty.save(postagem));
	}

	@Operation(summary = "Deleta Postagem existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Postagem deletada"),
			@ApiResponse(responseCode = "400", description = "Id de postagem inválido"), })

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		repositoty.deleteById(id);
	}
}
