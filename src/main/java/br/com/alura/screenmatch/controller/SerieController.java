package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.dto.FraseDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController

@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<SerieDTO> obterSeries() {
        return serieService.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series() {
        return serieService.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos() {
        return serieService.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Long id) {
        return serieService.obterPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id){
        return serieService.obterTodasTemporadas(id);
    }

    @GetMapping("/{idSerie}/temporadas/{numero}")
    public List<EpisodioDTO> obterTemporadaPorNumero(@PathVariable Long idSerie, @PathVariable Integer numero){
        return serieService.obterTemporadaPorNumero(idSerie, numero);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> obterTopTemporadas(@PathVariable Long id){
        return serieService.obterTopTemporadas(id);
    }

    @GetMapping("/categoria/{nome}")
    public List<SerieDTO> obterSeriesPorCategoria(@PathVariable String nome){
        return serieService.obterSeriesPorCategoria(nome);
    }

    @GetMapping("/frases")
    public FraseDTO obterFraseAleatoria() {
        return serieService.obterFraseAleatoria();
    }


}


