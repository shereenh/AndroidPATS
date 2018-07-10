package com.parkingauthorityticketingsystem.entity;

/**
 * Created by lintang on 10/08/2017.
 */

public class Shiftlog {
    int id;
    String plateNumber;
    String state;
    String street;
    String streetNum;
    String meterNum;
    int maxTime;
    String firstObserve;
    double x_coordinate;
    double y_coordinate;

    public Shiftlog(int id, String plateNumber, String state, String street, String streetNum, String meterNum, int maxTime, String firstObserve,
                    double x_coordinate, double y_coordinate) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.state = state;
        this.street = street;
        this.streetNum = streetNum;
        this.meterNum = meterNum;
        this.maxTime = maxTime;
        this.firstObserve = firstObserve;
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;

    }

    public Shiftlog() {

    }


    public int getId() {
        return id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getState() {
        return state;
    }

    public String getStreet() {
        return street;
    }

    public String getMeterNum() {
        return meterNum;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public String getFirstObserve() {
        return firstObserve;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setMeterNum(String meterNum) {
        this.meterNum = meterNum;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public void setFirstObserve(String firstObserve) {
        this.firstObserve = firstObserve;
    }

    public String getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(String streetNum) {
        this.streetNum = streetNum;
    }

    public void setX_coordinate(double x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public void setY_coordinate(double y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    public double getX_coordinate() {
        return x_coordinate;
    }

    public double getY_coordinate() {
        return y_coordinate;
    }

    @Override
    public String toString() {
        return "Shiftlog{" +
                "id=" + id +
                ", plateNumber='" + plateNumber + '\'' +
                ", state='" + state + '\'' +
                ", street='" + street + '\'' +
                ", streetNum='" + streetNum + '\'' +
                ", meterNum='" + meterNum + '\'' +
                ", maxTime=" + maxTime +
                ", firstObserve='" + firstObserve + '\'' +
                ", x_coordinate=" + x_coordinate +
                ", y_coordinate=" + y_coordinate +
                '}';
    }
}
