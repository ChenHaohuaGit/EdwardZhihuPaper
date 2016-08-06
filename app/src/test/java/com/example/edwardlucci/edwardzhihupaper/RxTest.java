//package com.example.edwardlucci.edwardzhihupaper;
//
//import org.junit.Test;
//
//import java.util.List;
//
//import rx.Observable;
//import rx.functions.Action1;
//
///**
// * Created by edward on 16/6/21.
// */
//public class RxTest {
//    int i = 0;
//    @Test
//    public void test(){
//
//        Observable<String> observable1 = Observable.just("hello");
//        Observable<String> observable2 = Observable.just("world");
//        Observable.merge(observable1,observable2)
//                .toList()
//                .subscribe(new Action1<List<String>>() {
//                    @Override
//                    public void call(List<String> strings) {
//
//                    }
//                });
//    }
//
//}
