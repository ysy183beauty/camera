package com.camera.util;
import com.camera.entity.CameraEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/***
 * carmerName 为redis中的摄像头的key
 * key 为搜索输入的值
 * redisTemplate redis对象
 * objectMapper
 */
public class LoadCarmers {
    public static List<CameraEntity> getCamers(String carmerName, String key, RedisTemplate<String,? extends Object> redisTemplate, ObjectMapper objectMapper){
        List<CameraEntity> result=new ArrayList<>();
        if(redisTemplate.hasKey(carmerName)){
            //此时拿到的是jackson序列化后的json字符串
            ListOperations<String, CameraEntity> listOps = (ListOperations<String, CameraEntity>) redisTemplate.opsForList();
            List<CameraEntity> lists = listOps.range(carmerName, 0, -1);
            //jackson解析出具体的bean
            List<CameraEntity> list= objectMapper.convertValue(lists, new TypeReference<List<CameraEntity>>() { });
            //模糊查询 使用（Pattern、Matcher）
            Pattern pattern = Pattern.compile(key);
            for(int i=0; i < list.size(); i++){
                Matcher matcher = pattern.matcher(((CameraEntity)list.get(i)).getCameraName());
                if(matcher.find()){  //matcher.find()-为模糊查询   matcher.matches()-为精确查询
                    result.add(list.get(i));
                }
            }
        }
        return result;
    }
}
