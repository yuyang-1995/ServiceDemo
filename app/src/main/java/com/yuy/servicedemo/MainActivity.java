package com.yuy.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    //IBinder
//    ServicerConnection:用于绑定客户端和Service
//    进度监控
    private ServiceConnection conn = new ServiceConnection() {
        //当客户端正常连接着服务时，执行服务的绑定操作会被调用
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //获取IBinder
//            MyService.MyBinder mb = (MyService.MyBinder) iBinder;
//            int step = mb.getProcess();
//            Log.e("TAG","客户端与服务端建立连接  当前进度是：" + step);

            IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);

            try {
                iMyAidlInterface.showProgress();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        //当客户端和服务的连接丢失了
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("TAG","客户端与服务端断开连接 ");
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void operate(View v){
        switch (v.getId()){
            case R.id.start:
                //启动服务:创建-->启动-->销毁
                //如果服务已经创建了，后续重复启动，操作的都是同一个服务，不会再重新创建了，除非你先销毁它
                Intent it1 = new Intent(this,MyService.class);
                startService(it1);
                break;
            case R.id.stop:
                Intent it2 = new Intent(this,MyService.class);
                stopService(it2);
                break;
            case R.id.bind:
                //绑定服务：最大的 作用是用来实现对Service执行的任务进行进度监控
                //如果服务不存在： onCreate-->onBind-->onUnbind-->onDestory
                // （此时服务没有再后台运行，并且它会随着Activity的摧毁而解绑并销毁）
                //服务已经存在：那么bindService方法只能使onBind方法被调用，而unbindService方法只能使onUnbind被调用
                Intent it3 = new Intent(this,MyService.class);
                bindService(it3, conn, BIND_AUTO_CREATE);

                break;
            case R.id.unbind:
                //解绑服务
                unbindService(conn);
                break;

        }

    }
}
