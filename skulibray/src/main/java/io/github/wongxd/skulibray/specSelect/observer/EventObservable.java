package io.github.wongxd.skulibray.specSelect.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者的具体化，该类负责给观察者发送消息
 * 参考 {@link java.util.Observable}
 */
public class EventObservable implements IObservable{

    /**
     * 存放消息订阅者
     */
    List<IObserver> observers = new ArrayList<IObserver>();

    public EventObservable() {
    }

    /**
     * 向消息订阅队列添加订阅者
     */
    @Override
    public void addObserver(IObserver observer) {
        if (observer == null) {
            throw new NullPointerException("EventObservable == null");
        }
        synchronized (this) {
            if (!observers.contains(observer)) {
                observers.add(observer);
            }
        }
    }

    /**
     * 获得订阅者的数量
     */
    public int countObservers() {
        return observers.size();
    }

    /**
     * 删除指定订阅者
     */
    @Override
    public synchronized void deleteObserver(IObserver observer) {
        observers.remove(observer);
    }

    /**
     * 删除所有订阅者.
     */
    public synchronized void deleteObservers() {
        observers.clear();
    }

    /**
     * 通知消息订阅队列里的每一个订阅者
     */
    public void notifyObservers(int flag) {
        notifyObservers(null,flag);
    }
    /**
     * 通知消息订阅队列里的每一个订阅者
     * @param data  给订阅者传递的参数
     */
    @SuppressWarnings("unchecked")
    @Override
    public void notifyObservers(Object data, int flag) {
        int size = 0;
        IObserver[] arrays = null;
        synchronized (this) {
            size = observers.size();
            arrays = new IObserver[size];
            observers.toArray(arrays);
        }
        if (arrays != null) {
            for (IObserver observer : arrays) {
                observer.onMessageReceived(this, data,flag);
            }
        }
    }

}
