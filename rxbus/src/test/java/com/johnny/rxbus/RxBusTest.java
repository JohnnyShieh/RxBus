package com.johnny.rxbus;
/*
 * Copyright (C) 2017 Johnny Shieh Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.junit.Test;

import java.util.ArrayList;
import io.reactivex.functions.Consumer;

import static org.junit.Assert.assertEquals;

/**
 * description
 *
 * @author Johnny Shieh (JohnnyShieh17@gmail.com)
 * @version 1.0
 *
 * Created on 2017/8/8
 */
public class RxBusTest {

    @Test
    public void testNormalSubscribe() {
        final ArrayList<Integer> list = new ArrayList<>();
        ObservableUtils.subscribe(RxBus.get().toObservable(Integer.class), new Consumer<Integer>() {
            @Override
            public void accept(Integer value) {
                list.add(value);
            }
        });
        RxBus.get().post(2);
        RxBus.get().post(3);
        RxBus.get().post(5);

        assertEquals(3, list.size());
    }

    @Test
    public void testErrorSubscribe() {
        final ArrayList<Integer> list = new ArrayList<>();

        ObservableUtils.subscribe(RxBus.get().toObservable(Integer.class), new Consumer<Integer>() {
            @Override
            public void accept(Integer value) {
                if (3 == value) throw new IllegalArgumentException("value can not be 3 !");
                list.add(value);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                if (null != throwable) {
                    throwable.printStackTrace();
                    list.add(-1);
                }
            }
        });
        RxBus.get().post(2);
        RxBus.get().post(3);
        RxBus.get().post(5);

        assertEquals(3, list.size());
        assertEquals(2, list.get(0).intValue());
        assertEquals(-1, list.get(1).intValue());
        assertEquals(5, list.get(2).intValue());
    }
}
