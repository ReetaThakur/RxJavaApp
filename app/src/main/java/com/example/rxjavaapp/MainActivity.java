package com.example.rxjavaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private final String TAG = "reeta";
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_name);
        Task task = new Task(2, "Reeta", true);
        Integer[] arr = {1, 2, 3, 4, 5};
        Observable<Task> ree1 = Observable.fromIterable(getList()).repeat()
                .filter(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) throws Throwable {
                        return task.isComplete();
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observer<Task> arrayObserver = new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable=d;
                Log.v(TAG, "OnSubscribe");
            }

            @Override
            public void onNext(Task task1) {
                Log.v(TAG, "OnNext");
                String data = textView.getText().toString() + task.getName();
                textView.setText(data + "\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.v(TAG, "OnError");
            }

            @Override
            public void onComplete() {
                Log.v(TAG, "OnComplete");
            }
        };
        ree1.subscribe(arrayObserver);
    }
    private List<Task> getList(){
        List<Task> takList=new ArrayList<>();
        for (int i=0;i<10;i++){
            if (i%2==0){
                Task task=new Task(i,"Task"+i,true);
                takList.add(task);
            }else {
                Task task=new Task(i,"Task"+i,false);
                takList.add(task);
            }
        }
        return takList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();// ditroy our observer
    }
}

        // ONLY FOR 1 INTERGER
//        Observable<Task> ree=Observable.just(task);
//        Observer<Task> integerObserver=new Observer<Task>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//                Log.v(TAG,"OnSubcribe");
//            }
//
//            @Override
//            public void onNext(@NonNull Task task1) {
//                Log.v(TAG,"OnNext");
//                String data=textView.getText().toString()+task1.getName();
//                textView.setText(data+"\n");
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                Log.v(TAG,"OnError"+ e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                Log.v(TAG,"OnComplete");
//            }
//        };
//        ree.subscribe(integerObserver);


