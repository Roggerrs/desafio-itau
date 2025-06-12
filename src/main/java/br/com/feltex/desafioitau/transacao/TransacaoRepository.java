package br.com.feltex.desafioitau.transacao;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.DoubleStream;

@Repository
public class TransacaoRepository {

    private List<TransacaoRequest> transacoes = new ArrayList<>();

    public void add(TransacaoRequest transacaoRequest) {
        transacoes.add(transacaoRequest);
    }

    public void limpar() {
        transacoes.clear();
    }

    public DoubleSummaryStatistics estatistica(OffsetDateTime horaInicial) {
        if (transacoes.isEmpty()) {
            return new DoubleSummaryStatistics();
        }

        return transacoes.stream()
                // filtra tudo >= horaInicial (after OU equal)
                .filter(t -> t.getDataHora().isAfter(horaInicial)
                        || t.getDataHora().equals(horaInicial))
                // pega o BigDecimal do valor
                .map(TransacaoRequest::getValor)
                // converte pra double e já gera o resumo estatístico
                .mapToDouble(BigDecimal::doubleValue)
                .summaryStatistics();
    }
}