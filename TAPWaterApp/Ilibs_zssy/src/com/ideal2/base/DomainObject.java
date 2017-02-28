package com.ideal2.base;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/*
 * Ԫע��@Target,@Retention,@Documented,@Inherited
 * 
 *     @Target ��ʾ��ע������ʲô�ط������ܵ� ElemenetType ����������
 *         ElemenetType.CONSTRUCTOR ����������
 *         ElemenetType.FIELD ������������ enum ʵ����
 *         ElemenetType.LOCAL_VARIABLE �ֲ���������
 *         ElemenetType.METHOD ��������
 *         ElemenetType.PACKAGE ������
 *         ElemenetType.PARAMETER ��������
 *         ElemenetType.TYPE �࣬�ӿڣ�����ע�����ͣ���enum����
 *         
 *     @Retention ��ʾ��ʲô���𱣴��ע����Ϣ����ѡ�� RetentionPolicy ����������
 *         RetentionPolicy.SOURCE ע�⽫������������
 *         RetentionPolicy.CLASS ע����class�ļ��п��ã����ᱻVM����
 *         RetentionPolicy.RUNTIME VM����������Ҳ����ע�ͣ���˿���ͨ��������ƶ�ȡע�����Ϣ��
 *         
 *     @Documented ����ע������� javadoc ��
 *     
 *     @Inherited ��������̳и����е�ע��
 *   
 */
@Target(ElementType.LOCAL_VARIABLE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
/*
 * ����ע�� DomainObject
 * 
 */
public @interface DomainObject {
	public String elementName();
}
