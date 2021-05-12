package lab6;


import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

//import java.sql.Time;

public class Q2 {
	

	public static void main(String[]args)  {
		

		System.out.println("Start calculate finonacci numbers by sequential calculation (recursion) method");

		time time1=getTime(45);
		time time2=getTime(44);
		double totalTime=calculateTime(time1,time2);

		
		System.out.printf("Total calculation time:%.3f seconds %n",totalTime);
		System.out.println("------------------------------------------------------------------");
		
		System.out.println("Start calculate finonacci numbers by Asynchronous task");
		
		//CompletableFuture<Double>completableFuture=CompletableFuture.supplyAsync(()->getTime(45)).thenCombine(CompletableFuture.supplyAsync(()->getTime(44)), (s1,s2)->s1+s2);
	
		
		CompletableFuture<time>completableFuture1 = CompletableFuture.supplyAsync(() -> getTime(45));
		CompletableFuture<time> completableFuture2 = CompletableFuture.supplyAsync(() -> getTime(44));

		
		 try {
				time time3=completableFuture1.get();
				time time4=completableFuture2.get();
				
				double totalTime2=calculateTime(time3,time4);
	
		        System.out.println("Total calculation time:" + totalTime2);
		        
		 }catch(InterruptedException | ExecutionException ex)
		 {
			 
		 }
	}
	
	
	public static time getTime(int n) {
		
		time time=new time();
		time.starTime=Instant.now();
		int value=fibonacci(n);
		time.endTime=Instant.now();

		System.out.printf("fibonacci(%d) is %d ,calculation time for fibonacci(%d) is %.3f seconds %n",n,value,n,time.calculateTime());
		return time;
		
	}
	
	public static double calculateTime(time time1,time time2) {
		 time comparedTime = new time();
		 comparedTime.starTime = time1.starTime.compareTo(time2.starTime) < 0 ?time1.starTime: time2.starTime;
		 comparedTime.endTime = time1.endTime.compareTo(time2.endTime) > 0 ?time1.endTime: time2.endTime;
		 return comparedTime.calculateTime();
	}
	
	
	public static int fibonacci(int n)
	    {
	    if (n <= 1) {
	       return n;
	    }else {
	    return fibonacci(n-1) + fibonacci(n-2);
	    }
	 }	
}


class time{
	public Instant starTime;
	public Instant endTime;
	
	public double calculateTime() {
		return Duration.between(starTime, endTime).toMillis()/1000.0;
	}
	
}
