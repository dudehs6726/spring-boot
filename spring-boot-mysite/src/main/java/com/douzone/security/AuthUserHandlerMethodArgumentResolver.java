package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.douzone.mysite.vo.UserVo;

@Component
public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		if(supportsParameter(parameter) == false){
			
			return WebArgumentResolver.UNRESOLVED;
		}
		
		//@AuthUser 가 붙어 있고 type이 UserVo
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		HttpSession session = request.getSession();
		
		if(session == null) {
			return null;
		}
		
		return session.getAttribute("authuser");
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		//@AuthUser가 안붙어 있음
		if(authUser == null) {
			return false;
		}
		
		//파라미터 타입이 UserVo 인지 확인
		if(parameter.getParameterType().equals(UserVo.class) == false) {
			return false;
		}

		return true;
	}
}
