package mtylenda.polishroadsurface.model;

import android.graphics.Point;
import android.graphics.PointF;
import android.location.Location;

public class GuidePoint {

    private String name;
    private Point mapPoint;
    private Location location;

    public GuidePoint(String name, Point mapPoint, PointF locationPoint) {
        this.name = name;
        this.mapPoint = mapPoint;
        location = new Location("GuidePoint");
        location.setLatitude(locationPoint.y);
        location.setLongitude(locationPoint.x);
    }

    public String getName() {
        return name;
    }

    public Point getMapPoint() {
        return mapPoint;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GuidePoint that = (GuidePoint) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
