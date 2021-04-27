package com.example.surfaceviewexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class DrawView extends SurfaceView implements Runnable{
    private boolean mRunning;
    private Thread mGameThread = null;
    private SurfaceHolder mSurfaceHolder;
    private Context mContext;
    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mSurfaceHolder = getHolder();
    }

    @Override
    public void run() {
        Canvas canvas;
        long frameStartTime;
        long frameTime;
        final int FPS = 60;
        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        while (mRunning) {
            if (mSurfaceHolder == null){return;}
            // If valid drawing surface...
            if (mSurfaceHolder.getSurface().isValid()) {
                //record start time for current run
                frameStartTime = System.nanoTime();
                //access the canvas
                canvas = mSurfaceHolder.lockCanvas();
                if (canvas != null) {
                    canvas.save();
                    // clear the screen using black
                    canvas.drawARGB(255, 0, 0, 0);
                    try {
                        // Your drawing here
                        canvas.drawRect(0, 0, getWidth() / 2, getHeight() / 2, mPaint);
                    } finally {  //unlock and release canvas
                        canvas.restore();
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
                // calculate the time required to draw the frame in ms
                frameTime = (System.nanoTime() - frameStartTime) / 1000000;

                if (frameTime < (1000/FPS)) // if faster than the FPS -> wait until FPS matched
                {
                    try {
                        Thread.sleep((int)(1000/FPS) - frameTime);
                    } catch (InterruptedException e) {}
                }
            }
        }
    }
    /**
     * Called by MainActivity.onPause() to stop the thread.
     */
    public void pause() {
        mRunning = false;
        try {
            // Stop the thread == rejoin the main thread.
            mGameThread.join();
        } catch (InterruptedException e) {
        }
    }

    /**
     * Called by MainActivity.onResume() to start a thread.
     */
    public void resume() {
        mRunning = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }
}
