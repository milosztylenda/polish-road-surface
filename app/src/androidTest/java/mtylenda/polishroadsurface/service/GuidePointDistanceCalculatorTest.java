package mtylenda.polishroadsurface.service;

import android.location.Location;

import org.junit.Test;

import mtylenda.polishroadsurface.model.GuidePoint;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GuidePointDistanceCalculatorTest {

    private GuidePointDistanceCalculator calculator = new GuidePointDistanceCalculator();

    @Test
    public void should_find_two_closest_points_to_given_location() {
        // given
        Location location = mock(Location.class);
        when(location.getLongitude()).thenReturn(20.8858468);
        when(location.getLatitude()).thenReturn(52.1014747);

        // when
        GuidePoint[] twoClosestGuidePoints = calculator.findTwoClosestGuidePoints(location);

        // then
        assertEquals("Pruszków", twoClosestGuidePoints[0].getName());
        assertEquals("Piaseczno", twoClosestGuidePoints[1].getName());
    }
}
