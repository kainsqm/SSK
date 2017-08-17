package cn.sh.ideal.model;

public class CoachServer {
    private Integer sid;

    private Integer coachmainid;

    private String specificationlanguage;

    private String politetoneoofvoice;

    private String abilitytocommunicate;

    private String objectionhandling;

    private String flowstandard;

    private String deadlyerror;

    private String marketingskills;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getCoachmainid() {
        return coachmainid;
    }

    public void setCoachmainid(Integer coachmainid) {
        this.coachmainid = coachmainid;
    }

    public String getSpecificationlanguage() {
        return specificationlanguage;
    }

    public void setSpecificationlanguage(String specificationlanguage) {
        this.specificationlanguage = specificationlanguage == null ? null : specificationlanguage.trim();
    }

    public String getPolitetoneoofvoice() {
        return politetoneoofvoice;
    }

    public void setPolitetoneoofvoice(String politetoneoofvoice) {
        this.politetoneoofvoice = politetoneoofvoice == null ? null : politetoneoofvoice.trim();
    }

    public String getAbilitytocommunicate() {
        return abilitytocommunicate;
    }

    public void setAbilitytocommunicate(String abilitytocommunicate) {
        this.abilitytocommunicate = abilitytocommunicate == null ? null : abilitytocommunicate.trim();
    }

    public String getObjectionhandling() {
        return objectionhandling;
    }

    public void setObjectionhandling(String objectionhandling) {
        this.objectionhandling = objectionhandling == null ? null : objectionhandling.trim();
    }

    public String getFlowstandard() {
        return flowstandard;
    }

    public void setFlowstandard(String flowstandard) {
        this.flowstandard = flowstandard == null ? null : flowstandard.trim();
    }

    public String getDeadlyerror() {
        return deadlyerror;
    }

    public void setDeadlyerror(String deadlyerror) {
        this.deadlyerror = deadlyerror == null ? null : deadlyerror.trim();
    }

    public String getMarketingskills() {
        return marketingskills;
    }

    public void setMarketingskills(String marketingskills) {
        this.marketingskills = marketingskills == null ? null : marketingskills.trim();
    }
}