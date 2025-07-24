package pagamento.service;

import pagamento.model.Pagamento;
import pagamento.repository.PagamentoRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class PagamentoService {
    private final PagamentoRepository repository;

    public PagamentoService(PagamentoRepository repository) {
        this.repository = repository;
    }

    public Pagamento processarPagamento(Long ticketId, BigDecimal valor, String metodo) {
        Pagamento pagamento = new Pagamento();
        pagamento.setTicketId(ticketId);
        pagamento.setValor(valor);
        pagamento.setMetodo(metodo);

        if (valor.compareTo(BigDecimal.ZERO) > 0) {
            pagamento.setStatus("APROVADO");
        } else {
            pagamento.setStatus("RECUSADO");
        }

        return repository.save(pagamento);
    }

    public Pagamento consultarPagamento(Long id) {

        return null;
    }
}