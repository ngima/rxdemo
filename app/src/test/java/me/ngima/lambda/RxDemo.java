package me.ngima.lambda;

import android.util.Log;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by mangi on 16/11/2017.
 */

public class RxDemo {

    @Test
    public void testCreateObservable() {

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                Logger.log("CREATED");
                observableEmitter.onNext("Hello");
                observableEmitter.onNext("Welcome to");
                observableEmitter.onNext("GDF 2017");
                observableEmitter.onComplete();
            }
        });

        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.log(s);
            }
        });

        Logger.log("All Done");
    }

    @Test
    public void testCreateObserver() {

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                Logger.log("CREATED");
                observableEmitter.onNext("Hello");
                observableEmitter.onNext("Welcome to");
                observableEmitter.onNext("GDF 2017");
                observableEmitter.onComplete();
            }
        });

        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.log("Subscribed");
            }

            @Override
            public void onNext(String s) {
                Logger.log(s);
            }

            @Override
            public void onError(Throwable e) {
                Logger.log(e.getMessage());
            }

            @Override
            public void onComplete() {
                Logger.log("Completed");
            }
        });

        Logger.log("All Done");
    }

    @Test
    public void testMapOperator() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4);

        observable.map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                return integer * 2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.log(integer);
            }
        });
    }

    @Test
    public void testFlatMapOperator() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4);

        observable.flatMap(integer -> {
                    switch (integer % 10) {
                        case 1:
                            return Observable.just(integer + "st");
                        case 2:
                            return Observable.just(integer + "nd");
                        case 3:
                            return Observable.just(integer + "rd");
                        default:
                            return Observable.just(integer + "th");
                    }
                }
        ).subscribe(Logger::log);
    }

    @Test
    public void testFilter() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4);

        observable.filter(integer -> integer % 2 == 0)
                .subscribe(integer -> Logger.log(integer));
    }
}
