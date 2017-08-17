package cn.sh.ideal.model;

public class tExamExampaper {
    private Integer pkAutoId;

    private Integer fkExamId;

    private Integer fkExampaperId;
    
    public Integer getPkAutoId() {
        return pkAutoId;
    }

    public void setPkAutoId(Integer pkAutoId) {
        this.pkAutoId = pkAutoId;
    }
    
    public Integer getFkExamId() {
        return fkExamId;
    }

    public void setFkExamId(Integer fkExamId) {
        this.fkExamId = fkExamId;
    }

    public Integer getFkExampaperId() {
        return fkExampaperId;
    }

    public void setFkExampaperId(Integer fkExampaperId) {
        this.fkExampaperId = fkExampaperId;
    }
}