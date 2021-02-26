package com.camera.config;
import com.camera.entity.CameraEntity;
import com.camera.service.CommService;
import com.camera.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    @Autowired
    private CommService commService;
    @Autowired
    private RedisTemplate<String,? extends Object> redisTemplate;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //判断redis中是否存在
        ListOperations<String, CameraEntity> listOps = (ListOperations<String, CameraEntity>) redisTemplate.opsForList();
        SetOperations<String, String> set = (SetOperations<String, String>)redisTemplate.opsForSet();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(!redisTemplate.hasKey(Constant.KEY_LIST_CARMERAS)){
            List<CameraEntity> cameras = commService.selectCarmers();
            //存放进去
            listOps.leftPushAll(Constant.KEY_LIST_CARMERAS,cameras);
        }
        //判断是否存放了当前时间的字段信息
        if(!redisTemplate.hasKey(Constant.CURRENTTIMR)){
            set.add(Constant.CURRENTTIMR,sdf.format(new Date()));
        }
    }
}
