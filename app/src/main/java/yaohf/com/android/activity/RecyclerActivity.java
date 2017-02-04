
package yaohf.com.android.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yaohf.com.android.R;
import yaohf.com.android.stackFragment.test.FramentMainActivity;
import yaohf.com.tool.L;
import yaohf.com.widget.recyclerview.ItemTouchAdapter;
import yaohf.com.widget.recyclerview.ItemTouchAdapterWrapper;
import yaohf.com.widget.recyclerview.ItemTouchHelperCallback;
import yaohf.com.widget.recyclerview.RecyclerAdapter;
import yaohf.com.widget.recyclerview.listener.RvFabOffsetHidingScrollListener;
import yaohf.com.widget.recyclerview.listener.RvToolbarOffsetHidingScrollListener;

public class RecyclerActivity extends BaseActivity implements ItemTouchAdapter.OnStartActionListener{

    List<String> mDataList;
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;

    private Toolbar mToolbar;
    private FloatingActionButton fab;

    private ItemTouchHelper mItemTouchHelper;
    private ItemTouchAdapterWrapper wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        init();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mDataList = new ArrayList<>();
        mDataList.add("Frament Stack 管理");
        mDataList.add("涂鸦板");
        final int count = mDataList.size();
        for (int i = count; i <= 100; i++) {
            mDataList.add(String.valueOf(i));
        }
        //设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ItemTouchAdapter(this,mDataList);
        wrapper=new ItemTouchAdapterWrapper((ItemTouchAdapter) mAdapter);
        wrapper.addFooter(R.layout.footer_load_more);
        wrapper.addHeader(R.layout.header);
        //添加item点击事件监听
        mAdapter.setOnItemClickListener(itemClickListener);
        mAdapter.setOnItemLongClickListener(new RecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, int pos) {
                Toast.makeText(RecyclerActivity.this, "long click " + pos, Toast.LENGTH_SHORT).show();
            }
        });


        //设置布局样式LayoutManager
        final GridLayoutManager gridLayout = new GridLayoutManager(mContext,3);
//      final LinearLayoutManager linearLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayout);
        recyclerView.setAdapter(wrapper);
        recyclerView.addOnScrollListener(new RvToolbarOffsetHidingScrollListener(this, mToolbar));
        recyclerView.addOnScrollListener(new RvFabOffsetHidingScrollListener(this,fab));

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(wrapper));
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }
    //Fragment Stack test
    private static final int FRAGMENT_STACK_CLICK = 1;
    //涂鸦板绘画
    private static final int PANEL_CLICK = 2;
    RecyclerAdapter.OnItemClickListener itemClickListener = new RecyclerAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View itemView, int pos) {
            L.v("pos>>" + pos);
            switch(pos)
            {
                //Fragment Stack test
                case FRAGMENT_STACK_CLICK:
                    startActivity(FramentMainActivity.class,null);
                    break;
                case PANEL_CLICK:
                    startActivity(PanelActivity.class,null);
                    break;
            }
        }
    };

    @Override
    protected void activityHanlderMessage(Message m) {

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onStartSwipe(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startSwipe(viewHolder);
    }
}