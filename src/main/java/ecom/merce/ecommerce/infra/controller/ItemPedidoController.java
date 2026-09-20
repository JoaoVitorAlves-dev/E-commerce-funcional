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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itempedidos")
@RequiredArgsConstructor
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    @GetMapping
    public ResponseEntity<Page<ItemPedidoResponse>> listar(@PageableDefault(sort = "quantidade", size = 10)Pageable pageable) {
        return ResponseEntity.ok(itemPedidoService.listar(pageable));
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(itemPedidoService.buscarPorId(id));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ItemPedidoResponse> adicionarItemPedido(@RequestBody @Valid ItemPedidoRequest itemPedidoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemPedidoService.adicionarItemPedido(itemPedidoRequest));
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<ItemPedidoResponse> atualizarPorId(@PathVariable Long id, @RequestBody @Valid ItemPedidoRequest itemPedidoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemPedidoService.atualizarPorId(id, itemPedidoRequest));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        itemPedidoService.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
