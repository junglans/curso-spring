package com.curso.springboot.api.builders;

import org.apache.commons.text.CaseUtils;

import com.curso.springboot.api.dao.FilterBy;

public class ExpressionBuilderFactory {

	public static final Builder<?> getBuilder(FilterBy filter) throws Exception {
		String className = "com.curso.springboot.api.dao." + CaseUtils.toCamelCase(filter.getAttrType(),true,new char[]{'_'}) + "ExpressionBuilder";
		Class<?> clazz = Class.forName(className);
		return (Builder<?>) clazz.newInstance();
	}
}
