package com.ruipengkj.common.web.support;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**                                                                                 
 * 用于spring mvc 类型转换                                                                                                
 * @author Squall
 */
public class CustomBinding implements WebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(Date.class, new MultipleDateEditor());
	}

}
