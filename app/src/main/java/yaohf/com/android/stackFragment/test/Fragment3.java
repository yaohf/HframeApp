package yaohf.com.android.stackFragment.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yaohf.com.android.R;
import yaohf.com.android.activity.TEventActivity;
import yaohf.com.android.stackFragment.RootFragment;
import yaohf.com.tool.tevent.TEvent;


/**
 * User: chengwangyong(chengwangyong@vcinema.com)
 * Date: 2016-01-19
 * Time: 10:02
 */
public class Fragment3 extends RootFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment3, null);
        inflate.findViewById(R.id.fragment1).setOnClickListener(this);
        inflate.findViewById(R.id.fragment2).setOnClickListener(this);
        inflate.findViewById(R.id.fragment4).setOnClickListener(this);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment1:
                open(new Fragment1());
                TEvent.trigger(TEventActivity.TAG,new Object[]{1,Fragment1.class.getName()});
                break;
            case R.id.fragment2:
                open(new Fragment2());
                TEvent.trigger(TEventActivity.TAG,new Object[]{2,Fragment2.class.getName()});
                break;
            case R.id.fragment4:
                open(new Fragment4());
                TEvent.trigger(TEventActivity.TAG,new Object[]{3,Fragment3.class.getName()});
                break;
        }
    }
}
