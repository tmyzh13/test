package com.example.issuser.rxtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

/**
 * Created by issuser on 2018/2/24.
 */

public class ContactDemoActivity extends Activity {

    EditText et_name;
    EditText et_age;
    EditText et_job;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_demo);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_job = findViewById(R.id.et_job);

        Observable<CharSequence> nameObservable = RxTextView.textChanges(et_name).skip(1);
        Observable<CharSequence> ageObservable = RxTextView.textChanges(et_age).skip(1);
        Observable<CharSequence> jobObservable = RxTextView.textChanges(et_job).skip(1);

        Observable.combineLatest(nameObservable, ageObservable, jobObservable, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) throws Exception {
                boolean isUserNameValid= !TextUtils.isEmpty(et_name.getText().toString());
                boolean isUserAgeValid=!TextUtils.isEmpty(et_age.getText().toString());
                boolean isUserJobValid=!TextUtils.isEmpty(et_job.getText().toString());

                return isUserNameValid&isUserAgeValid&isUserJobValid;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Log.e("yzh","提交按钮是否可以点击--"+aBoolean);
            }
        });

    }
}