package estacionamento.controller;

import estacionamento.model.Veiculo;
import estacionamento.service.EstacionamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final EstacionamentoService service;

    @PostMapping
    public Veiculo registrar(@RequestBody Veiculo veiculo) {
        return service.registrarVeiculo(veiculo);
    }

    @GetMapping
    public List<Veiculo> listarTodos() {
        return service.listarVeiculos();
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletarVeiculo(id);
    }
}