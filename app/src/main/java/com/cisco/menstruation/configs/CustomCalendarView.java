package com.cisco.menstruation.configs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.cisco.menstruation.menstruation.R;

/**
 * Created by Administrator on 2016/5/30.
 */
public class CustomCalendarView extends View {
    private int mInvalidateTime = 1000;
    private Paint mPaintTitle;
    private int mViewWidth; // 视图的宽度
    private int mViewHeight; // 视图的高度
    private Paint mTextPaint;
    private static CustomDate mShowDate;
    private static final int TOTAL_COL = 7; // 7列
    private static final int TOTAL_ROW = 6; // 6行
    private Row rows[]; // 行数组，每个元素代表一行
    private int mCellSpaceWidth; // 单元格间距
    private int mCellSpaceHeight;
    private Paint mCirclePaint; // 绘制圆形的画笔
    private OnCellClickListener mCellClickListener; // 单元格点击回调事件
    private int touchSlop; //
    private Paint mLinePaint;
    private Context mContext;
    private Bitmap mBmpToday;
    private boolean callBackCellSpace;

    /**
     * 单元格点击的回调接口
     *
     * @author wuwenjie
     */
    public interface OnCellClickListener {
        void clickDate(CustomDate date); // 回调点击的日期

        void changeDate(CustomDate date); // 回调滑动ViewPager改变的日期
    }

    public CustomCalendarView(Context context) {
        super(context);
        init(context);
    }

    public CustomCalendarView(Context context, OnCellClickListener listener) {
        super(context);
        this.mCellClickListener = listener;
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.GRAY);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.parseColor("#F24949")); // 红色圆形
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        Bitmap resizeBmp = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.today);
        Matrix matrix = new Matrix();
        matrix.postScale(0.5f, 0.5f); //长和宽放大缩小的比例
        mBmpToday = Bitmap.createBitmap(resizeBmp, 0, 0,
                resizeBmp.getWidth(), resizeBmp.getHeight(), matrix, true);
        initDate();
    }

    private void initDate() {
        mShowDate = new CustomDate();
        fillDate();//
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mCellSpaceWidth = mViewWidth / TOTAL_COL;
        mCellSpaceHeight = mViewHeight / TOTAL_ROW;
        mTextPaint.setTextSize(mCellSpaceWidth / 3);
        if (!callBackCellSpace) {
            callBackCellSpace = true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawCalendar(canvas);
    }

    /**
     * 绘制函数
     *
     * @param canvas
     */
    private void onDrawCalendar(Canvas canvas) {
        Log.i("sys", "draw-----------"+mCellSpaceWidth);
        for (int i = 0; i < TOTAL_ROW; i++) {
            if (rows[i] != null) {
                rows[i].drawCells(canvas);
            }
        }
        onDrawLine(canvas);
    }

    private void onDrawLine(Canvas canvas) {
        for (int i = 1; i < TOTAL_ROW + 1; i++) {
            canvas.drawLine(0, i * mCellSpaceHeight,
                    7 * mCellSpaceWidth, i * mCellSpaceHeight, mLinePaint);
        }
        canvas.drawLine(0, 0, 0, 6 * mCellSpaceHeight, mLinePaint);
        canvas.drawLine(7 * mCellSpaceWidth, 0, 7 * mCellSpaceWidth, 6 * mCellSpaceHeight, mLinePaint);
    }

    private void fillDate() {
        int monthDay = DateUtils.getCurrentMonthDay(); // 今天
        int year = mShowDate.getYear();
        int month = mShowDate.getMonth();
        int lastMonthDays = DateUtils.getMonthDays(year,
                month - 1); // 上个月的天数
        int currentMonthDays = DateUtils.getMonthDays(year,
                month); // 当前月的天数
        int firstDayWeek = DateUtils.getWeekDayFromDate(year,
                month);
        boolean isCurrentMonth = false;
        rows = new Row[TOTAL_ROW];
        Log.i("sys","++++++++++"+TOTAL_ROW);
        if (DateUtils.isCurrentMonth(mShowDate)) {
            isCurrentMonth = true;
        }
        int day = 0;
        for (int j = 0; j < TOTAL_ROW; j++) {
            rows[j] = new Row(j);
            for (int i = 0; i < TOTAL_COL; i++) {
                int position = i + j * TOTAL_COL; // 单元格位置
                // 这个月的
                if (position >= firstDayWeek
                        && position < firstDayWeek + currentMonthDays) {
                    day++;
                    rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                            mShowDate, day), State.CURRENT_MONTH_DAY, i, j);
                    // 今天
                    if (isCurrentMonth && day == monthDay) {
                        CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);
                        rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
                    }

                    if (isCurrentMonth && day > monthDay) { // 如果比这个月的今天要大，表示还没到
                        rows[j].cells[i] = new Cell(
                                CustomDate.modifiDayForObject(mShowDate, day),
                                State.UNREACH_DAY, i, j);
                    }

                    // 过去一个月
                } else if (position < firstDayWeek) {
                    rows[j].cells[i] = new Cell(new CustomDate(year,
                            month - 1, lastMonthDays
                            - (firstDayWeek - position - 1)),
                            State.PAST_MONTH_DAY, i, j);
                    // 下个月
                } else if (position >= firstDayWeek + currentMonthDays) {
                    rows[j].cells[i] = new Cell((new CustomDate(year,
                            month + 1, position - firstDayWeek
                            - currentMonthDays + 1)),
                            State.NEXT_MONTH_DAY, i, j);
                }
            }
        }
        mCellClickListener.changeDate(mShowDate);
    }

    class Row {
        public int j;

        Row(int j) {
            this.j = j;
        }

        public Cell[] cells = new Cell[TOTAL_COL];

        // 绘制单元格
        public void drawCells(Canvas canvas) {
            for (int i = 0; i < cells.length; i++) {
                if (cells[i] != null) {
                    cells[i].drawSelf(canvas);
                }
            }
        }

    }

    class Cell {
        public CustomDate date;
        public State state;
        public int i;
        public int j;

        public Cell(CustomDate date, State state, int i, int j) {
            super();
            this.date = date;
            this.state = state;
            this.i = i;
            this.j = j;
        }

        public void drawSelf(Canvas canvas) {
            switch (state) {
                case TODAY: // 今天
                    RectF rectF = new RectF((float) ((i + 0.5) * mCellSpaceWidth - mCellSpaceWidth / 3),
                            (float) ((j + 0.5) * mCellSpaceHeight - mCellSpaceWidth / 3),
                            (float) ((i + 0.5) * mCellSpaceWidth + mCellSpaceWidth / 3),
                            (float) ((j + 0.5) * mCellSpaceHeight + mCellSpaceWidth / 3));
                    canvas.drawBitmap(mBmpToday, rectF.left, rectF.top, mCirclePaint);
                    break;
                case CURRENT_MONTH_DAY: // 当前月日期
                    mTextPaint.setColor(Color.BLACK);
                    break;
                case PAST_MONTH_DAY: // 过去一个月
                    mTextPaint.setColor(Color.GRAY);
                    break;
                case NEXT_MONTH_DAY: // 下一个月
                    mTextPaint.setColor(Color.GRAY);
                    break;
                case UNREACH_DAY: // 还未到的天
                    mTextPaint.setColor(Color.BLACK);
                    break;
                default:
                    break;
            }
            // 绘制文字
            String content = date.getDay() + "";
            canvas.drawText(content,
                    (float) ((i + 0.5) * mCellSpaceWidth - mTextPaint
                            .measureText(content) / 2), (float) ((j + 0.7)
                            * mCellSpaceHeight - mTextPaint
                            .measureText(content, 0, 1) / 2), mTextPaint);
        }
    }

    /**
     * 日历上单元格的状态
     */
    enum State {
        TODAY, CURRENT_MONTH_DAY, PAST_MONTH_DAY, NEXT_MONTH_DAY, UNREACH_DAY;
    }

    // 从左往右划，上一个月
    public void leftSlide() {
        if (mShowDate.getMonth() == 1) {
            mShowDate.setMonth(12);
            mShowDate.setYear(mShowDate.getYear() - 1);
        } else {
            mShowDate.setMonth(mShowDate.getMonth() - 1);
        }
        update();
    }

    // 从右往左划，下一个月
    public void rightSlide() {
        if (mShowDate.getMonth() == 12) {
            mShowDate.setMonth(1);
            mShowDate.setYear(mShowDate.getYear() + 1);
        } else {
            mShowDate.setMonth(mShowDate.getMonth() + 1);
        }
        update();
    }

    public void update() {
        fillDate();
        invalidate();
    }
}
