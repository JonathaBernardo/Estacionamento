package estacionamento.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Veiculo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 10)
    private String placa;
    
    @Column(nullable = false, length = 50)
    private String modelo;
    
    private LocalDateTime horaEntrada;

    // Construtor completo
    public Veiculo(String placa, String modelo) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser nula ou vazia");
        }
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("Modelo não pode ser nulo ou vazio");
        }
        this.placa = placa;
        this.modelo = modelo;
    }

    // Construtor para registro inicial
    public Veiculo(String placa, String modelo, LocalDateTime horaEntrada) {
        this(placa, modelo);
        if (horaEntrada == null) {
            throw new IllegalArgumentException("Hora de entrada não pode ser nula");
        }
        this.horaEntrada = horaEntrada;
    }

    // Método para registrar entrada
    public void setHoraEntrada(LocalDateTime horaEntrada) {
        if (this.horaEntrada != null) {
            throw new IllegalStateException("Veículo já possui hora de entrada registrada");
        }
        if (horaEntrada == null) {
            throw new IllegalArgumentException("Hora de entrada não pode ser nula");
        }
        this.horaEntrada = horaEntrada;
    }

    // Método auxiliar para registro com hora atual
    public void registrarEntrada() {
        this.setHoraEntrada(LocalDateTime.now());
    }
}