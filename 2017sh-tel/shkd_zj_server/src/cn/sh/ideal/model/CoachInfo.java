package cn.sh.ideal.model;

import java.util.Date;

public class CoachInfo {
    private Integer id;

    private Integer coachmainId;

    private String coachproject;

    private String type;

    private String coachmethod;

    private String instructions;

    private String coachrecord;

    private String starttime;

    private String endtime;

    private String comforablestate;

    private String uncomforable;

    private String describe;

    private String createtime;

    private String createid;

    private String modiftime;

    private String modifid;

    private String valid;

    private String state;

    private String passuserid;

    private String passtime;
    private String acceptorWorkId; //受理员工号
    
    
  

	public String getAcceptorWorkId() {
		return acceptorWorkId;
	}

	public void setAcceptorWorkId(String acceptorWorkId) {
		this.acceptorWorkId = acceptorWorkId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCoachmainId() {
        return coachmainId;
    }

    public void setCoachmainId(Integer coachmainId) {
        this.coachmainId = coachmainId;
    }

    public String getCoachproject() {
        return coachproject;
    }

    public void setCoachproject(String coachproject) {
        this.coachproject = coachproject == null ? null : coachproject.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getCoachmethod() {
        return coachmethod;
    }

    public void setCoachmethod(String coachmethod) {
        this.coachmethod = coachmethod == null ? null : coachmethod.trim();
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions == null ? null : instructions.trim();
    }

    public String getCoachrecord() {
        return coachrecord;
    }

    public void setCoachrecord(String coachrecord) {
        this.coachrecord = coachrecord == null ? null : coachrecord.trim();
    }


    public String getComforablestate() {
        return comforablestate;
    }

    public void setComforablestate(String comforablestate) {
        this.comforablestate = comforablestate == null ? null : comforablestate.trim();
    }

    public String getUncomforable() {
        return uncomforable;
    }

    public void setUncomforable(String uncomforable) {
        this.uncomforable = uncomforable == null ? null : uncomforable.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

  
    public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getCreateid() {
        return createid;
    }

    public void setCreateid(String createid) {
        this.createid = createid == null ? null : createid.trim();
    }

 
    public String getModifid() {
        return modifid;
    }

    public void setModifid(String modifid) {
        this.modifid = modifid == null ? null : modifid.trim();
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid == null ? null : valid.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getPassuserid() {
        return passuserid;
    }

    public void setPassuserid(String passuserid) {
        this.passuserid = passuserid == null ? null : passuserid.trim();
    }

	public String getModiftime() {
		return modiftime;
	}

	public void setModiftime(String modiftime) {
		this.modiftime = modiftime;
	}

	public String getPasstime() {
		return passtime;
	}

	public void setPasstime(String passtime) {
		this.passtime = passtime;
	}

    
}