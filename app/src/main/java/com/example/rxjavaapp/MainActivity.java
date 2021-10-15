package com.example.rxjavaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
     private Button create;
     private Button flatMap;
     private final String TAG="reeta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        create=findViewById(R.id.btnCreate);
        flatMap=findViewById(R.id.btnFaltMap);
        create.setOnClickListener(this);
        flatMap.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btnCreate:
                Observable<Task> taskObservable=Observable.create(new ObservableOnSubscribe<Task>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Task> emitter) throws Throwable {
                        Task task=new Task(1,"Do it", true);

                        List<Task> taskList=getList();
                        for (Task task1:taskList){
                            if (!emitter.isDisposed()){
                                emitter.onNext(task1);
                            }
                        }
                        emitter.onComplete();
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
                Observer<Task> taskObserver=new Observer<Task>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Task task) {
                        Log.v(TAG,task.getName());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                };
                taskObservable.subscribe(taskObserver);
                break;
            case R.id.btnFaltMap:
                StudentResponse response=new StudentResponse(1,getStudent());
                Observable<Student> studentObservable=Observable.just(response).
                        flatMap(new Function<StudentResponse, Observable<Student>>() {
                            @Override
                            public Observable<Student> apply(StudentResponse studentResponse) throws Throwable {
                                List<Student> studentList=studentResponse.getStudentList();
                                return Observable.fromIterable(studentList);
                            }
                        }).filter(new Predicate<Student>() {
                    @Override
                    public boolean test(Student student) throws Throwable {
                        return student.getS_marks()>75;
                    }
                });
                Observer<Student> studentObserver=new Observer<Student>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Student student) {
                     Log.v("reeta",student.getS_name());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.v("reeta",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                };
                studentObservable.subscribe(studentObserver);
        }

    }

    private List<Student> getStudent(){
        List<Student> s_list=new ArrayList<>();
        s_list.add(new Student("reeta",1,95.4));
        s_list.add(new Student("deeksha",2,90.4));
        s_list.add(new Student("ruchi",3,89.4));
        s_list.add(new Student("rupali",4,34.4));
        s_list.add(new Student("shailee",5,70.4));
        s_list.add(new Student("sapna",6,23.4));
        s_list.add(new Student("ritu",7,67.4));
        s_list.add(new Student("priyanka",8,76.4));
        s_list.add(new Student("leena",9,70.4));
        s_list.add(new Student("arya",10,45.4));
        return s_list;

    }

    public List<Task> getList(){
        List<Task> taskList=new ArrayList<>();
        String[] tasks={"Excersice","Meditaion","house work","eating"};
        for (int i=0;i<tasks.length;i++){
            Task task=new Task(i+1,tasks[i],true);
            taskList.add(task);
        }
        return taskList;

    }
}