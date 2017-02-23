package entity;

/**
 * Created by songchi on 17/1/19.
 */
public class GpsData {

  /**
   * course : 向北 callLetter : 13774314683 lon : 87.633622 gpsTime : 2016-8-02 07:43:49 numberPlate :
   * 4683 speed : 0 lat : 43.947167
   */

  private String course;
  private String callLetter;
  private double lon;
  private String gpsTime;
  private String numberPlate;
  private int speed;
  private double lat;

  public String getCourse() {
    return course;
  }

  public void setCourse(String course) {
    this.course = course;
  }

  public String getCallLetter() {
    return callLetter;
  }

  public void setCallLetter(String callLetter) {
    this.callLetter = callLetter;
  }

  public double getLon() {
    return lon;
  }

  public void setLon(double lon) {
    this.lon = lon;
  }

  public String getGpsTime() {
    return gpsTime;
  }

  public void setGpsTime(String gpsTime) {
    this.gpsTime = gpsTime;
  }

  public String getNumberPlate() {
    return numberPlate;
  }

  public void setNumberPlate(String numberPlate) {
    this.numberPlate = numberPlate;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }
}
