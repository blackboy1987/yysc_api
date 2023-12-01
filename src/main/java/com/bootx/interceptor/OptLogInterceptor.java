
package com.bootx.interceptor;

import com.bootx.audit.Audit;
import com.bootx.entity.OptLog;
import com.bootx.service.AdminService;
import com.bootx.service.OptLogService;
import com.bootx.util.IPUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;


public class OptLogInterceptor implements HandlerInterceptor {

	@Resource
	private OptLogService optLogService;
	@Resource
	private AdminService adminService;

	/**
	 * 请求前处理
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param handler
	 *            处理器
	 * @return 是否继续执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Audit audit = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Audit.class);
			if (audit != null) {
				OptLog auditLog = new OptLog();
				auditLog.setAction(audit.action());
				auditLog.setIp(IPUtils.getIpAddr(request));
				auditLog.setRequestUrl(request.getRequestURI());
				auditLog.setParameters(new HashMap<>(request.getParameterMap()));
				auditLog.setUser(adminService.getCurrent());
				request.setAttribute(OptLog.OPT_LOG_ATTRIBUTE_NAME, auditLog);
			}
		}
		return true;
	}

	/**
	 * 请求后处理
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param handler
	 *            处理器
	 * @param modelAndView
	 *            数据视图
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Audit audit = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Audit.class);
			if (audit != null) {
				OptLog auditLog = (OptLog) request.getAttribute(OptLog.OPT_LOG_ATTRIBUTE_NAME);
				if (auditLog != null && auditLog.isNew()) {
					optLogService.create(auditLog);
				}
			}
		}
	}

}