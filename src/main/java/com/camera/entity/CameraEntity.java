package com.camera.entity;

import java.io.Serializable;

public class CameraEntity implements Serializable {
    private Integer id;//主键
    private String cameraName;//摄像机名称
    private double lng;//设备所属经度
    private double lat;//设备所属纬度
    private double bdLng;//百度经度
    private double bdLat;//百度纬度
    public CameraEntity(Integer id, String cameraName, double lng, double lat, double bdLng, double bdLat) {
        this.id = id;
        this.cameraName = cameraName;
        this.lng = lng;
        this.lat = lat;
        this.bdLng = bdLng;
        this.bdLat = bdLat;
    }

    public CameraEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getBdLng() {
        return bdLng;
    }

    public void setBdLng(double bdLng) {
        this.bdLng = bdLng;
    }

    public double getBdLat() {
        return bdLat;
    }

    public void setBdLat(double bdLat) {
        this.bdLat = bdLat;
    }
}
