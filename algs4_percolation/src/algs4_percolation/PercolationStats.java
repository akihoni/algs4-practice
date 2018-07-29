package algs4_percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.introcs.StdRandom;
import java.util.*;

public class PercolationStats {
	private double[] x;
	private int expt;
	public PercolationStats(int N, int T){
		/* 在N*N矩阵上进行T次独立实验 */
		if(N <= 0 || T <= 0)
			throw new IllegalArgumentException("Illegal Argument");
		x = new double[T + 1];
		expt = T;
		for(int i = 0; i < T; i++){
			Percolation perc = new Percolation(N);
			boolean[] isThisLineOpen = new boolean[N + 1];
			int numOfOpenLine = 0;
			while(true){
				int posX, posY;
				do{
					posX = StdRandom.uniform(N) + 1;
					posY = StdRandom.uniform(N) + 1;
				}while(perc.isOpen(posX, posY));
				perc.open(posX, posY);
				x[i] += 1;
				if(!isThisLineOpen[posX]){
					isThisLineOpen[posX] = true;
					numOfOpenLine++;
				}
				if(numOfOpenLine == N){
					if(perc.percolates())
						break;
				}
			}
			x[i] = x[i] / (double) (N * N);
		}
	}
	public double mean(){
		/* 渗透模型的阈值的平均数 */
		double mu = 0.0;
		for(int i = 0; i < expt; i++)
			mu += x[i];
		return mu / (double) expt;
	}
	public double stddev(){
		/* 渗透模型的阈值的标准差 */
		if(expt == 1)
			return Double.NaN;
		double sigma = 0.0;
		double mu = mean();
		for(int i = 0; i < expt; i++)
			sigma += (x[i] - mu) * (x[i] - mu);
		return Math.sqrt(sigma / (double) (expt - 1));
	}
	public double confidenceLo(){
		/* 返回置信区间下界 */
		double mu = mean();
		double sigma = stddev();
		return mu + 1.96 * sigma / Math.sqrt(expt);
	}
	public double confidenceHi(){
		/* 返回置信区间上界 */
		double mu = mean();
		double sigma = stddev();
		return mu + 1.96 * sigma / Math.sqrt(expt);
	}
	public static void main(String[] args) {
		Date startDate = new Date();
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats perstat = new PercolationStats(N, T);
		System.out.println("N = " + N);
		System.out.println("T = " + T);
		System.out.println("mean = " + perstat.mean());
		System.out.println("stddev = " + perstat.stddev());
		System.out.println("confidenceLo = " + perstat.confidenceLo()); 
		System.out.println("confidenceHi = " + perstat.confidenceHi());
		Date endDate = new Date();
		System.out.println("运行时间：" + (endDate.getTime() - startDate.getTime()) + " ms");
	}

}
