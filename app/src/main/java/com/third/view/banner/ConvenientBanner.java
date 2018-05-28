package com.third.view.banner;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.he.joint.R;
import com.he.joint.mgr.DataMgr;
import com.he.joint.utils.CollectionUtils;

/**
 * 椤甸潰缈昏浆鎺т欢锛屾瀬鏂逛究鐨勫箍鍛婃爮
 * �?寔鏃犻檺寰幆锛岃嚜鍔ㄧ炕椤碉紝缈婚�?�鐗规晥
 *
 * @author Sai �?寔鑷姩缈婚�??
 */
public class ConvenientBanner<T> extends LinearLayout {
    private CBViewHolderCreator holderCreator;
    private List<T> mDatas;
    private int[] page_indicatorId;
    private ArrayList<ImageView> mPointViews = new ArrayList<ImageView>();
    private CBPageChangeListener pageChangeListener;
    private CBPageAdapter pageAdapter;
    private CBLoopViewPager viewPager;
    private ViewGroup loPageTurningPoint;
    private long autoTurningTime;
    private boolean turning;
    private boolean canTurn = false;
    private boolean manualPageable = true;

    public enum Transformer {
        DefaultTransformer("DefaultTransformer"), AccordionTransformer(
                "AccordionTransformer"), BackgroundToForegroundTransformer(
                "BackgroundToForegroundTransformer"), CubeInTransformer(
                "CubeInTransformer"), CubeOutTransformer("CubeOutTransformer"), DepthPageTransformer(
                "DepthPageTransformer"), FlipHorizontalTransformer(
                "FlipHorizontalTransformer"), FlipVerticalTransformer(
                "FlipVerticalTransformer"), ForegroundToBackgroundTransformer(
                "ForegroundToBackgroundTransformer"), RotateDownTransformer(
                "RotateDownTransformer"), RotateUpTransformer(
                "RotateUpTransformer"), StackTransformer("StackTransformer"), TabletTransformer(
                "TabletTransformer"), ZoomInTransformer("ZoomInTransformer"), ZoomOutSlideTransformer(
                "ZoomOutSlideTransformer"), ZoomOutTranformer(
                "ZoomOutTranformer");

        private final String className;

        // 鏋勯�犲櫒榛樿涔熷彧鑳芥槸private, 浠庤�屼繚璇佹�?�閫犲嚱鏁板彧鑳藉湪鍐呴儴浣跨敤
        Transformer(String className) {
            this.className = className;
        }

        public String getClassName() {
            return className;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearFocus();
        clearAnimation();
        clearDisappearingChildren();
        clearData();
    }

    //    private MyHandler timerHandler = new MyHandler(this);
//    private Timer mTimer = null;
//    private TimerTask task = null;
//
//    private static class MyHandler extends Handler {
//        public WeakReference<ConvenientBanner> weakReference;
//
//        public MyHandler(ConvenientBanner instance) {
//            weakReference = new WeakReference<ConvenientBanner>(instance);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 100) {
//                ConvenientBanner _instance = weakReference.get();
//                if (_instance != null) {
//                    if (_instance.viewPager != null && _instance.turning) {
//                        int page = _instance.viewPager.getCurrentItem() + 1;
//                        _instance.viewPager.setCurrentItem(page);
//                    }
//                }
//            }
//        }
//    }

    private static Handler timeHandler = new Handler();
    private WeakReference<ConvenientBanner> weakReference = new WeakReference<ConvenientBanner>(this);
    public Runnable adSwitchTask = new Runnable() {
        @Override
        public void run() {
            ConvenientBanner _instance = weakReference.get();
            if (_instance != null) {
                if (_instance.viewPager != null && _instance.turning) {
                    int page = _instance.viewPager.getCurrentItem() + 1;
                    _instance.viewPager.setCurrentItem(page);
                    _instance.timeHandler.postDelayed(_instance.adSwitchTask, _instance.autoTurningTime);
                }
            }
        }
    };

    public ConvenientBanner(Context context) {
        this(context, null);
    }

    public ConvenientBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ConvenientBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    
    private void init(Context context) {
    	if (isInEditMode()) {
			return;
		}
        View hView = LayoutInflater.from(context).inflate(
                R.layout.include_viewpager, this, true);
        viewPager = (CBLoopViewPager) hView.findViewById(R.id.cbLoopViewPager);
        loPageTurningPoint = (ViewGroup) hView
                .findViewById(R.id.loPageTurningPoint);
        initViewPagerScroll();
    }

    public ConvenientBanner setPages(CBViewHolderCreator holderCreator, List<T> datas) {
        this.mDatas = datas;
        this.holderCreator = holderCreator;
        pageAdapter = new CBPageAdapter(holderCreator, mDatas);
        viewPager.setAdapter(pageAdapter);
        viewPager.setBoundaryCaching(true);
        if (page_indicatorId != null)
            setPageIndicator(page_indicatorId);
        return this;
    }

    /**
     * 璁剧疆搴曢儴鎸囩ず鍣ㄦ槸鍚﹀彲瑙�?
     *
     * @param visible
     */
    public ConvenientBanner setPointViewVisible(boolean visible) {
        loPageTurningPoint.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 搴曢儴鎸囩ず鍣ㄨ祫婧愬浘鐗�
     *
     * @param page_indicatorId
     */
    public ConvenientBanner setPageIndicator(int[] page_indicatorId) {
        loPageTurningPoint.removeAllViews();
        mPointViews.clear();
        this.page_indicatorId = page_indicatorId;
        if (mDatas == null || mDatas.size() < 2) return this;
        for (int count = 0; count < mDatas.size(); count++) {
            // 缈婚〉鎸囩ず鐨勭�?
        	int space = DataMgr.dip2px(5f);
            ImageView pointView = new ImageView(getContext());
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            p.gravity = Gravity.CENTER_VERTICAL;
            pointView.setLayoutParams(p);
            pointView.setPadding(space, 0, space, 0);
            //pointView.setLayoutParams(new ViewGroup.LayoutParams(space, space));
            if (mPointViews.isEmpty())
                pointView.setImageResource(page_indicatorId[1]);
            else
                pointView.setImageResource(page_indicatorId[0]);
            mPointViews.add(pointView);
            loPageTurningPoint.addView(pointView);
        }
        pageChangeListener = new CBPageChangeListener(mPointViews,
                page_indicatorId);
        pageChangeListener.pageListener = pageListener;
        viewPager.setOnPageChangeListener(pageChangeListener);
        viewPager.setCurrentItem(currentIndex);

        return this;
    }
    
    public int currentIndex;
    public PageChangedListener pageListener;
    
    public interface PageChangedListener {
		public void onChanged(int index);
	}

    /***
     * 鏄惁寮�鍚簡缈婚�?
     *
     * @return
     */
    public boolean isTurning() {
        return turning;
    }

    /***
     * 寮�濮嬬炕椤�?
     *
     * @param autoTurningTime 鑷姩缈婚�?�鏃堕棿
     * @return
     */
    public ConvenientBanner startTurning(long autoTurningTime) {
        //濡傛灉鏄鍦ㄧ炕椤电殑璇濆厛鍋滄帀
        if (turning) {
            stopTurning();
        }

        if (CollectionUtils.getSize(mDatas) <= 1) {
            return this;
        }

        //璁剧疆鍙互缈婚〉骞跺紑鍚炕椤�?
        canTurn = true;
        this.autoTurningTime = autoTurningTime;
        turning = true;
        timeHandler.postDelayed(adSwitchTask, autoTurningTime);

//        if (mTimer == null) {
//            mTimer = new Timer();
//        }
//        if (task == null) {
//            task = new TimerTask() {
//                @Override
//                public void run() {
//                    timerHandler.sendEmptyMessage(100);
//                }
//            };
//        }
//        mTimer.schedule(task, 3000);

        return this;
    }

    public ConvenientBanner startTurning(long autoTurningTime, boolean autoTurn) {
        //濡傛灉鏄鍦ㄧ炕椤电殑璇濆厛鍋滄帀
        if (turning) {
            stopTurning();
        }

        if (autoTurn) {
            canTurn = true;
            this.autoTurningTime = autoTurningTime;
            turning = true;
            timeHandler.postDelayed(adSwitchTask, autoTurningTime);
        }

        return this;
    }


    public void stopTurning() {
        turning = false;
        timeHandler.removeCallbacks(adSwitchTask);

//        if (mTimer != null) {
//            mTimer.cancel();
//            mTimer = null;
//        }
//
//        if (task != null) {
//            task.cancel();
//            task = null;
//        }
    }

    public void clearData() {
        timeHandler.removeCallbacks(adSwitchTask);
        timeHandler.removeCallbacksAndMessages(null);

//        if (mTimer != null) {
//            mTimer.cancel();
//            mTimer = null;
//        }
//
//        if (task != null) {
//            task.cancel();
//            task = null;
//        }
//
//        if (timerHandler != null) {
//            timerHandler.removeCallbacksAndMessages(null);
//            timerHandler = null;
//        }

    }

    /**
     * 鑷畾涔夌炕椤靛姩鐢绘晥鏋�
     *
     * @param transformer
     * @return
     */
    public ConvenientBanner setPageTransformer(PageTransformer transformer) {
        viewPager.setPageTransformer(true, transformer);
        return this;
    }

    /**
     * 鑷畾涔夌炕椤靛姩鐢绘晥鏋滐紝浣跨敤宸插瓨鍦ㄧ殑鏁堟�?
     *
     * @param transformer
     * @return
     */
    public ConvenientBanner setPageTransformer(Transformer transformer) {
//        try {
//            viewPager
//                    .setPageTransformer(
//                            true,
//                            (PageTransformer) Class.forName(
//                                    "com.third.view.banner.convenientBanner.transforms."
//                                            + transformer.getClassName())
//                                    .newInstance());
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        return this;
    }

    /**
     * 璁剧疆ViewPager鐨勬粦鍔ㄩ�熷害
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(
                    viewPager.getContext());
//			scroller.setScrollDuration(1500);
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean isManualPageable() {
        return manualPageable;
    }

    public void setManualPageable(boolean manualPageable) {
        this.manualPageable = manualPageable;
    }

    //瑙︾鎺т欢鐨勬椂鍊欙紝缈婚�?�搴旇鍋滄锛�?寮�鐨勬椂鍊欏鏋�?箣鍓嶆槸寮�鍚簡缈婚�?�鐨勮瘽鍒欓噸鏂板惎鍔ㄧ炕椤�?
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            // 寮�濮嬬炕椤�?
            if (canTurn) startTurning(autoTurningTime);
        } else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 鍋滄缈婚�??
            if (canTurn) stopTurning();
        }
        if (manualPageable) return super.dispatchTouchEvent(ev);

        else return true;
        
    }

}
