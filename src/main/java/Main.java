import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        final String START_URL = "https://skillbox.ru/";

        Set<String> links = new ForkJoinPool().invoke(new SiteMapBuilder(START_URL));
        List<String> sortedLinks = new ArrayList<>(links);
        Collections.sort(sortedLinks);
        printSiteMap(sortedLinks);
    }

    private static void printSiteMap(Collection<String> collection) { //Метод печати отсортированной коллекции
        for (String url : collection) {
            int count = (int) url.chars().filter(c -> c == '/').count(); //Считаем количество слешей в слове
            String prefix = "";
            for (int i = 3; i < count; i++) {
                prefix = "\t" + prefix; //Если слешей больше трёх, то на каждый слеш добавляем табуляцию
            }
            if (!url.endsWith("/")) prefix = "\t" + prefix;

            System.out.println(prefix + url);
        }
    }
}
