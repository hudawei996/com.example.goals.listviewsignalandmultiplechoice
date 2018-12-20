package com.goals.viewinjecthu;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Date: 2018/12/3.
 * Description:
 *
 * @author huyongqiang
 */
class ViewInjectUtils {
  private static final String METHOD_SET_CONTENTVIEW = "setContentView";
  private static final String METHOD_FIND_VIEW_BY_ID = "findViewById";

  public static void inject(Activity activity) {
    injectContentView(activity);
    injectViews(activity);
    injectEvents(activity);
  }

  /**
   * 注入主布局文件
   *
   * @param activity
   */
  private static void injectContentView(Activity activity) {
    Class<? extends Activity> clazz = activity.getClass();
    // 查询类上是否存在ContentView注解
    ContentViewInject contentView = clazz.getAnnotation(ContentViewInject.class);
    // 存在
    if (contentView != null) {
      //获得注解中的值
      int contentViewLayoutId = contentView.value();
      try {
        //获取方法名称为：setContentView的方法，参数类型为整型
        Method method = clazz.getMethod(METHOD_SET_CONTENTVIEW, int.class);
        //AccessibleTest类中的成员变量为private,故必须进行此操作
        method.setAccessible(true);
        //调用方法
        method.invoke(activity, contentViewLayoutId);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 注入所有的控件
   *
   * @param activity
   */
  private static void injectViews(Activity activity) {
    //获得这个Activity类
    Class<? extends Activity> clazz = activity.getClass();
    //获得这个类中的参数集合
    Field[] fields = clazz.getDeclaredFields();
    // 遍历所有成员变量
    for (Field field : fields) {

      //在每个参数中，获取注解类型
      ViewInject viewInjectAnnotation = field.getAnnotation(ViewInject.class);
      //如果获得的注解类，不为空
      if (viewInjectAnnotation != null) {
        int viewId = viewInjectAnnotation.value();
        if (viewId != -1) {
          Log.e("TAG", viewId + "");
          // 初始化View
          try {
            Method method = clazz.getMethod(METHOD_FIND_VIEW_BY_ID, int.class);
            Object resView = method.invoke(activity, viewId);
            field.setAccessible(true);
            field.set(activity, resView);
          } catch (Exception e) {
            e.printStackTrace();
          }

        }
      }

    }
  }


  /**
   * 注入所有的事件
   *
   * @param activity
   */
  private static void injectEvents(Activity activity) {

    Class<? extends Activity> clazz = activity.getClass();
    //获得这个类中的所有方法
    Method[] methods = clazz.getMethods();

    //遍历所有的方法
    for (Method method : methods) {
      //获取方法上的所有注解
      Annotation[] annotations = method.getAnnotations();

      //循环遍历注解列表
      for (Annotation annotation : annotations) {
        //获取注释类型
        Class<? extends Annotation> annotationType = annotation.annotationType();

        //拿到注解上的点击事件注解
        EventBase eventBaseAnnotation = annotationType.getAnnotation(EventBase.class);
        //如果注解类型为EventBase 的这个类不为空
        if (eventBaseAnnotation != null) {

          /**
           * 获得注解类中的几个有默认参数的值
           */
          //取出设置监听器的名称，监听器的类型，调用的方法名:setOnClickListener();
          String listenerSetter = eventBaseAnnotation.listenerSetter();
          //监听器类型:new View.OnClickListener();
          Class<?> listenerType = eventBaseAnnotation.listenerType();
          //调用监听的方法名:buttonClickMethodName();
          String methodName = eventBaseAnnotation.methodName();

          /**
           * 尝试用获得的整型数组类型的值来，调用方法
           */
          try {
            //找到参数
            //拿到Onclick注解中的value方法。int[] value();相当于在接口中申明了一个抽象的方法。
            Method aMethod = annotationType.getDeclaredMethod("value");
            //取出所有的viewId
            int[] viewIds = (int[]) aMethod.invoke(annotation, (Object) null);

            //通过InvocationHandler设置代理
            DynamicHandler handler = new DynamicHandler(activity);
            handler.addMethod(methodName, method);
            Object listener = Proxy.newProxyInstance(
              listenerType.getClassLoader(),
              new Class<?>[]{listenerType},
              handler);

            //遍历所有的View，设置事件
            for (int viewId : viewIds) {
              View view = activity.findViewById(viewId);
              //根据已知的参数找方法（找到方法）
              Method setEventListenerMethod = view.getClass().getMethod(listenerSetter, listenerType);
              //将找到的方法，（调用）
              setEventListenerMethod.invoke(view, listener);
            }

          } catch (Exception e) {
            e.printStackTrace();
          }
        }

      }
    }

  }
}
