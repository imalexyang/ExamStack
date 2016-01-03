package com.examstack.common.util;

import java.util.ArrayList;
import java.util.List;

import com.examstack.common.domain.exam.Paper;


public class Population {
	private int populationSize;
	private List<Paper> content;

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public List<Paper> getContent() {
		return content;
	}

	public void setContent(List<Paper> content) {
		this.content = content;
	}

	// 轮盘赌对种群进行选择（适应度）
	private List<Integer> choosePager() {
		// 选择结果（content的index）
		List<Integer> nextGeneration = new ArrayList<Integer>();
		double fitnessSum = 0;
		List<Double> p = new ArrayList<Double>();

		// 适应度总和
		for (Paper paper : content) {
			fitnessSum += paper.getFitness();
		}

		// 计算每个个体适应度占比
		for (Paper paper : content) {
			p.add(paper.getFitness() / fitnessSum);
		}

		// 轮盘选择，根据占比划分区域，随机数落在哪个区域，哪个就被选择
		for (int i = 0; i < content.size(); i++) {
			double radom = Math.random();
			int count = 0;
			// 第一个个体的占比，即[0，占比)
			double area = p.get(0);
			// 如果随机数大于个体所在区域的最大值，则进到下一个区域继续计算
			while (radom > area) {
				count++;
				/*
				 * if(count == p.size()){ count --; break; }
				 */
				area += p.get(count);
			}
			// 此时count获胜
			nextGeneration.add(count);
		}
		return nextGeneration;
	}
	// 交叉
	// 应避免相同的染色体进行交叉

	// 变异
}
