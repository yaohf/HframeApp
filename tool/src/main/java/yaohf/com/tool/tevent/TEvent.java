package yaohf.com.tool.tevent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class TEvent {
	protected static HashMap<Object, LinkedList<ITEvent>> listeners = new HashMap<Object, LinkedList<ITEvent>>();

	public  synchronized static void register(Object type, ITEvent iTever) {
		register(type, iTever, 0);
		type = null;
		iTever = null;
	}


	/**
	 * 注册 Tevent
	 * @param type
	 * @param iTever
	 * @param priority
	 */
	public synchronized static void register(Object type, ITEvent iTever, int priority) {
		LinkedList<ITEvent> l = null;
		if (listeners.get(type) == null) {
			l = new LinkedList<ITEvent>();
			listeners.put(type, l);
		} else {
			l = (LinkedList<ITEvent>) listeners.get(type);
		}
		if (priority == 0) {
			l.add(iTever);
		} else if (priority < 0) {
			l.add(0, iTever);
		} else {
			int len = l.size();
			priority = priority > len ? len : priority;
			l.add(priority, iTever);
		}
		type = null;
		iTever = null;
	}

	/**
	 * 发送消息，触发消息推送：
	 *
	 * @param type
	 *            传递消息时 Type 为指定发送地址，即注册时的Type
	 * @param data
	 *            Object[] data 消息体，可一次性发送多个消息体，不同类型，消息接收时，视发送消息类型为准。
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized static boolean trigger(Object type, Object[] data) {
		LinkedList<ITEvent> ls = listeners.get(type);
		type = null;
		if (ls != null) {
			ls = (LinkedList<ITEvent>) ls.clone();
			for (Iterator<ITEvent> it = ls.iterator(); it.hasNext();) {
				((ITEvent) it.next()).triggerHandler(data);
			}
			ls = null;
			data = (Object[]) null;
			return true;
		}
		listeners = null;
		data = (Object[]) null;
		return false;
	}


	/**
	 * 解除TEvent 绑定
	 *
	 * @param type
	 *            注册时Type
	 * @param iTever
	 *            绑定接口，当前类
	 */
	public synchronized static void unregister(Object type, ITEvent iTever) {
		LinkedList<ITEvent> ls = listeners.get(type);
		if (ls != null) {
			int i = ls.lastIndexOf(iTever);
			if (i != -1) {
				ls.remove(i);
				if (ls.size() == 0)
					ls.remove(type);
			}
		}
		type = null;
		iTever = null;
		ls = null;
	}

	public synchronized static void clearTrigger(Object type) {
		listeners.remove(type);
		type = null;
	}

	public synchronized static boolean hasTrigger(Object type) {
		return listeners.containsKey(type);
	}
}
