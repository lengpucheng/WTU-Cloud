package cn.hll520.wtu.cloud.Activity.Login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.cloud.CloudReg;
import cn.hll520.wtu.cloud.databinding.ActivityRegisteredBinding;
import cn.hll520.wtu.cloud.model.User;

public class RegisteredActivity extends AppCompatActivity {
    private ActivityRegisteredBinding binding;
    @SuppressLint("HandlerLeak")//忽略警告
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            binding.regIng.setVisibility(View.GONE);
            switch (msg.what){
                case 1:
                    Toast.makeText(RegisteredActivity.this, "注册成功"+msg.obj, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case 2:
                    Toast.makeText(RegisteredActivity.this, "注册失败！用户名重复", Toast.LENGTH_SHORT).show();
                    binding.regUname.setError("用户名已存在");
                    break;
                case 3:
                    Toast.makeText(RegisteredActivity.this, "注册失败！邀请码不正确！", Toast.LENGTH_SHORT).show();
                    binding.regInvitcode.setError("邀请码不存在");
                    break;
                case 4:
                    Toast.makeText(RegisteredActivity.this, "注册失败！"+msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    User user=new User();
    String invite;
    String Email;
    String name;
    String gender="男";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_registered);
        //返回
        binding.regReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //注册
        binding.regOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg();
            }
        });
    }

    private void reg() {
        String str;//工具人
        invite="0";//初始化邀请码
        user=new User();
        //用户名
        str=binding.regUname.getText().toString();
        if(!isUserName(str))
            return;
        user.setUname(str);
        //密码
        str=binding.regPassword.getText().toString();
        if(!isPassword(str))
            return;
        user.setPassword(str);
        //确认密码
        str=binding.regPasswordCheck.getText().toString();
        if(str.isEmpty()||!user.getPassword().equals(str)){
            binding.regPasswordCheck.setError("两次密码不一致");
            return;
        }
        //密保
        str=binding.regPassinfo.getText().toString();
        if(str.isEmpty()||str.length()<8){
            binding.regPassinfo.setError("请至少设置8位长度的问题，这将用于日后的密码找回");
            return;
        }
        user.setPassinfo(str);
        //答案
        str=binding.regPasskey.getText().toString();
        if(str.isEmpty()){
            binding.regPasskey.setError("答案不能为空");
            return;
        }
        user.setPasskey(str);
        //邀请码
        str=binding.regInvitcode.getText().toString();
        if(!str.isEmpty())
            invite=str;
        //电子邮件
        str=binding.regEmail.getText().toString();
        if(str.isEmpty()){
            binding.regEmail.setError("电子邮件不能为空");
            return;
        }
        Email=str;
        //姓名
        str=binding.regName.getText().toString();
        if(str.isEmpty()){
            binding.regName.setError("姓名不能为空");
            return;
        }
        name=str;
        //性别
        gender=binding.regGender.getSelectedItem().toString();
        //打开圈圈
        binding.regIng.setVisibility(View.VISIBLE);
        //执行
        new Thread(new Runnable() {
            @Override
            public void run() {
                reg_do(user,invite,Email,name,gender);
            }
        }).start();
    }



    //执行
    private void reg_do(User user, String invite, String email, String name, String gender) {
        CloudReg cloudReg=new CloudReg(user,invite,name,email,gender);
        Message msg=new Message();
        cloudReg.doReg();
        if(cloudReg.isReg_status()){
            msg.what=1;
            msg.obj=cloudReg.getMsg();
        }else {
            //注册失败
            if(!cloudReg.isUsername_status())
                msg.what=2;//用户名重复
            else if(!cloudReg.isInvite_status())
                msg.what=3;//邀请码不存在
            else
                msg.what=4;//其他情况
            msg.obj=cloudReg.getMsg();
        }
        handler.sendMessage(msg);
    }


    //用户名检验
    private boolean isUserName(String userName) {
        //不为空
        if(userName.isEmpty()){
            binding.regUname.setError("用户名不能为空");
            return false;
        }
        if(userName.matches("[0-9]+")){
            binding.regUname.setError("用户名不能为纯数字");
            return false;
        }

        return true;
    }

    //密码检验
    private boolean isPassword(String password) {
        if(password.isEmpty()){
            binding.regPassword.setError("密码不能为空");
            return false;
        }
        if(!password.matches("\\S{6,}")){
            binding.regPassword.setError("密码格式不正确");
            return false;
        }
        return true;
    }

}
