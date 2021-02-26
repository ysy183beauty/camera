package com.camera.dao;

import com.camera.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
@Mapper
@Component
public interface UserMapper {
    /**
     * 登录信息
     * @param user
     * @return
     */
    Integer login(User user);
}
