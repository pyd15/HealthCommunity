package com.example.health_community.util;

import android.os.AsyncTask;

/**
 * Created by Dr.P on 2017/11/6.
 */

public class DownImage extends AsyncTask {

//    private InfoDetail infoDetail;
//    //    private static final String _URL="http://120.78.72.153:8080";
//
//    private InputStream is;
//    private FileOutputStream fileOutputStream;
//
//    public DownImage(InfoDetail infoDetail) {
//        this.infoDetail = infoDetail;
//    }

    @Override
    protected Object doInBackground(Object[] params) {
//        String imagePaths = (String) params[0];
//        String pathlist = "";
//        String imagepath = "";
//        if (imagePaths!=null) {
//            if (!imagePaths.contains(".jpg")) {
//                return "";
//            }
//
//            String[] strings = imagePaths.split(",");
//            for (int i = 0; i < strings.length; i++) {
//            }
//            for (int i = 0; i < strings.length; i++) {
//                imagepath = Constants._URL + strings[i];
//                String fileName = infoDetail.getName() + i + ".jpg";
//                String directory = "/data/data/com.example.btf/files";
//                File dir = new File(directory + "/btf/");
////                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ "/btf/");
//                if (!dir.exists()) {
//                    dir.mkdir();
//                }
//                if (dir.exists() && dir.canWrite()) {
//                    File imageFile = new File(dir.getAbsolutePath() + "/" + fileName);//directory
//                    imageFile.setReadable(true);
//                    imageFile.setWritable(true);
//                    if (imageFile.exists()) {
//                        pathlist = pathlist + "," + imageFile.getPath();
//                        continue;
//                    }
//                    try {
//                        //加载一个网络图片
//                        is = new URL(imagepath).openStream();
//                        byte[] data = readInputStream(is);
//                        fileOutputStream = new FileOutputStream(imageFile);
//                        fileOutputStream.write(data);
//                        fileOutputStream.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    pathlist = pathlist + "," + imageFile.getPath();
//                }
//            }
//            return pathlist;
//        } else
            return "";
    }


}
