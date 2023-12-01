
package com.bootx.interceptor;

import com.bootx.audit.Audit;
import com.bootx.entity.MemberOptLog;
import com.bootx.entity.OptLog;
import com.bootx.service.MemberOptLogService;
import com.bootx.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;


public class MemberOptLogInterceptor implements HandlerInterceptor {

	@Resource
	private MemberOptLogService memberOptLogService;
	@Resource
	private MemberService memberService;

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
				MemberOptLog auditLog = new MemberOptLog();
				auditLog.setAction(audit.action());
				auditLog.setIp(request.getRemoteAddr());
				auditLog.setRequestUrl(String.valueOf(request.getRequestURL()));
				auditLog.setParameters(new HashMap<>(request.getParameterMap()));
				auditLog.setUser(memberService.getCurrent());
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
				MemberOptLog auditLog = (MemberOptLog) request.getAttribute(MemberOptLog.OPT_LOG_ATTRIBUTE_NAME);
				if (auditLog != null && auditLog.isNew()) {
					memberOptLogService.create(auditLog);
				}
			}
		}
	}

}