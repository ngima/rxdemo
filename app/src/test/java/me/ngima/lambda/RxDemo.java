package me.ngima.lambda;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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

    public class DatabaseDao {

        SQLiteOpenHelper mDatabaseHelper;

        //init
        //...
        public List<UserModel> getAllUsers(String[] selection) {
            SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

            String query = "SELECT * FROM table_user WHERE _id ";

            Cursor c = db.rawQuery(query, selection);
            c.close();

            return Mapper.mapToUserList(c);
        }
    }

    public class DatabaseManager {
        DatabaseDao mDatabaseDao;

        //init
        //...
        public Observable<List<UserModel>> getAllUsers(String[] selection) {
            return Observable.fromCallable(new Callable<List<UserModel>>() {
                @Override
                public List<UserModel> call() throws Exception {
                    return mDatabaseDao.getAllUsers(selection);
                }
            });
        }
    }

    public static class UserModel {
        public String name;
    }

    public static class Mapper {
        public static List<UserModel> mapToUserList(Cursor c) {
            List<UserModel> userModels = new ArrayList<UserModel>();
            //mapping
            if (c.moveToFirst()) {

                do {
                    userModels.add(maToUser(c));
                } while (c.moveToNext());
            }
            return userModels;
        }

        public static UserModel maToUser(Cursor c) {
            UserModel userModel = new UserModel();
            userModel.name = c.getString(c.getColumnIndex("name"));
            return userModel;
        }
    }

    public class SomeActivity extends AppCompatActivity{
        DatabaseManager mDatabaseManager;

        //init
        //...
        void fetchUsersFromDatabase() {
            mDatabaseManager.getAllUsers(null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::showUsers);
        }

        private void showUsers(List<UserModel> allUsers) {
            //show users list
        }
    }

    public static void functionalProgramming() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) % 2 == 0) {
                Logger.log(numbers.get(i));
            }
        }
    }
}
