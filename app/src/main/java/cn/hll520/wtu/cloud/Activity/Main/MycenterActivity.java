package cn.hll520.wtu.cloud.Activity.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import cn.hll520.wtu.cloud.Activity.Login.LoginActivity;
import cn.hll520.wtu.cloud.Activity.PeoInfoActivity;
import cn.hll520.wtu.cloud.Activity.updataPassActivity;
import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.databinding.ActivityMycenterBinding;
import cn.hll520.wtu.cloud.model.People;

public class MycenterActivity extends AppCompatActivity {
    MycenterViewModel mViewModel;
    private ActivityMycenterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_mycenter);
        mViewModel=new ViewModelProvider(this).get(MycenterViewModel.class);

        //初始化
        mViewModel.iniData();

        mViewModel.getInfo().observe(this, new Observer<People>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(People people) {
                binding.myCenterName.setText(people.getName());
                binding.MyTime.setText("注册时间："+people.getRegTime());
            }
        });


        //返回
        binding.MyCradBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //个人资料
        binding.MyCardTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MycenterActivity.this, PeoInfoActivity.class);
                intent.putExtra("_ID", Objects.requireNonNull(mViewModel.getInfo().getValue()).get_ID());
                startActivity(intent);
            }
        });

        //退出登录
        binding.EndLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.out_login();
                Intent intent=new Intent(MycenterActivity.this, LoginActivity.class);
                //将这个意图设置为窗口顶端，并释放其他窗口
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //修改密码
        binding.UpMyPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MycenterActivity.this, updataPassActivity.class);
                intent.putExtra("UID", Objects.requireNonNull(mViewModel.getUser().getValue()).getUID());
                startActivity(intent);
            }
        });
    }
}
