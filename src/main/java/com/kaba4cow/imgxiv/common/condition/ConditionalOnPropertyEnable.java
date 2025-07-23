package com.kaba4cow.imgxiv.common.condition;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@Conditional(OnPropertyEnableCondition.class)
public @interface ConditionalOnPropertyEnable {

	String prefix();

	boolean matchIfMissing() default false;

}
