package br.com.vinicius.screenmatch;

import br.com.vinicius.screenmatch.Model.DadoSerie;
import br.com.vinicius.screenmatch.Model.DadosEpisodios;
import br.com.vinicius.screenmatch.Model.DadosTemporada;
import br.com.vinicius.screenmatch.Services.ConsumoApi;
import br.com.vinicius.screenmatch.Services.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

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
		json = consumoApi.obterDados("https://www.omdbapi.com/?t=breaking+bad&season=1&episode=2&apikey=cdf3e920");
		DadosEpisodios dadosep = conversor.obterDados(json, DadosEpisodios.class);
		System.out.println(dadosep);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i<= dados.totalTemporadas(); i++){
			json = consumoApi.obterDados("https://www.omdbapi.com/?t=Lost&season=" + i + "&apikey=cdf3e920");
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

	}
}
