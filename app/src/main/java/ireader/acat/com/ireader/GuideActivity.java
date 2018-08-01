package ireader.acat.com.ireader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.PluralsRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GuideActivity extends Activity implements View.OnTouchListener{

    private final int[] resId = {R.mipmap.guide00,R.mipmap.guide01,R.mipmap.guide02};
    private ViewPager viewPager = null;
    private int lastX = 0;
    private boolean isRunning = false;//只允许调用一次

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }

    private void initView(){
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return resId.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container,int position){
                ImageView iv = new ImageView(GuideActivity.this);//动态创建
                iv.setBackgroundResource(resId[position]);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(iv);
                return iv;
            }

            @Override
            public void destroyItem(ViewGroup container,int position,Object object){
                container.removeView((View)object);
            }
        });

        viewPager.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = (int)event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //是否到达第三张照片
                if(viewPager.getCurrentItem() == resId.length-1 && lastX - event.getX() >= 100){
                    jumpToMativity();
                }
                break;
        }
        return false;
    }

    private void jumpToMativity(){
        if(isRunning){
            return;
        }
        isRunning = true;
        Intent iv = new Intent(this,MainActivity.class);
        startActivity(iv);
        finish();
    }

}
