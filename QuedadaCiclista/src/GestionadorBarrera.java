import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class GestionadorBarrera implements Runnable {

    private final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("HH:mm:ss", Locale.getDefault());

    private int counter = 0;

    @Override
    public void run() {
        if (counter == 0){
            System.out.printf("%s -> Comienza la etapa (ejecutado en %s)\n",
                    LocalTime.now().format(dateTimeFormatter), Thread.currentThread().getName());
        }else if (counter == 1){
            System.out.printf("%s -> De vuelta a casa (ejecutado en %s)\n",
                    LocalTime.now().format(dateTimeFormatter), Thread.currentThread().getName());
        }else{
            System.out.printf("%s -> Etapa finalizada (ejecutado en %s)\n",
                    LocalTime.now().format(dateTimeFormatter), Thread.currentThread().getName());
        }

        counter++;

    }

}