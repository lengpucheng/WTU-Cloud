package cn.hll520.wtu.cloud.Activity.Main.MyCenter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import cn.hll520.wtu.cloud.cloud.CloudPeo;
import cn.hll520.wtu.cloud.model.People;

public class EditdataViewModel extends AndroidViewModel {

    People people=new People();
    String birthday="2020-04-04";
    People newPeople=new People();
    private CloudPeo cloudPeo=new CloudPeo();
    public EditdataViewModel(@NonNull Application application) {
        super(application);
    }
    //获取结果
    LiveData<CloudPeo.Result> getResult(){return cloudPeo.getResult();}
    //执行操作
    void updata(){cloudPeo.updataPeo(newPeople);}


}
