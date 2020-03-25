import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Main {
    final static String FILE_PATH = "src/main/resources/sitemap.txt";

    public static void main(String[] args) {
        final String START_URL = "https://skillbox.ru/";

        Set<String> links = new ForkJoinPool().invoke(new SiteMapBuilder(START_URL));
        List<String> sortedLinks = new ArrayList<>(links);
        Collections.sort(sortedLinks);
        try {

            writeSiteMapToFile(sortedLinks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Writing successfully!");
        try {
            Files.lines(Paths.get(FILE_PATH)).forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeSiteMapToFile(Collection<String> collection) throws IOException {
        FileWriter fileWriter = new FileWriter(FILE_PATH, false);
        for (String url : collection) {
            int count = (int) url.chars().filter(c -> c == '/').count(); //Считаем количество слешей в слове
            StringBuilder prefix = new StringBuilder();
            for (int i = 3; i < count; i++) {
                prefix.insert(0, "\t"); //Если слешей больше трёх, то на каждый слеш добавляем табуляцию
            }
            if (!url.endsWith("/")) prefix.insert(0, "\t");

            fileWriter.write(prefix + url + "\n");
        }
        fileWriter.close();
    }
}
