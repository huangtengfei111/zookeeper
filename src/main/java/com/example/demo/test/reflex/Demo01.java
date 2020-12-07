package com.example.demo.test.reflex;

import com.example.demo.entity.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description: java类作用描述
 * @Author: huangtf
 * @CreateDate: 2020/12/6 16:28
 */
public class Demo01 {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        User u = new User();
        //通过反射获取的一个类对象
        Class<?> c1 = Class.forName("com.example.demo.entity.User");

        //类名.class
        Class<User> c2 = User.class;

        //实例名.getClass
        Class c3 = u.getClass();

        if(u instanceof User){
            System.out.println(111);
        }

        //通过类对象获取一个实例对象  这个需要类中有无参构造器
        User user = (User) c1.newInstance();

        //反射操作一个方法
        //获取方法
        Method setName = c1.getDeclaredMethod("setName", String.class);
        //操作方法
        setName.invoke(user, "aa");
        System.out.println(user);

        //操作属性
        Field age = c1.getDeclaredField("age");

        age.setAccessible(true); //关闭安全检测，否则private的属性不能访问
        age.set(user, 12);
        System.out.println(user);

    }
}
