package chu.bilibili.type;

import chu.bilibili.util.HttpClientDownPage;
import chu.bilibili.util.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//获取专栏
public class DownloadColumn {

    public static List<String> start(String uid) throws JsonProcessingException {
        JacksonUtil jacksonUtil = new JacksonUtil();
        List<String> strings = new ArrayList<String>();

        listInfo(uid);
        System.out.println("输入需要获取的专栏文集编号,全选输入0");
        try {
            Scanner scanner = new Scanner(System.in);
            int next = scanner.nextInt();
            List<String> anthologyList = getAnthologyList(uid);

            if (next == 0) {
                for (String s : anthologyList) {
                    String id = jacksonUtil.parse(s, "id");
                    List<String> list = getList(id);
                    for (String str : list) {
                        strings.add(str);
                    }
                }
            } else {
                String s = anthologyList.get(next - 1);
                String id = jacksonUtil.parse(s, "id");
                List<String> list = getList(id);
                for (String str : list) {
                    strings.add(str);
                }
            }

        } catch (Exception e) {
            System.out.println("输入有误");
        }
        return strings;
    }

    //输入专栏文集ID获取所有专栏的链接
    private static List<String> getList(String anthologyId) throws JsonProcessingException {
        HttpClientDownPage httpClientDownPage = new HttpClientDownPage();
        JacksonUtil jacksonUtil = new JacksonUtil();
        List<String> strings = new ArrayList<String>();

        String url = "https://api.bilibili.com/x/article/list/web/articles?id=" + anthologyId + "&jsonp=jsonp";

        String res = httpClientDownPage.sendGet(url);
        String data = jacksonUtil.parse(res, "data");
        List<String> list = jacksonUtil.parseList(data, "articles");
        for (String s : list) {
            String id = jacksonUtil.parse(s, "id");
            strings.add("https://www.bilibili.com/read/cv" + id);
        }
        return strings;
    }

    //输出文集信息
    private static void listInfo(String uid) throws JsonProcessingException {
        JacksonUtil jacksonUtil = new JacksonUtil();

        List<String> list = getAnthologyList(uid);
        if (list.size() > 0) {
            System.out.println("共有" + list.size() + "个专栏");
            for (int i = 0; i < list.size(); i++) {
                String name = jacksonUtil.parse(list.get(i), "name");
                System.out.println(i + 1 + "." + name.replace("\"", ""));
            }
        }
    }

    //输入用户ID获取所有文集
    private static List<String> getAnthologyList(String uid) throws JsonProcessingException {
        HttpClientDownPage httpClientDownPage = new HttpClientDownPage();
        JacksonUtil jacksonUtil = new JacksonUtil();
        List<String> lists = new ArrayList<String>();

        String url = "https://api.bilibili.com/x/article/up/lists?mid=" + uid + "&sort=0&jsonp=jsonp";
        String res = httpClientDownPage.sendGet(url);
        try {
            String parse = jacksonUtil.parse(res, "data");
            lists = jacksonUtil.parseList(parse, "lists");
            if (lists.size() <= 0) {
                System.out.println("该用户没有发布专栏");
                System.exit(1);
            }
        } catch (Exception e) {
            System.out.println("该用户没有发布专栏");
            System.exit(1);
        }

        return lists;
    }
}
