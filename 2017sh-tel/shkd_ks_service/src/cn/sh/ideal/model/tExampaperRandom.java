package cn.sh.ideal.model;
/**
 * 模板试卷Bean
 * @author Administrator
 *
 */
public class tExampaperRandom {
    private Integer pkAutoId;	//主键ID

    private Integer fkCodetypeId;	//业务分类ID

    private String queType;		//考题类型

    private Integer queCount;	//题目数量
    
    private Integer queScore;	//分数

    private Integer fkExampaperId;	//试卷ID

    private String enabled;		//是否有效	0-无效 1-有效

    private String queNandu;	//难度	1-难 2-中 3-易

    private Integer zuhuId;		///租户Id

    public Integer getPkAutoId() {
        return pkAutoId;
    }

    public void setPkAutoId(Integer pkAutoId) {
        this.pkAutoId = pkAutoId;
    }

    public Integer getFkCodetypeId() {
        return fkCodetypeId;
    }

    public void setFkCodetypeId(Integer fkCodetypeId) {
        this.fkCodetypeId = fkCodetypeId;
    }

    public String getQueType() {
        return queType;
    }

    public void setQueType(String queType) {
        this.queType = queType == null ? null : queType.trim();
    }

    public Integer getQueCount() {
        return queCount;
    }

    public void setQueCount(Integer queCount) {
        this.queCount = queCount;
    }

    public Integer getQueScore() {
        return queScore;
    }

    public void setQueScore(Integer queScore) {
        this.queScore = queScore;
    }

    public Integer getFkExampaperId() {
        return fkExampaperId;
    }

    public void setFkExampaperId(Integer fkExampaperId) {
        this.fkExampaperId = fkExampaperId;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    public String getQueNandu() {
        return queNandu;
    }

    public void setQueNandu(String queNandu) {
        this.queNandu = queNandu == null ? null : queNandu.trim();
    }

    public Integer getZuhuId() {
        return zuhuId;
    }

    public void setZuhuId(Integer zuhuId) {
        this.zuhuId = zuhuId;
    }
}