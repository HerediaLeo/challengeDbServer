package com.dbserver.sincronizacaoreceita.service;

import com.dbserver.sincronizacaoreceita.model.ContaModel;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
@Slf4j
public class GeraArquivoCSVService {

    private static final String CSV_PATH = "D:/contasProcessadas.csv";

    public void gravarArquivo(List<ContaModel> contasProcessadas) throws IOException,
            CsvRequiredFieldEmptyException,
            CsvDataTypeMismatchException {

        try {
            Writer writer = new FileWriter(CSV_PATH);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                .withSeparator(';')
                    //.withMappingStrategy(ordernaHeaderCsv())
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build()
                    ;

            beanToCsv.write(contasProcessadas);
            writer.close();

            log.info("Arquivo gerado no diretorio: " + CSV_PATH);

        }
        catch (Exception e){
            log.error("Não foi possível gravar o arquivo. e={}", e.getMessage());
            throw e;
        }

    }

    private ColumnPositionMappingStrategy ordernaHeaderCsv(){
        ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
        mappingStrategy.setType(ContaModel.class);

        String[] columns = new String[]
                {"agencia","conta","saldo","status","resultado"};
        mappingStrategy.setColumnMapping(columns);
        return mappingStrategy;
    }
}
