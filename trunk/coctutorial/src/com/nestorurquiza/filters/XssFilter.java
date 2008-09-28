package com.nestorurquiza.filters;

import com.nestorurquiza.utils.XssRequestWrapper;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class XssFilter implements Filter 
{
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		chain.doFilter(new XssRequestWrapper( (HttpServletRequest)request ), response);
	}

	public void destroy() 
	{	

	}

	public void init(FilterConfig config) throws ServletException 
	{
	
	}
}