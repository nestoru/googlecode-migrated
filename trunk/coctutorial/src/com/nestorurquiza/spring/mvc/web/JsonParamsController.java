package com.nestorurquiza.spring.mvc.web;
   
import java.util.Map;
 
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.nestorurquiza.spring.mvc.view.JSONView;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class JsonParamsController extends MultiActionController{

  public ModelAndView write(HttpServletRequest request,
    HttpServletResponse response) throws ServletException, IOException {
      
      //Returning JSON
      Map parameters = request.getParameterMap();
      return new ModelAndView(new JSONView(), parameters);
  }
}
