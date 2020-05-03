package cn.hll520.wtu.cloud.link;

import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.hll520.wtu.cloud.model.UNCourse;

public class DataLinkCourse {
    private  final String TAG = "EFF_LINK_COURSE";
    private  static DataLink LINK = DataLink.getLink();//获取链接
    private  String MSG ="";


    /*
     * ——————————————————————————对外接口————————————————————————————
     * */

    //获取错误信息
    public String getMSG(){return MSG;}

    /**上传
     * @param unCourses  空课表链表
     * @return   返回是否上传成功
     */
    public boolean uploadCourse(List<UNCourse> unCourses){return upload_mpl(unCourses);}


    /**
     * @param OID 组织id
     * @return 返回的是空的课表
     */
    public List<UNCourse> downCourse(int OID){return downCourse_mpl(OID);}




    /*
    * ————————————————————————————功能实现——————————————————————————————
    *
    * */
    //上传
    private boolean upload_mpl(List<UNCourse> unCourses){
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            MSG="网络异常";
            return false;
        }
        String SQL;
        try {
            //删除其全部课表
            SQL="DELETE FROM course_null WHERE UID=?";
            PreparedStatement pres = LINK.getConnection().prepareStatement(SQL);
            pres.setInt(1,unCourses.get(0).getUID());
            pres.execute();

            SQL ="INSERT INTO course_null(UID, SID, NAME, WEEK, AM1_2, AM3_5, PM1_2, PM3_4, NIGHT) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            //写入数据
            for(UNCourse unCourse:unCourses){
            pres = LINK.getConnection().prepareStatement(SQL);
            pres.setInt(1,unCourse.getUID());
            pres.setString(2,unCourse.getSID());
            pres.setString(3,unCourse.getName());
            pres.setInt(4,unCourse.getWeek());
            pres.setString(5,unCourse.getAm1_2());
            pres.setString(6,unCourse.getAm3_5());
            pres.setString(7,unCourse.getPm1_2());
            pres.setString(8,unCourse.getPm3_4());
            pres.setString(9,unCourse.getNight());
            pres.execute();
            pres.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "upload_mpl: ",e );
            MSG="网络异常";
            return false;
        }
        return true;
    }

    //下载
    private List<UNCourse> downCourse_mpl(int OID){
        List<UNCourse> unCourses=new ArrayList<>();
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            MSG="网络异常";
            return null;
        }
        String SQL="SELECT " +
                "course_null.UID,user_info.NAME,course_null.WEEK,course_null.AM1_2,course_null.AM3_5,course_null.PM1_2," +
                "course_null.PM3_4,course_null.NIGHT  " +
                "FROM course_null LEFT JOIN user_info ON course_null.uid=user_info.UID  " +
                "WHERE course_null.UID IN(SELECT organ_peo.UID FROM organ_peo WHERE organ_peo.OID=?)";
        try {
            PreparedStatement pres=LINK.getConnection().prepareCall(SQL);
            pres.setInt(1,OID);
            ResultSet res = pres.executeQuery();
            while (res.next()){
                UNCourse unCourse=new UNCourse();
                unCourse.setUID(res.getInt(1));
                unCourse.setName(res.getString(2));
                unCourse.setWeek(res.getInt(3));
                unCourse.setAm1_2(res.getString(4));
                unCourse.setAm3_5(res.getString(5));
                unCourse.setPm1_2(res.getString(6));
                unCourse.setPm3_4(res.getString(7));
                unCourse.setNight(res.getString(8));
                unCourses.add(unCourse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            MSG="加载失败";
            return null;
        }
        return unCourses;
    }


}