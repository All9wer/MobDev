package ru.mirea.chirkovia.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

public class MyLooper extends Thread {

    public static final String KEY_AGE = "AGE";
    public static final String KEY_JOB = "JOB";

    private final Handler mainHandler;
    public Handler mHandler;

    public MyLooper(Handler mainThreadHandler) {
        this.mainHandler = mainThreadHandler;
    }

    @Override
    public void run() {
        Log.d("MyLooper", "run: start");

        Looper.prepare();

        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                int age = data.getInt(KEY_AGE, 0);
                String job = data.getString(KEY_JOB, "");

                // Задержка = возраст в секундах
                Log.d("MyLooper", "Получены данные: age=" + age + ", job=" + job);
                SystemClock.sleep(age * 1000L);

                String result = "Пользователю " + age + " лет, он работает: " + job;

                // Передаём результат в главный поток только ради вывода (Log.d)
                Message out = Message.obtain();
                Bundle outBundle = new Bundle();
                outBundle.putString("RESULT", result);
                out.setData(outBundle);
                mainHandler.sendMessage(out);
            }
        };

        Looper.loop();
    }
}
