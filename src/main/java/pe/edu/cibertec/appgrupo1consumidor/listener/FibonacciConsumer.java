package pe.edu.cibertec.appgrupo1consumidor.listener;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pe.edu.cibertec.appgrupo1consumidor.service.FibonacciService;

@Component
public class FibonacciConsumer {
    private final FibonacciService fibonacciService;

    public FibonacciConsumer(FibonacciService fibonacciService) {
        this.fibonacciService = fibonacciService;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void recibirMensaje(String mensaje) {
        Integer[] integerArray = Stream.of(mensaje.split(";")).map(String::trim).map(Integer::parseInt).toArray(Integer[]::new);
        List<Integer> posiciones = Arrays.asList(integerArray);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        List<Long> resultado = fibonacciService.calculateSequence(posiciones);
        System.out.println("Resultado Fibonacci: " + resultado);
    }
}
