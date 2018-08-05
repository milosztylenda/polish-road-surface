package mtylenda.polishroadsurface.service;

import android.graphics.PointF;
import android.location.Location;

import mtylenda.polishroadsurface.model.GuidePoint;

public class LocationToImageConverter {

    private final GuidePointDistanceCalculator distanceCalculator;

    public LocationToImageConverter(GuidePointDistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    public LocationToImageConverter() {
        this.distanceCalculator = new GuidePointDistanceCalculator();
    }


    public PointF convert(Location location) {

        GuidePoint[] closestPoints = distanceCalculator.findTwoClosestGuidePoints(location);
        GuidePoint point0 = closestPoints[0];
        GuidePoint point1 = closestPoints[1];

        float dx = (float) ((point1.getMapPoint().x - point0.getMapPoint().x) /
                (point1.getLocation().getLongitude() - point0.getLocation().getLongitude()));
        float dy = (float) ((point1.getMapPoint().y - point0.getMapPoint().y) /
                (point1.getLocation().getLatitude() - point0.getLocation().getLatitude()));

        float dlon = (float) (location.getLongitude() - point0.getLocation().getLongitude());
        float dlat = (float) (location.getLatitude() - point0.getLocation().getLatitude());

        float x = point0.getMapPoint().x + dlon * dx;
        float y = point0.getMapPoint().y + dlat * dy;

        return new PointF(x, y);
    }
}
