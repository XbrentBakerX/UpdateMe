/*=============================================================================
 |   Assignment:  Program #5
 |       Author:  Brent Baker (brentbaker@email.arizona.edu)
 |
 |       Course:  CSC245 (Discrete Structures), Fall 2017
 |   Instructor:  L. McCann
 | Sect. Leader:  Chad
 |     Due Date:  11/3/2017, at the beginning of class
 |
 |     Language:  Java (Eclipse IDE)
 |     Packages:  java.math
 |  Compile/Run:  [in Unix-based env. - "javac Hmwk5.java" [enter], "java Hmwk5"[enter]
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  This program recursively proves the Fundamental Theorem of
 |				  Arithmetic for any given integer.
 |					
 |				  The user provides an integer as a query, via the Command Line.
 |				  This integer is broken down into its prime factorization using the following
 |				  algorithm recursively:
 |				  
 |				  if (n >= 2)
 |				  	if n is not divisible by any prime less than or equal to its square root. n is prime, return n. 
 |				  else
 |				  	n = a * b, where <b> is the smallest prime factor of n (prime factorization),
 |				  	and <a> is the remaining factor(1<a<n). 
 |				  If <a> is not prime 
 | 					Repeat process with <a> passed as argument
 |                
 |        Input:  A single integer will be taken from the command line arguments. No further input 
 |				  Will be required from the user after program execution has begun.
 |
 |       Output:  The program will output various hierarchically indented print statements
 |					that catalog the recursive process of calculating the prime factorization
 |					of the given integer. All output is displayed on the user's screen.
 |
 |   Techniques:  [1. attempt to calculate two factors of the argument
 |						- one of which is a prime divisor less than or equal to square root of argument
 | 						- the other is either prime or composite
 |				   2. if the other is composite, repeat step one with the other as the argument
 |				   3. perform steps one and two until remaining number is not composite]
 |
 |
 |   Required Features Not Included:  All required features are included.
 |
 |   Known Bugs:  None; the program operates correctly.
 *===========================================================================*/
/*
 *  THE FUNDAMENTAL THEOREM OF ARITHMETIC:
 * 	"Every Integer greater than 1 can be written uniquely as a prime or as the product of 
 * 	two or more primes where the prime factors are written in order of nondecreasing size."
 */

package csc245HW5; //default location of package for homework5 project

import java.math.*;	//for access to math methods
import java.util.Scanner; //for testing various numbers during program development

public class Hmwk5 {
	/*=======================================================================
	 * Begin Function Definitions
	 *=====================================================================*/
    /*--------------------------------------------------- isPrime() -----
    |  Method isPrime (x)
    |
    |  Purpose:  to determine whether a number is prime
    |
    |  Pre-condition:  number provided must be an integer >= 2.
    |
    |  Post-condition: return must be true or false
    |
    |  Parameters:
    |      integer (x) -- the integer whose prime-ness is to be queried
    |
    |  Returns:  boolean evaluation
    *-------------------------------------------------------------------*/
	public static boolean isPrime(int x) {
		
		//if x is even and > 2, it is not prime
		if(x > 2 && x % 2 == 0) return false;
		//check all odd numbers up to sqrt(x)
		for(int i = 3; (i*i) <= x; i+=2) {
			if(x % i == 0) {
				return false;
			}
		}
		return true;		
	}
    /*--------------------------------------------------- getPrimeFactor() -----
    |  Method getPrimeFactor (x)
    |
    |  Purpose:  to calculate a prime factor
    |
    |  Pre-condition:  number provided must be an integer >= 2.
    |
    |  Post-condition: return must be a prime integer
    |
    |  Parameters:
    |      integer (x) -- the integer whose factor is to be queried
    |
    |  Returns:  prime integer
    *-------------------------------------------------------------------*/
	public static int getPrimeFactor(int n){
		int p = 2; //number to be incremented until evenly divides queried value
		int max = (int)Math.sqrt(n);//Square root of the queried number (loop control)
		//start at 2
		//if(n%2 == 0)
		while(p <= max) {
			if(n%p != 0) {
				p++;
			}
			else {
				break;
			}
		}
		return p;
	}	
    /*--------------------------------------------------- proveFToA() -----
    |  Method proveFToA(n)
    |
    |  Purpose:  to prove the fundamental theorem of arithmetic
    |
    |  Pre-condition:  number provided must be an integer >= 2.
    |
    |  Post-condition: ...theorem must be proven, i guess?
    |
    |  Parameters:
    |      int x -- the integer to be deconstructed into its prime factorization
    |	   int count -- integer to control indentation of print statements
    |
    |  Returns:  void
    *-------------------------------------------------------------------*/
	public static void proveFToA(int n, int count) {
		
		int x;
		int y;
		
		//print the proper number of indentations
		indent(count);
		//base case, if number being checked is prime, return
		if(isPrime(n)) {
			System.out.println(n+" is prime.");
			return;
		}
		else {
			if(n%2 == 0) {
				x = n/2;
				y = n/x;
			}
			else {
				x = getPrimeFactor(n); //get the prime factor
				y = n / x; //get the other factor
			}
			//if either factor is prime and the square root of queried value, act differently
			if(x * x == n && isPrime(x)) {
				//indent(count+1);
				System.out.println(n+" = "+x+" squared; is this factor either prime or product of primes?");
				proveFToA(x, count +  1);
				indent(count);
				System.out.println(n+" is the square of "+x+" which is prime or the product of primes.");
			}
			else if(isPrime(y) && y * y == n) {
				indent(count+1);
				System.out.println(n+" = "+y+" squared; is this factor either prime or product of primes?");
				proveFToA(x, count +  1);
			}
			else {
				//print initial message - bigger number goes first
				if(y > x) {
					System.out.println(n+" = "+y+" * "+x+"; are these factors either prime or products of primes?");
					proveFToA(y, count+1);
					proveFToA(x, count+1);
					indent(count);
					System.out.println(n+" is the product of primes ("+y+" and "+x
							+" are prime or prime products).");
				}
				else {
					System.out.println(n+" = "+x+" * "+y+"; are these factors either prime or products of primes?");
					proveFToA(x, count+1);
					proveFToA(y, count+1);
					indent(count);
					System.out.println(n+" is the product of primes ("+x+" and "+y
							+" are prime or prime products).");
				}
			}			
		}
	}
    /*--------------------------------------------------- indent() -----
    |  Method indent(x)
    |
    |  Purpose:  to indent the current line the correct number of times
    |
    |  Pre-condition:  number provided must be an integer >= 0.
    |
    |  Post-condition: should have indented (n * 4) spaces on current line
    |
    |  Parameters:
    |      int x -- the integer to control number of indentations
    |
    |  Returns:  void
    *-------------------------------------------------------------------*/
	public static void indent(int x) {
		for(int i = 0; i < x; i++) {
			System.out.print("    ");
		}
	}
	/*=======================================================================
	 * End Function Definitions, Begin main()
	 *=====================================================================*/
	public static void main(String[] args) {
		//int n = Integer.parseInt(args[0]); //storage for the original integer parameter
		int n = getVal();
		
		System.out.println("This program will demonstrate that "+n
				+" is either prime\nor is the product of two or more prime numbers.\n");
		
		//if queried integer is not prime, it must be composite
		if(!isPrime(n)) {
			proveFToA(n, 0);
		}
		else {
			System.out.println("n is prime");
		}
		System.out.println("");
		System.out.println("As this output shows, the Fundamental Theorem of Arithmetic holds for "+n+".");
	}

	

	
	
	
	
	
	
	
	
	
	
	/*===============================================================
	 * TESTING AND EXPERIMENTATION/CONSTRUCTION AREA - WEAR A HARD-HAT DOWN HERE
	 *===============================================================
	/*
	 * input gathering function, used only for quick testing of values
	 */
	public static int getVal() {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter your desired integer:");
		int retVal = in.nextInt();
		
		return retVal;
	}

}
