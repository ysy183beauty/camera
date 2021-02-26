package com.camera.service;

import com.camera.entity.Camera;
import com.camera.entity.CameraEntity;
import com.camera.entity.User;

import java.util.List;
import java.util.Map;

public interface CommService {
    /**
     * 登录信息
     * @param user
     * @return
     */
    Integer login(User user);
    //查询所有的数据信息
    List<Camera> selectAll(Map<String, Object> map);
    //查询条件下的所有数据信息
    Integer selectTotal(Map<String, Object> map);
    List<Camera> selectBaseInfo();
    void updateBatchBaseCarmera(List<Camera> list);
    //添加数据信息
    void addCameraInfo(Camera camera);
    //通过主键查询
    Camera selectCameraById(Integer id);
    void updateCamera(Camera camera);
    //通过主键查询数据信息
    List<Camera> selectCarmersByIds(List<Integer> ids);
    //查询摄像头所有数据信息，查询部分数据
    List<CameraEntity> selectCarmers();
}
