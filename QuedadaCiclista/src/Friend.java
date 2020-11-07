import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Friend implements Runnable {

    private final String name;
    private final CyclicBarrier cyclicBarrier;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Friend(String name, CyclicBarrier cyclicBarrier) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(cyclicBarrier);
        this.name = name;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            goToMeeting();
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras iba al lugar de salida\n", name);
            return;
        }
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras esperaba a sus colegas en la gasolinera\n", name);
            return;
        } catch (BrokenBarrierException e) {
        }
        try {
            goToVenta();
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras iba a la venta desde la gasolinera\n", name);
            return;
        }
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras esperaba a sus colegas en la venta\n", name);
            return;
        } catch (BrokenBarrierException e) {
        }
        try {
            goBack();
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras volvía a la gasolinera desde la venta\n", name);
            return;
        }
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido mientras esperaba a sus colegas en la gasolinera\n", name);
            return;
        } catch (BrokenBarrierException e) {
        }
        try {
            goHome();
        } catch (InterruptedException e) {
            System.out.printf("%s ha sido interrumpido volviendo a casa\n", name);
        }
    }

    private void goToMeeting() throws InterruptedException {
        System.out.printf("%s -> %s ha salido de casa\n",
                LocalTime.now().format(dateTimeFormatter), name);
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3) + 1);
        System.out.printf("%s -> %s ha llegado a la gasolinera de su casa\n",
                LocalTime.now().format(dateTimeFormatter), name);
    }

    private void goToVenta() throws InterruptedException {
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(6) + 5);
        System.out.printf("%s -> %s ha legado a la venta\n",
                LocalTime.now().format(dateTimeFormatter), name);
    }

    private void goBack() throws InterruptedException {
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(6) + 5);
        System.out.printf("%s -> %s ha vuelto a la gasolinera desde la venta\n",
                LocalTime.now().format(dateTimeFormatter), name);
    }

    private void goHome() throws InterruptedException {
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3) + 1);
        System.out.printf("%s -> %s ya en casa\n",
                LocalTime.now().format(dateTimeFormatter), name);
    }

}
