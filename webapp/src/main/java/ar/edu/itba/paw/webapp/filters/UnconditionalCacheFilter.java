package ar.edu.itba.paw.webapp.filters;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnconditionalCacheFilter extends OncePerRequestFilter {

    private static final int MAX_AGE = 60 * 60 * 24 * 365; // One year

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (request.getMethod().equals("GET")) {
            response.setHeader("Cache-Control", String.format("public, max-age=%d", MAX_AGE));
        }
        chain.doFilter(request, response);
    }
}
