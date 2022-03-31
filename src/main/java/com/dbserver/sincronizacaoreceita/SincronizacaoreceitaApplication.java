package com.dbserver.sincronizacaoreceita;

import com.dbserver.sincronizacaoreceita.service.ProcessamentoArquivo;
import com.dbserver.sincronizacaoreceita.service.impl.ProcessamentoArquivoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SincronizacaoreceitaApplication implements CommandLineRunner {
    public static final String CONTAS_CSV = "contas.csv";

    @Autowired
    ProcessamentoArquivo process;

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(SincronizacaoreceitaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        process.processaArquivo(CONTAS_CSV);
    }

}
