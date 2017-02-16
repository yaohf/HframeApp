
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

import java.util.Arrays;
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

public class RecyclerActivity extends BaseActivity implements ItemTouchAdapter.OnStartActionListener {

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
        initData();
        init();
    }

    private void initData() {
        String[] initItem = mContext.getResources().getStringArray(R.array.recycler_items);
        mDataList = Arrays.asList(initItem);

        final int count = mDataList.size();
        for (int i = count; i <= 100; i++) {
            mDataList.add(String.valueOf(i));
        }
        initItem = null;
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = findById(R.id.recyclerView);


        //设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ItemTouchAdapter(this, mDataList);
        wrapper = new ItemTouchAdapterWrapper((ItemTouchAdapter) mAdapter);
        wrapper.addFooter(R.layout.footer_load_more);
//        wrapper.addHeader(R.layout.header);
        //添加item点击事件监听
        mAdapter.setOnItemClickListener(itemClickListener);
        mAdapter.setOnItemLongClickListener(new RecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, Object item, int pos) {
                Toast.makeText(mContext, "long click " + pos, Toast.LENGTH_SHORT).show();
            }
        });
        //设置布局样式LayoutManager
        final GridLayoutManager gridLayout = new GridLayoutManager(mContext, 3);
//      final LinearLayoutManager linearLayout = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayout);
        recyclerView.setAdapter(wrapper);
        recyclerView.addOnScrollListener(new RvToolbarOffsetHidingScrollListener(this, mToolbar));
        recyclerView.addOnScrollListener(new RvFabOffsetHidingScrollListener(this, fab));

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(wrapper));
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    //Fragment Stack test
    RecyclerAdapter.OnItemClickListener itemClickListener = new RecyclerAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View itemView, Object item, int pos) {
            String value = String.valueOf(item);
            Toast.makeText(mContext, value, Toast.LENGTH_SHORT).show();

            String[] initItem = mContext.getResources().getStringArray(R.array.recycler_items);
            L.v("value>>" + value);

            if (value.equals(initItem[0])) {
                startActivity(FramentMainActivity.class, null);
            } else if (value.equals(initItem[1])) {
                startActivity(PanelActivity.class, null);
            } else if (value.equals(initItem[2])) {
                startActivity(TEventActivity.class, null);
            } else if (value.equals(initItem[3])) {
                startActivity(HookActivity.class, null);
            } else if (value.equals(initItem[4])) {
                startActivity(CountDownActivity.class, null);
            } else if (value.equals(initItem[5])) {
                startActivity(LiteOrmActivity.class, null);
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