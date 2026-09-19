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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<Page<PagamentoResponse>> listar(@PageableDefault(sort = "valor", size = 10) Pageable pageable) {
        return ResponseEntity.ok(pagamentoService.listar(pageable));
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagamentoService.buscarPorId(id));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<PagamentoResponse> adicionarPagamento(@RequestBody @Valid PagamentoRequest pagamentoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoService.adicionarPagamento(pagamentoRequest));
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<PagamentoResponse> atualizarPorId(@PathVariable Long id, @RequestBody @Valid PagamentoRequest pagamentoRequest) {
        return ResponseEntity.ok(pagamentoService.atualizarPorId(id, pagamentoRequest));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        pagamentoService.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
