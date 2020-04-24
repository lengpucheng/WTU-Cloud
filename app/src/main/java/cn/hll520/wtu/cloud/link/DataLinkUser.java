package cn.hll520.wtu.cloud.link;

import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataLinkUser {
    private static final String TAG ="EFF_LINK_USER" ;
    private static DataLink LINK=DataLink.getLink();
    private static String USERNAME="";
    private static String PASSWORD="";
    private static int UID=0;
    //————————————————————登录接口——————————————————————————

    //用户名和密码
    public boolean login(String username,String password){
        USERNAME=username;
        PASSWORD=password;
        return login_uname();
    }
    //UID和密码
    public boolean login(int uid,String password){
        UID=uid;
        PASSWORD=password;
        return login_uname();
    }


    //————————————登录实现——————————————————————
    private boolean login_uname() {
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            return false;
        }
        String SQL="SELECT COUNT(*) FROM user WHERE (UID=? or UNAME=?)and PASSWORD=?";
        try {
            PreparedStatement pres =LINK.getConnection().prepareStatement(SQL);
            pres.setInt(1,UID);
            pres.setString(2,USERNAME);
            pres.setString(3,PASSWORD);
            ResultSet res = pres.executeQuery();
            return res.next();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "login_uname: ",e );
        }
        return true;
    }
}
