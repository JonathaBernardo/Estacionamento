package estacionamento.service;

import estacionamento.model.Ticket;
import estacionamento.model.Veiculo;
import estacionamento.repository.TicketRepository;
import estacionamento.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstacionamentoService {
    private final VeiculoRepository veiculoRepository;
    private final TicketRepository ticketRepository;

    public Veiculo registrarVeiculo(Veiculo veiculo) {
        veiculo.setHoraEntrada(LocalDateTime.now());
        return veiculoRepository.save(veiculo);
    }

    public Ticket gerarTicket(Long veiculoId) {
        Veiculo veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        
        Ticket ticket = new Ticket();
        ticket.setVeiculo(veiculo);
        ticket.setCodigo("TKT-" + System.currentTimeMillis());
        return ticketRepository.save(ticket);
    }

    public BigDecimal calcularValor(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));
        
        LocalDateTime horaSaida = LocalDateTime.now();
        long horas = Duration.between(ticket.getHoraEntrada(), horaSaida).toHours();
        return BigDecimal.valueOf(horas * 5.0);
    }

    public Ticket finalizarTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));
        
        ticket.setHoraSaida(LocalDateTime.now());
        ticket.setValor(calcularValor(ticketId));
        return ticketRepository.save(ticket);
    }

    public Ticket buscarTicketPorId(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));
    }

    public Iterable<Ticket> listarTickets() {
        return ticketRepository.findAll();
    }

    public List<Veiculo> listarVeiculos() {
        return veiculoRepository.findAll();
    }

    public void deletarVeiculo(Long id) {
        veiculoRepository.deleteById(id);
    }

	public long contarVagasDisponiveis() {
        return 0;
	}
}