package com.infogain.igconverge.util;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

/**
 * 
 * Description: IGConvergeLoggerPostProcessor BeanPostProcessor
 * 
 * @author Manish Bassi
 * 
 */

@Component
public class IGConvergeLoggerPostProcessor implements BeanPostProcessor,
		Ordered {

	public final String IGCONVERGE_PACKAGE = "com.infogain.igconverge";

	public Object postProcessBeforeInitialization(final Object bean,
			String beanName) throws BeansException {
		ReflectionUtils.doWithFields(bean.getClass(), new FieldCallback() {
			public void doWith(Field field) throws IllegalArgumentException,
					IllegalAccessException {

				if (field.isAnnotationPresent(IGConvergeLogger.class)
						&& bean.getClass().getPackage().toString()
								.contains(IGCONVERGE_PACKAGE)) {
					if (!field.isAccessible()) {
						ReflectionUtils.makeAccessible(field);
					}
					Logger log = Logger.getLogger(bean.getClass());
					field.set(bean, log);
				}
			}
		});
		return bean;
	}

	public int getOrder() {

		return 10;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

}