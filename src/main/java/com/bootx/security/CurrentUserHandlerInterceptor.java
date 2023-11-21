
package com.bootx.security;

import com.bootx.entity.Admin;
import com.bootx.entity.Member;
import com.bootx.entity.User;
import com.bootx.service.AdminService;
import com.bootx.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author black
 */
public class CurrentUserHandlerInterceptor implements HandlerInterceptor {

	/**
	 * 默认"当前用户"属性名称
	 */
	public static final String DEFAULT_CURRENT_USER_ATTRIBUTE_NAME = "currentUser";

	/**
	 * 用户类型
	 */
	private Class<? extends User> userClass;

	/**
	 * "当前用户"属性名称
	 */
	private String currentUserAttributeName = DEFAULT_CURRENT_USER_ATTRIBUTE_NAME;

	@Resource
	private AdminService adminService;
	@Resource
	private MemberService memberService;

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
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
		Class<? extends User> userClass = getUserClass();
		if(userClass.isAssignableFrom(Admin.class)){
			request.setAttribute(getCurrentUserAttributeName(), adminService.getCurrent());
		}else if(userClass.isAssignableFrom(Member.class)){
			request.setAttribute(getCurrentUserAttributeName(), memberService.getCurrent());
		}

	}

	/**
	 * 获取用户类型
	 * 
	 * @return 用户类型
	 */
	public Class<? extends User> getUserClass() {
		return userClass;
	}

	/**
	 * 设置用户类型
	 * 
	 * @param userClass
	 *            用户类型
	 */
	public void setUserClass(Class<? extends User> userClass) {
		this.userClass = userClass;
	}

	/**
	 * 获取"当前用户"属性名称
	 * 
	 * @return "当前用户"属性名称
	 */
	public String getCurrentUserAttributeName() {
		return currentUserAttributeName;
	}

	/**
	 * 设置"当前用户"属性名称
	 * 
	 * @param currentUserAttributeName
	 *            "当前用户"属性名称
	 */
	public void setCurrentUserAttributeName(String currentUserAttributeName) {
		this.currentUserAttributeName = currentUserAttributeName;
	}

}