package org.himu.springweb.co;

public class RepaySchedule {  
   private int paymentNo;  
   private double principal;  
   private double interest;  
   private double outstanding;  
 
   //getters 
   //
   public double getOutstanding()
   {
	   	return outstanding;
   }
   //
   public double getInterest()
   {
	   	return interest;
   }
   //
   public double getPrincipal()
   {
	   	return principal;
   }
   //
   public int getPaymentNo()
   {
	   	return paymentNo;
   }

   //setters  
   //
   public void setOutstanding(double  outstanding)
   {
	   	this.outstanding=outstanding;
   }
   //
   public void setInterest(double  interest)
   {
	   	this.interest=interest;
   }
   //
   public void setPrincipal(double  principal)
   {
	   	this.principal=principal;
   }
   //
   public void setPaymentNo(int  paymentNo)
   {
	   	this.paymentNo=paymentNo;
   }

   public RepaySchedule(int paymentNo, double principal, double interest, double outstanding){ // ... all-argument constructor 
      setPaymentNo(paymentNo);
      setPrincipal(principal);
      setInterest(interest);
      setOutstanding(outstanding);
   }  
}  
