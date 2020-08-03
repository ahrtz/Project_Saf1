package io.ssafy.p.i3a110.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// Controller 인지 확인
		if(handler instanceof HandlerMethod == false) {
			return true;
		}
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		if(auth == null) {
			return true;
		}
		
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		if(email == null) {
			response.getWriter().write("Access Denied");
			response.setStatus(401);
			return false;
		}else {
			return super.preHandle(request, response, handler);
		}
	}

}
