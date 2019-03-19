package com.example.health_community.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import com.example.health_community.model.Medicine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Dr.P on 2018/abc/29.
 * runas /user:Dr.P "cmd /k"
 */

public class SQLUtil {
    /**
     * 这个类就是实现从assets目录读取数据库文件然后写入SDcard中,如果在SDcard中存在，就打开数据库，不存在就从assets目录下复制过去
     *
     * @author Big_Adamapple
     */
    //数据库存储路径
    String filePath = "data/data/com.example.health_community/databases/ButterflyStore.db";
    //数据库存放的文件夹 data/data/com.example.butterflyrecognition/databases 下面
    String pathStr = "data/data/com.example.health_community/databases";

    static final int BUFFER_SIZE = 20000000;
    static final String DB_NAME = "HealthCommunityStore.db"; //保存的数据库文件名
    static final String PACKAGE_NAME = "com.example.health_community";
    static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME;  //在手机里存放数据库的位置
    static final String dbPath = DB_PATH + "/databases/";
    static final String dbfile = dbPath + DB_NAME;

    public static boolean createDatabase(Context context) {

        try {
            if (!(new File(dbfile).exists())) {//判断数据库文件是否存在，若不存在则执行导入
                File filepath = new File(dbPath);
                if (!filepath.exists()) {//若路径不存在则先创建文件夹
                    if (filepath.mkdir()) {
                    } else {
                    }
                }
                if (filepath.exists()) {
                    //得到资源
                    AssetManager am = context.getAssets();
                    //得到数据库的输入流
                    //HealthCommunityStore.db
                    InputStream is = am.open("HealthCommunityStore.db");
                    FileOutputStream fos = new FileOutputStream(dbfile);
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int count = 0;
                    while ((count = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, count);
                    }
                    fos.close();
                    is.close();
                }
                return true;
            } else {
                return true;
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return false;
    }

    /**
     * 按行读取txt
     *
     * @return
     * @paramis
     * @throwsException
     */
    private String readTextFromSDcard(InputStream is)throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public static void readMonDataCsv(Context context) {
        int i = 0;// 用于标记打印的条数
        Medicine medicine1 = null;
        try {
//                if (!(new File(DB_PATH+ "/1.csv").exists())) {//判断数据库文件是否存在，若不存在则执行导入
//                    File filepath = new File(DB_PATH);
//                    if (!filepath.exists()) {//若路径不存在则先创建文件夹
//                        if (filepath.mkdir()) {
//                        } else {
//                        }
//                    }
//                    if (filepath.exists()) {
//                        //得到资源
//                        AssetManager am = context.getAssets();
//                        //得到数据库的输入流
//                        //HealthCommunityStore.db
//                        InputStream is = am.open("1.csv");
//                        FileOutputStream fos = new FileOutputStream(DB_PATH+ "/1.csv");
//                        byte[] bytes = new byte[BUFFER_SIZE];
//                        int count = 0;
//                        while ((count = is.read(bytes)) > 0) {
//                            fos.write(bytes, 0, count);
//                        }
//                        fos.close();
//                        is.close();
//                        if (new File(DB_PATH+ "/1.csv").exists()) {
                            File csv = new File(DB_PATH + "/1.csv"); // CSV文件路径
                            BufferedReader br = new BufferedReader(new FileReader(csv));
                            br.readLine();
                            String line = "";
                            /**
                             * 这里读取csv文件中的前10条数据
                             * 如果要读取第10条到30条数据,只需定义i初始值为9,wile中i<10改为i>=9&&i<30即可,其他范围依次类推
                             */
                            // && i<1366
                            while ((line = br.readLine()) != null) { // 这里读取csv文件中的前10条数据
                                //                System.out.println("第" + i + "行：" + line);// 输出每一行数据
                                /**
                                 *  csv格式每一列内容以逗号分隔,因此要取出想要的内容,以逗号为分割符分割字符串即可,
                                 *  把分割结果存到到数组中,根据数组来取得相应值
                                 */
                                String buffer[] = line.split(",");// 以逗号分隔
                                //                System.out.println("第" + i + "行：" + buffer[1]);// 取第一列数据
                                //                System.out.println("第" + i + "行：" + buffer[2]);
                                //                System.out.println("第" + i + "行：" + buffer[3]);
                                Medicine medicine = new Medicine();
                                if (i==0||medicine1.getMed_name().equals(buffer[1])&&medicine1.getMed_company().equals(buffer[6]))
                                    continue;
                                else {
                                    medicine.setMed_id(Integer.valueOf(buffer[0]));
                                    medicine.setMed_name(buffer[1]);
                                    medicine.setMed_en_name(buffer[2]);
                                    medicine.setMed_pre_img(buffer[3]);
                                    medicine.setMed_desc_img(buffer[4]);
                                    medicine.setMed_desc(buffer[5]);
                                    medicine.setMed_company(buffer[6]);
                                    medicine.setMed_type(buffer[7]);
                                    medicine.setMed_rates(buffer[8]);
                                    medicine.setMed_pre_tag(buffer[9]);
                                    medicine.setMed_desc_tag(buffer[10]);
                                    medicine.setMed_related_tips(buffer[11]);
                                    medicine.setMed_component(buffer[12]);
                                    medicine.setMed_useage(buffer[13]);
                                    medicine.setMed_avoid(buffer[14]);
                                    medicine.setMed_attention(buffer[15]);
                                    medicine.setMed_adverse(buffer[16]);
                                    medicine.setMed_purpose(buffer[17]);
                                    medicine.setMed_href(buffer[18]);
                                    medicine.save();
                                    medicine1 = medicine;
                                    i++;
                                }
//                            }
                            br.close();
//                        }
//                    }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
