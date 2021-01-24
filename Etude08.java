import java.util.*;
import java.io.*;

public class Etude08{
  static boolean normal = false;
  static boolean halt = false;
  static String oper = "";
  static String oper1 = "";
 
  public static boolean isMin(int[] numbers, int target){
	  int minimum = numbers[0];
	    for(int i = 1; i< numbers.length; i ++){
	      if (minimum==1){
	    	  minimum *= numbers[i];
	      } else if (numbers[i] == 1){
	    	  minimum *= 1;
	      }else{
	    	  minimum += numbers[i];
	      }
	    }
	    if(minimum > target){
	      return false;
	    }
	    return true;
	  }
  
  public static boolean isMax(int[] numbers, int target){
    int maximum = numbers[0];
    for(int i = 1; i < numbers.length; i ++){
      if(maximum < 0){
        return true;
      }if(maximum==1){
    	  maximum += numbers[i];
      }else if(numbers[i]==1){
    	  maximum +=1;
      }else{
    	  maximum *= numbers[i];   
      }
    }
    if(maximum > target){
      return true;
    }
    return false;
  }

  
  private static String rmChar(String str) {
	    return str.substring(0, str.length() - 1);
	  }
 
  public static boolean reachTarget(int[] numberList, int goal){
    if(isMin(numberList,goal) && isMax(numberList, goal)){
      return true;
    }else {
    	return false;
    } 
  }

  public static void leftOperation( int[] numbers, int index, int partialSum, int target) {
    
    if (index == numbers.length) {
      if (partialSum == target){
        oper1 = oper;
        halt = true;
      }
      return;
    }
    int newSumAdd = partialSum + numbers[index];
    int newSumMultiply = partialSum * numbers[index];
    
    if(newSumAdd <= target){
      oper += "+";
      leftOperation(numbers, index + 1, newSumAdd, target);
      oper = rmChar(oper);
    }   
    if(halt){
      return;
    }
    if(newSumMultiply <= target){
      oper += "*";
      leftOperation(numbers, index + 1, newSumMultiply, target);
      oper = rmChar(oper);
    }   
  }

public static void normOperation( int[] numbers, int index, int i, int i1, int target) {
  
  if (index == numbers.length) {
    int endSum = 0;
    if(oper.length() == 0||i1==-1){
      endSum = i;
    }else {
    	endSum = i1 + i;
    }
    //Found it! 
    if (endSum == target){
      oper1 = oper;
      halt = true;
    }
    return;
  }
  
    int nextNum = numbers[index];
    if(!(i > target||i1 > target)){
      oper += "+";
      if( i1 == -1){
    	  normOperation(numbers, index + 1, i, nextNum, target);
      }
      else{
         normOperation(numbers, index + 1, i+i1, nextNum, target);
      }
      oper = rmChar(oper);
    }   
    if(halt){
      return;
    }
    oper += "*";
    if(i1 == -1){
    	normOperation(numbers, index + 1, i*nextNum, i1, target);
    }else{
    	normOperation(numbers, index + 1, i, i1*nextNum, target);
    }
    oper = rmChar(oper);
  }

public static void solve(int[] numbers, int target, char order) {
	String output = "";
	if ( order == 'L'){ 
        normal = false;
      }else{
        normal = true;
      }
      
      if(numbers[0] == target) {
    	  System.out.println(order + " " + target + " " + target);
      }
      
      if(reachTarget(numbers, target)){ 
        if(normal){
        	normOperation(numbers, 1, numbers[0], -1, target); 
        }
        else{
          leftOperation(numbers, 1, numbers[0], target);
        }
        if(!oper1.isEmpty()){
          
          for (int i=0; i < numbers.length; i++){
            if(i != numbers.length-1){
            	output+= numbers[i];
            	output+= " ";
            	output+= oper1.charAt(i);
            	output+= " ";
            }
            else{
            	output+= numbers[i];
            }
          }
          System.out.println(order + " " + target + " " + output); 
        }
        else{
          
          System.out.println(order + " " + target + " " + "impossible");
        }
      }
      else{
        System.out.println(order + " " + target + " " + "impossible");
      }

}
  
  public static void main (String[] args){
	
    Scanner input = new Scanner (System.in);
    while(input.hasNextLine()){
      oper = ""; // These 3 lines reset global variables for each new calculation.
      oper1 = ""; 
      halt = false; 
      String line = input.nextLine().trim().toUpperCase();
      String[] tokens = line.split(" ");
      int numbers[] = new int[tokens.length];
      if (line == "END") return;
      for (int i = 0; i < tokens.length; i++) {
          numbers[i] = Integer.parseInt(tokens[i]);
      }
      int target = input.nextInt();
      char order = input.nextLine().trim().charAt(0);
      solve(numbers, target, order);
      
    }
  }
}