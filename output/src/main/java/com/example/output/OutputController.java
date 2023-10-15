package com.example.output;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

@RestController
public class OutputController {

    private final MatrixConsoleWriter matrixConsoleWriter;
    private final Scheduler scheduler = Schedulers.fromExecutorService(Executors.newFixedThreadPool(1));

    public OutputController(MatrixConsoleWriter matrixConsoleWriter) {
        this.matrixConsoleWriter = matrixConsoleWriter;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/calculated/{size}")
    public Flux<MatrixResponse> write(@PathVariable @NonNull Integer size) {
        System.out.println("requested " + size);
        Flux<MatrixResponse> matrix = WebClient.create()
                .get()
                .uri("http://localhost:8081/multiplicated/" + size)
                .retrieve()
                .bodyToFlux(MatrixResponse.class);

        return matrix
                .map(response -> {
                    matrixConsoleWriter.write(response.getMatrix());
                    return response;
                })
                .doOnNext(__ -> System.out.println("Executed. Current time: " + (System.currentTimeMillis())))
                .subscribeOn(scheduler)
                .doOnNext(value -> System.out.println(value.getMatrix()));
    }
}
