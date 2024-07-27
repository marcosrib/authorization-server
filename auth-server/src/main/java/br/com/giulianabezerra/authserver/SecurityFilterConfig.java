package br.com.giulianabezerra.authserver;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class SecurityFilterConfig {

    /*
     * Configura os endpoints do protocolo OpenID e redirecionamento automático para
     * tela de login.
     */
    @Bean
    @Order(1)
    SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(withDefaults()); // Ativa suporte ao OpenID Connect

        // Verifica se está configurado para suportar o fluxo de concessão de senha
        http.exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
                .oauth2ResourceServer(conf -> conf.jwt(withDefaults())); // Conf

        return http.build();
    }

    /*
     * Configura a segurança e tela de autenticação redirecionada pelo filtro de
     * autorização.
     */
    @Bean
    @Order(2)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/perform_login").permitAll()
    //("/login", "/perform_login").permitAll() // Permitir acesso ao endpoint de login
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login") // Definir endpoint de login
                        .loginProcessingUrl("/perform_login") // URL para processamento do login
                        .permitAll());

        return http.build();
    }

}
