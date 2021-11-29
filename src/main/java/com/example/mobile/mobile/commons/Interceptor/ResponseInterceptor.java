package com.example.mobile.mobile.commons.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @ClassName ResponseInterceptor
 * @Description: 获取controller 返回值
 * @Author ts-lingcai.kong
 * @Date 2021/11/29 14:54
 */
@ControllerAdvice
@Component
@Slf4j
public class ResponseInterceptor implements ResponseBodyAdvice {

  @Override
  public boolean supports(MethodParameter returnType, Class converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType,
      MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request,
      ServerHttpResponse response) {

    //获取指定接口返回值
    //if ("".equals(request.getURI().getPath())) {
      //通过RequestContextHolder 获取request
      HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder
          .getRequestAttributes()).getRequest();
      HttpSession httpSession = servletRequest.getSession(true);
      httpSession.setAttribute("body",body);

      //return body;
    //}
    httpSession.setAttribute("body",body);
    return body;
  }
}
