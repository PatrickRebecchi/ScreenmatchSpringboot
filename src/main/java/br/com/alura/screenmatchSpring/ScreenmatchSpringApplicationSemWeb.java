package br.com.alura.screenmatchSpring;

import br.com.alura.screenmatchSpring.principal.Principal;
import br.com.alura.screenmatchSpring.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
//public class ScreenmatchSpringApplicationSemWeb implements CommandLineRunner {
//
//	public static void main(String[] args) {
//		SpringApplication.run(ScreenmatchSpringApplicationSemWeb.class, args);
//	}
//    @Autowired
//    private SerieRepository repositorio;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        Principal principal = new Principal(repositorio);
//        principal.exibeMenu();
//
//    }
//}
