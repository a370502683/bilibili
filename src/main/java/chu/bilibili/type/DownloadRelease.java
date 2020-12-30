package chu.bilibili.type;

import chu.bilibili.util.HttpClientDownPage;
import chu.bilibili.util.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;

//获取发布视频
public class DownloadRelease {

    public static List<String> start(String uid) throws JsonProcessingException {
        int page = 1;
        List<String> arrayList = new ArrayList<String>();
        HttpClientDownPage httpClientDownPage = new HttpClientDownPage();
        JacksonUtil jacksonUtil = new JacksonUtil();

        //String url = "https://api.bilibili.com/x/space/arc/search?mid=" + uid + "&pn=" + page + "&ps=25&index=1&jsonp=jsonp";
        int count = getCount(uid);
        if (count % 25 == 0) {
            count = count - 1;
        }
        for (int i = 0; i < (count / 25) + 1; i++) {
            String url = "https://api.bilibili.com/x/space/arc/search?mid=" + uid + "&pn=" + page + "&ps=25&index=1&jsonp=jsonp";
            String response = httpClientDownPage.sendGet(url);
            String filed = jacksonUtil.parse(response, "data");
            String parse = jacksonUtil.parse(jacksonUtil.parse(response, "data"), "list");
            List<String> list = jacksonUtil.parseList(parse, "vlist");
            for (String s : list) {
                if (s != null) {
                    String bvid = jacksonUtil.parse(s, "bvid");
                    arrayList.add(bvid.replace("\"", ""));
                }
            }
            page += 1;
        }
        return arrayList;
    }

    /**
     * 获取总数
     * 由于B站请求获取发布视频最多能获取一页五个
     * 所以需要先获取总数觉得发送多少次请求
     **/
    private static int getCount(String uid) throws JsonProcessingException {
        HttpClientDownPage httpClientDownPage = new HttpClientDownPage();
        JacksonUtil jacksonUtil = new JacksonUtil();

        String url = "https://api.bilibili.com/x/space/arc/search?mid=" + uid + "&pn=1&ps=25&index=1&jsonp=jsonp";
        String response = httpClientDownPage.sendGet(url);
        String data = jacksonUtil.parse(response, "data");
        Integer count = jacksonUtil.parseInteger(jacksonUtil.parse(data, "page"), "count");

        return count;
    }
}
