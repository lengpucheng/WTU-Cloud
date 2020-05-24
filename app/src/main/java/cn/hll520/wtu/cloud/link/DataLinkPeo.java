package cn.hll520.wtu.cloud.link;

import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.hll520.wtu.cloud.model.People;


public class DataLinkPeo {
    private static final String TAG = "EFF_LINK_PEO";
    private static DataLink LINK = DataLink.getLink();
    private List<People> peoples=new ArrayList<>();
    private int UID;
    private String MSG="";//信息


    //获取信息
    public String getMSG(){return MSG;}

    //————————————————————联系人接口————————————————————————
    public List<People> getPeoples(int UID){
        this.UID=UID;
        getAllPeo();
        return peoples;
    }

    //获取单个信息
    public People getPeo(int UID){return getPeo_mpl(UID);}

    //修改联系人信息
    public boolean updataPeo(People people){return updataPeo_mpl(people);}

    //——————————————————封装的实现方法——————————————————————
    //修改联系人资料
    private boolean updataPeo_mpl(People people) {
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            MSG="网络链接失败";
            return false;
        }
        String SQL="UPDATE user_info SET name=?,GENDER=?,EMAIL=?,PHONE=?,QQ=?,BIRTHDAY=?,CAMPUS=?,COLLEG=?,CLAS=?,HOUSE=?" +
                "  WHERE UID=?";
        try {
            PreparedStatement pres = LINK.getConnection().prepareStatement(SQL);
            pres.setString(1,people.getName());
            pres.setString(2,people.getGender());
            pres.setString(3,people.getEmail());
            pres.setString(4,people.getPhone());
            pres.setString(5,people.getQQ());
            pres.setString(6,people.getBirthday());
            pres.setString(7,people.getCampus());
            pres.setString(8,people.getCollege());
            pres.setString(9,people.getClas());
            pres.setString(10,people.getHouse());
            pres.setInt(11,people.getUID());
            pres.execute();
            pres.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "updataPeo_mpl: ",e );
            MSG="修改失败";
            e.printStackTrace();
            return false;
        }
        return true;
    }


    //获取联系人列表
    private void getAllPeo(){
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            MSG="网络链接失败";
            return;
        }
        String SQL="SELECT organ_peo.OID,user_info.UID,user_info.SID,user_info.IMG,user_info.NAME,user_info.gender," +
                "user_info.BIRTHDAY,user_info.PHONE,user_info.QQ,user_info.EMAIL,user_info.CAMPUS," +
                "user_info.COLLEG,user_info.CLAS,user_info.MAINORG,user_info.MAINMENT,user_info.MAINPOSITION," +
                "user_info.REGTIME,user_info.LOGIN FROM user_info left JOIN organ_peo on organ_peo.UID=user_info.UID " +
                "WHERE OID in(SELECT DISTINCT OID FROM organ_peo WHERE UID=?)";
        try {
            PreparedStatement pres = LINK.getConnection().prepareStatement(SQL);
            pres.setInt(1,UID);
            ResultSet res = pres.executeQuery();
            while (res.next()){
                People people=new People();
                people.setWho(res.getInt(1));
                people.setUID(res.getInt(2));
                people.setSID(res.getInt(3));
                people.setImg(res.getString(4));
                people.setName(res.getString(5));
                people.setGender(res.getString(6));
                people.setBirthday(res.getString(7));
                people.setPhone(res.getString(8));
                people.setQQ(res.getString(9));
                people.setEmail(res.getString(10));
                people.setCampus(res.getString(11));
                people.setCollege(res.getString(12));
                people.setClas(res.getString(13));
                people.setMainOrg(res.getString(14));
                people.setMainMent(res.getString(15));
                people.setMainPositior(res.getString(16));
                people.setRegTime(res.getString(17));
                people.setLogin(res.getInt(18));
                peoples.add(people);
            }
            MSG="联系人获取成功";
            res.close();
            pres.close();
            //添加自己
            People people=getPeo_mpl(UID);
            if(people!=null)
                peoples.add(people);//如果不为空就添加自己
        } catch (SQLException e) {
            Log.e(TAG, "getAllPeo: ",e );
            MSG="获取联系人列表失败";
            e.printStackTrace();
        }
    }

    //获取单个联系人信息
    private People getPeo_mpl(int UID){
        People people=new People();
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            MSG="网络链接失败";
            return null;
        }
        String SQL="SELECT 0,user_info.UID,user_info.SID,user_info.IMG,user_info.NAME,user_info.gender," +
                "user_info.BIRTHDAY,user_info.PHONE,user_info.QQ,user_info.EMAIL,user_info.CAMPUS,user_info.COLLEG," +
                "user_info.CLAS,'自己','个人资料','个人',user_info.REGTIME,user_info.LOGIN  " +
                "FROM user_info WHERE uid=?";
        try {
            PreparedStatement pres = LINK.getConnection().prepareStatement(SQL);
            pres.setInt(1,UID);
            ResultSet res = pres.executeQuery();
            while (res.next()){
                people.setWho(res.getInt(1));
                people.setUID(res.getInt(2));
                people.setSID(res.getInt(3));
                people.setImg(res.getString(4));
                people.setName(res.getString(5));
                people.setGender(res.getString(6));
                people.setBirthday(res.getString(7));
                people.setPhone(res.getString(8));
                people.setQQ(res.getString(9));
                people.setEmail(res.getString(10));
                people.setCampus(res.getString(11));
                people.setCollege(res.getString(12));
                people.setClas(res.getString(13));
                people.setMainOrg(res.getString(14));
                people.setMainMent(res.getString(15));
                people.setMainPositior(res.getString(16));
                people.setRegTime(res.getString(17));
                people.setLogin(res.getInt(18));
            }
            MSG="获取成功";
            res.close();
            pres.close();
        } catch (SQLException e) {
            Log.e(TAG, "getPeo_mpl: ",e );
            MSG="获取联系人列表失败";
            e.printStackTrace();
        }
        return people;
    }

}
