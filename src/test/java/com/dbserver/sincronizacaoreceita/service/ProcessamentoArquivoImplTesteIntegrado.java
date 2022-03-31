package com.dbserver.sincronizacaoreceita.service;

import com.dbserver.sincronizacaoreceita.service.impl.ProcessamentoArquivoImpl;
import com.dbserver.sincronizacaoreceita.service.impl.ReceitaServiceImpl;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ProcessamentoArquivoImplTesteIntegrado {
    public static final String CONTAS_CSV = "contas.csv";
    public static final String CAMINHO_ERRADO_CSV = "caminho-errado.csv";


    @Autowired
    private ProcessamentoArquivoImpl  service;

    @Autowired
    private ReceitaServiceImpl receitaService;

    @Autowired
    private LerCSVService lerCSVService;


    @Test
    void deveriaProcessarArquivo() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, InterruptedException {

        service.processaArquivo(CONTAS_CSV);

        var contaModels = lerCSVService.lerCSV(CONTAS_CSV);

        assertNotNull(contaModels);

    }


    @Test
    void naoDeveriaProcessarArquivoQuandoNaoEncontrar() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, InterruptedException {

        assertThrows(NoSuchFileException.class, () -> service.processaArquivo(CAMINHO_ERRADO_CSV));
    }

}
