package week3_quicksort;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuickSort {
	
	public static int countComparisonsFirst(Integer[] original_array, int l, int r) {
		if (r <= l) return 0;
		
		int partition_index = partitionFirst(original_array, l, r);
		
		int left_comparisons = countComparisonsFirst(original_array, l, partition_index - 1);
		int right_comparisons = countComparisonsFirst(original_array, partition_index + 1, r);
		
		partitionFirst(original_array, l, r);
		
		return right_comparisons + left_comparisons + (r - l);
	}
	
	public static int countComparisonsLast(Integer[] original_array, int l, int r) {
		if (r <= l) return 0;
		
		int partition_index = partitionLast(original_array, l, r);
		
		int left_comparisons = countComparisonsLast(original_array, l, partition_index - 1);
		int right_comparisons = countComparisonsLast(original_array, partition_index + 1, r);
		
		partitionLast(original_array, l, r);
		
		return right_comparisons + left_comparisons + (r - l);
	}
	
	public static int countComparisonsMedian(Integer[] original_array, int l, int r) {
		if (r <= l) return 0;
		
		int partition_index = partitionMedian(original_array, l, r);
		
		int left_comparisons = countComparisonsMedian(original_array, l, partition_index - 1);
		int right_comparisons = countComparisonsMedian(original_array, partition_index + 1, r);
		
		partitionMedian(original_array, l, r);
		
		return right_comparisons + left_comparisons + (r - l);
	}
	
	/**
	 * return the index of the partition element
	 * @param a
	 * @param l
	 * @param r
	 * @return
	 */
	private static int partitionFirst(Integer[] a, int l, int r) {
		int pivot = a[l];
		int elements_smaller_than_pivot = l;
		
		for (int partition_index = l + 1; partition_index <= r; partition_index ++) {
			if (a[partition_index] < pivot) {
				elements_smaller_than_pivot++;
				int temp = a[elements_smaller_than_pivot];
				a[elements_smaller_than_pivot] = a[partition_index];
				a[partition_index] = temp;
			}
		}
		//place pivot in correct location
		int pivot_index = elements_smaller_than_pivot;
		int temp = a[pivot_index];
		a[pivot_index] = pivot;
		a[l] = temp;
		
		return pivot_index;
	}
	
	private static int partitionLast(Integer[] a, int l, int r) {
		int pivot = a[r];
		
		//swap pivot element with first element in array as outlined in assignment
		int swap = a[l];
		a[l] = pivot;
		a[r] = swap;
		
		int elements_smaller_than_pivot = l;
		
		for (int partition_index = l + 1; partition_index <= r; partition_index ++) {
			if (a[partition_index] < pivot) {
				elements_smaller_than_pivot++;
				int temp = a[elements_smaller_than_pivot];
				a[elements_smaller_than_pivot] = a[partition_index];
				a[partition_index] = temp;
			}
		}
		//place pivot in correct location
		int pivot_index = elements_smaller_than_pivot;
		int temp = a[pivot_index];
		a[pivot_index] = pivot;
		a[l] = temp;
		
		return pivot_index;
	}
	
	private static int partitionMedian(Integer[] a, int l, int r) {
		int median_element_index = findMedian(a, l, r);
		int pivot = a[median_element_index];
		
		//swap pivot element with first element in array as outlined in assignment
		int swap = a[l];
		a[median_element_index] = swap;
		a[l] = pivot;
		
		int elements_smaller_than_pivot = l;
		
		for (int partition_index = l + 1; partition_index <= r; partition_index ++) {
			if (a[partition_index] < pivot) {
				elements_smaller_than_pivot++;
				int temp = a[elements_smaller_than_pivot];
				a[elements_smaller_than_pivot] = a[partition_index];
				a[partition_index] = temp;
			}
		}
		//place pivot in correct location
		int pivot_index = elements_smaller_than_pivot;
		int temp = a[pivot_index];
		a[pivot_index] = pivot;
		a[l] = temp;
		
		return pivot_index;
	}
	
	private static int findMedian(Integer[] a, int l, int r) {
		int first_element = a[l];
		int last_element = a[r];
		
		//int index_of_middle_element = (r-l) % 2 == 0 ? ((r-l)/2) + l : ( (r-l)/2 ) - 1 + l;
		int index_of_middle_element = ( (r-l)/2 ) + l;
		int middle_element = a[index_of_middle_element];
		
		int max = Math.max(first_element, Math.max(middle_element, last_element));
		int min = Math.min(first_element, Math.min(middle_element, last_element));
		
		if ( first_element != max && first_element != min ) return l;
		else if (last_element != max && last_element != min ) return r;
		else return index_of_middle_element;
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
		System.out.println(countComparisonsFirst(input.toArray(array), 0, input.size() - 1));
		System.out.println(countComparisonsLast(input.toArray(array), 0, input.size() - 1));
		System.out.println(countComparisonsMedian(input.toArray(array), 0, input.size() - 1));
	}

}
