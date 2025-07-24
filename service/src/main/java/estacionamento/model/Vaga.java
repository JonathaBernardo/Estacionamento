package estacionamento.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}