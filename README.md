RxBus
=====

RxBus are [RxJava][rx] event bus which based on [RxRelay][rxrelay] and custom `LambdaObserver`.

RxBus has the following features:

* Observer can only receive items which was emited after subscribed the RxBus.

* Bus never emits `error` and `complete` items.

* Don't worry about exception throwed in `onNext` method of Observer.

* Thread safe.

ObservableUtils utils class can guarantee that Observer can normally receive item after throwing exception in `onNext` method and then jump to `onError` method.


Usage
-----

```java
RxBus.get()
    .toObservable(String.class)
    .subscribe(observer1);
// observer1 will receive all string items.

Consumer<String> onNext = new Consumer<String>() {
    @Override
    public void accept(String value) {
        if ("error".equals(value)) throw new IllegalArgumentException("value can not be error !");
        ...
    }
};
// observer2 will handle exception when receive "error" string. after that observer2 can also receive items.
ObservableUtils.subscribe(RxBus.get().toObservable(String.class), onNext);

RxBus.get().post("one");
RxBus.get().post("error");
RxBus.get().post("three");

// observer1 will receive all string items: one, error, three
// observer2 will receive all string items: one, error, three
```

Download
--------

Gradle:
```groovy
compile 'com.johnnyshieh.rxbus2:rxbus:2.0.0'
```

License
-------

    Copyright 2017 Johnny Shieh

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


[rx]: https://github.com/ReactiveX/RxJava/
[rxrelay]: https://github.com/JakeWharton/RxRelay/