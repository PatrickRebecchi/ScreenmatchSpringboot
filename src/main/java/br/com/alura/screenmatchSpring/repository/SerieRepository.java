package br.com.alura.screenmatchSpring.repository;

import br.com.alura.screenmatchSpring.model.Categoria;
import br.com.alura.screenmatchSpring.model.Episodio;
import br.com.alura.screenmatchSpring.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    Optional<Serie> findById(int idSerie);
    // usando a JPQL para puxar o id
    @Query("select s from Serie s where s.Id = :id")
    Optional<Serie> buscarPorId(int id);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, double avaliacao);

    List<Serie> findByGenero(Categoria genero);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();


    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(Integer totalDeTemporada, double avalicao);

    @Query("select s from Serie s Where s.totalTemporadas <= :totalDeTemporada AND s.avaliacao >= :avalicao")
    List<Serie> seriesPorTemporadaEAvaliacao(Integer totalDeTemporada, double avalicao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio%")
    List<Episodio> episodiosPorTrecho(String trechoEpisodio);
}
