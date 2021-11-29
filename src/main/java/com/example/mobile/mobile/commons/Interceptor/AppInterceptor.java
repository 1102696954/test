package com.example.mobile.mobile.commons.Interceptor;

import cn.hutool.json.JSONUtil;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName AppInterceptor
 * @Description: 第一步：自定义一个实现了HandlerInterceptor接口的类，或者继承抽象类AbstractInterceptor。
 * @Author ts-lingcai.kong
 * @Date 2021/11/29 11:33
 */
@Component
@Slf4j
public class AppInterceptor implements HandlerInterceptor {

  private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>(
      "ThredLocal StartTime");

  private String getParamString(Map<String, String[]> map) {
    StringBuilder sb = new StringBuilder();
    for (Entry<String, String[]> e : map.entrySet()) {
      sb.append(e.getKey()).append("=");
      String[] value = e.getValue();
      if (value != null && value.length == 1) {
        sb.append(value[0]).append("\t");
      } else {
        sb.append(Arrays.toString(value)).append("\t");
      }
    }
    return sb.toString();
  }

  //将errorStack转化为String
  public static String getStackTraceAsString(Throwable throwable) {
    if (throwable == null) {
      return "";
    }
    StringWriter stringWriter = new StringWriter();
    throwable.printStackTrace(new PrintWriter(stringWriter));
    return stringWriter.toString();
  }


  //重写preHandle方法  在请求处理之前调用该方法
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    long startTime = System.currentTimeMillis();
    request.setAttribute("startTime", startTime);
    //线程绑定变量 （该数据只有当前请求的线程可见）
    startTimeThreadLocal.set(startTime);
    //将handler 转化为 HandlerInterceptor
    //if (handler instanceof HandlerInterceptor) {
    StringBuilder sb = new StringBuilder();
    sb.append("----------- 开始时间:")
        .append(new SimpleDateFormat("hh:mm:ss.sss").format(startTime))
        .append("-----------\n");
    if (handler instanceof HandlerMethod) {
      HandlerMethod h = (HandlerMethod) handler;
      sb.append("Controller: ").append(h.getBean().getClass().getName()).append("\n");
      sb.append("Method    : ").append(h.getMethod().getName()).append("\n");
      sb.append("Params    : ").append(getParamString(request.getParameterMap())).append("\n");
      sb.append("URL       : ").append(request.getRequestURI()).append("\n");
      log.info(sb.toString());
    }
    return true;
  }

  //controller请求之后执行，可以对返回的数据和视图对象进行操作  只有在preHandle方法返回true执行
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    long startTime = (Long) request.getAttribute("startTime");
    long endTime = System.currentTimeMillis();
    long executeTime = endTime - startTime;
    if (handler instanceof HandlerMethod) {
      StringBuilder sb = new StringBuilder();
      Object result = request.getSession().getAttribute("body");
      if (result != null) {
        sb.append("Response   : ").append(JSONUtil.parse(result)).append("\n");
      }
      sb.append("ExecuteTime: ").append(executeTime).append("ms").append("\n");
      sb.append("-----------------------------------结束------------------------------------------");
      log.info(sb.toString());
    }
  }


  // 整个请求结束后 用于进行资源清理工作 只有在preHandle方法返回true执行
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    //打印JVM信息
    if (log.isInfoEnabled()) {
      //得到线程绑定的局部变量（开始时间）
      long beginTime = startTimeThreadLocal.get();
      //结束时间
      long endTime = System.currentTimeMillis();
      //controller 报错记录异常信息
      if (ex != null) {
        log.error("Controller error: " + getStackTraceAsString(ex));
      }
      startTimeThreadLocal.remove();
    }
  }
}
