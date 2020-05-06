package cn.hll520.wtu.cloud.link;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.util.Log;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.hll520.wtu.cloud.model.User;

public class DataLinkUser {
    private  final String TAG = "EFF_LINK_USER";
    private  static DataLink LINK = DataLink.getLink();//获取链接
    private  String USERNAME = "";//用户名
    private  String PASSWORD = "";//密码
    private  String TIME = "";//登录时间
    private  User user=null;//用户信息
    private  int UID = 0;//UID
    private  String MSG ="";



    //————————————————————登录/注册接口——————————————————————————
    //获取状态消息
    public String getMsg(){return MSG;}

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
        return TIME;
    }
    //退出登录
    public void outLogin(){
        login_out();
    }

    //根据用户名或UID查询信息
    public User getUser(String userNameOrSID){return find_user(userNameOrSID,0);}
    public User getUser(int UID){return find_user("",UID);}

    //判断用户名是否存在，重复/存在返回true
    public boolean isUserName(String userName){return find_user(userName,0)!=null;}

    //查询邀请码信息，存在返回ture
    public boolean isInvite(String Invite){return find_invite(Invite)!=null;}

    //注册
    public boolean reg(User user,String name,String email,String gender,String invite){
       if(invite.equals("0"))
           return do_regUser_noCode(user,gender,email,name);
       return do_regUser_Code(user,gender,email,name,invite);
    }

    //添加联系人到部门
    public boolean addMent(int UID,int OID,int MID,int safety){ return add_peo(OID,MID,UID,safety); }
    public boolean addMent(int UID,int OID,int MID){
        return addMent(UID,OID,MID,101);
    }

    //修改密码
    public boolean updataUserPass(int UID,String passKey,String newPass){return _upUser(UID,passKey,newPass);}

    //返回所在组织及其名称
    public List<ContentValues> getOrganAndName(int UID){return getOrganAndName_mpl(UID);}
    //返回部门及其名称
    public List<ContentValues> getMentAndName(int OID){return getMentAndName_mpl(OID);}



    /*
    * ————————————————————————————————————-封装的登录实现————————————————————————————————————————
    * */
    //执行登录
    private boolean login_do() {
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            MSG="网络异常";
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
                TIME = res.getString(6);
                user.setRead(res.getInt(7));
                user.setUserOrg(res.getInt(8));
                user.setUserMent(res.getInt(9));
                UID=user.getUID();
                USERNAME=user.getUname();
                login_read();
                res.close();
                pres.close();
                return true;
            }
            pres.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "login_do: ",e );
            MSG="网络异常";
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
            Log.e(TAG, "login_read: ",e );
            MSG="网络错误";
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
            Log.e(TAG, "login_out: ",e );
            MSG="网络错误";
        }
    }


    /*
    * ——————————————————————————封装的注册和修改信息实现——————————————————————————
    * */

    //查询用户
    private User find_user(String usernameOrSID,int UID){
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            MSG="网络异常";
            return null;
        }
        User user=null;
        String sql = "SELECT uid,uname,SID,PASSINFO,LOGIN FROM user WHERE uname=? or uid=? or SID=?";
        try {
            PreparedStatement pres=LINK.getConnection().prepareCall(sql);
            pres.setString(1,usernameOrSID);
            pres.setInt(2,UID);
            pres.setString(3,usernameOrSID);
            ResultSet res = pres.executeQuery();
            while (res.next()){
                user=new User();
                user.setUID(res.getInt(1));
                user.setUname(res.getString(2));
                user.setSID(res.getString(3));
                user.setPassinfo(res.getString(4));
                user.setLogin(res.getInt(5) == 1);
                res.close();
                pres.close();
                return user;
            }
            res.close();
            pres.close();
            MSG="用户不存在";
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "find_user: ",e );
            MSG="网络异常";
        }
        return user;
    }

    //查询邀请码是否存在
    private String[] find_invite(String invite){
        String[]  strings = new String[4];//OID,MID,ONAME,MNAME
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            MSG="网络错误";
            return null;
        }
        String sql = "SELECT organ_ment.OID,organ_ment.MID,organ.NAME,organ_ment.MENTNAME FROM organ_ment " +
                "LEFT JOIN organ on organ_ment.OID=organ.OID WHERE organ_ment.INVITCODE=?";
        try {
            PreparedStatement pres=LINK.getConnection().prepareCall(sql);
            pres.setString(1,invite);
            ResultSet res = pres.executeQuery();
            while (res.next()){
                strings[0]= String.valueOf(res.getInt(1));
                strings[1]= String.valueOf(res.getInt(2));
                strings[2]= res.getString(3);
                strings[3]= res.getString(4);
                res.close();
                pres.close();
                return strings;
            }
            res.close();
            pres.close();
            MSG="邀请码不存在";
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "find_invite: ",e );
            MSG="网络错误";
        }
        return null;
    }

    //添加用户到部门
    private boolean add_peo(int OID,int MID,int UID,int safety){
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            MSG="网络错误！";
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
            Log.e(TAG, "add_peo: ",e);
            MSG="网络错误！";
            return false;
        }
    }


    //用户注册一：无邀请码
    private boolean do_regUser_noCode(User user,String Gender,String Email,String NAME){
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            MSG="网络链接失败";
            return false;
        }
        //新建用户
        String sql = "INSERT user(UNAME,PASSWORD,PASSINFO,PASSKEY) VALUES(?,?,?,?)";
        try {
            //写入信息
            PreparedStatement pres=LINK.getConnection().prepareCall(sql);
            pres.setString(1,user.getUname());
            pres.setString(2,user.getPassword());
            pres.setString(3,user.getPassinfo());
            pres.setString(4,user.getPasskey());
            pres.execute();
            pres.close();
            Log.d(TAG, "do_regUser_Code: 注册写入成功");
        } catch (SQLException e) {
            MSG ="用户注册失败!";
            e.printStackTrace();
            return false;
        }
        //更新用户详情信息
        try {
            sql="UPDATE  user_info SET gender=?,EMAIL=?,NAME=?  WHERE UID=?";
            //获取新的用户信息
            user=find_user(user.getUname(),0);
            //更新信息
            PreparedStatement pres = LINK.getConnection().prepareCall(sql);
            pres.setString(1,Gender);
            pres.setString(2,Email);
            pres.setString(3,NAME);
            pres.setInt(4,user.getUID());
            pres.execute();
            pres.close();
            Log.d(TAG, "do_regUser_Code: 信息更新成功");
        } catch (SQLException e) {
            MSG ="用户信息写入失败!";
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //用户注册二：有邀请码
    private boolean do_regUser_Code(User user,String Gender,String Email,String NAME,String invite){
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            MSG="网络链接失败";
            return false;
        }
        //新建用户
        String sql = "INSERT user(UNAME,PASSWORD,PASSINFO,PASSKEY) VALUES(?,?,?,?)";
        try {
            //写入信息
            PreparedStatement pres=LINK.getConnection().prepareCall(sql);
            pres.setString(1,user.getUname());
            pres.setString(2,user.getPassword());
            pres.setString(3,user.getPassinfo());
            pres.setString(4,user.getPasskey());
            pres.execute();
            pres.close();
            Log.d(TAG, "do_regUser_Code: 注册写入成功");
        } catch (SQLException e) {
            MSG ="用户注册失败!";
            e.printStackTrace();
            return false;
        }
        //更新用户详情信息
        try {
            sql="UPDATE  user_info SET gender=?,EMAIL=?,NAME=?  WHERE UID=?";
            //获取新的用户信息
            user=find_user(user.getUname(),0);
            //更新信息
            PreparedStatement pres = LINK.getConnection().prepareCall(sql);
            pres.setString(1,Gender);
            pres.setString(2,Email);
            pres.setString(3,NAME);
            pres.setInt(4,user.getUID());
            pres.execute();
            pres.close();
            Log.d(TAG, "do_regUser_Code: 信息更新成功");
        } catch (SQLException e) {
            MSG ="用户信息写入失败!";
            e.printStackTrace();
            return false;
        }
        //写入部门信息
        try {
            String[] strings=find_invite(invite);
            add_peo(Integer.parseInt(strings[0]),Integer.parseInt(strings[1]),user.getUID(),100);
            MSG="成功使用邀请码！以自动加入"+strings[2]+strings[3];
        } catch (Exception e) {
            e.printStackTrace();
            MSG="使用邀请码失败！请重试";
            return false;
        }
        return true;
    }

    private boolean _upUser(int UID,String passKey,String newPass){
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            MSG="网络链接失败";
            return false;
        }
        String sql="UPDATE user SET PASSWORD=? WHERE UID=? AND PASSKEY=?";
        try {
            PreparedStatement pres=LINK.getConnection().prepareCall(sql);
            pres.setString(1,newPass);
            pres.setInt(2,UID);
            pres.setString(3,passKey);

            MSG="答案错误";
            int i= 0;
            i=pres.executeUpdate();
            pres.close();
            Log.e(TAG, "SQL: "+sql );
            return i!=0;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "_upUser: ",e );
            MSG="网络链接失败";
            return false;
        }
    }



    /*
    * ——————————————————————————————————封装的查询实现——————————————————————————————————
    * */
    //返回所在组织和其名称
    private List<ContentValues> getOrganAndName_mpl(int UID){
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            MSG="网络链接失败";
            return null;
        }
        String SQL="SELECT DISTINCT organ_peo.OID,organ.NAME FROM organ_peo  " +
                    "LEFT JOIN organ ON organ_peo.OID=organ.OID  " +
                    "WHERE organ_peo.UID=?";
        try {
            PreparedStatement pres=LINK.getConnection().prepareCall(SQL);
            pres.setInt(1,UID);
            ResultSet res = pres.executeQuery();
            List<ContentValues> values=new ArrayList<>();
            while (res.next()){
                ContentValues cv=new ContentValues();
                cv.put("OID",res.getInt(1));
                cv.put("NAME",res.getString(2));
                values.add(cv);
            }
            res.close();
            pres.close();
            return values;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "获取列表失败");
            MSG="网络列表失败";
        }
        return null;
    }

    //返回部门和名称
    private List<ContentValues> getMentAndName_mpl(int OID){
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            MSG="网络链接失败";
            return null;
        }
        String SQL="SELECT DISTINCT organ_ment.MID,organ_ment.MENTNAME FROM organ_ment WHERE organ_ment.OID=?";
        try {
            PreparedStatement pres=LINK.getConnection().prepareCall(SQL);
            pres.setInt(1,OID);
            ResultSet res = pres.executeQuery();
            List<ContentValues> values=new ArrayList<>();
            while (res.next()){
                ContentValues cv=new ContentValues();
                cv.put("MID",res.getInt(1));
                cv.put("NAME",res.getString(2));
                values.add(cv);
            }
            res.close();
            pres.close();
            return values;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "获取列表失败");
            MSG="网络列表失败";
        }
        return null;
    }

}
