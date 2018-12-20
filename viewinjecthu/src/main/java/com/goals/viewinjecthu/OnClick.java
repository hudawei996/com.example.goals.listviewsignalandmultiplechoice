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

import android.view.View;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * 参数数额这了默认值
 * listenerType = View.onClickListener.class
 * listenerSetter = "setOnClickListener"
 * methodName = "onClick";如果设置了值也可以取你叫的方法的名称
 */
@EventBase(listenerType = View.OnClickListener.class, listenerSetter = "setOnClickListener", methodName = "onClick")
public @interface OnClick {

  //主要是需要传递进来，View的ID
  int[] value();

}
