package algs4_percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[] matrix;
	private int row, col;
	private WeightedQuickUnionUF uf, ufTop;
	private boolean isPercolated;

	public Percolation(int N){		
		/* 创建boolean类型的N*N矩阵，且所有方格都是封锁的 */
		if(N < 1)
			throw new IllegalArgumentException("Illegal Argument");
		isPercolated = false;
		uf = new WeightedQuickUnionUF(N * N + 2); /* 包含两个虚拟节点 */
		ufTop = new WeightedQuickUnionUF(N * N + 1); /* 只包含虚拟头结点，isFull()使用它来判断 */
		row = N;
		col = N;
		matrix = new boolean[N * N + 1]; /* 约定第一个元素的位置是(1,1)，所以数组应该有N*N+1个元素 */
	}
	public void validate(int i, int j){
		/* 用于抛出例外 */
		if(i < 1 || i > row)
			throw new IndexOutOfBoundsException("row index i out of bounds");
		if(j < 1 || j > col)
			throw new IndexOutOfBoundsException("col index j out of bounds");
	}
	public void open(int i, int j){
		/* 如果(row i, column j)方格没有打开，那么打开此方格 */
		validate(i, j);
		int num = (i - 1) * col + j; /* 计算元素(i,j)在数组matrix[]中的位置 */
		matrix[num] = true;
		if(i == 1){
			/* 如果是第一行的元素，那么还要将它和虚拟头节点连接起来 */
			uf.union(num, 0);
			ufTop.union(num, 0);
		}
		if(i == row){
			/* 如果是最后一行的元素，那么还要将它和虚拟尾节点连接起来 */
			uf.union(num, row * col + 1);
		}
		int[] r = {1, -1, 0, 0};
		int[] c = {0, 0, 1, -1};
		for(int k = 0; k < 4; k++){
			int posX = r[k] + i;
			int posY = c[k] + j;
			if(posX >= 1 && posX <= row && posY >=1 && posY <= col && isOpen(posX, posY)){
				uf.union(num, (posX - 1) * col + posY);
				ufTop.union(num, (posX - 1) * col + posY);
			}
		}
	}
	public boolean isOpen(int i, int j){
		/* 判断方格(row i, column j)是否打开 */
		validate(i, j);
		return matrix[(i - 1) * col + j];
	}
	public boolean isFull(int i, int j){
		/* 判断方格(row i, column j)是否能被渗透到 */
		validate(i, j);
		int number = (i - 1) * col + j;
		if(ufTop.find(number) == ufTop.find(0))
			return true;
		return false;
	}
	public boolean percolates(){
		/* 判断整个系统是否能够渗透 */
		if(isPercolated == true)
			return true;
		if(uf.connected(0, row * col + 1)) /* 如果头尾虚拟节点连通，说明可以渗透 */
			return true;
		return false;
	}
	public static void main(String[] args){
		Percolation per = new Percolation(2);
		per.open(1, 1);
		per.open(1, 2);
		per.open(2, 1);
		System.out.println(per.percolates());
	}
}