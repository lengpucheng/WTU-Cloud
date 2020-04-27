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
    private  final String TAG = "EFF_LINK_USER";
    private  DataLink LINK = DataLink.getLink();
    private  String USERNAME = "";//用户名
    private  String PASSWORD = "";//密码
    private  String LOGINTIME = "";//登录时间
    private  User user=null;//用户信息
    private  int UID = 0;//UID
    private  int OID=0;//组织号
    private  int MID=0;//部门号
    private  String USER_INFO ="";
    private  String INVITE="";
    //————————————————————登录/注册接口——————————————————————————

    public DataLinkUser() { USER_INFO=""; }

    //获取链接消息
    public String getInfo(){return USER_INFO;}

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

    //根据用户名查询信息
    public User getUser(String userName){return user;}

    //判断用户名是否存在，重复/存在返回true
    public boolean isUserName(String userName){this.USERNAME=userName;find_user();return user!=null;}

    //查询邀请码信息，存在返回ture
    public boolean isInvite(String Invite){this.INVITE=Invite;find_invite();return MID!=0;}

    //注册
    public boolean Reg(User user,String name,String email,String gender,String invite){
        this.user=user;
        if(isUserName(user.getUname()))
           return false;
        if(!do_regUser(gender,email,name))
            return false;
        if(isInvite(invite))
            add_peo(100);
        return true;
    }

    //添加联系人到部门
    public boolean addMent(int UID,int OID,int MID,int safety){
        this.UID=UID;
        this.OID=OID;
        this.MID=MID;
        return add_peo(safety);
    }
    public boolean addMent(int UID,int OID,int MID){
        return addMent(UID,OID,MID,101);
    }
    /*
    * ————————————封装的登录实现——————————————————————
    * */
    //执行登录
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
                res.close();
                pres.close();
                return true;
            }
            pres.close();
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
            pres.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //退出后刷新状态
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


    /*
    * ——————————————————————————封装的注册和修改信息实现——————————————————————————
    * */
    //查询用户
    private void find_user(){
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            return;
        }
        String sql = "SELECT uid,uname,SID,PASSINFO,LOGIN FROM user WHERE uname=? or uid=?";
        try {
            PreparedStatement pres=LINK.getConnection().prepareCall(sql);
            pres.setString(1,USERNAME);
            pres.setInt(2,UID);
            ResultSet res = pres.executeQuery();
            while (res.next()){
                user=new User();
                user.setUID(res.getInt(1));
                user.setUname(res.getString(2));
                user.setPassinfo(res.getString(3));
                user.setLogin(res.getInt(4) == 1);
                UID=user.getUID();
                USERNAME=user.getUname();
            }
            res.close();
            pres.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查询邀请码
    private void find_invite(){
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            return;
        }
        String sql = "SELECT organ_ment.OID,organ_ment.MID,organ.NAME,organ_ment.MENTNAME FROM organ_ment " +
                "LEFT JOIN organ on organ_ment.OID=organ.OID WHERE organ_ment.INVITCODE=?";
        try {
            PreparedStatement pres=LINK.getConnection().prepareCall(sql);
            pres.setString(1,INVITE);
            ResultSet res = pres.executeQuery();
            while (res.next()){
                OID=res.getInt(1);
                MID=res.getInt(2);
                USER_INFO+="已自动加入："+res.getString(3)+":";
                USER_INFO+=res.getString(4)+"部";
            }
            res.close();
            pres.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //注册用户
    private boolean do_regUser(String Gender,String Email,String NAME){
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            return false;
        }
        String sql = "INSERT user(UNAME,PASSWORD,PASSINFO,PASSKEY) VALUES(?,?,?,?)";
        try {
            PreparedStatement pres=LINK.getConnection().prepareCall(sql);
            pres.setString(1,user.getUname());
            pres.setString(2,user.getPassword());
            pres.setString(3,user.getPassinfo());
            pres.setString(4,user.getPasskey());
            pres.execute();
            pres.close();
            //获取新的用户信息
            find_user();
            //更新用户详情信息
            sql="UPDATE  user_info SET gender=?,EMAIL=?,NAME=?  WHERE UID=?";
            pres=LINK.getConnection().prepareCall(sql);
            pres.setString(1,Gender);
            pres.setString(2,Email);
            pres.setString(3,NAME);
            pres.setInt(4,UID);
            pres.execute();
            pres.close();
            return true;
        } catch (SQLException e) {
            USER_INFO="网络错误!";
            e.printStackTrace();
            return false;
        }

    }

    //添加用户到部门
    private boolean add_peo(int safety){
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            return false;
        }
        String sql="INSERT organ_peo(OID,MID,UID,SAFETY) VALUES(?,?,?,?)";
        try {
            PreparedStatement pres=LINK.getConnection().prepareCall(sql);
            pres.setInt(1,OID);
            pres.setInt(2,MID);
            pres.setInt(3,UID);
            pres.setInt(4,safety);
            pres.execute();
            pres.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

}
