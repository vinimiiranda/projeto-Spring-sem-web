package br.com.vinicius.screenmatch;

import br.com.vinicius.screenmatch.Model.DadoSerie;
import br.com.vinicius.screenmatch.Services.ConsumoApi;
import br.com.vinicius.screenmatch.Services.ConverteDados;
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
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=breaking+bad&apikey=cdf3e920");
		System.out.println(json);

		ConverteDados conversor = new ConverteDados();
		DadoSerie dados = conversor.obterDados(json,DadoSerie.class);
		System.out.println(dados);

	}
}
