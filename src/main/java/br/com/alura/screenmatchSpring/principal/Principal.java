package br.com.alura.screenmatchSpring.principal;

import br.com.alura.screenmatchSpring.model.DadosEpisodio;
import br.com.alura.screenmatchSpring.model.DadosSerie;
import br.com.alura.screenmatchSpring.model.DadosTemporada;
import br.com.alura.screenmatchSpring.service.ConsumoAPI;
import br.com.alura.screenmatchSpring.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private ConsumoAPI api = new ConsumoAPI();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    public void exibeMenu(){
        while (true) {

            System.out.println("Digite o nome da série para a busca:");
            var nomeSerie = leitura.nextLine();

            var json = consumo.obterDados(
                    ENDERECO + nomeSerie.replace(" ", "+") + API_KEY
            );

            DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
            System.out.println(dados);

            System.out.println("Deseja ver todos os episódios? (Sim / Sair)");
            var resposta = leitura.nextLine();

            if (resposta.equalsIgnoreCase("sair")) {
                System.out.println("Obrigado, até mais!");
                break;
            }

            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= dados.totalTemporadas(); i++) {
                json = api.obterDados(
                        ENDERECO + nomeSerie.replace(" ", "+") +
                                "&Season=" + i +
                                API_KEY
                );

                DadosTemporada dadosTemporada =
                        conversor.obterDados(json, DadosTemporada.class);

                temporadas.add(dadosTemporada);
            }

            // Imprime episódios
            temporadas.forEach(
                    t -> t.episodios().forEach(
                            e -> System.out.println(e.titulo())
                    )
            );
        }
    }
}
