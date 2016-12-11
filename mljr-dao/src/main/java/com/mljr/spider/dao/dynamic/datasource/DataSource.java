/**
 * DataSource.java by Ckex.zha
 */
package com.mljr.spider.dao.dynamic.datasource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Ckex zha </br>
 *         2016年11月7日,下午1:37:32
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface DataSource {
	String value();
}
