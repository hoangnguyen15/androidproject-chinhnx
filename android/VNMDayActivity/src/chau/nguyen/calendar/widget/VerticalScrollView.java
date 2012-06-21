package chau.nguyen.calendar.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class VerticalScrollView extends ViewGroup {
    private static final int INVALID_SCREEN = -1;
    
    /**
     * The velocity at which a fling gesture will cause us to snap to the next screen
     */
    private static final int SNAP_VELOCITY = 500;
    
    private boolean mFirstLayout = true;

    private int mCurrentScreen;
    private int mNextScreen = INVALID_SCREEN;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
        
    private float mLastMotionY;
    private int mScrollY;
    
    private final static int TOUCH_STATE_REST = 0;
    private final static int TOUCH_STATE_SCROLLING = 1;

    private int mTouchState = TOUCH_STATE_REST;   

    private int mTouchSlop;
    private int mMaximumVelocity;
    
    private OnScreenSelectedListener listener;
    
    public VerticalScrollView(Context context) {
    	super(context);
    	init();
    }

    /**
     * Used to inflate the ScrollView from XML.
     *
     * @param context The application's context.
     * @param attrs The attribtues set containing the ScrollView's customization values.
     */
    public VerticalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Used to inflate the ScrollView from XML.
     *
     * @param context The application's context.
     * @param attrs The attribtues set containing the ScrollView's customization values.
     * @param defStyle Unused.
     */
    public VerticalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
        
    public OnScreenSelectedListener getOnItemSelectedListener() {
		return listener;
	}
	public void setOnScreenSelectedListener(OnScreenSelectedListener listener) {
		this.listener = listener;
	}
					
	private boolean rotateLastView = false;
	public void rotateLastView() {
		this.rotateLastView = true;		
		this.requestLayout();
	}
	
	private void rotateLastViewInLayout() {
		View lastView = getChildAt(getChildCount() - 1);
		this.removeViewAt(getChildCount() - 1);		
		mCurrentScreen++;
    	mNextScreen = INVALID_SCREEN;
    	mScrollY = mCurrentScreen * getHeight();
    	scrollTo(0, mScrollY);
    	this.rotateLastView = false;    	
    	super.addViewInLayout(lastView, 0, 
				new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
	private boolean rotateFirstView = false;
	public void rotateFirstView() {
		this.rotateFirstView = true;
		this.requestLayout();
	}
	
	private void rotateFirstViewInLayout() {
		View firstView = getChildAt(0);
		this.removeViewInLayout(firstView);
		mCurrentScreen--;
		mNextScreen = INVALID_SCREEN;
		mScrollY = mCurrentScreen * getHeight();
    	scrollTo(0, mScrollY);
		rotateFirstView = false;
		super.addViewInLayout(firstView, getChildCount(), 
				new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
		
	/**
     * Initializes various states for this workspace.
     */
    private void init() {
        Context context = getContext();
        mScroller = new Scroller(context);//, new ElasticInterpolator(5f));// new BackInterpolator(Type.OUT, 3f));//new ElasticInterpolator(Type.OUT, 1, 0.3f));        
        mCurrentScreen = 0;

        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();        
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }   
    
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mScrollY = mScroller.getCurrY();
            scrollTo(0, mScrollY);
            postInvalidate();
        } else if (mNextScreen != INVALID_SCREEN) {
            mCurrentScreen = Math.max(0, Math.min(mNextScreen, getChildCount() - 1));
            fireScreenSelectedEvent(mCurrentScreen);
            mNextScreen = INVALID_SCREEN;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // ViewGroup.dispatchDraw() supports many features we don't need:
        // clip to padding, layout animation, animation listener, disappearing
        // children, etc. The following implementation attempts to fast-track
        // the drawing dispatch by drawing only what we know needs to be drawn.

        boolean fastDraw = mTouchState != TOUCH_STATE_SCROLLING && mNextScreen == INVALID_SCREEN;
        // If we are not scrolling or flinging, draw only the current screen
        if (fastDraw) {
    		drawChild(canvas, getChildAt(mCurrentScreen), getDrawingTime());
    		mScrollY = mCurrentScreen * getHeight();        	        
        } else {        	
            final long drawingTime = getDrawingTime();
            // If we are flinging, draw only the current screen and the target screen
            if (mNextScreen >= 0 && mNextScreen < getChildCount() &&
                    Math.abs(mCurrentScreen - mNextScreen) == 1) {
                drawChild(canvas, getChildAt(mCurrentScreen), drawingTime);
                drawChild(canvas, getChildAt(mNextScreen), drawingTime);
            } else {
                // If we are scrolling, draw all of our children
                final int count = getChildCount();
                for (int i = 0; i < count; i++) {
                    drawChild(canvas, getChildAt(i), drawingTime);
                }
            }
        }                
    }    

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	if (rotateLastView) {
    		this.rotateLastViewInLayout();
    	}
    	if (rotateFirstView) {
    		this.rotateFirstViewInLayout();
    	}
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int height = MeasureSpec.getSize(heightMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("ScrollView can only be used in EXACTLY mode.");
        }

        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("ScrollView can only be used in EXACTLY mode.");
        }

        // The children are given the same width and height as the workspace
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }


        if (mFirstLayout) {
            setHorizontalScrollBarEnabled(false);
            scrollTo(0, mCurrentScreen * height);
            setHorizontalScrollBarEnabled(true);
            mFirstLayout = false;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {    	
        int childTop = 0;

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                final int childHeight = child.getMeasuredHeight();                
                child.layout(0, childTop, child.getMeasuredWidth(), childTop + childHeight);
                childTop += childHeight;
            }
        }
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {    	
        /*
         * This method JUST determines whether we want to intercept the motion.
         * If we return true, onTouchEvent will be called and we do the actual
         * scrolling there.
         */

        /*
         * Shortcut the most recurring case: the user is in the dragging
         * state and he is moving his finger.  We want to intercept this
         * motion.
         */
        final int action = ev.getAction();
        if ((action == MotionEvent.ACTION_MOVE) && (mTouchState != TOUCH_STATE_REST)) {
            return true;
        }
        
        final float y = ev.getY();        
        
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                /*
                 * mIsBeingDragged == false, otherwise the shortcut would have caught it. Check
                 * whether the user has moved far enough from his original down touch.
                 */

                /*
                 * Locally do absolute value. mLastMotionX is set to the y value
                 * of the down event.
                 */
                final int yDiff = (int) Math.abs(y - mLastMotionY);
                
                final int touchSlop = mTouchSlop;
                boolean yMoved = yDiff > touchSlop;
                if (yMoved) {
                    // Scroll if the user moved far enough along the X axis
                    mTouchState = TOUCH_STATE_SCROLLING;
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;

            case MotionEvent.ACTION_DOWN:
                // Remember location of down touch            	
                mLastMotionY = y;
                /*
                 * If being flinged and user touches the screen, initiate drag;
                 * otherwise don't.  mScroller.isFinished should be false when
                 * being flinged.
                 */
                mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                
                // Release the drag
                mTouchState = TOUCH_STATE_REST;
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        /*
         * The only time we want to intercept motion events is if we are in the
         * drag mode.
         */
        return mTouchState != TOUCH_STATE_REST;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);

        final int action = ev.getAction();        
        final float y = ev.getY();

        switch (action) {
        case MotionEvent.ACTION_DOWN:
            /*
             * If being flinged and user touches, stop the fling. isFinished
             * will be false if being flinged.
             */
            if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
            }

            // Remember where the motion event started            
            mLastMotionY = y;            
            break;
        case MotionEvent.ACTION_MOVE:
            if (mTouchState == TOUCH_STATE_SCROLLING) {
                // Scroll to follow the motion event            	
                final int deltaY = (int) (mLastMotionY - y);                
                mLastMotionY = y;                

                if (deltaY < 0) {
                    if (mScrollY > 0) {
                        scrollBy(0, Math.max(-mScrollY, deltaY));
                        mScrollY = getScrollY();
                    }
                } else if (deltaY > 0) {
                    final int availableToScroll = getChildAt(getChildCount() - 1).getBottom() -
                            mScrollY - getHeight();
                    if (availableToScroll > 0) {
                        scrollBy(0, Math.min(availableToScroll, deltaY));
                        mScrollY = getScrollY();
                    }
                }
            }
            break;
        case MotionEvent.ACTION_UP:
            if (mTouchState == TOUCH_STATE_SCROLLING) {
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityY = (int) velocityTracker.getYVelocity();
                                
                if (velocityY > SNAP_VELOCITY && mCurrentScreen > 0) {
                    // Fling hard enough to move left
                    snapToScreen(mCurrentScreen - 1);
                } else if (velocityY < -SNAP_VELOCITY && mCurrentScreen < getChildCount() - 1) {
                    // Fling hard enough to move right
                    snapToScreen(mCurrentScreen + 1);
                } else {
                    snapToDestination();
                }                

                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
            }
            mTouchState = TOUCH_STATE_REST;
            break;
        case MotionEvent.ACTION_CANCEL:
            mTouchState = TOUCH_STATE_REST;
            snapToDestination();
        }
        
        return true;
    }   

    private void snapToDestination() {    	
        final int screenHeight = getHeight();
        final int whichScreen = (mScrollY + (screenHeight / 2)) / screenHeight;
        snapToScreen(whichScreen);
    }
    
    public int getDisplayedChild() {
    	return mCurrentScreen;
    }

    public void snapToScreen(int whichScreen) {
        if (!mScroller.isFinished()) return;
        
        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        
        final int screenDelta = Math.abs(whichScreen - mCurrentScreen);
        mNextScreen = whichScreen;
        
        final int newY = whichScreen * getHeight();
        final int delta = newY - mScrollY;
        final int duration = screenDelta * 400;

        if (!mScroller.isFinished()) mScroller.abortAnimation();
        mScroller.startScroll(0, mScrollY, 0, delta, duration > 0 ? duration : 400);
        invalidate();
    }

    public void scrollLeft() {
        if (mScroller.isFinished()) {
            if (mCurrentScreen > 0) snapToScreen(mCurrentScreen - 1);
        } else {
            if (mNextScreen > 0) snapToScreen(mNextScreen - 1);            
        }
    }

    public void scrollRight() {
        if (mScroller.isFinished()) {
            if (mCurrentScreen < getChildCount() -1) snapToScreen(mCurrentScreen + 1);
        } else {
            if (mNextScreen < getChildCount() -1) snapToScreen(mNextScreen + 1);            
        }
    }	
    
    private void fireScreenSelectedEvent(int selectedIndex) {
    	if (listener != null) {
    		listener.onSelected(selectedIndex);
    	}    	
    }
    
    public void showScreen(int whichScreen) {    	
    	mCurrentScreen = whichScreen;
    	mNextScreen = INVALID_SCREEN;
    	mScrollY = whichScreen * getHeight();
    	scrollTo(0, mScrollY);
    	postInvalidate();
    	fireScreenSelectedEvent(whichScreen);
    }
    
    public interface OnScreenSelectedListener {
    	void onSelected(int selectedIndex);
    }
}
