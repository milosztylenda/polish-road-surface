package mtylenda.polishroadsurface.service;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

public class MatrixCalculator {

    private static final String TAG = "MatrixCalculator";

    private Matrix matrix = new Matrix();
    private PointF touchStart = new PointF();

    public Matrix calculateMatrix(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                touchStart.set(event.getX(), event.getY());
                Log.d(TAG, "DOWN: " + event.getX() + " " + event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                matrix.postTranslate(event.getX() - touchStart.x, event.getY() - touchStart.y);
                Log.d(TAG, "MOVE: " + event.getX() + " " + event.getY());
        }
        return matrix;
    }
}
