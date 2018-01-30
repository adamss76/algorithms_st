package week2_divide_and_conquer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javafx.geometry.Point2D;
import javafx.util.Pair;

public class ClosestPair {
	
	/**
	 * 
	 * @param p_x the list of points in P sorted by the x coordinate
	 * @param p_y the list of points in P sorted by the y coordinate
	 * @return the closest pair of points in P
	 */
	private static Pair<Point2D, Point2D> closest_pair(List<Point2D> p_x, List<Point2D> p_y) {
		//base case
		if (p_x.size() <= 3) {
			return compute_closest_pair(p_x);
		}
		
		//split sorted list of points in half
		int mid = p_x.size()/2; 
		
		//left half
		List<Point2D> left_x = p_x.subList(0, mid); 
		List<Point2D> left_y = p_y.subList(0, mid);
		
		//right half
		List<Point2D> right_x = p_x.subList(mid, p_x.size());
		List<Point2D> right_y = p_y.subList(mid, p_x.size());
		
		Pair<Point2D, Point2D> left_closest_pair = closest_pair(left_x, left_y);
		Pair<Point2D, Point2D> right_closest_pair = closest_pair(right_x, right_y);
		
		//find the closest pair: left or right?
		Point2D left_1 = left_closest_pair.getKey();
		Point2D left_2 = left_closest_pair.getValue();
		Point2D right_1 = right_closest_pair.getKey();
		Point2D right_2 = right_closest_pair.getValue();
		
		double left_distance = left_1.distance(left_2);
		double right_distance = right_1.distance(right_2);
		double best_delta = Math.min(left_distance, right_distance);
		
		Pair<Point2D, Point2D> closest_split_pair = closest_split_pair(p_x, p_y, best_delta);
		
		if ( closest_split_pair != null ) return closest_split_pair;
		else if ( left_distance < right_distance ) return left_closest_pair;
		
		return right_closest_pair;
	}
	/**
	 * computes the closest pair in the list p by comparing each pair in the list and computing the distance between them.
	 * @param p must contain at least two entries
	 * @return
	 */
	private static Pair<Point2D, Point2D> compute_closest_pair(List<Point2D> p) {
		if ( p.size() < 2 ) throw new RuntimeException(" invalid number of points to compare. This should never happen");		
		
		Pair<Point2D, Point2D> closest_pair = new Pair<Point2D, Point2D>(p.get(0), p.get(1)); 
		
		double best_distance = closest_pair.getKey().distance(closest_pair.getValue());
		
		//if more than two points in list, compute the closest pair
		if ( p.size() > 2 ) {
			for (Point2D point : p) {
				for (Point2D another_point : p) {
					if ( !another_point.equals(point) ) {
						double distance = point.distance(another_point);
						if (distance <= best_distance) closest_pair = new Pair<Point2D, Point2D>(point, another_point);
					}
				}
			}
		}		
		
		return closest_pair;
	}
	
	private static Pair<Point2D, Point2D> closest_split_pair(List<Point2D> p_x, List<Point2D> p_y, double delta)  {				
		
		double best_distance = delta;
		Pair<Point2D, Point2D> closest_split_pair = null;
		
		int mid = (p_x.size() / 2) - 1 ;
		double x_bar =p_x.get(mid).getX();
		List<Point2D> s_y = new ArrayList<Point2D>(); //list of pairs, sorted by y coordinate, whose x coordinates are within delta of the median x value (x_bar)
		
		//find all points that lie between (x_bar-delta) and (x_bar + delta), sorted by y coordinates
		for (Point2D point_in_y : p_y ) {
			if ( (point_in_y.getX() >= x_bar - delta) && (point_in_y.getX() <= x_bar + delta) ) {
				s_y.add(point_in_y);
			}
		}
		
		//iterate through s_y to find closest pair, if it is a split pair
		for (int i = 0; i < s_y.size(); i++) {
			Point2D point = s_y.get(i);
			for (int j = i + 1; j < Math.min( 7, s_y.size() ); j++ ) {
				Point2D another_point = s_y.get(j);
				double distance_between_these_two_points = point.distance(another_point);
				if ( distance_between_these_two_points < best_distance) {
					//found a closest split pair candidate
					best_distance = distance_between_these_two_points;
					closest_split_pair = new Pair<Point2D, Point2D>(point, another_point);
				}
			}
		}
		
		return closest_split_pair;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		List<Point2D> point_list = new ArrayList<Point2D> ();
		Map<Double, List<Point2D>> p_x = new HashMap<Double, List<Point2D>>(); //index points by x coordinates
		Map<Double, List<Point2D>> p_y = new HashMap<Double, List<Point2D>>(); //index points by y coordinates
		
		//read in points
		while ( in.hasNext() ) {
			String next = in.nextLine();
			String[] parsed_line = next.split(" ");
			Double x_coordinate = Double.parseDouble(parsed_line[0]);
			Double y_coordinate = Double.parseDouble(parsed_line[1]);
			Point2D point = new Point2D(x_coordinate, y_coordinate);
			point_list.add(point);
			
			//add points to p_x and p_y for later sorting by x and y coordinates
			if ( p_x.containsKey(x_coordinate) ) {
				List<Point2D> temp = p_x.get(x_coordinate);
				temp.add(point);
			} else {
				List<Point2D> temp = new ArrayList<Point2D>();
				temp.add(point);
				p_x.put(x_coordinate, temp );
			}
			
			if ( p_y.containsKey(y_coordinate) ) {
				List<Point2D> temp_y = p_y.get(y_coordinate);
				temp_y.add(point);
			} else {
				List<Point2D> temp_y = new ArrayList<Point2D>();
				temp_y.add(point);
				p_y.put(y_coordinate, temp_y);
			}
			
		}
		
		//sort the points by x coordinates and y coordinates
		List<Double> sorted_x_coordinates = new ArrayList<Double>(p_x.keySet());
		List<Double> sorted_y_coordinates = new ArrayList<Double>(p_y.keySet());		
		Collections.sort(sorted_x_coordinates);
		Collections.sort(sorted_y_coordinates);
		
		//the list of points sorted by x or y coordinates
		List<Point2D> points_sorted_by_x_coordinate = new ArrayList<Point2D>();
		List<Point2D> points_sorted_by_y_coordinate = new ArrayList<Point2D>();
		
		for ( Double x_coordinate : sorted_x_coordinates ) {
			points_sorted_by_x_coordinate.addAll(p_x.get(x_coordinate));
		}
		
		for ( Double y_coordinate : sorted_y_coordinates ) {
			points_sorted_by_y_coordinate.addAll(p_y.get(y_coordinate));
		}
		
		System.out.println(points_sorted_by_x_coordinate);
		System.out.println(points_sorted_by_y_coordinate);
		System.out.println(closest_pair(points_sorted_by_x_coordinate, points_sorted_by_y_coordinate));
	}
}
