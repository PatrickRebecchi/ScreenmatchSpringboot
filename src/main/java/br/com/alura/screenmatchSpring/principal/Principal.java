package br.com.alura.screenmatchSpring.principal;

import br.com.alura.screenmatchSpring.model.*;
import br.com.alura.screenmatchSpring.repository.SerieRepository;
import br.com.alura.screenmatchSpring.service.ConsumoAPI;
import br.com.alura.screenmatchSpring.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private ConsumoAPI api = new ConsumoAPI();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    private SerieRepository repositorio;

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibirMenu() {
        while (true) {

            System.out.println("**********************************************");
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
            //imprimir todos os episodios
            //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

            List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream())
                    .collect(Collectors.toList());
//            System.out.println("\nTop 10 episódios");
//            dadosEpisodios.stream()
//                    .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                    .peek(e -> System.out.println("Primeiro filtro(N/A) " + e))
//                    .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                    .peek(e -> System.out.println("Ordenação " + e))
//                    .limit(10)
//                    .peek(e -> System.out.println("Limite " + e))
//                    .map(e -> e.titulo().toUpperCase())
//                    .peek(e -> System.out.println("Mapeamento " + e))
//                    .forEach(System.out::println);
//
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream().map(d -> new Episodio(t.numero(), d))
                    )
                    .collect(Collectors.toList());

            episodios.forEach(System.out::println);

//            System.out.println("A partir de que ano você deseja ver os episódios? ");
//            var ano = leitura.nextInt();
//            leitura.nextLine();
//
//            LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//            episodios.stream()
//                    .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                    .forEach(e -> System.out.println(
//                            "Temporada:  " + e.getTemporada() +
//                                    " Episódio: " + e.getTitulo() +
//                                    " Data lançamento: " + e.getDataLancamento().format(formatador)
//                    ));
//
//
//            System.out.println("Digite um trcho do titulo para buscar o episódio. ");
//
//            var trechoDoTitulo = leitura.nextLine();
//            Optional<Episodio> episodiobuscado = episodios.stream()
//                    .filter(e -> e.getTitulo().toUpperCase().contains(trechoDoTitulo.toUpperCase()))
//                    .findFirst();
//                    if (episodiobuscado.isPresent()){
//                        System.out.println("Episódio encontrado");
//                        System.out.println("Temporada: " + episodiobuscado.get());
//                    }else{
//                        System.out.println("Episódio não encontrado!");
//                    }

            Map<Integer, Double> avaliacaoDeTemporada = episodios.stream()
                    .filter(e -> e.getAvaliacao() > 0.0)
                    .collect(Collectors.groupingBy(Episodio::getTemporada,
                            Collectors.averagingDouble(Episodio::getAvaliacao)));
            System.out.println("Avaliação da temporada: " + avaliacaoDeTemporada);


            DoubleSummaryStatistics est = episodios.stream()
                    .filter(e -> e.getAvaliacao() > 0.0)
                    .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
            System.out.println("Dados da Serie: " + nomeSerie);
            System.out.println("Média: " + est.getAverage());
            System.out.println("Melhor episódio: " + est.getMax());
            System.out.println("Pior episódio: " + est.getMin());
            System.out.println("Total de episódios: " + est.getCount());
        }


    }

    private List<DadosSerie> dadosSeries = new ArrayList<>();


    public void exibeMenu() {
        //var opcao = leitura.nextInt();
        var opcao = -1;

        try {


            while (opcao != 0) {
                var menu = """
                         1 - Buscar séries
                         2 - Buscar episódios
                         3 - Listar series do banco de dados
                         0 - Sair                                \s
                        \s""";

                System.out.println(menu);
                opcao = leitura.nextInt();
                leitura.nextLine();

                switch (opcao) {
                    case 1:
                        buscarSerieWeb();
                        break;
                    case 2:
                        buscarEpisodioPorSerie();
                        break;
                    case 3:
                        listarSerieBuscada();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            }
        }catch (InputMismatchException e) {
            System.out.println("Valor inválido. Digite um número.");
            leitura.nextLine(); // LIMPA o valor inválido
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
//        dadosSeries.add(dados);
        repositorio.save(serie);  // codigo para slv os dados no banco de dados
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        DadosSerie dadosSerie = getDadosSerie();

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            var json = consumo.obterDados(
                    ENDERECO + dadosSerie.titulo().replace(" ", "+")
                            + "&season=" + i + API_KEY
            );
            DadosTemporada dadosTemporada =
                    conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        // Converte para Episodio (igual você já fazia)
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios()
                        .stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .toList();

        episodios.forEach(System.out::println);

        // Média por temporada
        Map<Integer, Double> avaliacaoPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(
                        Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)
                ));

        System.out.println("Avaliação por temporada: " + avaliacaoPorTemporada);

        // Estatísticas gerais
        DoubleSummaryStatistics stats = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("Dados da série: " + dadosSerie.titulo());
        System.out.println("Média: " + stats.getAverage());
        System.out.println("Melhor episódio: " + stats.getMax());
        System.out.println("Pior episódio: " + stats.getMin());
        System.out.println("Total de episódios avaliados: " + stats.getCount());
    }

    private void listarSerieBuscada(){
//        List<Serie> series = new ArrayList<>();
//        series = dadosSeries.stream()
//                        .map(d-> new Serie(d))
//                                .collect(Collectors.toList());  Aqui eu posso subistituir esse codigo por um findAll()

        List<Serie> series = repositorio.findAll(); // Consigo puxar a lista do banco de dados

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);

    }

}


