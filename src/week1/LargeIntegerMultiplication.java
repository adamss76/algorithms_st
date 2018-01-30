package week1;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.Timer;

public class LargeIntegerMultiplication {
	
	private static BigInteger naiveMultiply(int[] digits_x, int[] digits_y) {
		
		BigInteger partial_product_accumulator = BigInteger.ZERO;
		for (int i = 0; i < digits_x.length; i ++) {
			
			BigInteger x_digit =BigInteger.valueOf(digits_x[i]);
			
			for (int j = 0; j < digits_y.length; j ++) {
				BigInteger y_digit = BigInteger.valueOf(digits_y[j]);
				
				BigInteger left_shifted_x_value =  x_digit.multiply(BigInteger.TEN.pow(i));
				BigInteger left_shifted_y_value = y_digit.multiply(BigInteger.TEN.pow(j));
			
				BigInteger partial_product = left_shifted_x_value.multiply(left_shifted_y_value);
				partial_product_accumulator = partial_product_accumulator.add(partial_product);
			}
		}
		
		return partial_product_accumulator;
	}
	
	private static BigInteger karatsuba(BigInteger x, BigInteger y) {
		//if (x < 10 && y < 10) return BigInteger.valueOf(x*y);
		if (x.compareTo(BigInteger.TEN) < 0 && y.compareTo(BigInteger.TEN) < 0) return x.multiply(y);
		
		int n = x.max(y).toString().length();
		int half_n = (int) Math.ceil( (double) n/2 );
		
		BigInteger x_high = x.divide(BigInteger.TEN.pow(half_n));
		BigInteger x_low = x.mod(BigInteger.TEN.pow(half_n));
		BigInteger y_high = y.divide(BigInteger.TEN.pow(half_n));
		BigInteger y_low = y.mod(BigInteger.TEN.pow(half_n));
		
		BigInteger a = karatsuba(x_high, y_high );
		BigInteger d = karatsuba(x_low, y_low);
		BigInteger e = karatsuba( x_high.add( x_low ), y_high.add( y_low ) ).subtract( a ).subtract( d );
		/*System.out.println("n = " + n);
		System.out.println("half_n = " + half_n);
		System.out.println("a = " + a);
		System.out.println("e = " + e);
		System.out.println("d = " + d);*/
		//a*10^n + e*10^n/2 + d;
		BigInteger answer = a.multiply(BigInteger.TEN.pow(half_n * 2)).add( e.multiply(BigInteger.TEN.pow(half_n)) ).add( d );
		
		return answer;
	}
	
	public static void main(String[] args) {
		Scanner numbers = new Scanner(System.in);
		String x = numbers.next();
		String y = numbers.next();
		
		int[] x_digits = new int[x.length()];
		int[] y_digits = new int[y.length()];
		
		for (int i = x.length() - 1; i >= 0; i --) {
			int index = Math.abs(i - (x.length() - 1) );
			x_digits[index] = Integer.parseInt(x.substring(i, i + 1));
		}
		
		for (int i = y.length() - 1; i >= 0; i --) {
			int index = Math.abs(i - (y.length() - 1) );
			y_digits[index] = Integer.parseInt(y.substring(i, i + 1));
		}
		long naive_multiply_start_time = System.currentTimeMillis();
		System.out.println(naiveMultiply(x_digits, y_digits));
		long naive_multiply_end_time = System.currentTimeMillis();
		long naive_multiply_runtime = naive_multiply_end_time - naive_multiply_start_time;
		
		long karatsuba_start_time = System.currentTimeMillis();
		System.out.println(karatsuba(new BigInteger( x ), new BigInteger( y ) ) );
		long karatsuba_end_time = System.currentTimeMillis();
		long karatsuba_runtime = karatsuba_end_time - karatsuba_start_time;
		
		System.out.println("naive mulitply runtime = " + naive_multiply_runtime);
		System.out.println("karatsuba runtime = " + karatsuba_runtime);
		//test loop
		/*int test_x, test_y = 0;
		for (int k = 100; k < 2000; k++) {
			test_x = k;
			test_y = k;
			
			int[] test_x_digits = new int[Integer.toString( test_x ).length()];
			int[] test_y_digits = new int[Integer.toString( test_y ).length()];
			for (int i = Integer.toString( test_x ).length() - 1; i >= 0; i --) {
				int index = Math.abs(i - (Integer.toString( test_x ).length() - 1) );
				test_x_digits[index] = Integer.parseInt(Integer.toString( test_x ).substring(i, i + 1));
			}
			
			for (int i = Integer.toString( test_y ).length() - 1; i >= 0; i --) {
				int index = Math.abs(i - (Integer.toString( test_y ).length() - 1) );
				test_y_digits[index] = Integer.parseInt(Integer.toString( test_y ).substring(i, i + 1));
			}
			if (!karatsuba(test_x, test_y).equals(naiveMultiply(test_x_digits, test_y_digits)) ) {
				
				System.out.println(test_x);
				System.out.println(test_y);
				System.out.println("naive multiply = " + naiveMultiply(test_x_digits, test_y_digits));
				System.out.println("karatsuba = " + karatsuba(test_x, test_y));
				
			}
		}*/
	}

}
