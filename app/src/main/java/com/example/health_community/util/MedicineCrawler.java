package com.example.health_community.util;


import com.example.health_community.model.Medicine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/12
 * desc:
 */

public class MedicineCrawler {
    private Medicine medicine;
    private List<Medicine> list = new ArrayList<>();
    private String resource = "mybatis-config.xml";
    private static int index;

    public void getMedicines() {
        //        getMedicinesOption("http://m.39.net/news/xinzhi/index_", 40, TYPE_39_xinzhi);
        //                getMedicinesOption("http://m.39.net/care/ys/index",18,40,TYPE_39_xinzhi,"39_ysaq");
//        getMedicinesOption("http://ypk.39.net", "/ganmao/p",1, 2, "gan_mao");
//        getMedicinesOption("http://ypk.39.net", "/ganmao/p",1, 20, "gan_mao");
//        getMedicinesOption("http://ypk.39.net", "/ganmao/p",21, 40, "gan_mao");
//        getMedicinesOption("http://ypk.39.net", "/ganmao/p",41, 60, "gan_mao");
        getMedicinesOption("http://ypk.39.net", "/ganmao/p",61, 80, "gan_mao");
//        getMedicinesOption("http://ypk.39.net", "/ganmao/p",81, 100, "gan_mao");
        getMedicinesOption("http://ypk.39.net", "/ganmao/p",101, 120, "gan_mao");
        getMedicinesOption("http://ypk.39.net", "/ganmao/p",121, 134, "gan_mao");

        //        getMedicinesOption("https://m.fh21.com.cn/news/list/7818/", 30,40, TYPE_FeiHua_redian, "xinzhi");
        //        getMedicinesOption("https://m.fh21.com.cn/news/list/7838/", 30,100, TYPE_FeiHua_redian, "keyan");
    }

    /**
     * icon1：红OTC 甲类非处方药。不须医生处方就可以购买和出售，但必须在药店出售，并在药师指导下使用。
     * icon2：绿OTC 乙类非处方药。有着长期安全使用的记录，可以像普通商品一样在超市、杂货店直接出售。
     * icon4：蓝基 国家基本药物是指由政府制定的《国家基本药物目录》中的药品。国家基本药物的遴选原则为：临床必需、安全有效、价格合理、使用方便、中西药并重。
     * icon5：绿保 医保药品乙类
     * icon6：ZB 中药保护品种二级
     * icon9：红外 该产品为外用，请勿内服！
     * icon13：GS1 条码已在中国物品编码中心注册
     */
    public void getMedicinesOption(String baseUrl,String postfix, int start_page, int end_page, String news_type) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Elements els;
                Elements med_pre_tag;
                Document doc;
                StringBuilder stringBuilder = new StringBuilder();
                String med_name, med_href, med_desc, med_pre_img;
                int count = 0;
                try {
                    for (int k = start_page; k <= end_page; k++) {
                        if (k==5)continue;
                        doc = Jsoup.connect(baseUrl + postfix + k).get();
                        els = doc.select(".search_ul").select(".search_ul_yb > li");
                        for (int i = 0; i < els.size(); i++) {
                            medicine = new Medicine();
                            stringBuilder.delete(0, stringBuilder.length());
                            Element el = els.get(i);
                            med_name = el.select("img").attr("alt");
                            med_href = el.select("a").first().attr("abs:href");
                            med_desc = el.select("p.p1").text();
                            med_pre_img = el.select("img").attr("src");
                            med_pre_tag = el.select("i[class]");
                            for (Element el1 : med_pre_tag) {
                                stringBuilder.append(el1.className()).append(",");
                            }
                            System.out.println(med_href);
                            medicine.setMed_id(index);
                            medicine.setMed_name(med_name);
                            medicine.setMed_desc(med_desc);
                            medicine.setMed_pre_img(med_pre_img);
                            medicine.setMed_pre_tag(stringBuilder.toString());
                            medicine.setMed_type(news_type);
                            medicine.setMed_href(med_href);
//                            try {
                                getHrefInfo(med_href, medicine);
                                getHrefManual(med_href + "manual", medicine);
//                            } catch (HttpStatusException e) {
//                                continue;
//                            }
//                            if (med_pre_img.isEmpty()) {
//                                continue;
//                            }
                            index += 1;
                            count++;
                            System.out.println(count+"-"+med_name+"-"+med_href);
//                                                        list.add(medicine);
                            medicine.save();
                        }
                    }
//                    LitePal.saveAll(list);
//                    index = iMedicineDAO.adds(list);
//                                                            List<Medicine> medicines= (List<Medicine>)iMedicineDAO.findAll();
//                                                            System.out.println(medicines.size());
//                    System.out.println(count);
                    //                } catch (IOException | ParseException e) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }

    public void getHrefInfo(String href, Medicine medicine) throws IOException {
        Document hrefDoc = Jsoup.connect(href).get();
        StringBuilder stringBuilder = new StringBuilder();
        Elements elements;
        System.out.println(hrefDoc.select("cite.t2").text());//英文名
        medicine.setMed_en_name(hrefDoc.select("cite.t2").text());
        //        System.out.println(hrefDoc.select("div#ban_num1 >ul >li").attr("src"));
        //        System.out.println(hrefDoc.select("div#ban_num1 >ul >li >a >img").get(1).attr("src"));
        System.out.println(hrefDoc.select("li.company").text());
        System.out.println(hrefDoc.select("ul.t3 > li.telephone").text());
        medicine.setMed_company(hrefDoc.select("li.company").text() + " " + hrefDoc.select("li.telephone").text());
        elements = hrefDoc.select("div#ban_num1 >ul >li >a >img");
        for (Element e : elements) {
            stringBuilder.append(e.attr("src")).append(",");
        }
        System.out.println(stringBuilder);
        if (stringBuilder.toString().equals("")) {
            medicine.setMed_desc_img(hrefDoc.select("div.imgbox >img").attr("src"));
        } else
            medicine.setMed_desc_img(stringBuilder.toString());
        System.out.println(hrefDoc.select(".whatsthis").select(".clearfix").text());//详情标签
        medicine.setMed_desc_tag(hrefDoc.select(".whatsthis").select(".clearfix").text());
        System.out.println(hrefDoc.select("div.related_tips a").text());//相关标签
        medicine.setMed_related_tips(hrefDoc.select("div.related_tips a").text());
        //        System.out.println(hrefDoc.select("dl.clearfix >ul >li >span").get(1).className());
        elements = hrefDoc.select("dl.clearfix").select("span[class]");
        stringBuilder.delete(0, stringBuilder.length());
        for (Element e : elements) {
            stringBuilder.append(e.className()).append(",");
        }
        System.out.println(stringBuilder);
        medicine.setMed_rates(stringBuilder.toString());
    }

    public void getHrefManual(String href, Medicine medicine) throws IOException {
        Document hrefDoc = Jsoup.connect(href).get();
        //        System.out.println(hrefDoc.select("dl > dt").first().text());
        //        medicine.setMed_component(hrefDoc.select("dl > dt:contains(成  分)").text());
        //        System.out.println(hrefDoc.select("dl > dt:contains(功能主治)").text());
        //        medicine.setMed_purpose(hrefDoc.select("dl > dt:contains(功能主治)").text());
        //        System.out.println(hrefDoc.select("dl > dt:contains(用法用量)").text());
        //        medicine.setMed_useage(hrefDoc.select("dl > dt:contains(用法用量)").text());
        medicine.setMed_component(hrefDoc.select("dl > dt:contains(成份) + dd").text());
        System.out.println(hrefDoc.select("dl > dt:contains(成份) + dd").text());
        //        System.out.println(hrefDoc.select("dl > dt:contains(成份) ~ dd").text());
        if (hrefDoc.select("dl > dt:contains(功能主治) + dd").text().equals(""))
            medicine.setMed_purpose(hrefDoc.select("dl > dt:contains(适应症) + dd").text());
        else
            medicine.setMed_purpose(hrefDoc.select("dl > dt:contains(功能主治) + dd").text());
        System.out.println(hrefDoc.select("dl > dt:contains(功能主治) + dd").text());
        medicine.setMed_useage(hrefDoc.select("dl > dt:contains(用法用量) + dd").text());
        System.out.println(hrefDoc.select("dl > dt:contains(用法用量) + dd").text());
        medicine.setMed_adverse(hrefDoc.select("dl > dt:contains(不良反应) + dd").text());
        System.out.println(hrefDoc.select("dl > dt:contains(不良反应) + dd").text());
        medicine.setMed_avoid(hrefDoc.select("dl > dt:contains(禁忌) + dd").text());
        System.out.println(hrefDoc.select("dl > dt:contains(禁忌) + dd").text());
        medicine.setMed_attention(hrefDoc.select("dl > dt:contains(注意事项) + dd").text());
        System.out.println(hrefDoc.select("dl > dt:contains(注意事项) + dd").text());
    }

    public static void main(String[] args) {
        MedicineCrawler medicineCrawler = new MedicineCrawler();
        medicineCrawler.getMedicines();
    }
}
