package cn.hll520.wtu.cloud.Activity.Main.MyCenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.cloud.CloudReg;

import cn.hll520.wtu.cloud.databinding.ActivityUpdataPassBinding;
import cn.hll520.wtu.cloud.model.User;

public class updataPassActivity extends AppCompatActivity {
    private ActivityUpdataPassBinding binding;
    private User user;
    @SuppressLint("HandlerLeak")//忽略警告
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //隐藏旋转
            binding.updataIng.setVisibility(View.GONE);
            switch (msg.what) {
                case 1:
                    //用户不存在
                    binding.updataInputId.setError("此用户不存在");
                    Toast.makeText(updataPassActivity.this, "此用户不存在!" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //查询到用户
                    binding.updataFind.setVisibility(View.GONE);
                    //设置密保问题
                    binding.updataInfo.setText(user.getPassinfo());
                    //显示页面
                    binding.updataUppass.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    //修改密码成功
                    Toast.makeText(updataPassActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case 4:
                    //修改失败
                    Toast.makeText(updataPassActivity.this, "修改失败!" + msg.obj, Toast.LENGTH_SHORT).show();
                    binding.updataKey.setError("请检测回答是否正确");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_updata_pass);
        int UID=getIntent().getIntExtra("UID",0);
        if(UID!=0){
            binding.Title.setText("修改密码");
            //如果有参数传入就不用查找
            binding.updataFind.setVisibility(View.GONE);
            //并显示info
            findUser(String.valueOf(UID));
        }else {
             //隐藏修改密码页面
            binding.Title.setText("找回密码");
            binding.updataUppass.setVisibility(View.GONE);
        }

        //返回
        binding.updataReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //查找用户
        binding.updataBtFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断答案是否为空
                String answer=binding.updataInputId.getText().toString();
                if(answer.isEmpty()){
                    binding.updataInputId.setError("请输入正确的用户名/UID或学号且不能为空");
                    return;
                }
                binding.updataIng.setVisibility(View.VISIBLE);
                findUser(answer);
            }
        });

        //修改
        binding.updataBtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upPass();
            }
        });

    }

    //修改密码
    private void upPass() {
        final String answer=binding.updataKey.getText().toString();
        final String newPass=binding.updataNewpass.getText().toString();
        String newPass_check=binding.updataNewpassCheck.getText().toString();
        //正则检验
        if(answer.isEmpty()){
            binding.updataKey.setError("答案不能为空");
            return;
        }
        if(newPass.isEmpty()||!newPass.matches("\\S{6,}")){
            binding.updataNewpass.setError("密码格式不正确");
            return;
        }
        if(!newPass.equals(newPass_check)){
            binding.updataNewpassCheck.setError("两次密码不一致");
            return;
        }
        binding.updataIng.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                CloudReg cloudReg=new CloudReg();
                Message msg=new Message();
                if(cloudReg.upPass(user.getUID(),answer,newPass))
                    msg.what=3;
                else {
                    msg.what=4;
                    msg.obj=cloudReg.getMsg();
                }
                handler.sendMessage(msg);
            }
        }).start();
    }

    //查找用户
    private void findUser(final String answer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CloudReg cloudReg =new CloudReg();
                User _user=cloudReg.getUser(answer);
                Message msg=new Message();
                if(_user==null){
                    msg.what=1;
                    msg.obj=cloudReg.getMsg();
                }else{
                    msg.what=2;
                    user=_user;
                }
                handler.sendMessage(msg);
            }
        }).start();
    }
}
