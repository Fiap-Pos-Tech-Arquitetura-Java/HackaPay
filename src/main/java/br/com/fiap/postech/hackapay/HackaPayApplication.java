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

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("autenticacao", r -> r.path("/api/autenticacao/**")
                        .uri("http://usuario:8080/api/autenticacao"))
                .route("cliente", r -> r.path("/api/cliente/**")
                        .uri("http://cliente:8081/api/cliente"))
                .route("usuario", r -> r.path("/api/usuario/**")
                        .and()
                        .not( p->p.path("/api/usuario/findByLogin/**"))
                        .uri("http://usuario:8080/api/usuario"))
                .route("cartao", r -> r.path("/api/cartao/**")
                        .uri("http://cartao:8082/api/cartao"))
                .route("pagamento", r -> r.path("/api/pagamento/**")
                        .uri("http://pagamento:8083/api/pagamento"))
                .route("pagamento-cliente", r -> r.path("/api/pagamento/cliente/**")
                        .uri("http://pagamento:8083/api/pagamento/cliente"))
                .build();
    }
}
