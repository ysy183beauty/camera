package com.camera.entity;

import java.io.Serializable;

public class Camera implements Serializable {
    private Integer id;//主键
    private String cameraName;//摄像机名称
    private String cameraType;//摄像机类型
    private String ratio;//分辨率
    private String bitRate;//码率
    private String ipAdrres;//IP地址
    private String status;//状态
    private String mainEquipmentType;//主设备型号
    private String serverIp;//所属服务器IP
    private double lng;//设备所属经度
    private double lat;//设备所属纬度
    private String secondDirectory;//二级目录
    private String thirdDirectory;//三级目录
    private String fourDirectory;//四级目录
    private String fiveDirectory;//五级目录
    private String sixDirectory;//六级目录
    private Integer is_normal;//是否正常：0：正常；1：异常
    private String pageInfo;//分页信息
    private String userName;//用户名
    private String passWord;//密码
    private double bdLng;//百度经度
    private double bdLat;//百度纬度
    private String regionName;
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

    public String getCameraType() {
        return cameraType;
    }

    public void setCameraType(String cameraType) {
        this.cameraType = cameraType;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public String getIpAdrres() {
        return ipAdrres;
    }

    public void setIpAdrres(String ipAdrres) {
        this.ipAdrres = ipAdrres;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMainEquipmentType() {
        return mainEquipmentType;
    }

    public void setMainEquipmentType(String mainEquipmentType) {
        this.mainEquipmentType = mainEquipmentType;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
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

    public String getSecondDirectory() {
        return secondDirectory;
    }

    public void setSecondDirectory(String secondDirectory) {
        this.secondDirectory = secondDirectory;
    }

    public String getThirdDirectory() {
        return thirdDirectory;
    }

    public void setThirdDirectory(String thirdDirectory) {
        this.thirdDirectory = thirdDirectory;
    }

    public String getFourDirectory() {
        return fourDirectory;
    }

    public void setFourDirectory(String fourDirectory) {
        this.fourDirectory = fourDirectory;
    }

    public String getFiveDirectory() {
        return fiveDirectory;
    }

    public void setFiveDirectory(String fiveDirectory) {
        this.fiveDirectory = fiveDirectory;
    }

    public String getSixDirectory() {
        return sixDirectory;
    }

    public void setSixDirectory(String sixDirectory) {
        this.sixDirectory = sixDirectory;
    }

    public Integer getIs_normal() {
        return is_normal;
    }

    public void setIs_normal(Integer is_normal) {
        this.is_normal = is_normal;
    }

    public String getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(String pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
