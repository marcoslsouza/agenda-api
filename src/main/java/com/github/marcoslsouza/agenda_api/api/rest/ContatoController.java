package com.github.marcoslsouza.agenda_api.api.rest;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.PKIXRevocationChecker.Option;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Part;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    // dominio/api/contatos/1/foto
    @PutMapping("{id}/foto")
    public byte[] addFoto(@PathVariable Long id, @RequestParam("foto") Part arquivo) {
    	Optional<Contato> contato = repository.findById(id);
    	// Converter contato para array de bytes
    	return contato.map(c -> {
    		try {
    			// Representa a string do arquivo
    			InputStream is = arquivo.getInputStream();
    			byte[] bytes = new byte[(int) arquivo.getSize()];
    			IOUtils.readFully(is, bytes);
    			c.setFoto(bytes);
    			repository.save(c);
    			is.close();
    			return bytes;
    		} catch(IOException ex) {
    			return null;
    		}
    	}).orElse(null);
    }
}
