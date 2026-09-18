package ecom.merce.ecommerce.infra.controller;

import ecom.merce.ecommerce.domain.service.PedidoService;
import ecom.merce.ecommerce.dto.request.PedidoRequest;
import ecom.merce.ecommerce.dto.response.PedidoResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<Page<PedidoResponse>> listar(@PageableDefault(sort = "quantidade", size = 10) Pageable pageable) {
        return ResponseEntity.ok(pedidoService.listar(pageable));
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<PedidoResponse> adicionarPedido(@RequestBody @Valid PedidoRequest pedidoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.adicionarPedido(pedidoRequest));
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<PedidoResponse> atualizarPorId(@PathVariable Long id, @RequestBody @Valid PedidoRequest pedidoRequest) {
        return ResponseEntity.ok(pedidoService.atualizarPorId(id, pedidoRequest));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        pedidoService.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
