package com.vinovibes.vinoapi.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter for JWT authentication.
 */
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final UserAuthProvider userAuthProvider;

    /**
     * Method for filtering requests. If the request contains a valid JWT token, the user is authenticated.
     * If the token is invalid, an exception is thrown.
     * @param request request
     * @param response response
     * @param filterChain filter chain
     * @throws ServletException servlet exception
     * @throws IOException IO exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] authElements = header.split(" ");
            if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
                try {
                    SecurityContextHolder
                        .getContext()
                        .setAuthentication(userAuthProvider.validateToken(authElements[1]));
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
