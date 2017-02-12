package yaohf.com.android.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ColumnsValue;
import com.litesuits.orm.db.model.ConflictAlgorithm;
import com.litesuits.orm.log.OrmLog;

import java.util.ArrayList;
import java.util.List;

import yaohf.com.android.R;
import yaohf.com.model.bean.Boss;
import yaohf.com.model.bean.UserInfo;
import yaohf.com.tool.EncryptUtil;
import yaohf.com.tool.L;

/**
 * Created by viqgd on 2017/2/10.
 */

public class LiteOrmActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = LiteOrmActivity.class.getName();
    private TextView mTvSubTitle;
    public LinearLayout container;
    public ScrollView scroll;

    private UserInfo userInfo;

    private LiteOrm liteOrm;

    /**
     * 100 000 条数据
     */
    private static final int MAX = 100000;

    private List<Boss> bossList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lite_orm_main);
        OrmLog.setTag(TAG);
        setTitle(R.string.lite_orm_title);
        container = findById(R.id.container);
        scroll = (ScrollView) container.getParent();

        String[] bttxt = mContext.getResources().getStringArray(R.array.orm_test_case);
        if (bttxt != null) {
            for (int i = 0; i < bttxt.length; i++) {
                Button bt = new Button(this);
                LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                int margin = getResources().getDimensionPixelSize(R.dimen.common_marin);
                lp.setMargins(margin, margin, margin, margin);
                bt.setId(i);
                bt.setText(bttxt[i]);
                bt.setOnClickListener(this);
                bt.setLayoutParams(lp);
                container.addView(bt);
            }
        }

        mockData();
    }

    private void mockData() {

        if (liteOrm == null) {
            DataBaseConfig config = new DataBaseConfig(this, "liteorm.db");
            config.debugged = true; // open the log
            config.dbVersion = 1; // set database version
            config.onUpdateListener = null; // set database update listener
            liteOrm = LiteOrm.newSingleInstance(config);
        }

        if (userInfo != null) {
            return;
        }
        userInfo = new UserInfo("liming", EncryptUtil.makeMD5("123456"), "", "李明", 0);
//        liteOrm.insert(userInfo);

        bossList = new ArrayList<Boss>();
        Boss bj = new Boss("Cang boss", "北京", "010-87864356");
        Boss sh = new Boss("Shang boss", "上海", "021-87864356");
        bossList.add(bj);
        bossList.add(sh);
    }


    @Override
    protected void activityHanlderMessage(Message m) {

    }

    public Runnable getButtonClickRunnable(final int id) {
        //Main UI Thread
        //makeOrmTest(id);
        return new Runnable() {
            @Override
            public void run() {
                //Child Thread
                makeOrmTest(id);
            }
        };
    }

    private void makeOrmTest(int id) {
        switch (id) {
            case 0:
                testSave();
                break;
            case 1:
                testInsert();
                break;
            case 2:
                testUpdate();
                break;
            case 3:
                // LiteOrm 插入10w条数的效率测试
                testLargeScaleUseLiteOrm(liteOrm, MAX);
                break;
            case 4:
                testUpdateColumn();
                break;
            case 5:
                testQueryAll();
                break;
            case 6:
                testQueryByWhere();
                break;
            case 7:
                testDeleteAll();
                break;
            default:
                break;
        }
    }

    private void testSave() {
        liteOrm.save(userInfo);
        //保存集合
        liteOrm.save(bossList);
    }

    private void testInsert() {
        userInfo.setRealName("ih fe");
        liteOrm.insert(userInfo, ConflictAlgorithm.Replace);
        userInfo = new UserInfo("litianyi", EncryptUtil.makeMD5("423435"), "http://", "李天一", 1);
        liteOrm.insert(userInfo, ConflictAlgorithm.Rollback);
    }

    private void testUpdate() {
        userInfo.setRealName("小鸭");
        //交换2个User的信息
        liteOrm.update(userInfo, ConflictAlgorithm.Fail);
    }

    private void testQueryAll() {
        ArrayList<UserInfo> uList = liteOrm.query(UserInfo.class);
        ArrayList<Boss> ts = liteOrm.query(Boss.class);
        if (uList != null) {
            for (UserInfo user : uList) {
                L.v("query UserInfo: " + user);
            }
        }
        if (ts != null) {
            for (Boss uu : ts) {
                OrmLog.i(this, "query Boss: " + uu);
            }
        }
    }

    private void testQueryByWhere() {
        // 模糊查询：所有带“ZheJiang Xihu”字的地址
        QueryBuilder<Boss> qb = new QueryBuilder<Boss>(Boss.class)
                .where(Boss.ADDRESS + " LIKE ?", new String[]{"%ZheJiang Xihu%"});
       ArrayList<Boss> result = liteOrm.query(qb);
        for(Boss b : result)
        {
            L.v(b);
        }


    }


    public static boolean testLargeScaleUseLiteOrm(final LiteOrm liteOrm, int max) {
        boolean logPrint = OrmLog.isPrint;
        OrmLog.isPrint = false;

        L.v(" lite-orm test start ...");
        // 1. 初始化数据
        final List<Boss> list = new ArrayList<Boss>();
        for (int i = 0; i < max; i++) {
            Boss boss = new Boss();
            boss.setAddress("ZheJiang Xihu " + i);
            boss.setPhone("1860000" + i);
            boss.setName("boss" + i);
            list.add(boss);
        }

        long start, end;
        int num;

        // 2 批量插入测试
        start = System.currentTimeMillis();
        num = liteOrm.insert(list);
        end = System.currentTimeMillis();
        L.v(" lite-orm insert boss model num: " + num + " , use time: " + (end - start) + " MS");

        // 3. 查询数量测试
        start = System.currentTimeMillis();
        long count = liteOrm.queryCount(Boss.class);
        end = System.currentTimeMillis();
        L.v(" lite-orm query all boss model num: " + count + " , use time: " + (end - start) + " MS");

        // 4. 查询最后10条测试
        start = System.currentTimeMillis();
        ArrayList subList = liteOrm.query(
                new QueryBuilder<Boss>(Boss.class).appendOrderDescBy("_id").limit(0, 10));
        end = System.currentTimeMillis();
        L.v(" lite-orm select top 10 boss model num: " + subList.size() + " , use time: " + (end - start) + " MS");
        L.v(String.valueOf(subList));

        // 5. 删除全部测试
        start = System.currentTimeMillis();
        // direct delete all faster
        num = liteOrm.deleteAll(Boss.class);
        // delete list
        //num = liteOrm.delete(list);
        end = System.currentTimeMillis();
        L.v(" lite-orm delete boss model num: " + num + " , use time: " + (end - start) + " MS");

        // 6. 再次查询数量测试
        start = System.currentTimeMillis();
        count = liteOrm.queryCount(Boss.class);
        end = System.currentTimeMillis();

        L.v(" lite-orm query all boss model num: " + count + " , use time: " + (end - start) + " MS");

        OrmLog.isPrint = logPrint;
        return true;
    }

    /**
     * 仅更新指定字段
     */
    private void testUpdateColumn() {

        //1. 集合更新实例：
        Boss boss0 = bossList.get(0);
        boss0.setAddress("上海市");
        boss0.setPhone("168 8888 8888");

        Boss boss1 = bossList.get(1);
        boss1.setAddress("广东省");
        boss1.setPhone("168 0000 0000");

        ColumnsValue cv = new ColumnsValue(new String[]{"phone"});
        long c = liteOrm.update(bossList, cv, ConflictAlgorithm.None);
        L.v("update boss ：" + c);

    }

    /**
     * 简单全部删除
     */
    private void testDeleteAll() {
        liteOrm.deleteAll(UserInfo.class);
        liteOrm.deleteAll(Boss.class);

        // 顺带测试：连库文件一起删掉
        liteOrm.deleteDatabase();
        // 顺带测试：然后重建一个新库
        liteOrm.openOrCreateDatabase();
        // 满血复活
    }


    @Override
    public void onClick(View v) {
        Runnable r = getButtonClickRunnable(v.getId());
        if (r != null) {
            new Thread(r).start();
        }
    }
}
