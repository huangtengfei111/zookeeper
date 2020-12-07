package com.example.demo.test;

import java.lang.annotation.*;

/**
 * @Description: java类作用描述
 * @Author: huangtf
 * @CreateDate: 2020/12/6 13:40
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface AnnotationDemo {
}
