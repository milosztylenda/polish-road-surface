package mtylenda.polishroadsurface.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;


public class MapView extends SubsamplingScaleImageView {

    private int strokeWidth;
    private PointF vCenter = new PointF();
    private Paint paint = new Paint();
    private PointF currentPosition = new PointF(200, 200);

    public MapView(Context context, AttributeSet set) {
        super(context, set);

        int densityDpi = getResources().getDisplayMetrics().densityDpi;
        strokeWidth = (int) ((float) densityDpi / 60.f);
    }

    public void setCurrentPosition(PointF position) {
        currentPosition = position;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw before image is ready so it doesn't move around during setup.
        if (!isReady()) {
            return;
        }

        drawPoint(canvas, currentPosition);

        //paint.setColor(Color.BLACK);
//        paint.setTextSize(50);
//        canvas.drawText(String.valueOf(counter++), 20, 200, paint);
    }

    private void drawPoint(Canvas canvas, PointF point) {
        sourceToViewCoord(point, vCenter);

        paint.setStrokeWidth(strokeWidth * 2);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(vCenter.x, vCenter.y, 10, paint);
    }
}
