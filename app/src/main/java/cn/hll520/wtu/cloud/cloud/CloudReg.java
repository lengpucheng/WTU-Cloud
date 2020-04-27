package cn.hll520.wtu.cloud.cloud;

import cn.hll520.wtu.cloud.link.DataLinkUser;
import cn.hll520.wtu.cloud.model.User;

public class CloudReg {
    //状态用户名
    private boolean username_status;
    //邀请码状态
    private boolean invite_status;
    //注册状态
    private boolean reg_status;
    //用户信息
    private User user;
    //邀请码
    private String invite="0";//0为没有
    //姓名
    private String name;
    //电子邮件
    private String Email;
    //性别
    private String gender;
    //获取链接
    private DataLinkUser link=new DataLinkUser();

    public CloudReg(User user, String invite, String name, String email, String gender) {
        this.user = user;
        this.invite = invite;
        this.name = name;
        Email = email;
        this.gender = gender;
    }

    public void doReg(){
        //状态初始化
        username_status=true;
        invite_status=true;
        reg_status=false;
        //判断用户名是否重复
        if(link.isUserName(user.getUname())){
            username_status=false;
            return;
        }
        //填写则，判断邀请码是否存在
        if(!invite.equals("0")&&!link.isInvite(invite)){
            invite_status=false;
            return;
        }
        //判断注册是否成功
        if(link.reg(user,name,Email,gender,invite))
            reg_status=true;
    }

    public String getMsg(){return link.getMsg();}

    public boolean isUsername_status() {
        return username_status;
    }

    public boolean isInvite_status() {
        return invite_status;
    }

    public boolean isReg_status() {
        return reg_status;
    }

    public CloudReg setUser(User user) {
        this.user = user;
        return this;
    }

    public CloudReg setInvite(String invite) {
        this.invite = invite;
        return this;
    }

    public CloudReg setName(String name) {
        this.name = name;
        return this;
    }

    public CloudReg setEmail(String email) {
        Email = email;
        return this;
    }

    public CloudReg setGender(String gender) {
        this.gender = gender;
        return this;
    }
}
