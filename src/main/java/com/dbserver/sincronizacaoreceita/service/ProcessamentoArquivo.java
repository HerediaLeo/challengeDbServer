package com.dbserver.sincronizacaoreceita.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;

public interface ProcessamentoArquivo {
    public void processaArquivo(String caminhoArquivo) throws InterruptedException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException;
}
