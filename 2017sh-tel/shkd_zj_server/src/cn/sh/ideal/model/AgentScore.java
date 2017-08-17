package cn.sh.ideal.model;

public class AgentScore {
	
				private int id; //序号
				private String name; //姓名
				private String smailworkid;//工号
				private String startdate; //开始日期
				private String enddate; //结束日期
				private int is112;//112
				private int iscw;//c网
				private int ycl; //预处理
				private int gk; //管控
				private int lxcw;//联系信息输入错误
				private int cp;//错派
				private int lp;//漏派
				private int ywbs;//业务不熟处理不当
				private int ywbl;//有闻不录
				private int wfl; //号码未复录
				private int ywbgf; //用户不规范
				private int tdsy; //受理中态度生硬
				private int flxzcw;//分类选择错误
				private int wfxc;//违反现场管理
				private int bsbgf;//表式（原始记录）填写不规范
				private int jndmc;//受理/测试/结案代码错
				private int pdcs;//派单超时
				private int czbgf;//操作不规范
				private int yxbg;//用心服务不够
				private int qt;//其他
				private int yhts;//用户投诉
				private int yhby;//用户表扬
				private int bzxxc;//备注信息错
				private int ydbgf;//129应答不规范
				private int gkcp;//管控错派
				private int gkfl;//管控分类
				private int lgk;//漏管控/点评/关注
				private int ywgkbs;//业务不熟管控错误
				private int zldf;//质量得分
				private String isTl9000;
				private String qctime;
				private String qualified;//合格率
				
					
				public String getQualified() {
					return qualified;
				}
				public void setQualified(String qualified) {
					this.qualified = qualified;
				}
				public String getQctime() {
					return qctime;
				}
				public void setQctime(String qctime) {
					this.qctime = qctime;
				}
				public String getIsTl9000() {
					return isTl9000;
				}
				public void setIsTl9000(String isTl9000) {
					this.isTl9000 = isTl9000;
				}
				public int getId() {
					return id;
				}
				public void setId(int id) {
					this.id = id;
				}
				public String getName() {
					return name;
				}
				public void setName(String name) {
					this.name = name;
				}
				public String getSmailworkid() {
					return smailworkid;
				}
				public void setSmailworkid(String smailworkid) {
					this.smailworkid = smailworkid;
				}
				
				public String getStartdate() {
					return startdate;
				}
				public void setStartdate(String startdate) {
					this.startdate = startdate;
				}
				public String getEnddate() {
					return enddate;
				}
				public void setEnddate(String enddate) {
					this.enddate = enddate;
				}
				public int getIs112() {
					return is112;
				}
				public void setIs112(int is112) {
					this.is112 = is112;
				}
				public int getIscw() {
					return iscw;
				}
				public void setIscw(int iscw) {
					this.iscw = iscw;
				}
				public int getYcl() {
					return ycl;
				}
				public void setYcl(int ycl) {
					this.ycl = ycl;
				}
				public int getGk() {
					return gk;
				}
				public void setGk(int gk) {
					this.gk = gk;
				}
				public int getLxcw() {
					return lxcw;
				}
				public void setLxcw(int lxcw) {
					this.lxcw = lxcw;
				}
				public int getCp() {
					return cp;
				}
				public void setCp(int cp) {
					this.cp = cp;
				}
				public int getLp() {
					return lp;
				}
				public void setLp(int lp) {
					this.lp = lp;
				}
				public int getYwbs() {
					return ywbs;
				}
				public void setYwbs(int ywbs) {
					this.ywbs = ywbs;
				}
				public int getYwbl() {
					return ywbl;
				}
				public void setYwbl(int ywbl) {
					this.ywbl = ywbl;
				}
				public int getWfl() {
					return wfl;
				}
				public void setWfl(int wfl) {
					this.wfl = wfl;
				}
				public int getYwbgf() {
					return ywbgf;
				}
				public void setYwbgf(int ywbgf) {
					this.ywbgf = ywbgf;
				}
				public int getTdsy() {
					return tdsy;
				}
				public void setTdsy(int tdsy) {
					this.tdsy = tdsy;
				}
				public int getFlxzcw() {
					return flxzcw;
				}
				public void setFlxzcw(int flxzcw) {
					this.flxzcw = flxzcw;
				}
				public int getWfxc() {
					return wfxc;
				}
				public void setWfxc(int wfxc) {
					this.wfxc = wfxc;
				}
				public int getBsbgf() {
					return bsbgf;
				}
				public void setBsbgf(int bsbgf) {
					this.bsbgf = bsbgf;
				}
				public int getJndmc() {
					return jndmc;
				}
				public void setJndmc(int jndmc) {
					this.jndmc = jndmc;
				}
				public int getPdcs() {
					return pdcs;
				}
				public void setPdcs(int pdcs) {
					this.pdcs = pdcs;
				}
				public int getCzbgf() {
					return czbgf;
				}
				public void setCzbgf(int czbgf) {
					this.czbgf = czbgf;
				}
				public int getYxbg() {
					return yxbg;
				}
				public void setYxbg(int yxbg) {
					this.yxbg = yxbg;
				}
				public int getQt() {
					return qt;
				}
				public void setQt(int qt) {
					this.qt = qt;
				}
				public int getYhts() {
					return yhts;
				}
				public void setYhts(int yhts) {
					this.yhts = yhts;
				}
				public int getYhby() {
					return yhby;
				}
				public void setYhby(int yhby) {
					this.yhby = yhby;
				}
				public int getBzxxc() {
					return bzxxc;
				}
				public void setBzxxc(int bzxxc) {
					this.bzxxc = bzxxc;
				}
				public int getYdbgf() {
					return ydbgf;
				}
				public void setYdbgf(int ydbgf) {
					this.ydbgf = ydbgf;
				}
				public int getGkcp() {
					return gkcp;
				}
				public void setGkcp(int gkcp) {
					this.gkcp = gkcp;
				}
				public int getGkfl() {
					return gkfl;
				}
				public void setGkfl(int gkfl) {
					this.gkfl = gkfl;
				}
				public int getLgk() {
					return lgk;
				}
				public void setLgk(int lgk) {
					this.lgk = lgk;
				}
				public int getYwgkbs() {
					return ywgkbs;
				}
				public void setYwgkbs(int ywgkbs) {
					this.ywgkbs = ywgkbs;
				}
				public int getZldf() {
					return zldf;
				}
				public void setZldf(int zldf) {
					this.zldf = zldf;
				}
				
				
}
