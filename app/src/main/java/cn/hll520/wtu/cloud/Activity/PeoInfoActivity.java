package cn.hll520.wtu.cloud.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.databinding.ActivityPeoInfoBinding;
import cn.hll520.wtu.cloud.model.People;

public class PeoInfoActivity extends AppCompatActivity {
    ActivityPeoInfoBinding binding;
    PeoInfoViewModel mViewModel;
    People people=new People();
    Context context;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_peo_info);
        mViewModel=new ViewModelProvider(this).get(PeoInfoViewModel.class);
        int _ID= getIntent().getIntExtra("_ID",0);
        mViewModel.getPeople(_ID).observe(this, new Observer<People>() {
            @Override
            public void onChanged(People peo) {
                //显示数据
                people=peo;
                iniData(peo);
            }
        });
        context=this;
        activity=this;
        //触发
        onClick();

    }

    private void onClick() {
        //退出
        binding.peoBtout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //分享
        binding.peoShar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_SEND);
                in.setType("text/plain");
                in.putExtra(Intent.EXTRA_TEXT, "我通过" + getString(R.string.app_name) + "分享了" + people.getName() + "'的信息\n电话：" + people.getPhone() + "\nQQ:" + people.getQQ());
                startActivity(Intent.createChooser(in, "来自" + getString(R.string.app_name) + "的分享"));
            }
        });
        //拨打电话
        binding.peoBtCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //请求权限   1,请求的活动，2、权限，3、编号（相当于 msg.what）
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    String telstr = String.format("tel:%s", people.getPhone());
                    Uri uri = Uri.parse(telstr);
                    Intent in = new Intent(Intent.ACTION_CALL, uri);
                    startActivity(in);
                }
            }
        });
        //短信
        binding.peoBtMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:" + people.getPhone());
                Intent in = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(in);
            }
        });
        //电子邮件
        binding.peoBtEmail.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("mailto:" + people.getEmail());
                String[] email = {people.getEmail()};
                Intent in = new Intent(Intent.ACTION_SEND, uri);
                in.setType("message/rfc822"); // 设置邮件格式
                in.putExtra(Intent.EXTRA_EMAIL, email); // 接收人
                in.putExtra(Intent.EXTRA_TEXT, "\n来自纺大云客户端的"); // 正文
                startActivity(Intent.createChooser(in, "请选择邮件类应用"));
            }
        });
        //消息
        binding.peoBtSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void iniData(People people) {
        binding.dataName.setText(people.getName());
        binding.dataOrg.setText(people.getMainOrg());
        binding.dataMent.setText(people.getMainMent());
        binding.dataPosit.setText(people.getMainPositior());
        binding.dataPhone.setText(people.getPhone());
        binding.dataQQ.setText(people.getQQ());
        binding.dataGender.setText(people.getGender());
        binding.dataBith.setText(people.getBirthday());
//        binding.dataAge.setText("20");
        binding.dataCampus.setText(people.getCampus());
        binding.dataCollage.setText(people.getCollege());
        binding.dataClass.setText(people.getClas());
        binding.dataHouse.setText(people.getHouse());
        binding.dataRegTime.setText(people.getRegTime());
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String tel = String.format("tel:%s", people.getPhone());
                Uri uri = Uri.parse(tel);
                Intent in = new Intent(Intent.ACTION_CALL, uri);
                startActivity(in);
            } else {
                Toast.makeText(this, "需要呼叫权限才能拨打电话哦", Toast.LENGTH_SHORT).show();
            }
    }
}
