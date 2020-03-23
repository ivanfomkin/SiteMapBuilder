import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.concurrent.RecursiveTask;

public class SiteMapBuilder extends RecursiveTask<Set<String>> {
    private final String url;

    public SiteMapBuilder(String url) {
        this.url = url;
    }

    @Override
    protected Set<String> compute() {
        Set<String> result = new TreeSet<>(); //Будем тут хранить результат
        List<SiteMapBuilder> subTasks = new LinkedList<>(); //Лист подзаданий
        for (String subLink : getPageLinks(url)) { //Пробегаемся по каждой ссылке со странице
            SiteMapBuilder task = new SiteMapBuilder(subLink); //Добавляем для неё новое задание
            task.fork(); //Ответвляем её
            subTasks.add(task); //И добавляем в лист заданий
        }
        for (SiteMapBuilder task : subTasks) { //Джойним все задания в один Set
            result.addAll(task.join());
        }
        return result;
    }

    private static Set<String> getPageLinks(String mainPageUrl) { //Метод, который получает все ссылки со страницы в Set
        Set<String> mainPageLinks = new TreeSet<>();

        try {
            Thread.sleep(300);
            Document mainPage = Jsoup.connect(mainPageUrl).maxBodySize(0).get();
            Elements links = mainPage.select("a[href]");
            links.forEach(l -> {
                String currentLink = l.absUrl("href");
                if (currentLink.contains(mainPageUrl))
                    mainPageLinks.add(currentLink);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mainPageLinks;
    }
}
