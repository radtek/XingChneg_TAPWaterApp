package com.ideal2.base;

import java.lang.reflect.Method;



public class AnnotationFactory {
	
	public void buildDomainObject(Class form){
		Method[] methods = form.getDeclaredMethods();
		for (Method method : methods) {
			/*
			 * �жϷ������Ƿ���ָ��ע�����͵�ע��
			 */
			boolean hasAnnotation = method.isAnnotationPresent(DomainObject.class);
			if (hasAnnotation) {
				/*
				 * ����ע�����ͷ��ط�����ָ������ע��
				 */
				DomainObject annotation = method.getAnnotation(DomainObject.class);
				
			}
		}
	}
	
	
}
