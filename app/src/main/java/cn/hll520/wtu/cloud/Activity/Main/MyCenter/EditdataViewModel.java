package cn.hll520.wtu.cloud.Activity.Main.MyCenter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


import cn.hll520.wtu.cloud.model.People;

public class EditdataViewModel extends AndroidViewModel {

    People people=new People();
    String birthday="2020-04-04";
    public EditdataViewModel(@NonNull Application application) {
        super(application);
    }
    //获取联系人

}
