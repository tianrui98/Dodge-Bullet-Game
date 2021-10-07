package io.github.bbodin.yncgamelab.rendering;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import io.github.bbodin.yncgamelab.models.CellStatus;
import io.github.bbodin.yncgamelab.models.Grid;
import io.github.bbodin.yncgamelab.utils.Int2;

public class GridRenderer implements Grid.Callback, SurfaceHolder.Callback  {

    private final static String TAG = "RendererObject";

    private Grid grid;
    private SurfaceHolder holder;

    public GridRenderer (Grid g) {
        this.grid = g;
        this.grid.setCallBack(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "start surfaceCreated");
        this.holder = surfaceHolder;
        drawSurfaceView();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.d(TAG, "start surfaceChanged");
        drawSurfaceView();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "start surfaceDestroyed");
        this.holder = null;
    }

    private void drawSurfaceView() {
        Log.d(TAG, "start drawSurfaceView");
        if (grid != null && holder != null) {
            Canvas canvas = holder.lockCanvas();
            this.drawGrid(grid, canvas);
            holder.unlockCanvasAndPost(canvas);
        } else {
            Log.e(TAG, "error in drawSurfaceView");
        }
    }

    private void drawCell(Canvas canvas, Rect bounds, CellStatus status) {

        int gem_color = 0;

        switch (status) {
            case YELLOW:
                gem_color = Color.argb(255, 255, 255, 179);
                break;

            case RED:
                gem_color = Color.argb(255,251, 128, 114);
                break;

            case BLUE:
                gem_color = Color.argb(255, 128, 177, 211);
                break;
            case ORANGE:
                gem_color = Color.argb(255, 253, 180, 98);
                break;

            case GREEN:
                gem_color = Color.argb(255, 179, 222, 105);
                break;
            case PINK:
                gem_color = Color.argb(255, 252, 205, 229);
                break;

            case UNKNOWN:
            case EMPTY:
            default:
                gem_color = Color.argb(0, 0, 0, 0);
        }


        // Draw the color background
        Paint  gemPaint = new Paint();
        gemPaint.setColor(gem_color);
        gemPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        gemPaint.setStrokeWidth(5);
        canvas.drawRect(bounds, gemPaint);


    }

    private void drawGrid(Grid grid, Canvas canvas) {
        Log.d(TAG, "start drawGrid");

        if (grid == null) return;
        if (canvas == null) return;

        canvas.drawARGB(255, 255, 255, 255);

        Paint gridPaint = new Paint();
        gridPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        gridPaint.setStrokeWidth(10);
        gridPaint.setARGB(135, 0, 0, 0);

        int xcount = grid.getW();
        int ycount = grid.getH();

        for (int n = 0; n < xcount; n++) {
            float xpos = n * canvas.getWidth() / xcount ;
            canvas.drawLine(canvas.getWidth() - xpos, 0,canvas.getWidth() - xpos,  canvas.getHeight(),gridPaint);
        }

        for (int n = 0; n < ycount; n++) {
            float ypos = n * canvas.getHeight() / ycount ;
            canvas.drawLine(canvas.getWidth() - 0, ypos,canvas.getWidth() - canvas.getWidth(), ypos,gridPaint);
        }


        for (int nx = 0; nx < xcount; nx++) {
            for (int ny = 0; ny < ycount; ny++) {

                int xpos1 = nx * canvas.getWidth() / xcount ;
                int ypos1 = ny * canvas.getHeight() / ycount ;
                int xpos2 = (nx +1 ) * canvas.getWidth() / xcount ;
                int ypos2 = (ny+1 ) * canvas.getHeight() / ycount ;

                Rect bounds = new Rect (xpos1, canvas.getHeight() - ypos1, xpos2, canvas.getHeight() - ypos2);

                Int2 pos = new Int2(nx,ny);
                CellStatus status = grid.getCellStatus(pos);
                drawCell(canvas, bounds, status);

            }
        }

    }


    @Override
    public void gridChanged(Grid universe) {
        this.drawSurfaceView();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}