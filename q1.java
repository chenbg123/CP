package lab6;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;



class Q1 {
	

	private static int tasks;
	private static int numPerTask;
	private final static int totalNum=2500;

	
	
	public static void main(String[]args) {
			
			Scanner scan=new Scanner(System.in);

			while(true) {
				System.out.print("Please enter a number of each sub-task to process.(not more than 1000):");
				int num=scan.nextInt();
				if(num<=totalNum&&num>0) {
					numPerTask=num;
					if(totalNum%numPerTask!=0) {
						tasks=totalNum/numPerTask+1;
					}else {
						tasks=totalNum/numPerTask;
					}
					break;
				}
			}

			
			ExecutorService executor=Executors.newCachedThreadPool();
			List<CountDivisor> taskList=new ArrayList<>();
			
			int startIndex = 1;  
		    int endIndex = startIndex + numPerTask - 1; 
		    
			for(int a=1;a<=tasks;a++) {
				
				if (a == tasks) {
			        endIndex = totalNum;  
			     }
				CountDivisor task=new CountDivisor(startIndex,endIndex);
				taskList.add(task);
				
				startIndex=endIndex+1;
				endIndex=startIndex + numPerTask - 1;
			}
			
			
			List<Future<Number>> resultList = null;
			
			try {
				resultList = executor.invokeAll(taskList);
				}catch(InterruptedException e) {}
			

	        int count = 0,number=0;
	        
	        for (Future<Number> future : resultList) {
	            try {
	            	
	            	Number nu=future.get();
	                if (nu.MaxDivisorCount >= count) {
		            	count= nu.MaxDivisorCount;
		            	number=nu.MaxDivisorNum;
		            	}
	                
	            } catch (InterruptedException | ExecutionException e) {
	                e.printStackTrace();
	            }
	        }
	        System.out.println("In the range of 1 to "+totalNum+","+number +" has the most divisors of "+count);
	        executor.shutdown();
			
			
			
			
		}


		
		
	 public static class CountDivisor implements Callable<Number>{

			
			int startIndex,endIndex;
			
			CountDivisor(int startIndex,int endIndex){
				this.startIndex=startIndex;
				this.endIndex=endIndex;
			}
			
			
			public int count(int n) {
			      int count = 0;
			      for (int i = 1; i <= n ; i++) {
			         if ( n % i == 0 )
			            count ++;
			      }
			      return count;
		   }
			
			
			@Override
			public Number call() throws Exception {
				
				Number num=new Number();
				int count=0;
				
				  for (int i = startIndex; i <=endIndex; i++) {
			            int divisors = count(i);
			            if (divisors > count) {
			            	count = divisors;
			            	
			            	num.MaxDivisorCount=divisors;
			            	num.MaxDivisorNum=i;
			            }
			      }
				  
				  return num;

			  }
		
		}
}


class Number{
	int MaxDivisorCount,MaxDivisorNum;
}
