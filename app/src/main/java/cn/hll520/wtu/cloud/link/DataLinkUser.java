package cn.hll520.wtu.cloud.link;

import android.annotation.SuppressLint;
import android.util.Log;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import cn.hll520.wtu.cloud.model.User;

public class DataLinkUser {
    private static final String TAG = "EFF_LINK_USER";
    private static DataLink LINK = DataLink.getLink();
    private static String USERNAME = "";
    private static String PASSWORD = "";
    private static String LOGINTIME = "";
    private static User user;
    private static int UID = 0;
    //————————————————————登录接口——————————————————————————

    //用户名和密码
    public boolean login(String username, String password) {
        USERNAME = username;
        PASSWORD = password;
        return login_do();
    }

    //UID和密码
    public boolean login(int uid, String password) {
        UID = uid;
        PASSWORD = password;
        return login_do();
    }

    //获取登录后的用户信息
    public User getUser() {
        return user;
    }

    //获取上传登录时间
    public String getTime() {
        return LOGINTIME;
    }

    public void outLogin(){
        login_out();
    }



    //————————————封装的实现——————————————————————
    private boolean login_do() {
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            return false;
        }
        String SQL = "SELECT user.UID,user.UNAME,user.SID,user.SAFETY,user.PASSINFO," +
                "user.LOGINTIME,user.READSUM,organ_peo.OID,organ_peo.MID " +
                "FROM user LEFT JOIN organ_peo on organ_peo.UID=user.UID " +
                "WHERE ( user.uid=? or user.UNAME=? )and user.PASSWORD=?";
        try {
            PreparedStatement pres = LINK.getConnection().prepareStatement(SQL);
            pres.setInt(1, UID);
            pres.setString(2, USERNAME);
            pres.setString(3, PASSWORD);
            ResultSet res = pres.executeQuery();
            while (res.next()) {
                user = new User();
                user.setLogin(true);
                user.setUID(res.getInt(1));
                user.setUname(res.getString(2));
                user.setSID(res.getString(3));
                user.setUserSafety(res.getInt(4));
                user.setPassinfo(res.getString(5));
                LOGINTIME = res.getString(6);
                user.setRead(res.getInt(7));
                user.setUserOrg(res.getInt(8));
                user.setUserMent(res.getInt(9));
                login_read();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "login_uname: ", e);
        }
        return false;
    }

    //写入登录信息
    private void login_read() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        String sql = "UPDATE user SET LOGINTIME=?,LOGIN=1 WHERE uid=?";
        try {
            PreparedStatement pres = LINK.getConnection().prepareStatement(sql);
            pres.setString(1, time);
            pres.setInt(2, user.getUID());
            pres.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //跟新退出状态
    private void login_out(){
        String sql = "UPDATE user SET LOGIN=0 WHERE uid=?";
        try {
            PreparedStatement pres = LINK.getConnection().prepareStatement(sql);
            pres.setInt(1, user.getUID());
            pres.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
