package br.com.alura.screenmatchSpring.Controller;

import br.com.alura.screenmatchSpring.dto.SerieDTO;
import br.com.alura.screenmatchSpring.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class SerieController {

    @Autowired
    private SerieService service;

    @GetMapping("/series")
    public List<SerieDTO> obterSeries() {
        return service.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> top5Series(){
        return service.top5Series();
    }
    @GetMapping("/{name}")
    public List<SerieDTO> buscarSeriePorTitulo(@PathVariable String name){
        return service.obeterSeriePorTitulo(name);
    }

}
