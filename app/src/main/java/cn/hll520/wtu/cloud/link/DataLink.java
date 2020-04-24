package cn.hll520.wtu.cloud.link;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

class DataLink {
    private static Connection CONNECT;
    private static DataLink LINK;
    private final static String LINK_NAME="wtucloud_test_1";
    private final static String LINK_PASS="WTUCloud_test";
    private DataLink(){
        CONNECT =createConnection();
    }


    //获取链接
    static  DataLink getLink(){
        if(LINK==null)
            LINK = new DataLink();
        return LINK;
    }

    Connection getConnection(){return CONNECT;}

    //链接数据库
    private Connection createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://sql.hll520.cn:3306/wtucloud_test?useSSL=false";
            Connection connection=DriverManager.getConnection(url, DataLink.LINK_NAME, DataLink.LINK_PASS);
            Log.i("err","链接服务器成功\n");
            return connection;
        } catch (Exception e) {
            Log.i("err","链接服务器失败\n"+e);
            return null;
        }
    }








}
