package com.example.multiplication;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class MutiplicationController {

    private final MultiplicationService multiplicationService;
    public MutiplicationController(MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/multiplicated/{size}")
    public Flux<MatrixResponse> getMultiplicated(@PathVariable @NonNull Integer size) {
        return multiplicationService.multiplicate(size).map(MatrixResponse::new);
    }
}
