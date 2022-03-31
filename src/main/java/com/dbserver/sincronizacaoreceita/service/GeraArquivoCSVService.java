package com.dbserver.sincronizacaoreceita.service;

import com.dbserver.sincronizacaoreceita.model.ContaModel;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
public class GeraArquivoCSVService {

    private static final String CSV_PATH = "./contasProcessadas.csv";

    public void gravarArquivo(List<ContaModel> contasProcessadas) throws IOException,
            CsvRequiredFieldEmptyException,
            CsvDataTypeMismatchException {

        try {
            Writer writer = new FileWriter(CSV_PATH);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                .withSeparator(';')
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
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
