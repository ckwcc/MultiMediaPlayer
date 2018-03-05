package com.ckw.multimediaplayer.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.ckw.multimediaplayer.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ckw
 * on 2018/3/2.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnBinder;

    private Toolbar mToolbar;

    private InputMethodManager imm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayoutId());

        mUnBinder = ButterKnife.bind(this);

        if(needToolbar()){
            initToolbar();
//            setToolbar();
        }

        //处理从其他界面传过来的数据
        handleIntent();
        //view与数据绑定
        initView(savedInstanceState);

        initListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideSoftKeyBoard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mUnBinder != null){
            mUnBinder.unbind();
        }
    }

    /**
     * this activity layout res
     * 设置layout布局,在子类重写该方法.
     * @return res layout xml id
     */
    protected abstract int getLayoutId();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initListener();

    /**
     * 处理上个界面传过来的数据---所有的Intent跳转的数据都需要包装在Bundle中
     *
     * @param bundle 界面跳转时传递的数据
     */
    protected abstract void handleBundle(@NonNull Bundle bundle);

    //跳转界面时判读Intent是否携带数据
    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                handleBundle(bundle);
            }
        }
    }

    /**
     * 隐藏键盘
     */
    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    //ToolBar相关

    //返回false的时候，就不再需要重写setToolbar方法，当需要显示toolbar的时候，返回true
//    protected abstract boolean needToolbar();
    private boolean needToolbar(){
        return true;
    }

    private void initToolbar(){
        mToolbar =  findViewById(R.id.toolbar_id);
        if(mToolbar != null){
//            mToolbar.setTitle("");
            setToolbar();
            setSupportActionBar(mToolbar);
        }

    }

    public abstract void setToolbar();

    public Toolbar getToolbar(){
        return mToolbar;
    }

    /**
     * 设置头部标题
     * @param title
     */
    public void setToolBarTitle(String title) {
        if(mToolbar != null){
           mToolbar.setTitle(title);
        }
    }

    public void setToolBarTitle(int resId) {
        if(mToolbar != null){
            mToolbar.setTitle(resId);
        }
    }

    /**
     * 自定义导航图标
     *
     * @param resId 图片的资源id
     */
    protected void setNavigationIcon(int resId) {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(resId);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigationIconClick();
                }
            });
        }
    }

    /**
     * 自定义导航栏图标
     *
     * @param drawable drawable对象
     */
    protected void setNavigationIcon(Drawable drawable) {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(drawable);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigationIconClick();
                }
            });
        }
    }



    /**
     * 设置toolbar的返回箭头是否显示
     *
     * @param enabled true:显示  false:不显示
     */
    protected void setDisplayHomeAsUpEnabled(boolean enabled) {
        if (mToolbar != null) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(enabled);
                if (enabled) {
                    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onNavigationIconClick();
                        }
                    });
                }
            }
        }
    }

    /**
     * toolbar左侧返回键点击
     */
    protected void onNavigationIconClick() {
        onBackPressed();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
}
