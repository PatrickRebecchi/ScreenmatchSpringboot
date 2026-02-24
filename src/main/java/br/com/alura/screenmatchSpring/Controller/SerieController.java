package br.com.alura.screenmatchSpring.Controller;

import br.com.alura.screenmatchSpring.dto.EpisodioDTO;
import br.com.alura.screenmatchSpring.dto.SerieDTO;
import br.com.alura.screenmatchSpring.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService service;

    @GetMapping("/teste")
    public String home() {
        return "API Screenmatch está online 🚀";
    }

    @GetMapping()
    public List<SerieDTO> obterSeries() {
        return service.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> top5Series(){
        return service.top5Series();
    }
    @GetMapping("/nome/{name}")
    public List<SerieDTO> buscarSeriePorTitulo(@PathVariable String name){
        return service.obeterSeriePorTitulo(name);
    }

    @GetMapping("/{id}")
    public SerieDTO buscarPorId(@PathVariable long id){
        return  service.obterPorId(id);
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamento(){
        return service.obterLancamentos();
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable long id){
        return service.obterTodasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioDTO> obterTemporadasPorNumero(@PathVariable long id, @PathVariable long numero){
        return service.obterTemporadasPorNumero(id, numero);

    }

    @GetMapping("/categoria/{id1}")
    public List<SerieDTO> obterSeriePorCategoria(@PathVariable("id1") String nomeGenero){
        return service.obterSeriePorCategoria(nomeGenero);
    }
    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> obeterTop5Episodios(@PathVariable long id){
        return service.obterTopEpisodios(id);
    }


    @PostMapping("/importar")
    public ResponseEntity<SerieDTO> importarSerie(@RequestParam String titulo) {
        return ResponseEntity.ok(service.importarSerie(titulo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletar(@PathVariable long id){
        service.deletarSerie(id);
        return ResponseEntity.ok(
                Map.of("mensagem", "Série deletada com sucesso")
        );
    }

    // modo de usar o @PathVariable diferente
// @GetMapping("/autores/{id1}/livros/{id2}")
// public String obterLivroPeloId
// (@PathVariable(“id1”) Integer idAutor, @PathVariable(“id2” ) Integer idLivro){
}
