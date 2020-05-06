package cn.hll520.wtu.cloud.Activity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import cn.hll520.wtu.cloud.model.People;
import cn.hll520.wtu.cloud.repository.PeopleRepository;

public class PeoInfoViewModel extends AndroidViewModel {
    private PeopleRepository peopleRepository;
    public PeoInfoViewModel(@NonNull Application application) {
        super(application);
        peopleRepository=new PeopleRepository(application);
    }

    LiveData<People> getInfo(int _ID){return peopleRepository.getInfo_ID(_ID);}


}
