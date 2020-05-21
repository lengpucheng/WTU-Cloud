package cn.hll520.wtu.cloud.Activity.Main.MyCenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Binder;
import android.os.Bundle;
import android.view.View;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.databinding.ActivityEditdataBinding;

public class EditdataActivity extends AppCompatActivity {

    private ActivityEditdataBinding binding;
    private boolean birthday=true;
    private String data="2020-04-04";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_editdata);

        //点击生日
        binding.ediBirthdayOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(birthday){
                    //打开日期
                    binding.ediBirthday.setVisibility(View.VISIBLE);
                    //设置日期
                    String[] datas=data.split("-");
                    binding.ediBirthday.updateDate(Integer.parseInt(datas[0]),Integer.parseInt(datas[1])-1,Integer.parseInt(datas[2]));
                    binding.ediBirthdayOk.setText("确定");
                    birthday=false;
                }else {
                    //获取日期
                    data=binding.ediBirthday.getYear()+"-"+(binding.ediBirthday.getMonth()+1)+"-"+binding.ediBirthday.getDayOfMonth();
                    binding.ediBirthdayOk.setText(data);
                    //关闭日期
                    binding.ediBirthday.setVisibility(View.GONE);
                    birthday=true;
                }
            }
        });
        //生日被改变


    }
}
