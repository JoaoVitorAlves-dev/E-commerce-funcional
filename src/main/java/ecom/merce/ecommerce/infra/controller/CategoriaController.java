package ecom.merce.ecommerce.infra.controller;

import ecom.merce.ecommerce.domain.service.CategoriaService;
import ecom.merce.ecommerce.dto.request.CategoriaRequest;
import ecom.merce.ecommerce.dto.response.CategoriaResponse;
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
@RequestMapping("categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<Page<CategoriaResponse>> listar(@PageableDefault(sort = "nome", size = 10) Pageable pageable) {
        return ResponseEntity.ok(categoriaService.listar(pageable));
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<CategoriaResponse> adicionarCategoria(@RequestBody @Valid CategoriaRequest categoriaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.adicionarCategoria(categoriaRequest));
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<CategoriaResponse> atualizarPorId(@PathVariable Long id, @RequestBody @Valid CategoriaRequest categoriaRequest) {
        return ResponseEntity.ok(categoriaService.atualizarPorId(id, categoriaRequest));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        categoriaService.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
