package yaohf.com.tool.tevent;

public interface ITEvent {

	/**
	 * 消息数组
	 * Object[] 可接收任意类型消息，可自行设定消息机制
	 * @param paramArrayOfObject
	 */
	 public abstract void triggerHandler(Object[] paramArrayOfObject);
}
