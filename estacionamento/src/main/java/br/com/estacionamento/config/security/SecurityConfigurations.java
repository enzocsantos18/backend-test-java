package br.com.estacionamento.config.security;

import br.com.estacionamento.repository.usuario.UsuarioRepository;
import br.com.estacionamento.service.security.AutenticacaoService;
import br.com.estacionamento.service.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/tipo/veiculo").permitAll()
                .antMatchers(HttpMethod.GET, "/tipo/usuario").permitAll()
                .antMatchers(HttpMethod.GET, "/fabricante").permitAll()
                .antMatchers(HttpMethod.GET, "/modelo").permitAll()
                .antMatchers(HttpMethod.POST, "/empresa").permitAll()
                .antMatchers(HttpMethod.GET, "/empresa").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.PUT, "/empresa").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.DELETE, "/empresa").hasAnyAuthority("admin")
                .antMatchers("/empresa/telefone").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.POST, "/estacionamento").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.DELETE, "/estacionamento").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.GET, "/estacionamento").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.PUT, "/estacionamento/**").hasAnyAuthority("admin")

                .antMatchers(HttpMethod.GET, "/estacionamento/**").hasAnyAuthority("admin", "admin_estacionamento")

                .antMatchers(HttpMethod.GET, "/relatorio").hasAnyAuthority("admin_estacionamento")
                .antMatchers(HttpMethod.GET, "/relatorio/**").hasAnyAuthority("admin_estacionamento")
                .antMatchers(HttpMethod.POST,"/vaga").hasAnyAuthority("admin_estacionamento")
                .antMatchers(HttpMethod.PUT,"/vaga").hasAnyAuthority("admin_estacionamento")
                .antMatchers(HttpMethod.DELETE, "/vaga/**").hasAnyAuthority("admin_estacionamento")

                .antMatchers(HttpMethod.GET,"/vaga").hasAnyAuthority("admin_estacionamento","funcionario")
                .antMatchers(HttpMethod.POST, "/movimentacao/**").hasAnyAuthority( "admin_estacionamento", "funcionario")
                .antMatchers( "/veiculo").hasAnyAuthority( "admin_estacionamento", "funcionario")
                .antMatchers( "/veiculo/**").hasAnyAuthority( "admin_estacionamento", "funcionario")
                .and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
    }

}
