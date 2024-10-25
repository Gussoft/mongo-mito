# Concurrencia y parelelismo

# YAML GATEWAY

```
spring:
  application:
    name: gateway
  main:
    allow-bean-definition-overriding: true
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
      routes:
        - id: m1-service
          uri: lb://micro1
          predicates:
            - Path=/m1/**
          filters:
            - RewritePath=/m1/(?<x>.*), /$\{x}
        - id: m2-service
          uri: lb://micro2
          predicates:
            - Path=/m2/**
          filters:
            - RewritePath=/m2/(?<x>.*), /$\{x}

server:
  port: 7081
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka
```

# Cors in GATEWAY

````
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }

}

````