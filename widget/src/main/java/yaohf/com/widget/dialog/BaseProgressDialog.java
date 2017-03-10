package yaohf.com.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import yaohf.com.tool.L;
import yaohf.com.widget.R;


public class BaseProgressDialog extends Dialog {

	private TextView tvMsg;
    public BaseProgressDialog(Context context, int theme, String strMessage) {  
        super(context, theme);  
        this.setContentView(R.layout.waitting);
        
        this.getWindow().getAttributes().gravity = Gravity.CENTER;  
        tvMsg = (TextView) this.findViewById(R.id.waitting_text);  
        L.v("tvMsg>>" + strMessage);
        if (tvMsg != null) {  
            tvMsg.setText(strMessage);  
        }  
    }  
  
    @Override  
    public void onWindowFocusChanged(boolean hasFocus) {  
  
        if (!hasFocus) {  
            dismiss();  
        }  
    }  
    
    /**
	 * 显示圈圈
	 */
	public void showProgress() {
		if (!isShowing()) {
			show();
		}
	}

	/**
	 * 
	 * @Description: 设置等待文本内容 int 类型
	 * @param @param id
	 * @return void
	 * @throws
	 */
	public void setMessage(int id) {
		tvMsg.setText(id);
	}

	/**
	 * 
	 * @Description: 设置等待文本内容 String
	 * @param @param msg
	 * @return void
	 * @throws
	 */
	public void setMessage(String msg) {
		tvMsg.setText(msg);
	}

	/**
	 * 不显示圈圈
	 */
	public void dismissProgress() {
		if (isShowing()) {
			dismiss();
		}
	}

}
