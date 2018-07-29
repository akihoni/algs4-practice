package algs4_compare;

import java.io.IOException;
import java.util.*;

import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.introcs.StdRandom;

public class IS {
	// 插入排序
	static double TimeUsed = 0.0, SpaceUsed = 0.0, 
	        totalTime = 0.0, totalSpace = 0.0,
			AveTime = 0.0, AveSpace = 0.0;
	
	static Integer[] comp = new Integer[1000];
	
	static public void CompIS() {
		System.out.println("IS");
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 1000; i++)
				comp[i] = StdRandom.uniform(100);
			Runtime run = Runtime.getRuntime();
			run.gc();
			long startMem = run.totalMemory() - run.freeMemory();
			long startTime = System.currentTimeMillis();
			/* -------------------------------------------------- */
			Insertion.sort(comp);
			/* -------------------------------------------------- */	
			long endTime = System.currentTimeMillis();
			long endMem = run.totalMemory() - run.freeMemory();
			TimeUsed = endTime - startTime;
			SpaceUsed = endMem - startMem;
			System.out.println("第" + (j+1) + "次 运行时间：" + TimeUsed
					+ "ms    占用内存：" + (SpaceUsed / 1000) + "kilo bytes");
			totalTime += TimeUsed;
			totalSpace += SpaceUsed;
		}
		AveTime = totalTime / 10;
		AveSpace = totalSpace / 10;
		System.out.println("IS 平均运行时间：" + AveTime + "ms    平均占用内存：" + AveSpace + "byte");
	}
	public static void main(String[] args) {
		CompIS();
	}
}
