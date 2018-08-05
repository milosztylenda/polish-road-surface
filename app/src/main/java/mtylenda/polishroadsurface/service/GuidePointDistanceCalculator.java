package mtylenda.polishroadsurface.service;

import android.graphics.Point;
import android.graphics.PointF;
import android.location.Location;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mtylenda.polishroadsurface.model.GuidePoint;

public class GuidePointDistanceCalculator {

    private List<GuidePoint> guidePoints = Arrays.asList(
            new GuidePoint("Pruszków", new Point(2053, 1377), new PointF(20.8058153f, 52.1656400f)),
            new GuidePoint("Piaseczno", new Point(2121, 1421), new PointF(21.0238646f, 52.0812200f)),
            new GuidePoint("Grójec", new Point(2081, 1524), new PointF(20.8685343f, 51.8600200f)),
            new GuidePoint("Radom", new Point(2173, 1744), new PointF(21.1501038f, 51.4277346f)),

            new GuidePoint("Wołomin", new Point(2189, 1280), new PointF(21.2410424f, 52.3458800f)),
            new GuidePoint("Łochów", new Point(2324, 1186), new PointF(21.6815933f, 52.5305821f))
    );

    public GuidePoint[] findTwoClosestGuidePoints(Location location) {
        GuidePoint closest0 = Collections.min(guidePoints, new ClosestPointComparator(location));
        GuidePoint closest1 = Collections.min(
                guidePoints,
                new SecondClosestPointComparator(location, closest0)
        );
        return new GuidePoint[] {closest0, closest1};
    }

    private static class ClosestPointComparator implements Comparator<GuidePoint> {

        private final Location location;

        ClosestPointComparator(Location location) {
            this.location = location;
        }

        @Override
        public int compare(GuidePoint point0, GuidePoint point1) {
            return Float.compare(location.distanceTo(point0.getLocation()), location.distanceTo(point1.getLocation()));
        }
    }

    private static class SecondClosestPointComparator implements Comparator<GuidePoint> {

        private final Location location;
        private final GuidePoint closestPoint;

        SecondClosestPointComparator(Location location, GuidePoint closestPoint) {
            this.location = location;
            this.closestPoint = closestPoint;
        }

        @Override
        public int compare(GuidePoint point0, GuidePoint point1) {
            if (point0.equals(closestPoint)) {
                return 1;
            }
            if (point1.equals(closestPoint)) {
                return -1;
            }
            return Float.compare(location.distanceTo(point0.getLocation()), location.distanceTo(point1.getLocation()));
        }
    }
}

