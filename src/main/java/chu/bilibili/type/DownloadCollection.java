package chu.bilibili.type;

import chu.bilibili.util.HttpClientDownPage;
import chu.bilibili.util.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//获取收藏列表
public class DownloadCollection {

    public static List<String> start(String uid) throws JsonProcessingException {
        List<String> strings = new ArrayList<String>();
        JacksonUtil jacksonUtil = new JacksonUtil();

        listInfo(uid);
        System.out.println("输入需要获取的收藏夹编号,全选输入0");
        try {
            Scanner scanner = new Scanner(System.in);
            int next = scanner.nextInt();
            List<String> collectionList = getCollectionList(uid);

//            if (next < 0 || next > collectionList.size()){
//                System.out.println("输入有误");
//            }
            if (next == 0) {
                for (String s : collectionList) {
                    String id = jacksonUtil.parse(s, "id");
                    List<String> list = getList(id);
                    for (String str : list) {
                        strings.add(str);
                    }
                }
            } else {
                String s = collectionList.get(next - 1);
                List<String> list = getList(jacksonUtil.parse(s, "id"));
                for (String str : list) {
                    strings.add(str);
                }
            }
        } catch (Exception e) {
            System.out.println("输入有误");
        }

        return strings;
    }

    //输入收藏夹ID获取所有BV号
    private static List<String> getList(String mediaId) throws JsonProcessingException {
        HttpClientDownPage httpClientDownPage = new HttpClientDownPage();
        JacksonUtil jacksonUtil = new JacksonUtil();
        List<String> strings = new ArrayList<String>();

        int count = getCount(mediaId);
        if (count % 20 == 0) {
            count = count - 1;
        }
        int page = 1;
        for (int i = 0; i < (count / 20) + 1; i++) {
            String url = "https://api.bilibili.com/x/v3/fav/resource/list?media_id=" + mediaId + "&pn=" + page + "&ps=20&keyword=&order=mtime&type=0&tid=0&platform=web&jsonp=jsonp";
            String res = httpClientDownPage.sendGet(url);
            String parse = jacksonUtil.parse(res, "data");
            List<String> medias = jacksonUtil.parseList(parse, "medias");
            for (String s : medias) {
                String bvid = jacksonUtil.parse(s, "bvid");
                strings.add(bvid.replace("\"", ""));
            }
            page = page + 1;
        }
        return strings;
    }

    //输入收藏夹ID获取内容数量
    private static int getCount(String mediaId) throws JsonProcessingException {
        HttpClientDownPage httpClientDownPage = new HttpClientDownPage();
        JacksonUtil jacksonUtil = new JacksonUtil();
        List<String> strings = new ArrayList<String>();
//        String url = "https://api.bilibili.com/x/v3/fav/folder/created/list-all?up_mid="+uid+"&jsonp=jsonp";

        String url = "https://api.bilibili.com/x/v3/fav/resource/list?media_id=" + mediaId + "&pn=1&ps=20&keyword=&order=mtime&type=0&tid=0&platform=web&jsonp=jsonp";

        String res = httpClientDownPage.sendGet(url);
        String parse = jacksonUtil.parse(res, "data");
        String info = jacksonUtil.parse(parse, "info");
        Integer count = jacksonUtil.parseInteger(info, "media_count");

        return count;
    }

    //获取收藏夹列表
    private static List<String> getCollectionList(String uid) throws JsonProcessingException {
        HttpClientDownPage httpClientDownPage = new HttpClientDownPage();
        JacksonUtil jacksonUtil = new JacksonUtil();
        List<String> list = new ArrayList<String>();

        String url = "https://api.bilibili.com/x/v3/fav/folder/created/list-all?up_mid=" + uid + "&jsonp=jsonp";

        String res = httpClientDownPage.sendGet(url);
        try {
            String parse = jacksonUtil.parse(res, "data");
            list = jacksonUtil.parseList(parse, "list");
        } catch (Exception e) {
            System.out.println("该用户没有收藏");
            System.exit(1);
        }

        return list;
    }

    //获取收藏夹信息  多少个收藏夹，分别是什么名字
    private static void listInfo(String uid) throws JsonProcessingException {
        JacksonUtil jacksonUtil = new JacksonUtil();

        List<String> collectionList = getCollectionList(uid);
        if (collectionList.size() > 0) {
            System.out.println("共有" + collectionList.size() + "个收藏夹");
            for (int i = 0; i < collectionList.size(); i++) {
                String title = jacksonUtil.parse(collectionList.get(i), "title");
                System.out.println(i + 1 + "." + title.replace("\"", ""));
            }
        }
    }

}
