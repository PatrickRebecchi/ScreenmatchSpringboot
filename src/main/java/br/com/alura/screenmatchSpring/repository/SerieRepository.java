package br.com.alura.screenmatchSpring.repository;

import br.com.alura.screenmatchSpring.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {

}
