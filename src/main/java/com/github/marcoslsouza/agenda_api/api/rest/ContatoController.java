package com.github.marcoslsouza.agenda_api.api.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.marcoslsouza.agenda_api.model.entity.Contato;
import com.github.marcoslsouza.agenda_api.model.repository.ContatoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contatos")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ContatoController {

    private final ContatoRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contato save(@RequestBody @Valid Contato contato) {
    	return repository.save(contato);
    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // status 204 sucesso e não tem nenhum conteudo de retorno.
    public void delete(@PathVariable Long id) {
    	repository.deleteById(id);
    }
    
    @GetMapping
    public List<Contato> list() {
    	return repository.findAll();
    }
    
    // Em uma atualizacao total da entidade usamos @PutMapping, mas em uma atualizacao parcial usamos @PatchMapping
    @PatchMapping("{id}/favorito") // {id}/favorito para indicar que esta alterando o favorito
    public void favorite(@PathVariable Long id) {
    	Optional<Contato> contato = repository.findById(id);
    	contato.ifPresent(c -> {
    		c.setFavorito(c.getFavorito() == Boolean.TRUE ? false : true);
    		repository.save(c);
    	});
    }
}
