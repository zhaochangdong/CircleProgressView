package main.zhaocd.com.circleprogressview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private final int UPDATE_PROGRESS = 001;
    CircleProgressView circleProgressView = null;

     Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 001:
                    int progress =(int) msg.obj;
                    circleProgressView.setProgress(progress);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleProgressView = (CircleProgressView) findViewById(R.id.my_view);
        new MyThread().start();
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            for (int i=0;i<100;i++){
                Message msg = myHandler.obtainMessage();
                msg.what = UPDATE_PROGRESS;
                msg.obj = i+1;
                myHandler.sendMessage(msg);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
