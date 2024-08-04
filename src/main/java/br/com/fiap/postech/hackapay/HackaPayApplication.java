package br.com.fiap.postech.hackapay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HackaPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HackaPayApplication.class, args);
    }

    @Value("${hackapay.user}")
    String user;

    @Value("${hackapay.cliente}")
    String cliente;

    @Value("${hackapay.cartao}")
    String cartao;

    @Value("${hackapay.pagamentos}")
    String pagamentos;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("usuario", r -> r.path("/api/user/**")
                        .and()
                        .not( p->p.path("/api/user/findByLogin/**"))
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://" + user + ":8080" + user))
                .route("cliente", r -> r.path("/api/cliente/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://" + cliente + ":8081" + cliente))
                .route("cartao", r -> r.path("/api/cartao/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://" + cartao + ":8082" + cartao))
                .route("pagamento", r -> r.path("/api/pagamentos/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://" + pagamentos + ":8083" + pagamentos))
                .build();
    }
}
