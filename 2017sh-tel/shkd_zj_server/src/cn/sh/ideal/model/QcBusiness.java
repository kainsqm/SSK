package cn.sh.ideal.model;

public class QcBusiness {
				private  int id; //序号
				private String qcdate; //日期
				private String qctime; //时间
				private String agentName; //受理者
				private String sgphone; //申告号码
				private String sgContent;//申告内容/处理
				private String quesstion;//存在问题
				private String checkqr;//被检查人确认
				private String qcName; //质检员
				private String zxFzx; //专项非专项
				private String qcstartdatetime;//质检开始时间
				private String qcstopdatetime;//质检开始时间
				private String isTl9000; //是否TL90000
				
				
				public String getIsTl9000() {
					return isTl9000;
				}
				public void setIsTl9000(String isTl9000) {
					this.isTl9000 = isTl9000;
				}
				public String getQcstartdatetime() {
					return qcstartdatetime;
				}
				public void setQcstartdatetime(String qcstartdatetime) {
					this.qcstartdatetime = qcstartdatetime;
				}
				public String getQcstopdatetime() {
					return qcstopdatetime;
				}
				public void setQcstopdatetime(String qcstopdatetime) {
					this.qcstopdatetime = qcstopdatetime;
				}
				public int getId() {
					return id;
				}
				public void setId(int id) {
					this.id = id;
				}
			
				public String getQcdate() {
					return qcdate;
				}
				public void setQcdate(String qcdate) {
					this.qcdate = qcdate;
				}
				public String getQctime() {
					return qctime;
				}
				public void setQctime(String qctime) {
					this.qctime = qctime;
				}
				
				public String getAgentName() {
					return agentName;
				}
				public void setAgentName(String agentName) {
					this.agentName = agentName;
				}
				public String getSgphone() {
					return sgphone;
				}
				public void setSgphone(String sgphone) {
					this.sgphone = sgphone;
				}
				public String getSgContent() {
					return sgContent;
				}
				public void setSgContent(String sgContent) {
					this.sgContent = sgContent;
				}
				public String getQuesstion() {
					return quesstion;
				}
				public void setQuesstion(String quesstion) {
					this.quesstion = quesstion;
				}
				public String getCheckqr() {
					return checkqr;
				}
				public void setCheckqr(String checkqr) {
					this.checkqr = checkqr;
				}
				public String getQcName() {
					return qcName;
				}
				public void setQcName(String qcName) {
					this.qcName = qcName;
				}
				public String getZxFzx() {
					return zxFzx;
				}
				public void setZxFzx(String zxFzx) {
					this.zxFzx = zxFzx;
				}
				
		
}
