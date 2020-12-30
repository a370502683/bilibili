package chu.bilibili.type;

import chu.bilibili.util.HttpClientDownPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class DownloadColumnPic {
    public static List<String> start(String uid) throws JsonProcessingException {
        HttpClientDownPage httpClientDownPage = new HttpClientDownPage();
        List<String> strings = new ArrayList<String>();

        List<String> start = DownloadColumn.start(uid);
        for (String url : start) {
            String s = httpClientDownPage.sendGet(url);
            Document parse = Jsoup.parse(s);
            Elements select = parse.select(".img-box");
            for (int i = 1; i < select.size(); i++) {
                Elements img = select.get(i).select("img");
                strings.add("http:" + img.attr("data-src"));
            }

        }
        return strings;
    }

}
