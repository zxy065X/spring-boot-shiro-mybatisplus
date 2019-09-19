package com.java.common.springbootcommon.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;

/**
* @ClassName: TimeCostInterceptor
* @Description: 记录信息:</br> 访问时间</br>Controller路径</br>对应方法名</br>请求参数信息</br>请求相对路径</br>请求处理时长
*
*/
@Component
public class TimeCostInterceptor implements HandlerInterceptor {
    
    private static Logger logger = LoggerFactory.getLogger(TimeCostInterceptor.class);

    @SuppressWarnings("unchecked")
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler){
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        logger.info("\n"+request.getRequestURI());
        if (!(handler instanceof HandlerMethod)) {
        	return true;
        }
        if (handler instanceof HandlerMethod) {
            StringBuilder sb = new StringBuilder(1000);
            SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            sb.append("\nstart======================").append(sdf.format(new Date()))
                    .append("====================================\n");
            HandlerMethod h = (HandlerMethod) handler;
            sb.append("====================================\n");
            sb.append("URI       : ").append(request.getRequestURI()).append("\n");
            sb.append("====================================\n");
            sb.append("Controller: ").append(h.getBean().getClass().getName()).append("\n");
            sb.append("Method    : ").append(h.getMethod().getName()).append("\n");
            sb.append("====================================\n");
            sb.append("Headers    : ").append(getParamString(request)).append("\n");
            sb.append("====================================\n");
            sb.append("Params    : ").append(getParamString(request.getParameterMap())).append("\n");
            sb.append("====================================\n");
            logger.info(sb.toString());
        }
        return true;
    }

    @SuppressWarnings("rawtypes")
    private String getParamString(HttpServletRequest request) {
            Enumeration headerNames = request.getHeaderNames();
            StringBuilder sb = new StringBuilder();
            while (headerNames.hasMoreElements()) {
                String key = (String) headerNames.nextElement();
                String value = request.getHeader(key);
                
                sb.append(key).append("=");
                sb.append(value).append("\n");
            }
        return sb.toString();
    }

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView){
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        if (!(handler instanceof HandlerMethod)) {
        	return ;
        }
        
        HandlerMethod h = (HandlerMethod) handler;
        if(handler instanceof HandlerMethod){
            StringBuilder sb = new StringBuilder(1000);
            sb.append("\nCostTime  : ").append(executeTime).append("ms").append("\n");
            sb.append("Controller: ").append(h.getBean().getClass().getName()).append("\n");
            sb.append("\nend==============================================================================");
            logger.info(sb.toString());
        }
    }

    private String getParamString(Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder();
        for(Entry<String,String[]> e:map.entrySet()){
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if(value != null && value.length == 1){
                sb.append(value[0]).append("\n");
            }else{
                sb.append(Arrays.toString(value)).append("\n");
            }
        }
        return sb.toString();
    }

    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }
}