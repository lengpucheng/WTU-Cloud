package cn.hll520.wtu.cloud.link;

import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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




    /*
    * ————————————————————————————功能实现——————————————————————————————
    *
    * */
    private boolean upload_mpl(List<UNCourse> unCourses){
        if (LINK.getConnection() == null) {
            Log.e(TAG, "获取链接失败");
            MSG="网络异常";
            return false;
        }
        String SQL ="INSERT INTO course_null(UID, SID, NAME, WEEK, AM1_2, AM3_5, PM1_2, PM3_4, NIGHT) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            for(UNCourse unCourse:unCourses){
            PreparedStatement pres = LINK.getConnection().prepareStatement(SQL);
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




}