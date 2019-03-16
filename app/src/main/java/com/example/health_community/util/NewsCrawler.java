package com.example.health_community.util;

import com.example.health_community.News;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/02/06
 * desc:
 */

public class NewsCrawler {
    private News news;
    private List<News> list = new ArrayList<>();
    private String resource = "mybatis-config.xml";
    private static int index;
    private int TYPE_39_xinzhi = 1;
    private int TYPE_FeiHua_redian = 2;

    /**
     * 定时任务，每隔5秒隔行一次
     * cron表达式含义：
     * “0 0 12 * * ?” 每天中午十二点触发
     * “0 15 10 ? * *” 每天早上10：15触发
     * “0 15 10 * * ?” 每天早上10：15触发
     * “0 15 10 * * ? *” 每天早上10：15触发
     * “0 15 10 * * ? 2005” 2005年的每天早上10：15触发
     * “0 * 14 * * ?” 每天从下午2点开始到2点59分每分钟一次触发
     * “0 0/5 14 * * ?” 每天从下午2点开始到2：55分结束每5分钟一次触发
     * “0 0/5 14,18 * * ?” 每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
     * “0 0-5 14 * * ?” 每天14:00至14:05每分钟一次触发
     * “0 10,44 14 ? 3 WED” 三月的每周三的14：10和14：44触发
     * “0 15 10 ? * MON-FRI” 每个周一、周二、周三、周四、周五的10：15触发
     * http://m.39.net/news/xinzhi/index_
     * https://m.fh21.com.cn/news/list/7818/
     */
    //    @Scheduled(cron = "0/1000 * * * * ?")
    public void getNews() {
        //        getMedicinesOption("http://m.39.net/news/xinzhi/index_", 40, TYPE_39_xinzhi);
        //                getMedicinesOption("http://m.39.net/care/ys/index",18,40,TYPE_39_xinzhi,"39_ysaq");
        getNewsOption("http://m.39.net/xl/xltm/index", 0, 1, TYPE_39_xinzhi, "39_xlbk");

        //        getMedicinesOption("https://m.fh21.com.cn/news/list/7818/", 30,40, TYPE_FeiHua_redian, "xinzhi");
        //        getMedicinesOption("https://m.fh21.com.cn/news/list/7838/", 30,100, TYPE_FeiHua_redian, "keyan");
    }

    public void getNewsOption(String baseUrl, int start_page, int end_page, int type, String news_type) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date sqlDate;
                Elements els;
                Document doc;
                Document hrefDoc;
                String title, href, desc, img, date, content;
                try {
                    for (int k = start_page; k <= end_page; k++) {
                        if (k == 0 && type == 1)
                            doc = Jsoup.connect(baseUrl + ".html").get();
                        else if (type == 1)
                            doc = Jsoup.connect(baseUrl + "_" + k + ".html").get();
                        else
                            doc = Jsoup.connect(baseUrl + (k + 1) + "/").get();
                        if (type == 1)
                            //                            els = doc.select("ul#mListImg > li:has(span)");
                            els = doc.select("ul#mListImg > li:has(img)");
                        else
                            els = doc.select("div.mod-pic-list > div:has(mip-img)");
                        for (int i = 0; i < els.size(); i++) {
                            news = new News();
                            Element el = els.get(i);
                            if (type == TYPE_39_xinzhi) {
                                title = el.select("img").attr("alt");
                                href = el.select("a").first().attr("href");
                                //                                if (href.equals("http://m.39.net/xl/a_4365628.html")||href.equals("http://m.39.net/xl/a_4352576.html"))continue;
                                desc = el.select("p").text();
                                img = el.select("img").attr("src");
                                try {
                                    hrefDoc = Jsoup.connect(href).get();
                                    date = hrefDoc.select("span:contains(-)").first().text();
                                    content = getHrefContent(hrefDoc, TYPE_39_xinzhi);
                                    sqlDate = new Date(formatter.parse(date).getTime());
                                } catch (HttpStatusException e) {
                                    continue;
                                }
                            } else {
                                title = el.select("a.mix-title-link").text();
                                href = el.select("a").first().attr("href");
                                hrefDoc = Jsoup.connect(href).get();
                                desc = hrefDoc.select(".mod-detail-infro").select(".wbwr").text().substring(0, 29) + "......";
                                img = el.select("mip-img").attr("src");
                                date = el.select("time > span").first().text();
                                content = getHrefContent(hrefDoc, TYPE_FeiHua_redian);
                                sqlDate = new Date(formatter.parse(date).getTime());
                            }
                            System.out.println("1.标题：" + title);
                            System.out.println("2.详情链接:" + href);
                            System.out.println("3.概要:" + desc);
                            //                            System.out.println("4.详情：" + content);
                            if (!img.isEmpty()) {
                                System.out.println("4.图片连接:" + img);
                            } else {
                                //                                    System.out.println("4.图片连接:" + "无");
                                continue;
                            }
                            index += 1;
                            System.out.println("5.发布时间：" + date);
                            System.out.println("6.已爬取文章数量：" + index);
                            news.setNews_id(index);
                            news.setNews_title(title);
                            news.setNews_contents(content);
                            news.setNews_href(href);
                            news.setNews_desc(desc);
                            news.setNews_image(img);
//                            news.setNews_date(sqlDate);
                            //                            if (type == 1)
                            //                                news.setNews_type("39net");
                            //                            else {
                            //                                news.setNews_type("xinzhi");
                            //                            }
                            news.setNews_type(news_type);
                            list.add(news);
                        }
                    }
                    //                    List<News> news=iNewsDAO.findAll();
                    //                    System.out.println(news.size());
                    System.out.println(index);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }

    public String getHrefContent(Document hrefDoc, int type) throws IOException {
        Element head = hrefDoc.head();
        Element section;
        StringBuilder sectionString;
        Document newBody;
        if (type == 1) {
            section = hrefDoc.select("section:has(h1)").first();
            section.select(".tool").select(".w100").select(".ov").remove();
            newBody = Jsoup.parseBodyFragment(section.toString());
        } else {
            section = hrefDoc.select("section.mod-wrap").first();
            section.select("div.C009").remove();
            section.select("div.col-details-tip").remove();
            sectionString = new StringBuilder();
            sectionString.append("<div class=\"view-wrap\">\n").append(section.toString()).append("\n").append("</div>").append("\n").append(hrefDoc.select("script[src]"));
            newBody = Jsoup.parseBodyFragment(sectionString.toString());
        }
        Element body = newBody.body();
        StringBuilder newHTML = new StringBuilder();
        newHTML.append(head).append(body.toString());
        Document newDoc = Jsoup.parse(newHTML.toString());
        return newDoc.toString();
    }

    //    public void getFeiHuaNews(String baseUrl) {
    //        new Thread() {
    //            @Override
    //            public void run() {
    //                super.run();
    //                int count = 0;
    //                StringBuilder href = new StringBuilder();
    //                Document hrefDoc;
    //                try {
    //                    for (int k = 1; k < 164; k++) {
    //                        //前17页都有图
    //                        //https://m.fh21.com.cn/news/list/7818/
    //                        Document doc = Jsoup.connect("https://m.fh21.com.cn/news/list/7818/" + k + "/").get();
    //                        Elements els = doc.select("div.mod-pic-list > div:has(mip-img)");
    //                        System.out.println("每页文章数-elsl.size:" + els.size());
    //                        for (int i = 0; i < els.size(); i++) {
    //                            count++;
    //                            Element el = els.get(i);
    //                            href.append(el.select("a").first().attr("href"));
    //                            hrefDoc = Jsoup.connect(href.toString()).get();
    //                            String content = "";
    //                            String img = el.select("mip-img").attr("src");
    //                            String hrefContent = getHrefInfo(hrefDoc,2);
    //                            String date = el.select("time > span").first().text();
    //                            //                            System.out.println(hrefContent);
    //                            System.out.println("1.标题：" + el.select("a.mix-title-link").text());
    //                            System.out.println("2.详情链接:" + href);
    //                            System.out.println("3.概要:" + content);
    //                            if (!img.isEmpty()) {
    //                                System.out.println("4.图片链接:" + img);
    //                            } else {
    //                                System.out.println("4.图片链接:" + "无");
    //                            }
    //                            System.out.println("5.发布时间:" + date);
    //                            href = new StringBuilder();
    //                        }
    //                    }
    //                } catch (IOException e) {
    //                    e.printStackTrace();
    //                }
    //                System.out.println(count);
    //            }
    //        }.start();

    //    }

    //    private String getFeiHuaHrefContent(Document hrefDoc) throws IOException {
    //        Element head = hrefDoc.head();
    //        Element section = hrefDoc.select("section.mod-wrap").first();
    //        section.select("div.C009").remove();
    //        section.select("div.col-details-tip").remove();
    //        StringBuilder sectionString = new StringBuilder();
    //        sectionString.append("<div class=\"view-wrap\">\n").append(section.toString()).append("\n").append("</div>").append("\n");
    //        System.out.println(sectionString.toString());
    //        Document newBody = Jsoup.parseBodyFragment(sectionString.toString());
    //        Element body = newBody.body();
    //        StringBuilder newHTML = new StringBuilder();
    //        newHTML.append(head).append(body.toString());
    //        Document newDoc = Jsoup.parse(newHTML.toString());
    //        return newDoc.toString();
    //    }

    public static void main(String[] args) throws IOException {
        NewsCrawler newsCrawler = new NewsCrawler();
        newsCrawler.getNews();
    }

}
