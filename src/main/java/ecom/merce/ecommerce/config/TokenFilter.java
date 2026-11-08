package ecom.merce.ecommerce.config;

import ecom.merce.ecommerce.domain.service.TokenService;
import ecom.merce.ecommerce.domain.service.UsuarioDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final UsuarioDetailsService usuarioDetailsService;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String s = extrairToken(request);

        if (s == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                String s1 = tokenService.validarToken(s);
                UserDetails userDetails = usuarioDetailsService.loadUserByUsername(s1);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException | IllegalArgumentException | UsernameNotFoundException e) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou expirado.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extrairToken(HttpServletRequest req) {
        String authorization = req.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

}
