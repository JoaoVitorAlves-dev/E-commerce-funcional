package ecom.merce.ecommerce.infra.controller;

import ecom.merce.ecommerce.domain.service.PagamentoService;
import ecom.merce.ecommerce.dto.request.PagamentoRequest;
import ecom.merce.ecommerce.dto.response.PagamentoResponse;
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

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @GetMapping("/listar")
    public ResponseEntity<Page<PagamentoResponse>> listar(Authentication auth, Pageable pageable) {
        return ResponseEntity.ok(pagamentoService.listar(auth, pageable));
    }

    @Transactional
    @GetMapping("/buscar/{id}")
    public ResponseEntity<PagamentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagamentoService.buscarPorId(id));
    }

    @Transactional
    @PostMapping("/adicionar")
    public ResponseEntity<PagamentoResponse> adicionarPagamento(@RequestBody @Valid PagamentoRequest pagamentoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoService.adicionarPagamento(pagamentoRequest));
    }

    @Transactional
    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<PagamentoResponse> atualizarPorId(@PathVariable Long id, @RequestBody @Valid PagamentoRequest pagamentoRequest, Authentication auth) {
        return ResponseEntity.ok(pagamentoService.atualizarPorId(id, pagamentoRequest, auth));
    }

    @Transactional
    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarPorId(Authentication auth) {
        pagamentoService.deletar(auth);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
