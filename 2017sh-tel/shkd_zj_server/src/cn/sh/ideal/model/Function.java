package cn.sh.ideal.model;

import java.io.Serializable;

public class Function implements Serializable {
	/**
	 * 功能
	 */
	private static final long serialVersionUID = 1L;
	private Integer functionId;
	private String functionType;
	private String imgUrl;
	private Integer pId;
	private String funtionName;
	private String url;
	private String  enabled;
	private String  functionOrder;
	private String  remark;

	private String code;
	
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Function() {
		super();
	}

	public Integer getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;
	}

	public String getFunctionType() {
		return functionType;
	}

	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getFuntionName() {
		return funtionName;
	}

	public void setFuntionName(String funtionName) {
		this.funtionName = funtionName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getFunctionOrder() {
		return functionOrder;
	}

	public void setFunctionOrder(String functionOrder) {
		this.functionOrder = functionOrder;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Function(Integer functionId, String functionType, String imgUrl,
			Integer pId, String funtionName, String url, String enabled,
			String functionOrder, String remark) {
		super();
		this.functionId = functionId;
		this.functionType = functionType;
		this.imgUrl = imgUrl;
		this.pId = pId;
		this.funtionName = funtionName;
		this.url = url;
		this.enabled = enabled;
		this.functionOrder = functionOrder;
		this.remark = remark;
	}

}