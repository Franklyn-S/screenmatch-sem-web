package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.EpisodioJson;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import br.com.alura.screenmatch.model.Temporada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private final String URL = "https://omdbapi.com/?t=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private final String API_KEY = "&apikey=20d713bd";
    public void exibeMenu(){
        System.out.println("Digite o nome da série para buscar:");
        Scanner scanner = new Scanner(System.in);
        String nomeSerie = scanner.nextLine().replace(" ", "+");
        String url = URL + nomeSerie + API_KEY;
        var json = consumoAPI.obterDados(url);
		Serie serie = conversor.obterDados(json, Serie.class);
        System.out.println(serie);


        List<Temporada> temporadas = new ArrayList<>();
		for (int i = 1; i <= serie.totalTemporadas(); i++) {
			url = URL + nomeSerie + "&Season=" + i + API_KEY;
			json = consumoAPI.obterDados(url);
			Temporada temporada = conversor.obterDados(json, Temporada.class);
			temporadas.add(temporada);
		}
		temporadas.forEach(System.out::println);
//        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

//        List<EpisodioJson> episodiosJson = temporadas.stream()
//                .flatMap(t -> t.episodios().stream())
//                .toList();
//
//        episodiosJson.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(EpisodioJson::avaliacao).reversed())
//                .limit(5)
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(e -> new Episodio(t.numero(), e))
                ).toList();

        episodios.stream()
                .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episodios?");
        var ano = scanner.nextInt();
        scanner.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " Episódio: " + e.getTitulo() +
                                "Data lançamento: " + e.getDataLancamento().format(dtf)
                ));
    }
}
