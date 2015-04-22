package com.yoyo.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by liumin on 2015/4/22.
 */
public class SwipeView extends LinearLayout{
    private ViewDragHelper dragHelper;
    private View contentView;
    private View actionView;
    private int dragDistance; // 拖动距离
    private int draggedX;

    public SwipeView(Context context) {
        this(context,null);
    }

    public SwipeView(Context context, AttributeSet attrs) {
        this(context,attrs, -1);
    }

    public SwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        dragHelper = ViewDragHelper.create(this, new DragHelperCallBack());
    }
    // 获取子contentView和actionView
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        actionView = getChildAt(1);
        if(actionView != null)
            actionView.setVisibility(View.GONE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        dragDistance = actionView.getMeasuredWidth();
    }

    // 把容器的事件处理委托给ViewDragHelper对象
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(dragHelper.shouldInterceptTouchEvent(ev)){
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 我们可以通过ViewDragHelper.Callback来监听以下几种事件：
         1.拖动的状态改变
         2.被拖动的view的位置改变
         3.被拖动的view被放开的时间和位置
     */
    private class DragHelperCallBack extends ViewDragHelper.Callback{
        //DragHelperCallback的tryCaptureView方法，用来确定contentView和actionView是可以拖动的
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return (child == actionView) || (child == contentView);
        }

        //被拖动的view位置改变的时候调用，如果被拖动的view是contentView，我们需要在这里更新actionView的位置，反之亦然。
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            draggedX = left;
            System.out.println("---> onViewPositionChanged : left" + left);
            if(changedView == contentView){
                actionView.offsetLeftAndRight(dx);
            }else{
                contentView.offsetLeftAndRight(dx);
            }

            if(actionView.getVisibility() == View.GONE){
                actionView.setVisibility(View.VISIBLE);
            }

            invalidate();
        }

        /**
         * @param child   被拖动到view
         * @param left  移动到达的x轴的距离
         * @param dx  建议的移动的x距离
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            System.out.println("---> clampViewPositionHorizontal -> left: " + left);
            if(child == contentView){
                int leftBound = getPaddingLeft();
                int minLeftBound = -leftBound - dragDistance;
                int newLeft = Math.min(Math.max(minLeftBound, left), 0);

                System.out.println("---> clampViewPositionHorizontal : contentView ----------");
                System.out.println("---> contentView ----------dragDistance： " + dragDistance);
                System.out.println("---> contentView -> minLeftBound: " + minLeftBound);
                System.out.println("---> contentView -> newLeft: " + newLeft);
                return newLeft;
            }else{
                int minLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() - dragDistance;
                int maxLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() + getPaddingRight();
                int newLeft = Math.min(Math.max(minLeftBound, left) , maxLeftBound);

                System.out.println("---> clampViewPositionHorizontal : actionView ----------");
                System.out.println("---> actionView -> minLeftBound:" + minLeftBound);
                System.out.println("---> actionView -> maxLeftBound:" + maxLeftBound);
                System.out.println("---> actionView -> newLeft:" + newLeft);
                return newLeft;
            }
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return dragDistance;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            double auto_open_velocity_limit = 800.0;
            boolean openAction =  false;
            System.out.println("==> xvel: " + xvel);
            System.out.println("---> draggedX : " + draggedX + " , dragDistance/2: " + dragDistance/2);
            if(xvel > auto_open_velocity_limit){
                openAction = false;
            }else if(xvel < -auto_open_velocity_limit){
                openAction = true;
            }else if(draggedX <= -dragDistance/2){
                openAction = true;
            }else if(draggedX > -dragDistance/2){
                openAction = false;
            }

            int settleDestX = openAction ? -dragDistance : 0;
            dragHelper.smoothSlideViewTo(contentView, settleDestX, 0);
            ViewCompat.postInvalidateOnAnimation(SwipeView.this);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if(dragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
