package week2_divide_and_conquer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class CountInversions {
	
	private static BigInteger count(Integer[] a, int left_index, int right_index) {
		if (right_index <= left_index) return BigInteger.ZERO;
		
		int mid_point = left_index +  (right_index - left_index)/2; 
		BigInteger right_inversions = count(a, left_index, mid_point);
		BigInteger left_inversions = count(a, mid_point + 1, right_index);
		BigInteger split_inversions = count_splits(a, left_index, right_index, Arrays.copyOfRange(a, left_index, mid_point + 1), Arrays.copyOfRange(a, mid_point + 1, right_index + 1) );
				
		return right_inversions.add(left_inversions).add(split_inversions);
	}
	
	private static BigInteger count_splits(Integer[] original_array, int sorted_array_left_index, int sorted_array_right_index, Integer[] left, Integer[] right) {
		int left_pointer = 0;
		int right_pointer = 0;
		int i = sorted_array_left_index;
		BigInteger inversion_count = BigInteger.ZERO;
		
		//traverse both arrays counting up the inversions
		while ( left_pointer < left.length && right_pointer < right.length) {
			if (left[left_pointer] < right[right_pointer] ) {
				// not an inversion
				//copy value to original array 
				original_array[i] = left[left_pointer];
				
				//advance pointers
				left_pointer ++;
				i++;
			} else {
				//inversion case
				
				// 1. sort
				original_array[i]  = right[right_pointer];
				
				// 2. count inversions
				int inversions = left.length - left_pointer;
				inversion_count = inversion_count.add(BigInteger.valueOf(inversions));
				
				// 3. advance right pointer and original_array pointer
				right_pointer++;
				i++;
			}
		}
		
		if (i <= sorted_array_right_index && left_pointer < left.length) {
			//append left array
			while (i <= sorted_array_right_index && left_pointer < left.length) {
				original_array[i] = left[left_pointer];
				i++;
				left_pointer++;
			}
		}
		else if ( i <= sorted_array_right_index && right_pointer < right.length ) {
			//append right array
			original_array[i] = right[right_pointer];
			i++;
			right_pointer++;
		}
		
		return inversion_count;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		List<Integer> input = new ArrayList<Integer>();
		while (in.hasNext()) {
			String next = in.nextLine();
			if (next.isEmpty()) break;
			else input.add(Integer.parseInt(next));
		}
		Integer[] array = new Integer[input.size()];
		System.out.println(count(input.toArray(array), 0, input.size() - 1));
	}

}
