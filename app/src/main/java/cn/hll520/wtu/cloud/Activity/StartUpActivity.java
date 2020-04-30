package cn.hll520.wtu.cloud.Activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import cn.hll520.wtu.cloud.Activity.Login.LoginActivity;
import cn.hll520.wtu.cloud.Activity.Main.MainActivity;
import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.model.User;
import cn.hll520.wtu.cloud.repository.UserRepository;

public class StartUpActivity extends AppCompatActivity {

    private UserRepository userRepository;
    private MutableLiveData<Boolean> _flag=new MutableLiveData<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        userRepository=new UserRepository(this);
        new islogin().execute();
        _flag.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Intent intent;
                if(aBoolean)
                    intent=new Intent(StartUpActivity.this, MainActivity.class);
                else
                    intent=new Intent(StartUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    @SuppressLint("StaticFieldLeak")
    class islogin extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            User user=userRepository.getUser_login();
            if(user==null)
                _flag.postValue(false);
            else
                _flag.postValue(true);
            return null;
        }
    }
}
