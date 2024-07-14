package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.dto.FraseDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.EnumCategoria;
import br.com.alura.screenmatch.model.Frase;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obterTodasAsSeries() {
        return converteDados(repository.findAll());
    }

    public List<SerieDTO> obterTop5Series() {
        return converteDados(repository.findTop5ByOrderByAvaliacaoDesc());
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(repository.encontrarEpisodiosMaisRecentes());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);
        return serie.map(SerieDTO::new).orElse(null);
    }

    private List<SerieDTO> converteDados(List<Serie> series) {
        return series.stream().map(SerieDTO::new).collect(Collectors.toList());
    }

    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            return serieEncontrada.getEpisodios().stream()
                    .map(e ->  new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo())).collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obterTemporadaPorNumero(Long idSerie, Integer numero) {
        return repository.obterEpisoriodPorTemporada(idSerie, numero).stream()
                .map(EpisodioDTO::new).collect(Collectors.toList());
    }

    public List<SerieDTO> obterSeriesPorCategoria(String nome) {
        EnumCategoria categoria = EnumCategoria.fromPortugues(nome);
        return converteDados(repository.findByGenero(categoria));
    }

    public List<EpisodioDTO> obterTopTemporadas(Long id) {
        return repository.encontrarTop5Episodios(id).stream()
                .map(EpisodioDTO::new).collect(Collectors.toList());
    }

    public FraseDTO obterFraseAleatoria() {
        var fraseEncontrada = repository.obterFraseAleatoria();
        if (fraseEncontrada != null) {
            return new FraseDTO(repository.obterFraseAleatoria());
        }
        throw new NoSuchElementException("Não foi possível encontrar uma frase");
    }
}
