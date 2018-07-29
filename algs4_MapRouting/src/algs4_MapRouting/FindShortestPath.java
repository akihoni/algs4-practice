package algs4_MapRouting;

import edu.princeton.cs.algs4.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class FindShortestPath {
	private int[] head; //存放边的头节点
	public double[] dis; //路径权值
	private boolean[] vis; //是否已经访问
	private Edge[] edges; //边数组
	public Point2D[] points; //坐标
	private static int cnt, V, E; //v:顶点数  e:边数

	private IndexMinPQ<Double> pq; //带索引的优先队列
	private static Scanner in;
	
	class Edge{
		private int to,nxt;//to:一条边的另一端点   nxt:下一条边所在数组下标位置
		private double w; //边权值
		public Edge(){}
	}
		    
	public FindShortestPath() {	//初始化数据
		pq = new IndexMinPQ<Double>(V + 1);
		head = new int[V + 1];
		dis = new double[V + 1];
		vis = new boolean[V + 1];
		edges = new Edge[(E + 1) * 2];
		points = new Point2D[V + 2];
		cnt = 0;
		for(int i = 0; i < V; ++i) { //将所有边的权值置为无穷
			vis[i] = false; 
			dis[i] = Integer.MAX_VALUE; //权值
			head[i] = -1;
		}
		int val = (E + 1) * 2;
		for(int i = 0; i < val; i++){
			edges[i] = new Edge();
		}
	}
	public void add_edges(int u,int v,double w){ //添加边u-v 权值w
		edges[cnt].to=v; edges[cnt].w=w; edges[cnt].nxt=head[u]; head[u]=cnt++;
		edges[cnt].to=u; edges[cnt].w=w; edges[cnt].nxt=head[v]; head[v]=cnt++;
	}
	public double dijkstra(int s,int t){ //找出s到t的路径
		int u,vv;
		dis[s]=0;
		pq.insert(s,0.0);
		while(!pq.isEmpty()) {
			u = pq.minIndex();
			pq.delete(u);
			if(vis[u])
				continue;
			if(u == t) //找到目的点
				return this.dis[t];
			vis[u] = true;
			for(int i = head[u];i != -1;i = edges[i].nxt) {
				vv=edges[i].to;
				if(vis[vv])	//防止出现死循环，如果已经访问过此节点，则跳过它
					continue;
				if(dis[u] != Integer.MAX_VALUE && dis[u] + edges[i].w < dis[vv]) { //找到了更短路径
					if(dis[vv] != Integer.MAX_VALUE) {
						dis[vv] = dis[u]+edges[i].w;
						pq.decreaseKey(vv, dis[vv]);
					}
					else { //如果权值为无穷，那么更新权值
						dis[vv] = dis[u]+edges[i].w;
						pq.insert(vv, dis[vv]);
					}
				}

			}
		}
		return (double)Integer.MIN_VALUE; //目的点不可达
	}
	public void dijkstra(int s){ //由源点s出发的最短路径
		int u, vv;
		dis[s] = 0;
		pq.insert(s, (double)0);
		while(!pq.isEmpty()) {
			u = pq.delMin();
			if(vis[u]) 
				continue;
			vis[u] = true;
			for(int i = head[u]; i != -1;i = edges[i].nxt) {
				vv = edges[i].to;
				if(vis[vv])
					continue;
				if(dis[u] != Integer.MAX_VALUE && dis[u] + edges[i].w < dis[vv]) {
					if(dis[vv] != Integer.MAX_VALUE) {
						dis[vv] = dis[u] + edges[i].w;
						pq.decreaseKey(vv, dis[vv]);
					}
					else {
						dis[vv] = dis[u] + edges[i].w;
						pq.insert(vv, dis[vv]);
					}
				}
			}
		}
	}
	public static void input() throws Exception{
		in = new Scanner(new BufferedInputStream(new FileInputStream(new File("usa.txt"))));
		V = in.nextInt(); //顶点数;
		E = in.nextInt(); //边数
		System.out.println("V = " + V + " E = " + E);
		}
	public static void main(String[] args) throws Exception {
		input();
		FindShortestPath myShortestPath = new FindShortestPath();
		for(int i = 0; i < V; i++) {	//读入各顶点坐标（x,y）
			in.nextInt();
			double x = in.nextDouble();
			double y = in.nextDouble();
			myShortestPath.points[i] = new Point2D(x, y);
		}
		int m = E, uu, vv;
		double ww;
		while(m-- != 0) {	//读入各个边
			uu = in.nextInt();
			vv = in.nextInt();
			ww = myShortestPath.points[uu].distanceTo(myShortestPath.points[vv]);//计算权值
			myShortestPath.add_edges(uu,vv,ww);
		}
		long startTime = System.currentTimeMillis();
		int s = 0, t = 20000;
		System.out.println("起点：" + s + " 终点:" + t + " 最短距离:" + myShortestPath.dijkstra(s,t));
		long endTime = System.currentTimeMillis();
		System.out.printf("运行时间 : %.3f s\n", (endTime - startTime) / 1000f);

		s = 0;//设置源点
		startTime = System.currentTimeMillis();
		System.out.println("\n起点" + s + "到所有其他节点最短距离");
		myShortestPath.dijkstra(s);
		endTime = System.currentTimeMillis();
		System.out.printf("运行时间 : %.3f s\n", (endTime - startTime) / 1000f);
		int num=10;
		System.out.println("起点"+s+"到编号"+num+"前的节点最短距离：");
		for(int i=0;i<=num;++i) {
			System.out.println(myShortestPath.dis[i]+"  ");
		}
	}
}
