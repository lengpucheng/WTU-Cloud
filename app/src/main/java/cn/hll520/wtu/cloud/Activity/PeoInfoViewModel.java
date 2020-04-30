package cn.hll520.wtu.cloud.Activity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import cn.hll520.wtu.cloud.model.People;
import cn.hll520.wtu.cloud.repository.PeopleRepository;

public class PeoInfoViewModel extends AndroidViewModel {
    private PeopleRepository peopleRepository;
    private MutableLiveData<People> _people=new MutableLiveData<>();
    private int _ID;
    public PeoInfoViewModel(@NonNull Application application) {
        super(application);
        peopleRepository=new PeopleRepository(application);
    }

    LiveData<People> getPeople(int _ID){
        this._ID=_ID;
        _findpeo();
        return _people;
    }

    private void _findpeo(){new findPeo().execute();}


    @SuppressLint("StaticFieldLeak")
    class findPeo extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            People people=peopleRepository.getPeoForID(_ID);
            _people.postValue(people);
            return null;
        }
    }
}
