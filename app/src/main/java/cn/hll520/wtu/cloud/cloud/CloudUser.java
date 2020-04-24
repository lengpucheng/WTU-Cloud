package cn.hll520.wtu.cloud.cloud;


import android.os.AsyncTask;

import cn.hll520.wtu.cloud.link.DataLinkUser;
import cn.hll520.wtu.cloud.model.User;

public class CloudUser {
    //登录状态
    private boolean login=false;
    //用户信息
    private User user;
    //上次登录时间
    private String TIME="";
    //登录链接
    private DataLinkUser link=new DataLinkUser();

    public CloudUser(){ }

    public CloudUser(User user){
        this.user=user;
        login_do();
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        login_do();
    }

    public String getTIME(){return TIME;}

    /*
    * ————————————————————封装的登录工具——————————————————
    * */
    private void login_do(){
        if(user.getUID()==0)
            login=link.login(user.getUname(),user.getPassword());
        else
            login=link.login(user.getUID(),user.getPassword());
        user=link.getUser();
        TIME=link.getTime();
    }

}
