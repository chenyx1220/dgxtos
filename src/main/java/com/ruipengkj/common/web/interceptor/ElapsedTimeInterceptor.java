package com.ruipengkj.common.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 计算请求耗时拦截器
 * @author Squall
 *
 */
public class ElapsedTimeInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(ElapsedTimeInterceptor.class);

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		long startTime = System.currentTimeMillis();
		request.setAttribute("elapsedStartTime", startTime);

		return true;
	}
	

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		long startTime = (Long) request.getAttribute("elapsedStartTime");

		long endTime = System.currentTimeMillis();

		long executeTime = endTime - startTime;

		// log it
		if (logger.isDebugEnabled()) {
			logger.debug("[{}] executeTime : {}ms", handler, executeTime);
		}
	}

}
