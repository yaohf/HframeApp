package yaohf.com.android.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import utils.BitmapUtil;
import utils.SelectorUtils;
import utils.WidGetUtils;
import yaohf.com.android.R;
import yaohf.com.tool.L;
import yaohf.com.widget.panel.ISketchAbstractListener;
import yaohf.com.widget.panel.ISketchListener;
import yaohf.com.widget.panel.SketchPadView;
import yaohf.com.widget.panel.view.ColorSelectView;
import yaohf.com.widget.panel.view.DelSelectView;
import yaohf.com.widget.panel.view.EraserSelectView;
import yaohf.com.widget.panel.view.PaintSelectView;
import yaohf.com.widget.panel.view.PanelView;
import yaohf.com.widget.panel.view.PicSelectView;
import yaohf.com.widget.panel.view.ShapeSelectView;


/**
 *  涂鸭板
 */

public class PanelActivity extends BaseActivity implements
        View.OnClickListener, ISketchListener,ISketchAbstractListener {

    public static final int MSG_SET_BTIMAP = 0;
    // 拍照
    private static final int TAKE_PICTURE_RESULT = 1;
    // 本地相册
    private static final int LOAD_PICTURE_RESULT = 2;

    // 图片路径
    private String mPhotoPath;
    // 图片返回 Uri
    private Uri imageUri;
    // 图片拍照所在位置
    private File mPhotoFile;

    private Context mContext;

//	private String teventType;

    String imagePath = null;

    private SelectorUtils selector;

    private LinearLayout toolbar_ly;

    ImageButton color_btn;
    ImageButton paint_btn;
    ImageButton shape_btn;
    ImageButton peraser_btn;
    ImageButton pic_btn;
    ImageButton save_panel_btn;
    ImageButton history_panel_btn;
    SketchPadView mSketchpad;

    PanelView panel;

    // 画板工具属性值
    private int currentColor;
    private int paintSize;
    private int shapeType;
    private int eraser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.test_book);
        initView();
    }



    private void initView() {
        mSketchpad = findById(R.id.sketchpad);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(mContext.getAssets().open("paper.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mSketchpad.setBkBitmap(bitmap);

        findViewById(R.id.del_btn).setOnClickListener(this);
        findViewById(R.id.pen_go_btn).setOnClickListener(this);
        findViewById(R.id.pen_back_btn).setOnClickListener(this);

        color_btn = findById(R.id.color_btn);
        paint_btn = findById(R.id.paint_btn);
        shape_btn = findById(R.id.shape_btn);
        peraser_btn = findById(R.id.peraser_btn);
        pic_btn = findById(R.id.pic_btn);

        save_panel_btn = findById(R.id.save_panel_btn);
        save_panel_btn.setVisibility(View.VISIBLE);
        history_panel_btn = findById(R.id.history_panel_btn);
        history_panel_btn.setVisibility(View.VISIBLE);
        history_panel_btn.setOnClickListener(this);
        save_panel_btn.setOnClickListener(this);

        selector = SelectorUtils.getInstance(mContext);

        color_btn.setImageDrawable(selector.getStateDrawable(
                R.drawable.colour7_icon, R.drawable.colour5_icon));
        paint_btn.setImageDrawable(selector.getStateDrawable(
                R.drawable.pen_icon, R.drawable.pen_icon_xz));
        shape_btn.setImageDrawable(selector.getStateDrawable(
                R.drawable.xz_icon, R.drawable.xz_icon_xz));
        peraser_btn.setImageDrawable(selector.getStateDrawable(
                R.drawable.xp_icon, R.drawable.xp_icon_xz));
        pic_btn.setImageDrawable(selector.getStateDrawable(R.drawable.pic_icon,
                R.drawable.pic_icon_xz));

        color_btn.setOnClickListener(this);
        paint_btn.setOnClickListener(this);
        shape_btn.setOnClickListener(this);
        peraser_btn.setOnClickListener(this);
        pic_btn.setOnClickListener(this);

    }

    public PanelView getPanelView(int id) {
        if (id == R.id.color_btn) {
            panel = new ColorSelectView(mContext);
        }else if( id == R.id.paint_btn) {
            panel = new PaintSelectView(mContext);
        }
         else if(id == R.id.shape_btn) {
            panel = new ShapeSelectView(mContext);
        }
         else if(id == R.id.peraser_btn) {
            panel = new EraserSelectView(mContext);
        }
         else if(id == R.id.pic_btn) {
            panel = new PicSelectView(mContext);
        }else if( id == R.id.del_btn) {
            panel = new DelSelectView(mContext);
        }
        if (panel != null){
            panel.setSelectListener(this);
            panel.setSelectAbstractListener(this);
        }
        return panel;
    }
    public void showPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(panel);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //为对话框设置一个”取消“按钮

        builder.create().show();
    }


    private void defaultStatus() {
        paint_btn.setImageResource(R.drawable.pen_icon);
        color_btn.setImageResource(R.drawable.colour7_icon);
        shape_btn.setImageResource(R.drawable.xz_icon);
        peraser_btn.setImageResource(R.drawable.xp_icon);
        pic_btn.setImageResource(R.drawable.pic_icon);
    }

    private void selectedIndex(int id) {
            if( id == R.id.pic_btn) {
                pic_btn.setImageResource(R.drawable.pic_icon_2x);
            }
            else if (id == R.id.paint_btn) {
                paint_btn.setImageResource(R.drawable.pen_icon_2x);
            }
            else if (id == R.id.color_btn) {
                color_btn.setImageResource(R.drawable.colour5_icon);
            }
            else if (id == R.id.shape_btn) {
                shape_btn.setImageResource(R.drawable.xz_icon_xz);
            }
            else if (id == R.id.peraser_btn) {
                peraser_btn.setImageResource(R.drawable.xp_icon_xz);
            }
    }

    Dialog dialog;
    String inputStr;

    @Override
    public void onClick(View v) {
        defaultStatus();
        final int id = v.getId();
        // switchPic(id);
        // 获取显示view
        getPanelView(id);
        // 切换导航
        v.measure(0, 0);
        switch (id) {
            case R.id.back_btn:
                finish();
                break;

            case R.id.del_btn: // 清空
                showPopup();
                break;

            case R.id.pen_back_btn: // 上一步
                mSketchpad.undo();
                break;

            case R.id.pen_go_btn: // 下一步
                mSketchpad.redo();
                break;

            case R.id.color_btn: // 颜色
                showPopup();

                break;
            case R.id.paint_btn: // 画笔
                showPopup();
                break;

            case R.id.shape_btn: // 形状
                showPopup();
                break;

            case R.id.peraser_btn: // 橡皮
                showPopup();
                break;

            case R.id.pic_btn: // 图片
                showPopup();
                break;
            //保存
            case R.id.save_panel_btn:

                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }

                dialog=new Dialog(mContext, R.style.my_dialog);
                dialog.setContentView(R.layout.dialog_save_hint);
                ((TextView) dialog.findViewById(R.id.dialog_vote_save_tv)).setText(R.string.hint_str);
                ((TextView) dialog.findViewById(R.id.msg_tv)).setText(R.string.is_save_panel_str);
//			final EditText inputEt =  (EditText) dialog.findViewById(R.id.input_name_tv);
                Button btn_cancel = (Button) dialog.findViewById(R.id.dialog_save_cancel);
                btn_cancel.setText(R.string.cancel);
                btn_cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        return;
                    }
                });
                final Button btn_ok = (Button) dialog.findViewById(R.id.dialog_save_ok);
                btn_ok.setText(R.string.save_str);
                btn_ok.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        BitmapUtil.saveBitmap(BITMAP_CACHE_SAVE_PATH,"panel_save.png",mSketchpad.getCanvasSnapshot());
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }
        selectedIndex(id);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BitmapUtil.releaseResource();
    }

    /**
     * 设置画笔颜色
     */
    @Override
    public void setSketchColor(int color) {
        L.v("color>>" + color);
        currentColor = color;
        eraser = 0;
		mSketchpad.setStrokeColor(currentColor);
    }

    @Override
    public void setDeleteAll(int type) {
        eraser = 0;
    }

    /**
     * 设置画笔大小
     */
    @Override
    public void setSketchPaintSize(int size) {
        L.v("paint size>>" + size);
        paintSize = size;
        eraser = 0;
		mSketchpad.setStrokeSize(paintSize, SketchPadView.STROKE_PEN);

    }

    /**
     * 画笔样式
     */
    @Override
    public void setShapeType(int type) {
        shapeType = type;
        eraser = 0;
		mSketchpad.setStrokeType(shapeType);
    }


    @Override
    public void setEraser(int eraser) {
        this.eraser = eraser;
    }

    private static final String BITMAP_CACHE_SAVE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

    final String SAVE_PATH = "panel_cache.png";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        L.v("pic type >>" + requestCode);

        File file = null;
        Bitmap camorabitmap = null;
        switch (requestCode) {
		//拍照
            case TAKE_PICTURE_RESULT:
			// 检测内存卡是否以满，内存卡的时候，不进行拍照保存
                if (WidGetUtils.getAvailaleSize() < 1) {
                    Toast.makeText(mContext, R.string.sd_full, Toast.LENGTH_SHORT)
                            .show();
                    break;
                }
                if (mPhotoPath == null)
                    break;
                file = new File(mPhotoPath);
                if (file != null && file.length() <= 0) {
                    break;
                }

                camorabitmap = BitmapUtil.getSmallBitmap(mPhotoPath);
                BitmapUtil.saveBitmap(BITMAP_CACHE_SAVE_PATH,
                        SAVE_PATH, camorabitmap);
                //涂鸦添加照片，默认为原始寸尺，可自行设置平铺
                mSketchpad.setBkBitmap(BitmapUtil.duplicateBitmap(camorabitmap));
                BitmapUtil.addMemoryChche(mPhotoPath, camorabitmap);

                break;
            case LOAD_PICTURE_RESULT:
                if (data == null)
                    return;
                else if (data.getData() == null)
                    return;
                imageUri = data.getData();
                mPhotoPath = WidGetUtils.getPathByUri(PanelActivity.this,
                        imageUri);

                File isFile = new File(mPhotoPath);
                if (!isFile.exists()) {
                    Toast.makeText(mContext, R.string.picture_is_null,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                camorabitmap = BitmapUtil.getSmallBitmap(mPhotoPath);
                BitmapUtil.saveBitmap(BITMAP_CACHE_SAVE_PATH,
                        SAVE_PATH, camorabitmap);
                //涂鸦添加照片，默认为原始寸尺，可自行设置平铺
                mSketchpad.setBkBitmap(BitmapUtil.duplicateBitmap(camorabitmap));

                BitmapUtil.addMemoryChche(mPhotoPath, camorabitmap);
                imageUri = null;
                break;
        }
    }

    /**
     * 相册
     *
     * @param localDepotResult
     */
    private void localDepot(int localDepotResult) {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, localDepotResult);
    }

    /**
     * 拍照
     *
     * @param code
     */
    private void imageCapture(int code) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String filePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator + "DCIM/Camer";
        mPhotoPath = filePath
                + File.separator
                + WidGetUtils.getPhotoFileName("panel");

        File path = new File(filePath);
        if (!path.exists()) {
            path.mkdir();
        }
        mPhotoFile = new File(mPhotoPath);
        try {
            if (!mPhotoFile.exists()) {
                mPhotoFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(mPhotoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        // intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 2);
        startActivityForResult(intent, code);
    }

    @Override
    public void setPicMode(int mode) {
        if (mode == TAKE_PICTURE_RESULT) {
            imageCapture(mode);
        } else {
            localDepot(mode);
        }
    }

    @Override
    public void setAbsDeleteAll(int type) {
        switch(type)
        {
            case 1:
                mSketchpad.clearAllStrokes();
                mSketchpad.clearCanvas();
                break;
            case 2:
                mSketchpad.clearAllStrokes();
                break;
        }
    }
    @Override
    protected void activityHanlderMessage(Message m) {

    }
}
