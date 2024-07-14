package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.dto.FraseDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "frases")
public class Frase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String frase;
    private String personagem;
    private String titulo;
    private String poster;


    public Frase(String frase, String personagem, String titulo, String poster) {
        this.frase = frase;
        this.personagem = personagem;
        this.titulo = titulo;
        this.poster = poster;
    }
    public Frase() {}

    public Frase(FraseDTO fraseDto) {
        this(fraseDto.frase(), fraseDto.personagem(), fraseDto.titulo(), fraseDto.poster());
    }

    public Long getId() {
        return id;
    }

    public String getFrase() {
        return frase;
    }

    public String getPersonagem() {
        return personagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getPoster() {
        return poster;
    }
}
