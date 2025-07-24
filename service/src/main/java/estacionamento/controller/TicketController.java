package estacionamento.controller;

import estacionamento.model.Ticket;
import estacionamento.service.EstacionamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final EstacionamentoService service;

    @PostMapping("/{veiculoId}")
    public Ticket gerarTicket(@PathVariable Long veiculoId) {
        return service.gerarTicket(veiculoId);
    }

    @GetMapping("/{ticketId}")
    public Ticket buscarTicketPorId(@PathVariable Long ticketId) {
        return service.buscarTicketPorId(ticketId);
    }

    @GetMapping("/{ticketId}/valor")
    public BigDecimal calcularValor(@PathVariable Long ticketId) {
        return service.calcularValor(ticketId);
    }

    @GetMapping
    public Iterable<Ticket> listarTodosTickets() {
        return service.listarTickets();
    }

    @PutMapping("/{ticketId}/finalizar")
    public Ticket finalizarTicket(@PathVariable Long ticketId) {
        return service.finalizarTicket(ticketId);
    }
}