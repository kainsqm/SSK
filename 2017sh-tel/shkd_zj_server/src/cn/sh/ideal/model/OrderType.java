package cn.sh.ideal.model;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author lk
 * 工单类型
 *
 */
public class OrderType {
			private  String[] qjtype;	//区局
			private  String[] sgdltype;	//申告大类
			private  String[] sgxxtype;	//申告现象
			private  String[] csdmtype; //测试代码
			private  String[]  sgbztype; //申告备注
			private  String[]  sglxtype; //申告联系信息
			private  String[]  jafstype;//结案方式
			private  String[]  sllytype;//受理来源
			private  String[]  pxfxtype;//派修方向
			private  String[]  sjgztype;//三级故障大类
			private  String[]  gzxftype; //三级故障修复代码
			
			private String startTime;//任务工单领取开始时间
			private String stopTime;//任务工单领取结束时间
			
			
			
			public String[] getQjtype() {
				return qjtype;
			}
			public void setQjtype(String[] qjtype) {
				this.qjtype = qjtype;
			}
			public String[] getSgdltype() {
				return sgdltype;
			}
			public void setSgdltype(String[] sgdltype) {
				this.sgdltype = sgdltype;
			}
			public String[] getSgxxtype() {
				return sgxxtype;
			}
			public void setSgxxtype(String[] sgxxtype) {
				this.sgxxtype = sgxxtype;
			}
			public String[] getCsdmtype() {
				return csdmtype;
			}
			public void setCsdmtype(String[] csdmtype) {
				this.csdmtype = csdmtype;
			}
			public String[] getSgbztype() {
				return sgbztype;
			}
			public void setSgbztype(String[] sgbztype) {
				this.sgbztype = sgbztype;
			}
			public String[] getSglxtype() {
				return sglxtype;
			}
			public void setSglxtype(String[] sglxtype) {
				this.sglxtype = sglxtype;
			}
			public String[] getJafstype() {
				return jafstype;
			}
			public void setJafstype(String[] jafstype) {
				this.jafstype = jafstype;
			}
			public String[] getSllytype() {
				return sllytype;
			}
			public void setSllytype(String[] sllytype) {
				this.sllytype = sllytype;
			}
			public String[] getPxfxtype() {
				return pxfxtype;
			}
			public void setPxfxtype(String[] pxfxtype) {
				this.pxfxtype = pxfxtype;
			}
			public String[] getSjgztype() {
				return sjgztype;
			}
			public void setSjgztype(String[] sjgztype) {
				this.sjgztype = sjgztype;
			}
			public String[] getGzxftype() {
				return gzxftype;
			}
			public void setGzxftype(String[] gzxftype) {
				this.gzxftype = gzxftype;
			}
			public String getStartTime() {
				return startTime;
			}
			public void setStartTime(String startTime) {
				this.startTime = startTime;
			}
			public String getStopTime() {
				return stopTime;
			}
			public void setStopTime(String stopTime) {
				this.stopTime = stopTime;
			}
			
			
 			
}
