package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.model.EnumCategoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Frase;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(EnumCategoria categoria);

     List<Serie> findByTotalTemporadasLessThanEqualAndAndAvaliacaoGreaterThanEqual(Integer temporadas, Double avaliacao);

     @Query(value = "select s from Serie s WHERE s.totalTemporadas <=:totalTemporadas AND s.avaliacao >=:avaliacao")
     List<Serie> seriesPorTemporadaEAvaliacao(Integer totalTemporadas, Double avaliacao);

     @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio%")
    List<Episodio> episodiosPorTrecho(String trechoEpisodio);

     @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosPorSerie(Serie serie);
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR(e.dataLancamento) >= :anoLancamento")
    List<Episodio> episodiosPorSerieEPorAno(Serie serie, int anoLancamento);

//    List<Serie> findTop5ByOrderByEpisodiosDataLancamentoDesc();

    @Query("SELECT s FROM Serie s " +
            "JOIN s.episodios e " +
            "GROUP BY s " +
            "ORDER BY MAX(e.dataLancamento) DESC LIMIT 5")
    List<Serie> encontrarEpisodiosMaisRecentes();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numero")
    List<Episodio> obterEpisoriodPorTemporada(Long id, Integer numero);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> encontrarTop5Episodios(Long id);

    @Query("SELECT f FROM Frase f order by function('RANDOM') LIMIT 1")
    Frase obterFraseAleatoria();
}
