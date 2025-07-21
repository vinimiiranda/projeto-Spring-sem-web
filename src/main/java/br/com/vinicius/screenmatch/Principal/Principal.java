package br.com.vinicius.screenmatch.Principal;

import br.com.vinicius.screenmatch.Model.DadoSerie;
import br.com.vinicius.screenmatch.Model.DadosEpisodios;
import br.com.vinicius.screenmatch.Model.DadosTemporada;
import br.com.vinicius.screenmatch.Services.ConsumoApi;
import br.com.vinicius.screenmatch.Services.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=cdf3e920";


      public void exibeMnu(){
          System.out.println("Digte o nome da serie para busca:");
          var nomeSerie = leitura.nextLine();
          var json = consumo.obterDados( ENDERECO + nomeSerie.replace(" ","+") + API_KEY);
          DadoSerie dados = conversor.obterDados(json,DadoSerie.class);
          System.out.println(dados);


          List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i<= dados.totalTemporadas(); i++){
			json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ","+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		 temporadas.forEach(System.out::println);

         temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

          List<DadosEpisodios> dadosEpisodios = temporadas.stream()
                  .flatMap(t -> t.episodios().stream())
                  .collect(Collectors.toList());

          System.out.println("\n Top 5 episódios");
          dadosEpisodios.stream()
                  .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                  .sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
                  .limit(5)
                  .forEach(System.out::println);




      }

}
