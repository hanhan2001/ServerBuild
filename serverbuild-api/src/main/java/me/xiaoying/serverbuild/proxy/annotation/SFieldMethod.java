package me.xiaoying.serverbuild.proxy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SFieldMethod {
    String fieldName();

    Type type();

    boolean needInstance() default true;

    enum Type {
        GETTER,
        SETTER;
    }
}