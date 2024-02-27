package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoAPI consumoAPI = new ConsumoAPI();
		String url = "https://omdbapi.com/?t=friends&apikey=20d713bd";
		var json = consumoAPI.obterDados(url);
		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		Serie serie = conversor.obterDados(json, Serie.class);
		System.out.println(serie);
	}
}
