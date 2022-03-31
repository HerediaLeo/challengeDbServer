package com.dbserver.sincronizacaoreceita.service.impl;

import com.dbserver.sincronizacaoreceita.model.ContaModel;
import com.dbserver.sincronizacaoreceita.service.LerCSVService;
import com.dbserver.sincronizacaoreceita.service.ProcessamentoArquivo;
import com.dbserver.sincronizacaoreceita.service.ReceitaService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
@Slf4j
public class ProcessamentoArquivoImpl implements ProcessamentoArquivo {

    private static final String CSV_PATH = "./contasProcessadas.csv";

    @Autowired
    ReceitaService receitaservice;

    @Autowired
    LerCSVService leituraService;


    @Override
    public void processaArquivo(String caminhoArquivo) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException, InterruptedException {

        try {
            var contasRetornadas = leituraService.lerCSV(caminhoArquivo);

            atualizarConta(contasRetornadas);

            gravarArquivo(contasRetornadas);
        } catch (Exception e) {
            log.error("Não foi possível processar o arquivo. e={}", e.getMessage());
            throw e;
        }
    }

    private void atualizarConta(List<ContaModel> contasRetornadas) throws InterruptedException {
        for (ContaModel conta : contasRetornadas){

            String contaFormatada = conta.getConta().replaceAll("-","");
            conta.setResultado(receitaservice.atualizarConta(conta.getAgencia(),
                                            contaFormatada,
                                            conta.getSaldo(),
                                            conta.getStatus()));
        }
    }

    private ColumnPositionMappingStrategy orderHeaderCsv(){
        ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
        mappingStrategy.setType(ContaModel.class);

        String[] columns = new String[]
                {"agencia","conta","saldo","status","resultado"};
        mappingStrategy.setColumnMapping(columns);


        return mappingStrategy;
    }

    private void gravarArquivo(List<ContaModel> contasProcessadas) throws IOException,
            CsvRequiredFieldEmptyException,
            CsvDataTypeMismatchException {

        try {
            Writer writer = new FileWriter(CSV_PATH);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
//                .withSeparator(';')
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    //.withMappingStrategy(orderHeaderCsv())
                    .build()
                    ;

            beanToCsv.write(contasProcessadas);
            writer.close();
        }
        catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
