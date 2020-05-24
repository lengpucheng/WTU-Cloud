package cn.hll520.wtu.cloud.Activity.Main.MyCenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.cloud.CloudPeo;
import cn.hll520.wtu.cloud.databinding.ActivityEditdataBinding;
import cn.hll520.wtu.cloud.model.People;

public class EditdataActivity extends AppCompatActivity {

    private ActivityEditdataBinding binding;
    private EditdataViewModel mViewModel;
    private boolean birthday=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_editdata);
        mViewModel =new ViewModelProvider(this).get(EditdataViewModel.class);
        //获取信息
        getInfo();
        //初始化
        initial();
        //点击生日
        clickBirth();
        //按钮事件
        click();
        //监听修改
        mViewModel.getResult().observe(this, new Observer<CloudPeo.Result>() {
            @Override
            public void onChanged(CloudPeo.Result result) {
                if(!result.isOk)
                    return;
                if(result.result){
                    Toast.makeText(EditdataActivity.this, "修改成功!", Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(EditdataActivity.this, "修改失败："+result.MSG, Toast.LENGTH_SHORT).show();
            }
        });

    }

    //获取用户信息
    private void getInfo() {
        //获取用户
        mViewModel.people= (People) getIntent().getSerializableExtra("People");
    }

    //按钮事件
    private void click() {
        //返回键
        binding.regReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //保存按钮
        binding.editOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取新的信息
                getNewInfor();
                //执行操作
                mViewModel.updata();
            }
        });
    }


    //获取新信息
    private void getNewInfor() {
        //UID
        mViewModel.newPeople.setUID(mViewModel.people.getUID());
        //姓名
        mViewModel.newPeople.setName(binding.editName.getText().toString());
        //性别
        mViewModel.newPeople.setGender(binding.editGender.getSelectedItem().toString());
        //电子邮件
        mViewModel.newPeople.setEmail(binding.editEmail.getText().toString());
        //电话
        mViewModel.newPeople.setPhone(binding.editPhone.getText().toString());
        //QQ
        mViewModel.newPeople.setQQ(binding.editQQ.getText().toString());
        //生日
        mViewModel.newPeople.setBirthday(mViewModel.birthday);
        //校区
        mViewModel.newPeople.setCampus(binding.editCampus.getSelectedItem().toString());
        //学院
        mViewModel.newPeople.setCollege(binding.editCollege.getSelectedItem().toString());
        //班级
        mViewModel.newPeople.setClas(binding.editClass.getText().toString());
        //宿舍
        mViewModel.newPeople.setHouse(binding.editHouse.getText().toString());
    }

    //点击生日
    private void clickBirth() {
        binding.ediBirthdayOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(birthday){
                    //打开日期
                    binding.ediBirthday.setVisibility(View.VISIBLE);
                    //设置日期
                   if(mViewModel.birthday.length()<=8)
                       mViewModel.birthday="2020-04-04";
                    String[] datas= mViewModel.birthday.split("-");
                    binding.ediBirthday.updateDate(Integer.parseInt(datas[0]),Integer.parseInt(datas[1])-1,Integer.parseInt(datas[2]));
                    binding.ediBirthdayOk.setText("确定");
                    birthday=false;
                }else {
                    //获取日期
                    mViewModel.birthday=binding.ediBirthday.getYear()+"-"+(binding.ediBirthday.getMonth()+1)+"-"+binding.ediBirthday.getDayOfMonth();
                    binding.ediBirthdayOk.setText(mViewModel.birthday);
                    //关闭日期
                    binding.ediBirthday.setVisibility(View.GONE);
                    birthday=true;
                }
            }
        });
    }

    //初始化数据
    private void initial() {
        //姓名
        binding.editName.setText(mViewModel.people.getName());
        //性别
        if("男".equals(mViewModel.people.getGender()))
            binding.editGender.setSelection(0);
        else
            binding.editGender.setSelection(1);
        //电子邮件
        binding.editEmail.setText(mViewModel.people.getEmail());
        //手机
        binding.editPhone.setText(mViewModel.people.getPhone());
        //QQ
        binding.editQQ.setText(mViewModel.people.getQQ());
        //生日
        mViewModel.birthday= mViewModel.people.getBirthday();
        binding.ediBirthdayOk.setText(mViewModel.birthday);
        //校区
        String[] campus=getResources().getStringArray(R.array.campus);
        binding.editCampus.setSelection(campus.length-1);//默认选第最后一个
        for(int i=0;i<campus.length;i++)
            if(campus[i].equals(mViewModel.people.getCampus())){
                binding.editCampus.setSelection(i);//如果有相同的就选中它
                break;
            }
        //学院
        String[] college=getResources().getStringArray(R.array.college);
        binding.editCollege.setSelection(college.length-1);//默认选第最后一个
        for(int i=0;i<college.length;i++)
            if(college[i].equals(mViewModel.people.getCollege())){
                binding.editCollege.setSelection(i);//如果有相同的就选中它
                break;
            }
        //班级
        binding.editClass.setText(mViewModel.people.getClas());
        //宿舍
        binding.editHouse.setText(mViewModel.people.getHouse());
    }
}
