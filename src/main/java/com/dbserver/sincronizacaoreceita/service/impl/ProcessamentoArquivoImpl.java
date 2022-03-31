package com.dbserver.sincronizacaoreceita.service.impl;

import com.dbserver.sincronizacaoreceita.model.ContaModel;
import com.dbserver.sincronizacaoreceita.service.GeraArquivoCSVService;
import com.dbserver.sincronizacaoreceita.service.LerCSVService;
import com.dbserver.sincronizacaoreceita.service.ProcessamentoArquivo;
import com.dbserver.sincronizacaoreceita.service.ReceitaService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ProcessamentoArquivoImpl implements ProcessamentoArquivo {

    @Autowired
    ReceitaService receitaservice;

    @Autowired
    LerCSVService leituraService;

    @Autowired
    GeraArquivoCSVService geraArquivoCSVService;


    @Override
    public void processaArquivo(String caminhoArquivo) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException, InterruptedException {

        try {
            log.info("Comecando o processamento do arquivo.");

            var contasRetornadas = leituraService.lerCSV(caminhoArquivo);

            atualizarConta(contasRetornadas);

            geraArquivoCSVService.gravarArquivo(contasRetornadas);
        } catch (Exception e) {
            log.error("Não foi possível processar o arquivo. e={}", e.getMessage());
            throw e;
        }
    }

    public void atualizarConta(List<ContaModel> contasRetornadas) throws InterruptedException {
        for (ContaModel conta : contasRetornadas){

            String contaFormatada = conta.getConta().replaceAll("-","");

            boolean verificador = receitaservice.atualizarConta(conta.getAgencia(),contaFormatada,conta.getSaldo(),conta.getStatus());

            conta.setResultado(verificador);
        }
    }

}
