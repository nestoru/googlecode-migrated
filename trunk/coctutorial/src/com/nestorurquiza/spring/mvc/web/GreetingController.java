package com.nestorurquiza.spring.mvc.web;
    
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class GreetingController extends MultiActionController{

  public ModelAndView hi(HttpServletRequest request,
    HttpServletResponse response) throws ServletException, IOException {
      ModelAndView mav = new ModelAndView("messageView");
      mav.addObject("message", "Hi");
      return mav;
  }
  
  public ModelAndView hello(HttpServletRequest request,
    HttpServletResponse response) throws Exception {
      ModelAndView mav = new ModelAndView("messageView");
      mav.addObject("message", "Hello");
      return mav;
  }
}
