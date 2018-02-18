package mtylenda.polishroadsurface.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.piasy.biv.view.BigImageView;

import mtylenda.polishroadsurface.R;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_main);

        BigImageView bigImageView = (BigImageView) findViewById(R.id.mBigImage);
        bigImageView.showImage(Uri.parse("http://ssc.siskom.waw.pl/mapa-nawierzchni/mapa-nawierzchnia.png"));
    }
}
