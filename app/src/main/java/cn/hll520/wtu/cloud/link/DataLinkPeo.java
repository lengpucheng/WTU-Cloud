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
    private People people;
    private List<People> peoples=new ArrayList<>();
    private int UID;
    //————————————————————联系人接口————————————————————————

    public People getPeople() {
        return people;
    }

    public List<People> getPeoples(int UID){
        this.UID=UID;
        getAllPeo();
        return peoples;
    }


    //——————————————————封装的实现方法——————————————————————
    private void getAllPeo(){
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
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

        } catch (SQLException e) {
            Log.e(TAG, "getAllPeo: ",e );
            e.printStackTrace();
        }


    }

}
