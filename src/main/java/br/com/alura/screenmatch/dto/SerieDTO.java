package br.com.alura.screenmatch.dto;

import br.com.alura.screenmatch.model.EnumCategoria;
import br.com.alura.screenmatch.model.Serie;


public record SerieDTO(Long id,
                           String titulo,
                           Integer totalTemporadas,
                           Double avaliacao,
                           EnumCategoria genero,
                           String atores,
                           String poster,
                           String sinopse) {

    public SerieDTO(Serie serie) {
        this(serie.getId(),
                serie.getTitulo(),
                serie.getTotalTemporadas(),
                serie.getAvaliacao(),
                serie.getGenero(),
                serie.getAtores(),
                serie.getPoster(),
                serie.getSinopse());
    }

}
