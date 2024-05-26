package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    
    private List<Serie> series = new ArrayList<>();

    private List<DadosSerie> dadosSeries = new ArrayList();

    private SerieRepository repositorioSerie;

    public Principal(SerieRepository repositorioSerie) {
        this.repositorioSerie = repositorioSerie;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                1 - Buscar séries
                2 - Buscar episódios
                3 - Listar séries buscadas
                4 - Buscar série por título
                5 - Buscar séries por ator
                6 - Buscar top 5 séries
                7 - Buscar séries por categoria
                8 - Buscar séries por quantidade máxima de temporadas e avaliação
                9 - Busca por episódio
                10 - Top episódios por série
                11 - Buscar episódios a partir de uma data
                0 - Sair                                 
                """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5: 
                    buscarSeriesPorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 8:
                    buscarSeriesTopTemporadasPorAvaliacao();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break;
                case 10:
                    topEpisodiosPorSerie();
                    break;
                case 11:
                    buscarEpisodiosAPartirDeUmaData();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarEpisodiosAPartirDeUmaData() {
        Optional<Serie> serieBuscada = buscarSeriePorTitulo();
        if(serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();

            System.out.println("Digite o ano limite de lançamento:");
            var anoLancamento = leitura.nextInt();
            leitura.nextLine();
            List<Episodio> episodiosPorAno = repositorioSerie.episodiosPorSerieEPorAno(serieBuscada.get(), anoLancamento);
            episodiosPorAno.forEach(System.out::println);
        }
    }

    private void topEpisodiosPorSerie() {
        Optional<Serie> serieBuscada = buscarSeriePorTitulo();
        if (serieBuscada.isPresent()) {
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = repositorioSerie.topEpisodiosPorSerie(serie);
            topEpisodios.forEach(e ->
                    System.out.printf("Série: %s Temporada %s - Episódio %s - %s -10 Avaliação %s\n",
                            e.getSerie().getTitulo(), e.getTemporada(),
                            e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()));
        }
    }

    private void buscarEpisodioPorTrecho() {
        System.out.println("Nome do episódio para busca:");
        var trechoEpisodio = leitura.nextLine();
        List<Episodio> episodiosEncontrados = repositorioSerie.episodiosPorTrecho(trechoEpisodio);
        episodiosEncontrados.forEach(e ->
                System.out.printf("Série: %s Temporada %s - Episódio %s - %s\n",
                        e.getSerie().getTitulo(), e.getTemporada(),
                        e.getNumeroEpisodio(), e.getTitulo()));
    }

    private void buscarSeriesTopTemporadasPorAvaliacao() {
        System.out.println("Até quantas temporadas?");
        var totalTemporadas = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Avaliações a partir de que valor?");
        var avaliacao = leitura.nextDouble();
        leitura.nextLine();
//        List<Serie> seriesTop3Temporadas = repositorioSerie.findByTotalTemporadasLessThanEqualAndAndAvaliacaoGreaterThanEqual(totalTemporadas, avaliacao);
        List<Serie> seriesTop3Temporadas = repositorioSerie.seriesPorTemporadaEAvaliacao(totalTemporadas, avaliacao);
        seriesTop3Temporadas.forEach(System.out::println);
    }

    private void buscarSeriesPorCategoria() {
        System.out.println("Deseja buscar séries de qual categoria/gênero?");
        String nomeGenero = leitura.nextLine();
        EnumCategoria categoria = EnumCategoria.fromPortugues(nomeGenero);
        List<Serie> seriesPorCategoria = repositorioSerie.findByGenero(categoria);
        System.out.println("Séries da categoria:");
        seriesPorCategoria.forEach(System.out::println);
    }

    private void buscarTop5Series() {
        List<Serie> seriesTop = repositorioSerie.findTop5ByOrderByAvaliacaoDesc();
        seriesTop.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));
    }

    private List<Serie> buscarSeriesPorAtor() {
        System.out.println("Nome do ator para busca:");
        var nomeAtor = leitura.nextLine();
        System.out.println("Avaliações a partir de que valor?");
        var avaliacao = leitura.nextDouble();
        List<Serie> seriesEncontradas = repositorioSerie.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        if (!seriesEncontradas.isEmpty()) {
            System.out.println("Séries em que " + nomeAtor + " trabalhou:");
            seriesEncontradas.forEach(s ->
                    System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));
        }
        return seriesEncontradas;
    }

    private void listarSeriesBuscadas() {
        series = repositorioSerie.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        DadosSerie dadosSerie = getDadosSerie();
//        dadosSeries.add(dadosSerie);
        Serie serie = new Serie(dadosSerie);
        repositorioSerie.save(serie);
        System.out.println(dadosSerie);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = leitura.nextLine();
        Optional<Serie> serie = repositorioSerie.findByTituloContainingIgnoreCase(nomeSerie);

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<Temporada> temporadas = new ArrayList<>();
            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                Temporada dadosTemporada = conversor.obterDados(json, Temporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(temp -> temp.episodios().stream()
                            .map(ep -> new Episodio(temp.numero(), ep)))
                    .toList();
            serieEncontrada.setEpisodios(episodios);
            repositorioSerie.save(serieEncontrada);
        } else {
            System.out.println("Série não encontrada!");
        }


    }

    private Optional<Serie> buscarSeriePorTitulo() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        Optional<Serie> serieBuscada = repositorioSerie.findByTituloContainingIgnoreCase(nomeSerie);
        if(serieBuscada.isPresent()) {
            System.out.println("Dados da série: " + serieBuscada.get());
        } else {
            System.out.println("Série não encontrada!");
        }
        return serieBuscada;
    }

//    public static Categoria fromPortugues(String text) {
//        for (Categoria categoria : Categoria.values()) {
//            if (categoria.categoriaPortugues.equalsIgnoreCase(text)) {
//                return categoria;
//            }
//        }
//        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
//    }

}
