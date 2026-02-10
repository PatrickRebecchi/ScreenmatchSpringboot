package br.com.alura.screenmatchSpring.principal;

import br.com.alura.screenmatchSpring.model.*;
import br.com.alura.screenmatchSpring.repository.SerieRepository;
import br.com.alura.screenmatchSpring.service.ConsumoAPI;
import br.com.alura.screenmatchSpring.service.ConverteDados;

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

    private List<Serie> series = new ArrayList<>();

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
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream().map(d -> new Episodio(t.numero(), d))
                    )
                    .collect(Collectors.toList());

            episodios.forEach(System.out::println);


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
                         4 - Buscar serie por titulo
                         5 - Buscar serie por Id
                         6 - Buscar todas séries de um ator/atriz
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
                    case 4:
                        buscarSeriePorTitulo();
                        break;
                    case 5:
                        buscarSeriePorId();
                        break;
                    case 6:
                        buscarSeriesAtores();
                        break;
                    case 7:
                        buscarSerieCategoria();
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
        listarSerieBuscada();
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = leitura.nextLine();

        Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

//        series.stream()
//                .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
//                .findFirst();

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }else {
            System.out.println("Série não encontrada!");
        }
    }

    private void listarSerieBuscada(){
//        List<Serie> series = new ArrayList<>();
//        series = dadosSeries.stream()
//                        .map(d-> new Serie(d))
//                                .collect(Collectors.toList());  Aqui eu posso subistituir esse codigo por um findAll()
        series = repositorio.findAll(); // Consigo puxar a lista do banco de dados
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);

    }


    private void buscarSeriePorTitulo() {

        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = leitura.nextLine();

        repositorio.findByTituloContainingIgnoreCase(nomeSerie)
                .ifPresentOrElse(
                        serie -> System.out.println("Dados da série: " + serie),
                        () -> System.out.println("Série não encontrada. ")
                );

//         Atualizando o codigo para um estrutura mais moderna, utilizando o ifPresentOrElse
//        Optional<Serie> serieBuscada = repositorio.findByTituloContainingIgnoreCase(nomeSerie);
//        if (serieBuscada.isPresent()){
//            System.out.println("Dados da série: "+ serieBuscada.get());
//        }else{
//            System.out.println("Série não encontrada.");
//        }
    }

    private void buscarSeriePorId() {
        System.out.println("Escolha uma série pelo Id: ");
        var idSerie = leitura.nextInt();

         repositorio.findById(idSerie)
                .ifPresentOrElse(
                        serie -> System.out.println("Dados da série: " + serie),
                        () -> System.out.println("Série não encontrada. ")
                );


//         Atualizando o codigo para um estrutura mais moderna, utilizando o ifPresentOrElse
//        Optional<Serie> serieBuscadaId = repositorio.findById(idSerie);
//        if (serieBuscudaId.isPresent()){
//            System.out.println("Dados da série: "+ serieBuscudaId.get());
//        }else{
//            System.out.println("Série não encontrada.");
//        }
    }

    private void buscarSeriesAtores() {
        System.out.println("Qual o nome para a busca? ");
        var nomeAtor = leitura.nextLine();
        System.out.println("Avaliações a partir de qual valor? ");
        var avalicao = leitura.nextDouble();
       // List<Serie> seriesEncontrada = repositorio.findByAtoresContainingIgnoreCase(nomeAtor);

        List<Serie> seriesEncontrada = repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avalicao);
        if (seriesEncontrada.isEmpty()) {
            System.out.println("Série não encontrada.");
        } else {
            //seriesEncontrada.forEach(s -> System.out.println("Dados da série: " + s));
            System.out.println("Séries em que " + nomeAtor + " Trabalhou: ");
            seriesEncontrada.forEach(s -> System.out.println(s.getTitulo() + ", Avaliação: " + s.getAvaliacao()));
        }
    }

}


