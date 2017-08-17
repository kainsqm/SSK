package cn.sh.ideal.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 系统日志的注解
 */
@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface Log {
	/**
	 * 
	 * 日志描述信息
	 * @return
	 */
	String description();
	/**
	 * 
	 * 模块名称
	 * @return
	 */
	String modulename();
	/**
	 * 
	 * 功能名称
	 * @return
	 */
	String funcname();
	/**
	 * 
	 * 方法名称
	 * @return
	 */
	String methodname();
	/**
	 * 
	 * 类名
	 * @return
	 *//*
	String classname();*/
}
