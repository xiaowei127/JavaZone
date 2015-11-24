package com.example.mylib_test.activity.touch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;


public class Frame_HorizontalScrollView_OutConfict1 extends LinearLayout{
	private static final String TAG = "Frame_HorizontalScrollView_OutConfict1";
	private Scroller mScroller;
	private GestureDetector mGestureDetector;
	private Context context;
	
	

	public Frame_HorizontalScrollView_OutConfict1(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mScroller = new Scroller(context);
		this.context=context;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN :
			resetGesture();
			Log.i(TAG, "dispatchTouchEvent ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i(TAG, "dispatchTouchEvent ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			break;

		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
	
	private void resetGesture() {
		mGestureDetector = new GestureDetector(context,	new CustomGestureListener());
		first = true;
	}

	private int downx,downy;
	private boolean first;
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean intercepter=false;
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN :
			intercepter=false;
			downx=(int) ev.getX();
			downy=(int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaX=(int)ev.getX()-downx;
			int deltaY=(int)ev.getY()-downy;
			if(Math.abs(deltaX)>Math.abs(deltaY)){
				Log.i(TAG, "ACTION_MOVE intercepter   true \tX:"+ev.getX()+"  \ty:"+ev.getY());
				intercepter=true;
			}else{
				intercepter=false;
				Log.i(TAG, " ACTION_MOVE intercepter   false");
			}
			break;
		case MotionEvent.ACTION_UP:
			intercepter=false;
			break;

		default:
			break;
		}
		return intercepter;
	}

	public Frame_HorizontalScrollView_OutConfict1(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public Frame_HorizontalScrollView_OutConfict1(Context context) {
		this(context,null);
	}
	//���ô˷���������Ŀ��λ��
		public void smoothScrollTo(int fx, int fy) {
			int dx = fx - mScroller.getFinalX();
			int dy = fy - mScroller.getFinalY();
			smoothScrollBy(dx, dy);
		}
		//���ô˷������ù��������ƫ��
		public void smoothScrollBy(int dx, int dy) {

			//����mScroller�Ĺ���ƫ����
			mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
			invalidate();//����������invalidate()���ܱ�֤computeScroll()�ᱻ���ã�����һ����ˢ�½��棬����������Ч��
		}
		
		@Override
		public void computeScroll() {
			//���ж�mScroller�����Ƿ����
			if (mScroller.computeScrollOffset()) {
				//�������View��scrollTo()���ʵ�ʵĹ���
				scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
				//������ø÷���������һ���ܿ�������Ч��
				postInvalidate();
			}
			super.computeScroll();
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.i(TAG, "ACTION_DOWN  mGestureDetector\tX:"+event.getX()+"  \ty:"+event.getY());
				return mGestureDetector.onTouchEvent(event);
			case MotionEvent.ACTION_UP :
//				Log.i(TAG, "get Sy" + getScrollY());
				break;
			default:
				Log.i(TAG, "ACTION_Move mGestureDetector\tX:"+event.getX()+"  \ty:"+event.getY());
				return mGestureDetector.onTouchEvent(event);
			}
			return super.onTouchEvent(event);
		}
		class CustomGestureListener implements GestureDetector.OnGestureListener {

			@Override
			public boolean onDown(MotionEvent e) {
				return true;
			}

			@Override
			public void onShowPress(MotionEvent e) {
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				int dis = (int) distanceX;
				Log.i(TAG,"dis"+ dis );
				if(e1!=null){
					System.err.println("e1X:"+e1.getX());
				}
				if(e2!=null){
					System.err.println("e2X:"+e2.getX()+"\te2History"+e2.getSize());
				}
				if (first&&e1==null) {
					first=false;
				}else{
					smoothScrollBy(dis, 0);
				}
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				return false;
			}
			
		}

}