package br.com.alura.screenmatchSpring.Controller;

import br.com.alura.screenmatchSpring.dto.EpisodioDTO;
import br.com.alura.screenmatchSpring.dto.SerieDTO;
import br.com.alura.screenmatchSpring.service.SerieService;
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
    private SerieService service;

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

//    @GetMapping("/{id}")
//    public List<SerieDTO> buscarPorId(@PathVariable long id){
//        return service.obterPorId(id);
//    }


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

}
