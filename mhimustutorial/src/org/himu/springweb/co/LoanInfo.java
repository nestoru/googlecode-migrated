package org.himu.springweb.co;

import java.util.List;

public class LoanInfo  
{  
   // IN  
   private double principal;  
   private double apr; // annual percentage rate  
   private int years;  
   private int periodPerYear;  
   // OUT  
   private double payment; // periodic payment amount  
   private List<RepaySchedule> schedule; // repayment schedule  
 
   //getters
   public double getPayment()
   {
	   	return payment;
   }
   //
   public int getPeriodPerYear()
   {
	   	return periodPerYear;
   }
   //
   public int getYears()
   {
	   	return years;
   }
   //
   public double getApr()
   {
	   	return apr;
   }
   //
   public double getPrincipal()
   {
	   	return principal;
   }

   //setters
   //
   public void setPayment(double  payment)
   {
	   	this.payment=payment;
   }
   //
   public void setPeriodPerYear(int  periodPerYear)
   {
	   	this.periodPerYear=periodPerYear;
   }
   //
   public void setYears(int  years)
   {
	   	this.years=years;
   }
   //
   public void setApr(double  apr)
   {
	   	this.apr=apr;
   }
   //
   public void setPrincipal(double  principal)
   {
	   	this.principal=principal;
   }

   public List<RepaySchedule> getSchedule()  
   {  
       return schedule;  
   }  
 
   public void setSchedule(List<RepaySchedule> schedule)  
   {  
       this.schedule = schedule;  
   } 
}