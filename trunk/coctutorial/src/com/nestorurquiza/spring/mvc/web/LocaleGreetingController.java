package com.nestorurquiza.spring.mvc.web;
   
import java.util.Locale;
 
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LocaleGreetingController extends MultiActionController{

  public ModelAndView hi(HttpServletRequest request,
    HttpServletResponse response) throws ServletException, IOException {
      ModelAndView mav = new ModelAndView("messageView");
      String greeting = findLocaleGreeting(request, "greeting.hi"); 
      mav.addObject("message", greeting);
      return mav;
  }
  
  public ModelAndView hello(HttpServletRequest request,
    HttpServletResponse response) throws Exception {
      ModelAndView mav = new ModelAndView("messageView");
      String greeting = findLocaleGreeting(request, "greeting.hello"); 
      mav.addObject("message", greeting);
      return mav;
  }
  
  private String findLocaleGreeting(HttpServletRequest request, String greetingI18n){
    ApplicationContext applicationContext = this.getApplicationContext();  
    RequestContext requestContext = new RequestContext(request);
    Locale myLocale = requestContext.getLocale();
    if(myLocale == null)
      myLocale = Locale.US;
    String greeting = applicationContext.getMessage(greetingI18n, null, myLocale); 
    return greeting;
  }
}
