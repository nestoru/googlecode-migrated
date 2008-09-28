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
public class ParamsController extends MultiActionController{

  public ModelAndView write(HttpServletRequest request,
    HttpServletResponse response) throws ServletException, IOException {
      ModelAndView mav = new ModelAndView("paramsView");
      String parameter = request.getParameter("par");
      mav.addObject("par", parameter);
      mav.addObject("title", "Testing parameters ...");
      return mav;
  }
}
