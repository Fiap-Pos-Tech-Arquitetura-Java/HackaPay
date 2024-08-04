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

    @Value("${hackapay.user.host}")
    String userHost;

    @Value("${hackapay.cliente.host}")
    String clienteHost;

    @Value("${hackapay.cartao.host}")
    String cartaoHost;

    @Value("${ackapay.pagamentos.hosth}")
    String pagamentosHost;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("usuario", r -> r.path("/api/user/**")
                        .and()
                        .not( p->p.path("/hackapay/user/findByLogin/**"))
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://" + userHost + ":8080"))
                .route("cliente", r -> r.path("api/cliente/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://" + clienteHost + ":8081"))
                .route("cartao", r -> r.path("/api/cartao/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://" + cartaoHost + ":8082"))
                .route("pagamento", r -> r.path("/api/pagamentos/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://" + pagamentosHost + ":8083"))
                .build();
    }
}
