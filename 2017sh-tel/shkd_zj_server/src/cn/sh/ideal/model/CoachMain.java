package cn.sh.ideal.model;

import java.util.Date;

public class CoachMain {
    private Integer coachmainId;

    private String pass;

    private String passinfo;

    private String passuserid;

    private String passtime;

    private String coachproject;

    private String username;

    private Integer userid;

    private String coachgroup;

    private Integer coachgroupid;

    private String starttime;

    private String endtime;

    private String type;

    private Integer usergroupid;

    private String usergroupname;

    private Integer createid;

    private String createtime;

    private Integer modifid;

    private String modiftime;

    private String isValid;

    private String sumCoachproject;

    private String sumSummarize;

    private String sumImprove;

    private String sumSuggest;

    private Integer sumCreateUserId;

    private String sumCreateTime;
    
    private String qcUserName; //质检员姓名
    private String AcceptorWorkId; //受理员工号
    private String remaker;
    private String isgj;
    private String qcremark;
    
    private String teamname;
    private String teamvalue;

    
    
    public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public String getTeamvalue() {
		return teamvalue;
	}

	public void setTeamvalue(String teamvalue) {
		this.teamvalue = teamvalue;
	}

	public String getIsgj() {
		return isgj;
	}

	public void setIsgj(String isgj) {
		this.isgj = isgj;
	}

	public String getQcremark() {
		return qcremark;
	}

	public void setQcremark(String qcremark) {
		this.qcremark = qcremark;
	}

	

	public String getRemaker() {
		return remaker;
	}

	public void setRemaker(String remaker) {
		this.remaker = remaker;
	}

	public String getQcUserName() {
		return qcUserName;
	}

	public void setQcUserName(String qcUserName) {
		this.qcUserName = qcUserName;
	}

	public String getAcceptorWorkId() {
		return AcceptorWorkId;
	}

	public void setAcceptorWorkId(String acceptorWorkId) {
		AcceptorWorkId = acceptorWorkId;
	}

	public Integer getCoachmainId() {
        return coachmainId;
    }

    public void setCoachmainId(Integer coachmainId) {
        this.coachmainId = coachmainId;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass == null ? null : pass.trim();
    }

    public String getPassinfo() {
        return passinfo;
    }

    public void setPassinfo(String passinfo) {
        this.passinfo = passinfo == null ? null : passinfo.trim();
    }

    public String getPassuserid() {
        return passuserid;
    }

    public void setPassuserid(String passuserid) {
        this.passuserid = passuserid == null ? null : passuserid.trim();
    }

    public String getCoachproject() {
        return coachproject;
    }

    public void setCoachproject(String coachproject) {
        this.coachproject = coachproject == null ? null : coachproject.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getCoachgroup() {
        return coachgroup;
    }

    public void setCoachgroup(String coachgroup) {
        this.coachgroup = coachgroup == null ? null : coachgroup.trim();
    }

    public Integer getCoachgroupid() {
        return coachgroupid;
    }

    public void setCoachgroupid(Integer coachgroupid) {
        this.coachgroupid = coachgroupid;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getUsergroupid() {
        return usergroupid;
    }

    public void setUsergroupid(Integer usergroupid) {
        this.usergroupid = usergroupid;
    }

    public String getUsergroupname() {
        return usergroupname;
    }

    public void setUsergroupname(String usergroupname) {
        this.usergroupname = usergroupname == null ? null : usergroupname.trim();
    }

   
    
    

	public Integer getCreateid() {
		return createid;
	}

	public void setCreateid(Integer createid) {
		this.createid = createid;
	}

	public Integer getModifid() {
        return modifid;
    }

    public void setModifid(Integer modifid) {
        this.modifid = modifid;
    }


    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid == null ? null : isValid.trim();
    }

    public String getSumCoachproject() {
        return sumCoachproject;
    }

    public void setSumCoachproject(String sumCoachproject) {
        this.sumCoachproject = sumCoachproject == null ? null : sumCoachproject.trim();
    }

    public String getSumSummarize() {
        return sumSummarize;
    }

    public void setSumSummarize(String sumSummarize) {
        this.sumSummarize = sumSummarize == null ? null : sumSummarize.trim();
    }

    public String getSumImprove() {
        return sumImprove;
    }

    public void setSumImprove(String sumImprove) {
        this.sumImprove = sumImprove == null ? null : sumImprove.trim();
    }

    public String getSumSuggest() {
        return sumSuggest;
    }

    public void setSumSuggest(String sumSuggest) {
        this.sumSuggest = sumSuggest == null ? null : sumSuggest.trim();
    }

    public Integer getSumCreateUserId() {
        return sumCreateUserId;
    }

    public void setSumCreateUserId(Integer sumCreateUserId) {
        this.sumCreateUserId = sumCreateUserId;
    }

	public String getPasstime() {
		return passtime;
	}

	public void setPasstime(String passtime) {
		this.passtime = passtime;
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

	public String getModiftime() {
		return modiftime;
	}

	public void setModiftime(String modiftime) {
		this.modiftime = modiftime;
	}

	public String getSumCreateTime() {
		return sumCreateTime;
	}

	public void setSumCreateTime(String sumCreateTime) {
		this.sumCreateTime = sumCreateTime;
	}

    
}