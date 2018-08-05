package mtylenda.polishroadsurface.service;

import android.graphics.Point;
import android.graphics.PointF;
import android.location.Location;

import org.junit.Test;

import mtylenda.polishroadsurface.model.GuidePoint;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocationToImageConverterTest {

    private GuidePointDistanceCalculator distanceCalculator = mock(GuidePointDistanceCalculator.class);
    private LocationToImageConverter converter = new LocationToImageConverter(distanceCalculator);

    @Test
    public void should_convert_location_to_image_coords() throws Exception {
        // given
        Location location = mock(Location.class);
        when(location.getLatitude()).thenReturn(51.0);
        when(location.getLongitude()).thenReturn(11.0);
        when(distanceCalculator.findTwoClosestGuidePoints(location)).thenReturn(
                new GuidePoint[]{
                        new GuidePoint("A", new Point(100, 100), new PointF(10f, 52f)),
                        new GuidePoint("B", new Point(200, 150), new PointF(12f, 50f))
                }
        );

        // when
        PointF mapPoint = converter.convert(location);

        // then
        assertEquals(new PointF(150, 125), mapPoint);
    }
}
