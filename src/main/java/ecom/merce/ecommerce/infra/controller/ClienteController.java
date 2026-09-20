package ecom.merce.ecommerce.infra.controller;

import ecom.merce.ecommerce.domain.service.ClienteService;
import ecom.merce.ecommerce.dto.request.ClienteRequest;
import ecom.merce.ecommerce.dto.request.LoginRequest;
import ecom.merce.ecommerce.dto.response.ClienteResponse;
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
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Page<ClienteResponse>> listar(@PageableDefault(sort = "nome", size = 10) Pageable pageable) {
        return ResponseEntity.ok(clienteService.listar(pageable));
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ClienteResponse> adicionarCliene(@RequestBody @Valid ClienteRequest clienteRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.adicionarCliente(clienteRequest));
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity<ClienteResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(clienteService.login(loginRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizarPorId(@PathVariable Long id, @RequestBody @Valid ClienteRequest clienteRequest) {
        return ResponseEntity.ok(clienteService.atualizarCliente(id, clienteRequest));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        clienteService.deletarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
