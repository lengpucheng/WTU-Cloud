package cn.hll520.wtu.cloud.Activity.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import cn.hll520.wtu.cloud.Activity.Main.MainActivity;
import cn.hll520.wtu.cloud.Activity.RegisteredActivity;
import cn.hll520.wtu.cloud.Activity.updataPassActivity;
import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.cloud.CloudUser;
import cn.hll520.wtu.cloud.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    LoginViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        mViewModel=new ViewModelProvider(this).get(LoginViewModel.class);
        //监听
        mViewModel.getCloudUser().observe(this, new Observer<CloudUser>() {
            @Override
            public void onChanged(CloudUser cloudUser) {
                if(cloudUser.isLogin()) {
                    Toast.makeText(LoginActivity.this, "登录成功\n上次登录时间："+cloudUser.getTIME(), Toast.LENGTH_SHORT).show();
                    mViewModel.addUser(cloudUser.getUser());
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
        });

        //登录
        binding.loginGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname=binding.loginUname.getText().toString();
                String pass=binding.loginPass.getText().toString();
                if(isCheck(uname,pass))
                    mViewModel.setUser(uname,pass);
            }
        });
        //注册
        binding.loginRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, RegisteredActivity.class);
                startActivity(intent);
            }
        });
        //忘记密码
        binding.loginForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, updataPassActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isCheck(String uname, String pass) {
        if(uname.isEmpty()){
            binding.loginUname.setError("用户名不能为空");
            return false;
        }
        if(pass.isEmpty()){
            binding.loginUname.setError("密码不能为空");
            return false;
        }
        return true;
    }

}
