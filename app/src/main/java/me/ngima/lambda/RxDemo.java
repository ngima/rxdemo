package me.ngima.lambda;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mangi on 14/11/2017.
 */

public class RxDemo {
    private static final String TAG = "RxDemo";
    CompositeDisposable mCompositeDisposable;

    void main() {
        mCompositeDisposable = new CompositeDisposable();

        int numbers = 200;

        Observable.just(numbers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Observable.just(numbers)
                .subscribeOn(Schedulers.io())
                .map(this::getStatusByCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::show, Throwable::printStackTrace, () -> Log.d(TAG, ""),
                        mCompositeDisposable::add);
    }

    void show(String message) {

    }

    String getStatusByCode(int statusCode) {
        switch (statusCode) {
            case 200:
                return "SUCCESS";
            default:
            case 404:
                return "NOT FOUND";
        }

    }
}
