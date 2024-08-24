package br.com.fiap.wavemail.domain.service.auth;

import br.com.fiap.wavemail.domain.repository.UserEntityRepository;
import br.com.fiap.wavemail.infra.exceptions.ItemNotFoundException;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class VerifyToken extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserEntityRepository userEntityRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = "";

        if (authHeader == null) {
            token = null;
        } else {

            token = authHeader.replace("Bearer ", "").trim();
            String login = tokenService.validateToken(token);
            UserDetails user = userEntityRepository.findByEmail(login);

            if (user != null) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                throw new ItemNotFoundException("User not found");
            }
        }
        filterChain.doFilter(request, response);



    }
}




















