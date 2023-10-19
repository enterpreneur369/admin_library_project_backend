package co.calderonlabs.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/book/getBooks"), handler::getBooks)
                .andRoute(GET("/api/user/getUsers"), handler::getUsers)
                .andRoute(GET("/api/loan/getLoans"), handler::getLoans)
                .andRoute(GET("/api/book/{id}"), handler::getBookById)
                .andRoute(GET("/api/user/{id}"), handler::getUserById)
                .andRoute(GET("/api/loan/{id}"), handler::getLoanById)
                .andRoute(POST("/api/book/createBook"), handler::createBook)
                .andRoute(POST("/api/user/createUser"), handler::createUser)
                .andRoute(POST("/api/loan/createLoan"), handler::createLoan)
                .andRoute(PUT("/api/book/updateBook/{id}"), handler::updateBook)
                .andRoute(PUT("/api/user/updateUser/{id}"), handler::updateUser)
                .andRoute(PUT("/api/loan/updateLoan/{id}"), handler::updateLoan)
                .andRoute(DELETE("/api/book/deleteBook/{id}"), handler::deleteBook)
                .andRoute(DELETE("/api/user/deleteUser/{id}"), handler::deleteUser);
    }
}
