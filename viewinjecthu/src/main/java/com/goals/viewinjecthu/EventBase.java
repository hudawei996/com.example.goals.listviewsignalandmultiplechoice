package com.goals.viewinjecthu;

/**
 * Date: 2018/12/3.
 * Description:
 *
 * @author huyongqiang
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
  //点击事件类：new View.OnClickListener();
  Class<?> listenerType();

  //设置点击事件的方法名setOnClickListener,View.setOnClickListener();
  String listenerSetter();

  //点击事件的方法名称:onButtonClickMethodName();
  String methodName();
}
