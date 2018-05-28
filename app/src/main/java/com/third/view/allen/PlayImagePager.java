package com.third.view.allen;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.he.joint.R;
import com.he.joint.mgr.DataMgr;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class PlayImagePager extends RelativeLayout {

    public PlayImagePager(Context context) {
        super(context);
        if (!isInEditMode()) {
            initView(context);
        }
    }

    public PlayImagePager(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            initView(context);
        }
    }

    public PlayImagePager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            initView(context);
        }
    }

    private AutoScrollViewPager pager;
    private ViewGroupIndicator indicator;
    private List<String> imageList;
    private ItemClickedListener mItemClickedListener;


    private void initView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.play_image_pager, this, false);
        addView(contentView);
        pager = (AutoScrollViewPager) contentView.findViewById(R.id.scroll_pager);
        indicator = (ViewGroupIndicator) contentView.findViewById(R.id.scroll_pager_indicator);

    }

    public void initData(List<String> list) {
        this.imageList = list;

        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                if (imageList == null) {
                    return 0;
                }
                return imageList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView iv = new ImageView(container.getContext());
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                iv.setLayoutParams(params);
                container.addView(iv);

                ImageLoader.getInstance().displayImage(imageList.get(position), iv, DataMgr.options);
                final int index = position;
                iv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemClickedListener != null) {
                            mItemClickedListener.itemClicked(index);
                        }
                    }
                });

                return iv;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        indicator.setParent(pager);
        pager.startAutoScroll();
    }

    public void setItemClickedListener (ItemClickedListener listener) {
        this.mItemClickedListener = listener;
    }

    public interface ItemClickedListener {
        public void itemClicked(int index);
    }

}
