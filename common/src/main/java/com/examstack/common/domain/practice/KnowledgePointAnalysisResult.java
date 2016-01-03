package com.examstack.common.domain.practice;

import java.util.List;

public class KnowledgePointAnalysisResult {
	private int knowledgePointId;
	private String knowledgePointName;
	private List<TypeAnalysis> typeAnalysis;
	private float finishRate;

	public int getKnowledgePointId() {
		return knowledgePointId;
	}

	public void setKnowledgePointId(int knowledgePointId) {
		this.knowledgePointId = knowledgePointId;
	}

	public String getKnowledgePointName() {
		return knowledgePointName;
	}

	public void setKnowledgePointName(String knowledgePointName) {
		this.knowledgePointName = knowledgePointName;
	}

	public List<TypeAnalysis> getTypeAnalysis() {
		return typeAnalysis;
	}

	public void setTypeAnalysis(List<TypeAnalysis> typeAnalysis) {
		this.typeAnalysis = typeAnalysis;
	}

	public float getFinishRate() {
		return finishRate;
	}

	public void setFinishRate(float finishRate) {
		this.finishRate = finishRate;
	}
}
