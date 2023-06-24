package br.com.modelar.sislogmobile.security;

import br.com.modelar.sislogmobile.service.UsuarioService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;

public class JwtValidarFilter extends BasicAuthenticationFilter {

    public static final String HEADER_ATRIBUTO = "Authorization";
    public static final String ATRIBUTO_PREFIXO = "Bearer ";

    public static UsuarioService service;

    public JwtValidarFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    @Valid
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String atributo = request.getHeader(HEADER_ATRIBUTO);

        if(atributo == null) {
            chain.doFilter(request, response);
            return;
        }
        if(atributo.isEmpty()) {
            response.getWriter().write("Token vazio");
            response.getWriter().flush();
            return;
        }

        if (!atributo.startsWith(ATRIBUTO_PREFIXO)) {
            chain.doFilter(request, response);
            return;
        }

        String token = atributo.replace(ATRIBUTO_PREFIXO, "");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthenticationToken(token);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request, response);
    }


    @Valid
    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        String usuario = JWT.require(Algorithm.HMAC512(JwtAutenticarFilter.TOKEN_SIGN))
                .build()
                .verify(token)
                .getSubject();

        if (usuario == null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(usuario, null, new ArrayList<>());

    }
}
