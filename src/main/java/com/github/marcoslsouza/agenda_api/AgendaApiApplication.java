package com.github.marcoslsouza.agenda_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.github.marcoslsouza.agenda_api.model.entity.Contato;
import com.github.marcoslsouza.agenda_api.model.repository.ContatoRepository;

@SpringBootApplication
public class AgendaApiApplication {
	
	@Bean
	public CommandLineRunner commandLineRunner(@Autowired ContatoRepository repository) {
		return args -> {
			Contato c = new Contato();
			c.setEmail("mauricio@gmail.com.br");
			c.setNome("Mauricio");
			c.setFavorito(false);
			repository.save(c);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(AgendaApiApplication.class, args);
	}

}
