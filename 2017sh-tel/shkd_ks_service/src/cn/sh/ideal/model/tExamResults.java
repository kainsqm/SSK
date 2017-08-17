package cn.sh.ideal.model;

public class tExamResults {
    private Integer pkAutoId;

    private Integer fkEEpExamineeId;

    private Integer fkQuestionsId;

    private String resultsAnswer;

    private String resultsCorrectAnswer;

    private Integer resultsScore;

    private Integer zuhuId;

    public Integer getPkAutoId() {
        return pkAutoId;
    }

    public void setPkAutoId(Integer pkAutoId) {
        this.pkAutoId = pkAutoId;
    }

    public Integer getFkEEpExamineeId() {
        return fkEEpExamineeId;
    }

    public void setFkEEpExamineeId(Integer fkEEpExamineeId) {
        this.fkEEpExamineeId = fkEEpExamineeId;
    }

    public Integer getFkQuestionsId() {
        return fkQuestionsId;
    }

    public void setFkQuestionsId(Integer fkQuestionsId) {
        this.fkQuestionsId = fkQuestionsId;
    }

    public String getResultsAnswer() {
        return resultsAnswer;
    }

    public void setResultsAnswer(String resultsAnswer) {
        this.resultsAnswer = resultsAnswer == null ? null : resultsAnswer.trim();
    }

    public String getResultsCorrectAnswer() {
        return resultsCorrectAnswer;
    }

    public void setResultsCorrectAnswer(String resultsCorrectAnswer) {
        this.resultsCorrectAnswer = resultsCorrectAnswer == null ? null : resultsCorrectAnswer.trim();
    }

    

    public Integer getResultsScore() {
		return resultsScore;
	}

	public void setResultsScore(Integer resultsScore) {
		this.resultsScore = resultsScore;
	}

	public Integer getZuhuId() {
        return zuhuId;
    }

    public void setZuhuId(Integer zuhuId) {
        this.zuhuId = zuhuId;
    }
}