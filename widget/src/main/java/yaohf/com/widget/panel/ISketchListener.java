package yaohf.com.widget.panel;

public interface ISketchListener {

	/**
	* @Description: 画笔颜色
	* @param @param color  
	* @return void  
	* @throws
	 */
	public void setSketchColor(int color);
	/**
	 * 
	* @Description: 清除
	* @param @param type  
	* @return void  
	* @throws
	 */
	public void setDeleteAll(int type);
	/**
	 * 
	* @Description: 画笔大小
	* @param @param size  
	* @return void  
	* @throws
	 */
	public void setSketchPaintSize(int size);
	/**
	 * 
	* @Description: 画笔类型
	* @param @param type  
	* @return void  
	* @throws
	 */
	public void setShapeType(int type);
	
	/**
	 * 
	* @Description: 橡皮大小
	* @param @param eraser  
	* @return void  
	* @throws
	 */
	public void setEraser(int eraser);
	
	/**
	 * 
	* @Description: 图片
	* @param @param mode  
	* @return void  
	* @throws
	 */
	public void setPicMode(int mode);
	
}
