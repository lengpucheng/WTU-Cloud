package cn.hll520.wtu.cloud.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.hll520.wtu.cloud.Activity.Main.CourseViewModel;
import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.cloud.CloudUser;
import cn.hll520.wtu.cloud.databinding.ActivityLoginBinding;
import cn.hll520.wtu.cloud.link.GetData;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    LoginViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        //监听
        mViewModel.getCloudUser().observe(this, new Observer<CloudUser>() {
            @Override
            public void onChanged(CloudUser cloudUser) {
                if(cloudUser.isLogin())
                    Toast.makeText(LoginActivity.this, "登录成果", Toast.LENGTH_SHORT).show();
            }
        });


        binding.loginGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname=binding.loginUname.getText().toString();
                String pass=binding.loginPass.getText().toString();
                if(isCheck(uname,pass))
                    mViewModel.setUser(uname,pass);
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
