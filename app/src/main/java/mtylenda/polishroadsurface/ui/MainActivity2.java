package mtylenda.polishroadsurface.ui;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

import mtylenda.polishroadsurface.R;
import mtylenda.polishroadsurface.service.MatrixCalculator;

public class MainActivity2 extends AppCompatActivity implements View.OnTouchListener {

    private static Bitmap mapBitmap;
    private MatrixCalculator matrixCalculator = new MatrixCalculator();
    private ImageView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_main);

        mapView = (ImageView) findViewById(R.id.mapView);
        AssetManager assetManager = getAssets();

        try {
//            nie dziala, zawsze null Bitmap.
//            mapView.setImageBitmap(BitmapFactory.decodeFileDescriptor(assetManager.openFd("mapa.png").getFileDescriptor()));

//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inBitmap = mapView.get;


            if (mapBitmap == null) {
                mapBitmap = BitmapFactory.decodeStream(assetManager.open("mapa.png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

//        Matrix matrix = new Matrix();
//        matrix.setRotate(30);
//        matrix.postScale(0.5f, 0.5f);
//        mapView.setImageMatrix(matrix);

        mapView.setImageBitmap(mapBitmap);
        mapView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mapView.setImageMatrix(matrixCalculator.calculateMatrix(motionEvent));
        return true;
    }
}
