package br.com.alura.screenmatchSpring.service;

import br.com.alura.screenmatchSpring.model.DadosSerie;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);

}
