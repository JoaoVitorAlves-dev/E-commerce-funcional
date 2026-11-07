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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping("/listar")
    public ResponseEntity<Page<PedidoResponse>> listar(Pageable pageable, Authentication auth) {
        return ResponseEntity.ok(pedidoService.listar(pageable, auth));
    }

    @Transactional
    @GetMapping("/buscar/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable Long id, Authentication auth) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id, auth));
    }

    @Transactional
    @PostMapping("/adicionar")
    public ResponseEntity<PedidoResponse> adicionarPedido(@RequestBody @Valid PedidoRequest pedidoRequest, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.adicionarPedido(pedidoRequest, auth));
    }

    @Transactional
    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<PedidoResponse> atualizarPorId(@PathVariable Long id, @RequestBody @Valid PedidoRequest pedidoRequest, Authentication auth) {
        return ResponseEntity.ok(pedidoService.atualizarPorId(id, pedidoRequest, auth));
    }

    @Transactional
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id, Authentication auth) {
        pedidoService.deletar(id ,auth);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
