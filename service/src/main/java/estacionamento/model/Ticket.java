package estacionamento.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

@Entity
@Data
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime horaEntrada;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @PrePersist
    protected void onCreate() {
        horaEntrada = LocalDateTime.now();
    }

	public void setHoraSaida(LocalDateTime now) {
		
	}

	public void setCodigo(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setVeiculo(Veiculo veiculo2) {
		// TODO Auto-generated method stub
		
	}

	public Temporal getHoraEntrada() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setValor(BigDecimal calcularValor) {
		// TODO Auto-generated method stub
		
	}
}