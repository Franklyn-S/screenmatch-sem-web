package br.com.alura.screenmatch.dto;

import br.com.alura.screenmatch.model.Episodio;

public record EpisodioDTO(Integer temporada, Integer numeroEpisodio, String titulo) {
    public EpisodioDTO (Episodio episodio) {
        this(episodio.getTemporada(), episodio.getNumeroEpisodio(), episodio.getTitulo());
    }
}
