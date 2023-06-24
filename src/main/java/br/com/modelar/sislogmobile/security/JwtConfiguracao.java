package br.com.modelar.sislogmobile.security;
import br.com.modelar.sislogmobile.service.DetalhesUsuarioServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Deprecated
public class JwtConfiguracao extends WebSecurityConfigurerAdapter {
    private final DetalhesUsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;

    public JwtConfiguracao(DetalhesUsuarioServiceImpl usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder);
    }


    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").authenticated()
                .antMatchers(HttpMethod.GET, "/listarTodos").authenticated()
                .antMatchers(HttpMethod.GET, "/listar/{id}").authenticated()
                .antMatchers(HttpMethod.PUT, "/atualizar").authenticated()
                .antMatchers(HttpMethod.PUT, "/atualizarsenha").authenticated()
                .antMatchers(HttpMethod.DELETE, "/deletar/{id}").authenticated()
                .antMatchers(HttpMethod.GET, "/validarSenha").authenticated()
                .antMatchers(HttpMethod.POST, "/salvar").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAutenticarFilter(authenticationManager()))
                .addFilter(new JwtValidarFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfiguration() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

    }
}
