package ecom.merce.ecommerce.infra.controller;

import ecom.merce.ecommerce.domain.service.ProdutoService;
import ecom.merce.ecommerce.dto.request.ProdutoRequest;
import ecom.merce.ecommerce.dto.response.ProdutoResponse;
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
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<ProdutoResponse>> listar(@PageableDefault(sort = "nome", size = 10) Pageable pageable) {
        return ResponseEntity.ok(produtoService.listar(pageable));
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @Transactional
    @PutMapping
    public ResponseEntity<ProdutoResponse> adicionar(@RequestBody @Valid ProdutoRequest produtoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.adicionarProduto(produtoRequest));
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizarPorId(@PathVariable Long id, @RequestBody @Valid ProdutoRequest produtoRequest) {
        return ResponseEntity.ok(produtoService.atualizarPorId(id, produtoRequest));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        produtoService.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
