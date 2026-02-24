package br.com.alura.screenmatchSpring.model;

import br.com.alura.screenmatchSpring.dto.SerieDTO;
import br.com.alura.screenmatchSpring.service.ConsultaChatGPT;
import br.com.alura.screenmatchSpring.service.ConsultaGemini;
import jakarta.persistence.*;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (unique = true)
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String atores;
    private String poster;
    private String sinopse;
    public Serie() {
    }

    @OneToMany(mappedBy = "serie", cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios = new ArrayList<>();
    public List<Episodio> getEpisodios() {
        return episodios;
    }
    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

    public Serie(SerieDTO dto) {
        this.titulo = dto.titulo();
        this.totalTemporadas = dto.totalTemporadas();
        this.avaliacao = dto.avaliacao();
        this.genero = dto.genero();
        this.atores = dto.atores();
        this.poster = dto.poster();
        this.sinopse = dto.sinopse();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public Serie(DadosSerie dadosSerie){
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = dadosSerie.avaliacao().equals("N/A")
                ? 0.0
                : Double.valueOf(dadosSerie.avaliacao());
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
        this.sinopse = ConsultaGemini // ou ConsultaChatGPT  ( Estou sem tokens do gpt por isso uso o Gemini )
                .obterTraducao(dadosSerie.sinopse()).trim();
    }

    @Override
    public String toString() {
        return
                "genero= " + genero +
                ", titulo= '" + titulo + '\'' +
                ", totalTemporadas= " + totalTemporadas +
                ", avaliacao= " + avaliacao +
                ", atores= '" + atores + '\'' +
                ", poster= '" + poster + '\'' +
                ", sinopse= '" + sinopse + '\'' +
                ", episodios= '" + episodios + '\'' +
                '}';
    }
}
