import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        final String START_URL = "https://agat.lada.ru/";

        Set<String> links = new ForkJoinPool().invoke(new SiteMapBuilder(START_URL));
        links.forEach(System.out::println);
    }
}
