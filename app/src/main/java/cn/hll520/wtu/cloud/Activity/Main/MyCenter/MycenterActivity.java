package cn.hll520.wtu.cloud.Activity.Main.MyCenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.Serializable;

import cn.hll520.wtu.cloud.Activity.Login.LoginActivity;
import cn.hll520.wtu.cloud.Activity.PeoInfoActivity;
import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.databinding.ActivityMycenterBinding;
import cn.hll520.wtu.cloud.model.People;
import cn.hll520.wtu.cloud.model.User;

public class MycenterActivity extends AppCompatActivity {
    MycenterViewModel mViewModel;
    private ActivityMycenterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_mycenter);
        mViewModel=new ViewModelProvider(this).get(MycenterViewModel.class);
        //获取用户
        mViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user==null)
                    return;
                mViewModel.user=user;
                getInfo(user.getUID());
            }
        });

        //点击事件
        click();

    }

    //点击事件
    private void click() {
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
                intent.putExtra("_ID",mViewModel.people.get_ID());
                startActivity(intent);
            }
        });

        //退出登录
        binding.EndLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.outLogin();
                Intent intent=new Intent(MycenterActivity.this, LoginActivity.class);
                //将这个意图设置为窗口顶端，并释放其他窗口
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        //修改密码
        binding.UpMyPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MycenterActivity.this, updataPassActivity.class);
                intent.putExtra("UID", mViewModel.user.getUID());
                startActivity(intent);
            }
        });

        //编辑资料
        binding.UpMydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MycenterActivity.this, EditdataActivity.class);
                intent.putExtra("UID", mViewModel.user.getUID());
                intent.putExtra("People", mViewModel.people);
                startActivity(intent);
            }
        });
    }


    //获取信息
    private void getInfo(int UID) {
        mViewModel.getInfo(UID).observe(this, new Observer<People>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(People people) {
                if(people==null)
                    return;
                mViewModel.people=people;
                binding.myCenterName.setText(people.getName());
                binding.MyTime.setText("注册时间："+people.getRegTime());
            }
        });
    }
}
