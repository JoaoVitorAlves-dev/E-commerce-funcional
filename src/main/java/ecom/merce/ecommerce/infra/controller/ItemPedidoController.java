package ecom.merce.ecommerce.infra.controller;

import ecom.merce.ecommerce.domain.service.ItemPedidoService;
import ecom.merce.ecommerce.dto.request.ItemPedidoRequest;
import ecom.merce.ecommerce.dto.response.ItemPedidoResponse;
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
@RequestMapping("/itempedidos")
@RequiredArgsConstructor
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    @GetMapping("/listar")
    public ResponseEntity<Page<ItemPedidoResponse>> listar(Pageable pageable, Authentication auth) {
        return ResponseEntity.ok(itemPedidoService.listar(auth, pageable));
    }

    @Transactional
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ItemPedidoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(itemPedidoService.buscarPorId(id));
    }

    @Transactional
    @PostMapping("/adicionar")
    public ResponseEntity<ItemPedidoResponse> adicionarItemPedido(@RequestBody @Valid ItemPedidoRequest itemPedidoRequest, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemPedidoService.adicionarItemPedido(itemPedidoRequest, auth));
    }

    @Transactional
    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<ItemPedidoResponse> atualizarPorId(@PathVariable Long id, @RequestBody @Valid ItemPedidoRequest itemPedidoRequest, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(itemPedidoService.atualizarPorId(id, itemPedidoRequest, auth));
    }

    @Transactional
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id, Authentication auth) {
        itemPedidoService.deletar(id, auth);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
