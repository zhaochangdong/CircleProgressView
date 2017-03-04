package main.zhaocd.com.circleprogressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by dell on 2017/2/25.
 */

public class CircleProgressView extends View {

    private Context mContext;
    private Paint mBackgroudPaint;//圆的画笔
    private Paint mFrontPaint;//进度条的画笔
    private Paint mTextPaint;//数字的画笔
    private int mWidth;//控件的宽度
    private int mHeight;//控件的高度
    private  float redis = 200;//默认圆的半径
    private float roundWidth = 50;//默认圆形进度的宽度
    private float textSize = 50;//默认字体的大小
    private int progress = 0;//记录进度值
    private int maxProgress = 100;//最大进度值
    private int backProgressColor = 0xff778899;//默认背景的颜色
    private int roundProgressColor = 0xff00BFFF;//默认圆形进度条的颜色
    private int textColor = 0xff008080;//默认字体的颜色
    private int mXCenter;//圆心的x坐标
    private int mYCenter;//圆心的Y坐标

    public void setProgress(int progress) {
        this.progress = progress;
        if(progress<=maxProgress){//判断progress是否大于maxProgress
            invalidate();//调用这个方法，会重新绘制
        }
    }
    public int getProgress() {
        return progress;
    }

    public CircleProgressView(Context context) {
        this(context,null);
    }
    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initResource(attrs);
        //getPhoneCenter();
    }

    private void initResource(AttributeSet attrs) {
        //获取我们自定义的文件名，RoundProgressBar是attrs中定义的name
        TypedArray mTypeArray = mContext.obtainStyledAttributes(attrs,R.styleable.RoundProgressBar,0,0);
        //获取背景色，如果没有设置则使用默认
        backProgressColor = mTypeArray.getColor(R.styleable.RoundProgressBar_roundColor,backProgressColor);
        //获取进度条颜色，如果没有设置则使用默认
        roundProgressColor = mTypeArray.getColor(R.styleable.RoundProgressBar_roundProgressColor,roundProgressColor);
        //获取背景圆的半径，如果没有设置则使用默认
        redis = mTypeArray.getDimension(R.styleable.RoundProgressBar_roundWidth,redis);
        //进度条的宽度设置为半径的1/5
        roundWidth = redis/5;
        //获取文本的颜色，如果没有设置则使用默认
        textColor = mTypeArray.getColor(R.styleable.RoundProgressBar_textColor,textColor);
        //获取文本的大小，如果没有设置则使用默认
        textSize = mTypeArray.getDimension(R.styleable.RoundProgressBar_textSize,textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getRealSize(widthMeasureSpec);
        mHeight = getRealSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBackgroudPaint = new Paint();//初始化背景画笔
        mBackgroudPaint.setColor(backProgressColor);//
        mBackgroudPaint.setAlpha(100);//透明度   0~255  从完全透明到不透明
        mBackgroudPaint.setAntiAlias(true);//消除锯齿
        mBackgroudPaint.setStyle(Paint.Style.FILL);//实心圆
        //canvas.drawCircle(mXCenter,mYCenter,redis,mBackgroudPaint);//制定中心点的坐标，半径
        canvas.drawCircle(mWidth/2,mHeight/2,redis,mBackgroudPaint);

        mTextPaint = new Paint();//初始化数字的画笔
        mTextPaint.setColor(textColor);//
        mTextPaint.setAntiAlias(true);//消除锯齿
        mTextPaint.setTextSize(textSize);//
        mTextPaint.setTextAlign(Paint.Align.CENTER);//设置文字的对齐方式
        //canvas.drawText(progress+"%",mXCenter,mYCenter, mTextPaint);//progress是加载的进度值
        canvas.drawText(progress+"%",mWidth/2,mHeight/2, mTextPaint);

        mFrontPaint = new Paint();
        mFrontPaint.setColor(roundProgressColor);
        mFrontPaint.setAntiAlias(true);//消除锯齿
        mFrontPaint.setStyle(Paint.Style.STROKE);//空心圆
        mFrontPaint.setStrokeWidth(roundWidth);
        mFrontPaint.setAlpha(200);//透明度
        //确定一个矩形的区域
        //RectF oval = new RectF(mXCenter-redis+roundWidth/2,mYCenter-redis+roundWidth/2,mXCenter+redis-roundWidth/2,mYCenter+redis-roundWidth/2);
        RectF oval = new RectF(mWidth/2-redis+roundWidth/2,mHeight/2-redis+roundWidth/2,mWidth/2+redis-roundWidth/2,mHeight/2+redis-roundWidth/2);
        progress = (int)((progress*1.0/maxProgress)*360);
        /**
         * 绘制扇形
         * 第一个参数：确定的矩形区域
         * 第二个参数：开始绘制的角度，从12点中开始绘制，角度为-90°
         * 第三个参数：是否把弧形的2个点连接起来，false：不连接
         * 第四个参数：画笔
         */
        canvas.drawArc(oval,-90,progress,false,mFrontPaint);
    }

    private int getRealSize(int measureSpec){
        float res = -1;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if(mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED){
            res = redis*2;
        }else{
            res = size;
        }
        return (int)res;
    }

    /**
     * 得到屏幕的中心点
     */
    private void getPhoneCenter(){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mXCenter = wm.getDefaultDisplay().getWidth()/2;
        mYCenter = wm.getDefaultDisplay().getHeight()/2;
    }
}
