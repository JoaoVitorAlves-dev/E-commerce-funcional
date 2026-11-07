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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping("/listar")
    public ResponseEntity<Page<EnderecoResponse>> listar(@PageableDefault(sort = "rua", size = 10) Pageable pageable, Authentication auth) {
        return ResponseEntity.ok(enderecoService.listar(pageable, auth));
    }

    @Transactional
    @GetMapping("/buscar/{id}")
    public ResponseEntity<EnderecoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(enderecoService.buscarPorId(id));
    }

    @Transactional
    @PostMapping("/adicionar")
    public ResponseEntity<EnderecoResponse> adicionarEndereco(@RequestBody @Valid EnderecoRequest enderecoRequest, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.adicionarEndereco(enderecoRequest, auth));
    }

    @Transactional
    @PatchMapping("/atualizar")
    public ResponseEntity<EnderecoResponse> atualizar(@RequestBody @Valid EnderecoRequest enderecoRequest, Authentication auth) {
        return ResponseEntity.ok(enderecoService.atualizar(enderecoRequest, auth));
    }

    @Transactional
    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletar(Authentication auth) {
        enderecoService.deletar(auth);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
