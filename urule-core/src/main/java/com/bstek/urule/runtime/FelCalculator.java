package com.bstek.urule.runtime;

import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
/**
 * 
 * @author duhongming
 *
 */
public class FelCalculator {
	
	private static FelEngine fel = new FelEngineImpl(); 
	private static FelContext ctx = fel.getContext();
	
	public static Double[][] getResult(String expression, Double[] data){
		Double[][] resultData = new Double[data.length][2];
        for(int i=0;i<data.length;i++){
        	ctx.set("x", data[i]);
        	Object evalDouble = fel.eval(expression,ctx);    
        	resultData[i][0]=data[i];
        	resultData[i][1]=Double.parseDouble(evalDouble.toString());
		}
        return resultData;
	}
	
	public static WeightedObservedPoints getResult(String expression, WeightedObservedPoints points){
		WeightedObservedPoints otherPoints = new WeightedObservedPoints();
        for(WeightedObservedPoint w:points.toList()){
        	ctx.set("x", w.getX());
        	Object evalDouble = fel.eval(expression,ctx); 
        	otherPoints.add(w.getX(), Double.parseDouble(evalDouble.toString()));
		}
        return otherPoints;
	}
	
	public void test(){
		//坐标1
		double[] curve1 = { 1, 4, 5, 7, 8, 9, 6, 5, 3, 2, 1 }; 
		//坐标2
		double[] curve2 = { 1, 1.2, 8, 20, 9, 8, 6, 5, 3, 2, 1 }; 
		double x = 0, y = 0, z = 0;
		for (int i = 0; i < curve1.length; i++) {
		x += curve1[i] * curve1[i];
		y += curve2[i] * curve2[i];
		z += curve1[i] * curve2[i];
		}
		x = Math.sqrt(x);
		y = Math.sqrt(y);
		System.out.println(z / (x * y));
	}
}
