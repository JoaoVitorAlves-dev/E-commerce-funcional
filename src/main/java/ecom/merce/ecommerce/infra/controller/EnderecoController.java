package ecom.merce.ecommerce.infra.controller;

import ecom.merce.ecommerce.domain.service.EnderecoService;
import ecom.merce.ecommerce.dto.request.EnderecoRequest;
import ecom.merce.ecommerce.dto.response.EnderecoResponse;
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
@RequestMapping("enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<Page<EnderecoResponse>> listar(@PageableDefault(sort = "rua", size = 10) Pageable pageable) {
        return ResponseEntity.ok(enderecoService.listar(pageable));
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(enderecoService.buscarPorId(id));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<EnderecoResponse> adicionarEndereco(@RequestBody @Valid EnderecoRequest enderecoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.adicionarEndereco(enderecoRequest));
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<EnderecoResponse> atualizarPorId(@PathVariable Long id, @RequestBody @Valid EnderecoRequest enderecoRequest) {
        return ResponseEntity.ok(enderecoService.atualizarPorId(id, enderecoRequest));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        enderecoService.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
