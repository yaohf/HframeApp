package yaohf.com.android.activity;

import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

import yaohf.com.android.R;
import yaohf.com.android.stackFragment.RootFragment;
import yaohf.com.android.stackFragment.test.HomeFragment;
import yaohf.com.tool.tevent.ITEvent;
import yaohf.com.tool.tevent.TEvent;

/**
 * TEvent 注册使用示例
 *
 * 1. implements ITEvent 接口 ,实现　triggerHandler　函数调用
 *
 * 2. 在每个类的首加载函数中注册TEvent
 *   TEvent.register(TAG,this);　
 *
 * 3. 解绑定
 *   TEvent.unregister(TAG,this);
 *
 * 4. 添加消息传递体，传递至绑定Tevent所在实现类,示例
 *  Fragment3 发送trigger 实现消息传递
 *  TEvent.trigger(TEventActivity.TAG,new Object[]{1,"hao are you"});
 *
 *  5. triggerHandler 函数处理消息体分发
 */
public class TEventActivity extends BaseActivity implements ITEvent{

    public static final String TAG = TEventActivity.class.getName();

    @Override
    public RootFragment getRootFragment() {
        return new HomeFragment();
    }

    /**
     * 绑定注册Tevent
     */
    public TEventActivity()
    {
        //TAG 注册Type,区别开接收消息对象
        //this 实现接口类
        // 第一个参数，可以任意值，可以是String ,也可以是其它类型,但必须具备唯一性；
        // 如果出现两个相同的注册Type,则消息会发给两个注册者，两个注册者都能收到消息；
        // 同理，一个类中，可以注册多个TEvent,接收不同的消息体，
        TEvent.register(TEventActivity.class,this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑定 与 TEvent.register(TAG,this);成对出现
        TEvent.unregister(TEventActivity.class,this);
    }
    @Override
    public void onCreateNow(Bundle savedInstanceState) {
        setAnim(R.anim.next_in, R.anim.next_out, R.anim.quit_in, R.anim.quit_out);
    }

    @Override
    protected void activityHanlderMessage(Message m) {

    }
    /**
     * Set the time to click to Prevent repeated clicks,default 500ms
     *
     * @param CLICK_SPACE Repeat click time(ms)
     */
    public void setClickSpace(long CLICK_SPACE) {
        manager.setClickSpace(CLICK_SPACE);
    }

    @Override
    public void triggerHandler(Object[] paramArrayOfObject) {
        final int type = (int) paramArrayOfObject[0];

        switch(type)
        {
            case 1:
                Toast.makeText(mContext,paramArrayOfObject[1].toString(),Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(mContext,paramArrayOfObject[1].toString(),Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(mContext,paramArrayOfObject[1].toString(),Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
