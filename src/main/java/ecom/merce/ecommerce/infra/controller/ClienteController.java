package ecom.merce.ecommerce.infra.controller;

import ecom.merce.ecommerce.domain.service.ClienteService;
import ecom.merce.ecommerce.dto.request.ClienteRequest;
import ecom.merce.ecommerce.dto.request.LoginRequest;
import ecom.merce.ecommerce.dto.response.ClienteResponse;
import ecom.merce.ecommerce.dto.response.TokenResponse;
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
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/listar")
    public ResponseEntity<Page<ClienteResponse>> listar(@PageableDefault(sort = "nome", size = 10) Pageable pageable) {
        return ResponseEntity.ok(clienteService.listar(pageable));
    }

    @Transactional
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @Transactional
    @PostMapping("/registrar")
    public ResponseEntity<Void> adicionarCliene(@RequestBody @Valid ClienteRequest clienteRequest) {
        clienteService.adicionarCliente(clienteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Transactional
    @PostMapping("/logar")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(clienteService.login(loginRequest));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<ClienteResponse> atualizarPorId(@RequestBody @Valid ClienteRequest clienteRequest, Authentication auth) {
        return ResponseEntity.ok(clienteService.atualizarCliente(clienteRequest, auth));
    }

    @Transactional
    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarPorId(Authentication auth) {
        clienteService.deletar(auth);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
