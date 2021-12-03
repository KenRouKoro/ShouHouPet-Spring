package cn.korostudio.shouhoupetspring.file.jarFile.jarannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JarMods {
    String name() default "null";

    String version() default "null";

    String author() default "null";

    String ID() default "null";
}
