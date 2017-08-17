package cn.sh.ideal.intercept;
/**
 * 日志信息的参数实体
 *
 */
public class LogDescriptionArgsObject {
	// description对象参数
	private Object[] objects;
	
	public static LogDescriptionArgsObject newInstant(){
		return new LogDescriptionArgsObject();
	}
	public Object[] getObjects() {
		return objects;
	}
	public LogDescriptionArgsObject setObjects(Object[] objects) {
		this.objects = objects;
		return this;
	}
	
}
