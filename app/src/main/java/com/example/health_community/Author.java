package com.example.health_community;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/01/22
 * desc:
 */
public class Author {
    String name;
    int articleNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(int articleNum) {
        this.articleNum = articleNum;
    }

//    public static void main(String[] args) {
//        try {
//            /**
//             * Document：相当于一个Html文件
//             Elements：相当于一个标签的集合
//             Element：相当于一个标签
//             */
//            for (int k = 0; k < 5; k++) {//爬取5页内容
//                Document doc = Jsoup.connect("http://www.budejie.com/" + k).get();
//                Elements els = doc.select("div.j-r-list-c-desc");
//                //获取class为u-txt的div下span子元素内容
//                //                        Elements els_time = doc.select("div.u-txt > span");
//                //                        Elements span_time = doc.select("span.u-time");
//
//
//                for (int i = 0; i < els.size(); i++) {
//                    Elements el = els.get(i).select("a");
//
//                    String href = el.attr("href");
//                    //拿到这条段子的详情链接
//                    Log.e("链接", href);
//
//                    Document doc_detail = Jsoup.connect("http://www.budejie.com/" + href).get();
//                    Elements els_detail = doc_detail.select(".j-r-list-c-desc");
//                    Elements els_user = doc_detail.select("div.u-txt");
//                    Log.e("user", els_user.text());
//                    Elements els_time = doc_detail.select("div.u-txt > span");
//                    Elements span_time = doc_detail.select("span.u-time");
//                    Elements u_time = doc_detail.select(".u-time");
//                    Log.e("time", els_time.text());
//                    Log.e("sp_time", span_time.text());
//                    Log.e("u_time", u_time.text());
//                    Log.e("內容", els_detail.text());
//                    //
//                    Elements els_pic = doc_detail.select(".j-r-list-c-img img[src$=jpg]");
//                    if (!els_pic.isEmpty()) {
//                        String pic = els_pic.attr("src");
//                        Log.e("图片连接", "" + pic);
//                    } else {
//                        Log.e("图片连接", "无");
//                    }
//
//                    //                                                        Content content = new Content(els_detail.text(), els_pic.attr("src"));
//                    //                                                        content.save();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
