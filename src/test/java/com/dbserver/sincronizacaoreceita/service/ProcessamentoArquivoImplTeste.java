package com.dbserver.sincronizacaoreceita.service;

import com.dbserver.sincronizacaoreceita.model.ContaModel;
import com.dbserver.sincronizacaoreceita.service.impl.ProcessamentoArquivoImpl;
import com.dbserver.sincronizacaoreceita.service.impl.ReceitaServiceImpl;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProcessamentoArquivoImplTeste {
    public static final String CONTAS_CSV = "contas.csv";
    public static final String CAMINHO_ERRADO_CSV = "caminho-errado.csv";
    public static final int ESPERADO_UM = 1;
    public static final int ESPERADO_CINCO = 5;



    @InjectMocks
    private ProcessamentoArquivoImpl service;

    @Mock
    private ReceitaServiceImpl receitaService;

    @Mock
    private LerCSVService lerCSVService;

    @Mock
    private GeraArquivoCSVService geraArquivoCSVService;

    @Test
    void deveriaProcessarArquivo() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException, InterruptedException {

        service.processaArquivo(CONTAS_CSV);
        assertNotNull(CONTAS_CSV);
        assertNotNull(lerCSVService.lerCSV(CONTAS_CSV));

    }

    @Test
    void naoDeveriaProcessarArquivoQuandoNaoEncontrarELancarIOException() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException, InterruptedException {

        when(lerCSVService.lerCSV(anyString()))
                .thenThrow(IOException.class);

        assertThrows(IOException.class, () -> service.processaArquivo(CAMINHO_ERRADO_CSV));
    }

    @Test
    void deveChamarMetodoAtualizarConta() throws InterruptedException {

        var conta = popularContaLeituraCSV();

        assertNotNull(conta);

        receitaService.atualizarConta(conta.getAgencia(), conta.getConta(), conta.getSaldo(), conta.getStatus());
    }

    @Test
    void deveChamarAtualizarContaEAlterarResultado() throws InterruptedException {

        var conta = popularContaLeituraCSV();

        assertTrue(receitaService.atualizarConta(conta.getAgencia(), conta.getConta(), conta.getSaldo(), conta.getStatus()));
    }

    public ContaModel popularContaLeituraCSV(){

        ContaModel conta = new ContaModel();

        conta.setAgencia("4321");
        conta.setConta("123450");
        conta.setSaldo(25.0);
        conta.setStatus("A");

        return conta;
    }

}
