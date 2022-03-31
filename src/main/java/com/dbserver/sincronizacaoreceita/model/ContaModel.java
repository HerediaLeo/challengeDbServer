package com.dbserver.sincronizacaoreceita.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvNumber;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ContaModel {

    @CsvBindByName(required = true)
    private String agencia;

    @CsvBindByName(required = true)
    private String conta;

    @CsvBindByName(required = true)
    @CsvNumber(value = ",", writeFormatEqualsReadFormat = false)
    private double saldo;

    @CsvBindByName(required = true)
    private String status;

    @CsvBindByName
    private boolean resultado;

}
