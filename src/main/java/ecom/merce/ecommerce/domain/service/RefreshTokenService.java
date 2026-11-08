package ecom.merce.ecommerce.domain.service;

import ecom.merce.ecommerce.domain.repository.RefreshTokenRepository;
import ecom.merce.ecommerce.entity.Cliente;
import ecom.merce.ecommerce.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${api.token.expiration-days}")
    private long expiration;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken criar(Cliente cliente) {
        refreshTokenRepository.deleteByCliente(cliente);

        RefreshToken rt = new RefreshToken();
        rt.setCliente(cliente);
        rt.setExpiraEm(Instant.now().plus(expiration, ChronoUnit.DAYS));
        rt.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(rt);
    }

    public RefreshToken validarERotacionar(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (refreshToken.getExpiraEm().isBefore(Instant.now())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token expirado.");
        }

        return criar(refreshToken.getCliente());
    }

    public void apagarPorToken(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(refreshTokenRepository::delete);
    }

}
