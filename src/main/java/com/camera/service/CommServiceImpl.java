package com.camera.service;

import com.camera.dao.CamerMapper;
import com.camera.dao.UserMapper;
import com.camera.entity.Camera;
import com.camera.entity.CameraEntity;
import com.camera.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class CommServiceImpl implements CommService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CamerMapper camerMapper;
    @Override
    public Integer login(User user) {
        return userMapper.login(user);
    }

    @Override
    public List<Camera> selectAll(Map<String, Object> map) {
        return camerMapper.selectAll(map);
    }

    @Override
    public Integer selectTotal(Map<String, Object> map) {
        return camerMapper.selectTotal(map);
    }

    @Override
    public List<Camera> selectBaseInfo() {
        return camerMapper.selectBaseInfo();
    }

    @Override
    public void updateBatchBaseCarmera(List<Camera> list) {
        camerMapper.updateBatchBaseCarmera(list);
    }

    @Override
    public void addCameraInfo(Camera camera) {
        camerMapper.addCameraInfo(camera);
    }

    @Override
    public Camera selectCameraById(Integer id) {
        return camerMapper.selectCameraById(id);
    }

    @Override
    public void updateCamera(Camera camera) {
        camerMapper.updateCamera(camera);
    }

    @Override
    public List<Camera> selectCarmersByIds(List<Integer> ids) {
        return camerMapper.selectCarmersByIds(ids);
    }

    @Override
    public List<CameraEntity> selectCarmers() {
        return camerMapper.selectCarmers();
    }
}
