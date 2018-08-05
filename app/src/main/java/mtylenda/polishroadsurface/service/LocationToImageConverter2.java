package mtylenda.polishroadsurface.service;

import android.graphics.PointF;
import android.location.Location;

import static java.lang.Math.abs;

@Deprecated
public class LocationToImageConverter2 {

    public PointF convert(Location location) {



        float MAP_HEIGTH = 2980;
        float MAP_WIDTH = 3167;

        float e = 24.220f; // longitude
        float w = 14.0638283f;
        float n = 54.9090641f; // latitude
        //float s = 48.9625711f;
        float s = 48.93f;

        float ewSpan = abs(e - w);
        float nsSpan = abs(n - s);

        float ewPix = MAP_WIDTH / ewSpan;
        float nsPix = MAP_HEIGTH / nsSpan;

        float multY = (float) (0.97 + (n - location.getLatitude()) / nsSpan * 0.03);

        float x = (float) ((location.getLongitude() - w) * ewPix);
        float y = (float) (((n - location.getLatitude()) * multY) * nsPix);

        return new PointF(x, y);
    }
}

// WSCHOD 50.8584605°N, 24.1405483°E
//            // Terespol 52.0747400°N, 23.6152118°E
//            // Sejny 54.1067200°N, 23.3488523°E
//            location.setLongitude(23.3488523);
//            location.setLatitude(54.1067200);
//
//            mapImageView.setEast(locationToImageConverter.convert(location));
//
//            // ZACHOD 52.8431812°N, 14.1228007°E
//            // cedynia 52.8792120°N, 14.2025163°E
//            location.setLongitude(14.2025163);
//            location.setLatitude(52.8792120);
//
//            mapImageView.setWest(locationToImageConverter.convert(location));
//
//            // POLNOC 54.8355652°N
//            location.setLongitude(18.10);
//            location.setLatitude(54.8355652);
//
//            mapImageView.setNorth(locationToImageConverter.convert(location));
//
//            // POLUDNIE 49.0031412°N, 22.8589274°E
//            location.setLongitude(22.858927);
//            location.setLatitude(49.0031412);
//
//            mapImageView.setSouth(locationToImageConverter.convert(location));
//
//            // Dynow 49.8148800°N, 22.2320251°E
//            // Ustrzyki 49.4299400°N, 22.5866965°E
//            location.setLongitude(22.5866965);
//            location.setLatitude(49.4299400);
//
//            mapImageView.setTown1(locationToImageConverter.convert(location));
//
//            // Maszewo 53.4957600°N, 15.0621365°E
//            location.setLongitude(15.0621365);
//            location.setLatitude(53.4957600);
//
//            mapImageView.setTown2(locationToImageConverter.convert(location));