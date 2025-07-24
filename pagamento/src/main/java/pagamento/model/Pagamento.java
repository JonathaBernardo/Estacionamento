package pagamento.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ticketId;
    private BigDecimal valor;
    private String status;
    private LocalDateTime dataHora;
    private String metodo;
    // Construtor para inicializar valores padrão
    public Pagamento() {
        this.status = "PENDENTE";
        this.dataHora = LocalDateTime.now();
    }
}