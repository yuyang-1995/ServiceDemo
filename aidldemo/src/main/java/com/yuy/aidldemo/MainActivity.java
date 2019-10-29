package com.yuy.aidldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import com.yuy.servicedemo.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {


    ServiceConnection bindService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            //这就是另一个的 应用服务中 返回的 IBinder
            IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);

            try {
                iMyAidlInterface.showProgress();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void operate(View view) {
        switch (view.getId()) {
            case R.id.start:
                //远程启动服务
                Intent intent = new Intent();
                intent.setAction("com.yuy.myservice");
                intent.setPackage("com.yuy.servicedemo");
                startService(intent);
                break;

            case R.id.stop:
                Intent intent2 = new Intent();
                intent2.setAction("com.yuy.myservice");
                intent2.setPackage("com.yuy.servicedemo");
                stopService(intent2);
                break;

            case R.id.bind:
                Intent intent3 = new Intent();
                intent3.setAction("com.yuy.myservice");
                intent3.setPackage("com.yuy.servicedemo");
                bindService(intent3, bindService, BIND_AUTO_CREATE);
                break;
            case R.id.unbind:
                Intent intent4 = new Intent();
                intent4.setAction("com.yuy.myservice");
                intent4.setPackage("com.yuy.servicedemo");
                unbindService(bindService);
                break;
                default:break;

        }

    }
}
