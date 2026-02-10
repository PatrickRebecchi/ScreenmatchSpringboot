package br.com.alura.screenmatchSpring.repository;

import br.com.alura.screenmatchSpring.model.Categoria;
import br.com.alura.screenmatchSpring.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    Optional<Serie> findById(int idSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, double avaliacao);

    List<Serie> findByGenero(Categoria genero);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

//    List<Serie> findByAtoresContainingIgnoreCase(String nomeDosAtores);
//    Optional<Serie> findByAtoresContainingIgnoreCase(String nomeAtores);
}
