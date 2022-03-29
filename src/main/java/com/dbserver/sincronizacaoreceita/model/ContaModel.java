package com.dbserver.sincronizacaoreceita.model;

import com.dbserver.sincronizacaoreceita.enums.StatusConta;
import lombok.Data;

@Data
public class ContaModel {

    private String agencia;

    private String conta;

    private double saldo;

    private StatusConta status;


}
