package br.com.feltex.desafioitau.transacao;

import br.com.feltex.desafioitau.estatistica.EstatisticaDTO;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Repository
public class TransacaoRepository {

    private final List<TransacaoRequest> transacoes = new ArrayList<>();

    public void add(TransacaoRequest transacaoRequest) {
        transacoes.add(transacaoRequest);
    }

    public void limpar() {
        transacoes.clear();
    }

    public EstatisticaDTO estatistica(OffsetDateTime horaInicial) {
        // Se estiver vazio retorna DTO padrão
        if (transacoes.isEmpty()) {
            return new EstatisticaDTO();
        }

        // Calcula as estatísticas em double
        DoubleSummaryStatistics stats = transacoes.stream()
                .filter(t -> !t.getDataHora().isBefore(horaInicial))  // isAfter ou equal
                .map(TransacaoRequest::getValor)
                .mapToDouble(BigDecimal::doubleValue)
                .summaryStatistics();

        // Constrói o DTO a partir das estatísticas
        return new EstatisticaDTO(stats);
    }
}