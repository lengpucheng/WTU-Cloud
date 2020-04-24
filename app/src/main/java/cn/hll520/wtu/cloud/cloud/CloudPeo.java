package cn.hll520.wtu.cloud.cloud;

import java.util.List;

import cn.hll520.wtu.cloud.link.DataLinkPeo;
import cn.hll520.wtu.cloud.model.People;

public class CloudPeo {
    private DataLinkPeo LINK=new DataLinkPeo();
    private List<People> peos;
    private int UID=0;


    //获取联系人列表
    public CloudPeo(int UID){this.UID=UID;get_peoples();}

    public void setUID(int UID){this.UID=UID;get_peoples();}

    public List<People> getPeos(){return peos;}

    //————————————————————————封装的方法————————————————————————
    private void get_peoples(){
        peos=LINK.getPeoples(UID);
    }



}
