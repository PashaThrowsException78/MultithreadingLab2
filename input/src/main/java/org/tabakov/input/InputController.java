package org.tabakov.input;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class InputController {

    private static final int DURATION_MILLIS = 150;
    private static final int THREAD_COUNT = 1;

    private final MatrixGenerator matrixGenerator;

    public InputController(MatrixGenerator matrixGenerator) {
        this.matrixGenerator = matrixGenerator;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/matrices/{size}")
    public Flux<MatricesDTO> getMatrices(@PathVariable @NonNull Integer size) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        Scheduler scheduler = Schedulers.fromExecutorService(executorService);
        return
                Flux.range(1, 3)
                        .publishOn(scheduler)
                        .flatMap(x ->
                                Mono.just(new MatricesDTO(
                                                matrixGenerator.generate(size),
                                                matrixGenerator.generate(size)
                                        )
                                )
                        )
                        .doOnNext(__ -> System.out.println("Executed matrix generation. Current time: " + (System.currentTimeMillis())))
                        .doFinally(__ -> executorService.shutdownNow());
    }

    private void multiplyAndPrint(List<List<Integer>> m1, List<List<Integer>> m2) {
        int[][] res = new int[m1.size()][m1.size()];
        for (int i = 0; i < m1.size(); i++) {
            for (int j = 0; j < m1.size(); j++) {
                for (int k = 0; k < m1.size(); k++) {
                    res[i][j] += m1.get(i).get(k) * m2.get(k).get(j);
                }
            }
        }

        System.out.println("result: ");
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                System.out.format("%6d ", res[i][j]);
            }
            System.out.println();
        }
    }
}
