package br.com.alura.screenmatch.dto;

import br.com.alura.screenmatch.model.Frase;

public record FraseDTO(String frase, String personagem, String titulo, String poster) {
    public FraseDTO(Frase frase) {
        this(frase.getFrase(), frase.getPersonagem(), frase.getTitulo(), frase.getPoster());
    }
}
