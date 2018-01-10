package com.cyxk.wrframelibrary.base;


import com.cyxk.wrframelibrary.framework.RxActionManager;
import com.cyxk.wrframelibrary.utils.LogUtil;

import java.util.HashMap;
import java.util.Set;

import rx.Subscription;

public class RxApiManager implements RxActionManager<Object> {

   private static RxApiManager sInstance = null;

   private HashMap<Object, Subscription> maps;

  public static RxApiManager get() {

    if (sInstance == null) {
      synchronized (RxApiManager.class) {
         if (sInstance == null) {
              sInstance = new RxApiManager();
           }
        }
      }
        return sInstance;
    }


     private RxApiManager() {
        maps = new HashMap<>();
      }

   @Override
   public void add(Object tag, Subscription subscription) {
      LogUtil.e("请求的tag ---"+tag);
        maps.put(tag, subscription);
   }

   @Override
    public void remove(Object tag) {
        if (!maps.isEmpty()) {
           maps.remove(tag);
         }
     }

   public void removeAll() {
        if (!maps.isEmpty()) {
           maps.clear();
         }
   }

   @Override public void cancel(Object tag) {
       if (maps.isEmpty()) {
          return;
        }
      if (maps.get(tag) == null) {
        return;
      }
     if (!maps.get(tag).isUnsubscribed()) {
       maps.get(tag).unsubscribe();
       maps.remove(tag);
       LogUtil.e("移除tag ---"+tag);
      }else{
         maps.remove(tag);
         LogUtil.e("已经退订");
     }
     LogUtil.e("最终maps长度 --- "+maps.size());
//       for (int i = 0; i < maps.size(); i++) {
//           LogUtil.e("具体内容"+maps.get(i));
//       }
  }

   @Override public void cancelAll() {
        if (maps.isEmpty()) {
          return;
        }
     Set<Object> keys = maps.keySet();
      for (Object apiKey : keys) {
            cancel(apiKey);
       }
    }
}

