package pagamento.controller;

import pagamento.DTO.PagamentoRequest;
import pagamento.model.Pagamento;
import pagamento.service.PagamentoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @GetMapping("/{ticketId}")
    public Pagamento consultar(@PathVariable Long ticketId) {
        return service.consultarPagamento(ticketId);
    }

    @PostMapping
    public Pagamento pagar(@RequestBody PagamentoRequest request) {
        return service.processarPagamento(
                request.getTicketId(),
                request.getValor(),
                request.getMetodo()
        );
    }
}