package com.bstek.urule.runtime.builtinaction;

import com.bstek.urule.runtime.FelCalculator;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.util.FastMath;

import java.util.logging.Logger;
/**
 * 
 * @author duhongming
 *
 */
public class PolynomialCurveAction{
	
	/**
	 * 多项式最高次幂为100,因为符合正态分布，所以程序的R^2小于零就停止程序运行，寻找最优解；
	 */
	private static final int DEGREE=100;	
	private Logger log = Logger.getLogger("com.hrhx.duhongming.calculate.PolynomialCurveCore");
	
	private String getPolynomialCurveExpresion(Double[][] dataPoints,int degree){
		//创建并初始化输入数据
		//将原始的x-y数据序列合成带权重的观察点数据序列
		//将x-y数据元素调用points.add(x[i], y[i])加入到观察点序列中
		WeightedObservedPoints points = new WeightedObservedPoints();
		for(int i=0;i<dataPoints.length;i++){
			points.add(dataPoints[i][0], dataPoints[i][1]);  
		}
		//log.info(degree+"阶状态导入观察数据成功！");
		
		// degree 指定多项式阶数 
		PolynomialCurveFitter fitter = PolynomialCurveFitter.create(DEGREE);
		//log.info(degree+"阶状态指定多项式阶数成功！");
		
		// 曲线拟合，结果保存于双精度数组中，由常数项至最高次幂系数排列
        double[] result = fitter.fit(points.toList());
        //log.info(degree+"阶状态曲线拟合成功，由常数项至最高次幂系数排列！");
        
        String expression="";
        //String humanExpression="";
        for(int j=0;j<result.length;j++){
        	if(j==0){
        		expression+=result[j];
        	}else{        		
        		expression+="+("+result[j]+"*$('Math').pow(x,"+j+"))";
        	}
    		//humanExpression+="+("+result[j]+"*x^"+j+")";
        }
        //log.info("fel计算机表达式分析："+expression);
        //log.info("human人类表达式分析："+humanExpression);
        return expression;
	}
	
	private String getPolynomialCurveExpresion(WeightedObservedPoints observedPoints,int degree){
		//log.info(degree+"阶状态导入观察数据成功！");
		
		// degree 指定多项式阶数 
		PolynomialCurveFitter fitter = PolynomialCurveFitter.create(DEGREE);
		
		//log.info(degree+"阶状态指定多项式阶数成功！");
		// 曲线拟合，结果保存于双精度数组中，由常数项至最高次幂系数排列
        double[] result = fitter.fit(observedPoints.toList());
        //log.info(degree+"阶状态曲线拟合成功，由常数项至最高次幂系数排列！");
        
        String expression="";
        //String humanExpression="";
        for(int j=0;j<result.length;j++){
        	if(j==0){
        		expression+=result[j];
        	}else{        		
        		expression+="+("+result[j]+"*$('Math').pow(x,"+j+"))";
        	}
    		//humanExpression+="+("+result[j]+"*x^"+j+")";
        }
        //log.info("fel计算机表达式分析："+expression);
        //log.info("human人类表达式分析："+humanExpression);
        return expression;
	}
	
	public String polynomialCurveExpresionAll(WeightedObservedPoints observedPoints){
		String selExpression="";
		double selR2=0;
		for(int i=1;i<DEGREE;i++){
			String expression = getPolynomialCurveExpresion(observedPoints,i);
			//预测数据
			WeightedObservedPoints predictedPoints= FelCalculator.getResult(expression, observedPoints);
			double r2 = getR2(observedPoints,predictedPoints);
			if(r2>=selR2){
				selExpression=expression;
				selR2=r2;
			}else{
				log.info("表达式最优即R^2="+selR2+"最接近1为："+selExpression);
				return selExpression;
			}
		}
		log.info("表达式最优即R^2最小为："+selExpression);
		return selExpression;
	}
	public String polynomialCurveExpresionAll(Double[][] dataPoints){
		//观测变量X
		Double[] observeX = getX(dataPoints);
		String selExpression="";
		double selR2=0;
		for(int i=1;i<DEGREE;i++){
			String expression = getPolynomialCurveExpresion(dataPoints,i);
			//预测数据
			Double[][] predictedValue = FelCalculator.getResult(expression, observeX);
			double r2 = getR2(dataPoints,predictedValue);
			if(r2>=selR2){
				selExpression=expression;
				selR2=r2;
			}else{
				log.info("表达式最优即R^2="+selR2+"最接近1为："+selExpression);
				return selExpression;
			}
		}
		//log.info("表达式最优即R^2最小为："+selExpression);
		return selExpression;
	}
	
	/**
	 * 得到观察值Y和预测值Y计算R^2
	 * @param dataPoints
	 * @param predictedValue
	 * @return
	 */
	private Double[][] getObserveYAndPredictedY(Double[][] dataPoints,Double[][] predictedValue){
		/**
		 * 标准数组长度判断写法
		 */
		int len1 = dataPoints.length;
        int len2 = predictedValue.length;
        int lim = Math.min(len1, len2);
        
		Double[][] observeYAndPredictedY = new Double[lim][2];
		for(int i = 0;i < lim;i++){
			observeYAndPredictedY[i][0] = dataPoints[i][1];
			observeYAndPredictedY[i][1] = predictedValue[i][1];
		}
		return observeYAndPredictedY;
	}
	
	/**
	 * 计算R^2
	 * @param observedPoints
	 * @param predictedPoints
	 * @return
	 */
	private double getR2(WeightedObservedPoints observedPoints,WeightedObservedPoints predictedPoints){
		/**
		 * 标准数组长度判断写法
		 */
		int len1 = observedPoints.toList().size();
        int len2 = predictedPoints.toList().size();
        int lim = Math.min(len1, len2);
        
		Double observeY=0.0;
		for(WeightedObservedPoint w : observedPoints.toList()){
			observeY += w.getY();
		}
		Double observeYAvg = observeY/lim;
		//残差平方和
		Double sstotal = 0.0;
		//总平方和
		Double ssresid = 0.0;
		for(int i=0;i<lim;i++){
			WeightedObservedPoint observedPoint = observedPoints.toList().get(i);
			WeightedObservedPoint predictedPoint = predictedPoints.toList().get(i);
			sstotal += FastMath.pow(observedPoint.getY()-predictedPoint.getY(),2);
			ssresid += FastMath.pow(observedPoint.getY()-observeYAvg,2);
		}
		return 1-sstotal/ssresid;
	}
	
	/**
	 * 计算R^2
	 * @param dataPoints
	 * @param predictedValue
	 * @return
	 */
	private double getR2(Double[][] dataPoints,Double[][] predictedValue){
		Double[][] observeYAndPredictedY = getObserveYAndPredictedY(dataPoints,predictedValue);
		Double observeY=0.0;
		for(int i = 0;i < observeYAndPredictedY.length;i++){
			observeY += observeYAndPredictedY[i][0];
		}
		Double observeYAvg = observeY/observeYAndPredictedY.length;
		//残差平方和
		Double sstotal = 0.0;
		for(int i=0;i<observeYAndPredictedY.length;i++){
			sstotal += FastMath.pow(observeYAndPredictedY[i][0]-observeYAndPredictedY[i][1],2);
		}
		//总平方和
		Double ssresid = 0.0;
		for(int i = 0;i < observeYAndPredictedY.length;i++){
			ssresid += FastMath.pow(observeYAndPredictedY[i][0]-observeYAvg,2);
		}
		return 1-sstotal/ssresid;
	}
	
	/**
	 * 观察值X轴数据
	 * @param dataPoints
	 * @return
	 */
	private Double[] getX(Double[][] dataPoints){
		Double[] x = new Double[dataPoints.length];
		for(int i=0;i<dataPoints.length;i++){
			x[i] = dataPoints[i][0];  
		}
		return x;
	}
	
}
