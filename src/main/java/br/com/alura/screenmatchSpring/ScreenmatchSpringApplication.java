package br.com.alura.screenmatchSpring;

import br.com.alura.screenmatchSpring.model.DadosEpisodio;
import br.com.alura.screenmatchSpring.model.DadosSerie;
import br.com.alura.screenmatchSpring.service.ConsumoAPI;
import br.com.alura.screenmatchSpring.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchSpringApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchSpringApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        ConsumoAPI api = new ConsumoAPI();
        var json = api.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
        System.out.println(json);

        ConverteDados converso = new ConverteDados();
        DadosSerie dados = converso.obterDados(json, DadosSerie.class);
        System.out.println(dados);
        json = api.obterDados("https://www.omdbapi.com/?t=gilmore+girls&Season=1&episode=2&apikey=6585022c");
        DadosEpisodio dadosEpisodio = converso.obterDados(json, DadosEpisodio.class);
        System.out.println(dadosEpisodio);
    }
}
