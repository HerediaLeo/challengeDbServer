package com.dbserver.sincronizacaoreceita.service;

import com.dbserver.sincronizacaoreceita.model.ContaModel;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class LerCSVService {

    public List<ContaModel> lerCSV (String caminhoArquivo) throws IOException {
        log.info("Log do leo");
        try {
            Reader reader = Files.newBufferedReader(Paths.get(caminhoArquivo));

            return new CsvToBeanBuilder(reader)
                    .withSeparator(';')
                    .withType(ContaModel.class)
                    .build()
                    .parse();
        }
        catch (IOException e) {
            System.out.println("Erro na leitura do arquivo");
            throw e;
        }
    }

}
