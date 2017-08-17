package cn.sh.ideal.model;
/*****
 * 公告实体类
 * @author niewenqiang
 * 2017-4-18
 * ****/
public class Notice {
    public int pkAutoId; //主键
    public String title ;// 标题
    public String content; //内容
    public String releaseDate; //开始时间
    public String status; //状态
    public String enabled; //是否有效
    public String insertTime; //创建时间
    public String insertUserName; //创建人姓名
    public int insertUserId; //创建人
    public String updateTime; //修改时间
    public int updateUserId; //修改人
    
	public String getInsertUserName() {
		return insertUserName;
	}
	public void setInsertUserName(String insertUserName) {
		this.insertUserName = insertUserName;
	}
	public int getPkAutoId() {
		return pkAutoId;
	}
	public void setPkAutoId(int pkAutoId) {
		this.pkAutoId = pkAutoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
    
	
	
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public int getInsertUserId() {
		return insertUserId;
	}
	public void setInsertUserId(int insertUserId) {
		this.insertUserId = insertUserId;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
	}
    
    
    
}
