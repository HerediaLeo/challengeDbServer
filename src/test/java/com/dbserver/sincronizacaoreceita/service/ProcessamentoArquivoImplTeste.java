//package com.dbserver.sincronizacaoreceita.service;
//
//import com.dbserver.sincronizacaoreceita.service.impl.ProcessamentoArquivoImpl;
//import com.dbserver.sincronizacaoreceita.service.impl.ReceitaServiceImpl;
//import com.opencsv.exceptions.CsvDataTypeMismatchException;
//import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.internal.verification.Times;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class ProcessamentoArquivoImplTeste {
//    public static final String CONTAS_CSV = "contas.csv";
//    public static final String CAMINHO_ERRADO_CSV = "caminho-errado.csv";
//    public static final int ESPERADO_UM = 1;
//
//
//    @Mock
//    private ProcessamentoArquivoImpl service;
//
//    @InjectMocks
//    private ReceitaServiceImpl receitaService;
//
//    @InjectMocks
//    private LerCSVService lerCSVService;
//
//    @Test
//    void deveriaProcessarArquivo() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException, InterruptedException {
//
//        service.processaArquivo(CONTAS_CSV);
//
//        verify(lerCSVService, Mockito.times(ESPERADO_UM)).lerCSV(any());
//        verify(receitaService, Mockito.times(ESPERADO_UM)).atualizarConta(any(), any(), any(), any());
//
//    }
//
//    @Test
//    void naoDeveriaProcessarArquivoQuandoNaoEncontrarELancarIOException() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException, InterruptedException {
//
//        when(lerCSVService.lerCSV(anyString()))
//                .thenThrow(IOException.class);
//
//        assertThrows(IOException.class, () -> service.processaArquivo(CAMINHO_ERRADO_CSV));
//    }
//
//}
