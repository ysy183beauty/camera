package com.camera.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.camera.entity.Camera;
import com.camera.entity.CameraEntity;
import com.camera.entity.User;
import com.camera.service.CommService;
import com.camera.util.AppSendUtils;
import com.camera.util.Constant;
import com.camera.util.LoadCarmers;
import com.camera.util.MD5Util;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class CommController {
    @Autowired
    private CommService commService;
    private Map<String,Object> map;
    @Autowired
    private RedisTemplate<String,? extends Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @RequestMapping(value ="/login",method ={RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> login(HttpServletRequest  request){
        map=new HashMap<>();
        try {
            String userName=request.getParameter("username")==null?"":request.getParameter("username");
            String pwd=request.getParameter("password")==null?"":request.getParameter("password");
            String passWord= MD5Util.getMD5Str(pwd);
            User user=new User();
            user.setUserName(userName);
            user.setPassWord(passWord);
            Integer i=commService.login(user);
            if(i==0){//用户名或密码出错
                map.put("msg","用户名或密码出错！");
                map.put("flag",false);
            }else{
                map.put("flag",true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg","请联系管理员");
            map.put("flag",false);
        }
        return map;
    }
    @ResponseBody
    @RequestMapping(value = "/selectAll",method = RequestMethod.POST)
    public Map<String,Object> selectAll(HttpServletRequest request, HttpServletResponse response){
        map=new HashMap<>();
        //获取当前页数
        String page=request.getParameter("page")==null?"1":request.getParameter("page");
        String cameraName=request.getParameter("cameraName")==null?"":request.getParameter("cameraName");
        Map<String,Object> parameters=new HashMap<>();
        parameters.put("cameraName",cameraName);
        parameters.put("pagestart",0);
        parameters.put("pagesize",Integer.parseInt(page)*20);
        List<Camera> cameras=commService.selectAll(parameters);
        map.put("data",cameras);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/getRedisCamers",method ={RequestMethod.POST,RequestMethod.GET})
    public Map<String,Object> getRedisCamers(HttpServletRequest request){
        map=new HashMap<>();
        String key=request.getParameter("key")==null?"":request.getParameter("key");
        List<CameraEntity> result= LoadCarmers.getCamers(Constant.KEY_LIST_CARMERAS,key,redisTemplate,objectMapper);
        map.put("data",result);
        return map;
    }

    /**
     * 多点导航路线信息
     */
    @ResponseBody
    @RequestMapping(value = "/selectMoreAll",method = RequestMethod.POST)
    public Map<String,Object> selectMoreAll(HttpServletRequest request, HttpServletResponse response){
        map=new HashMap<>();
        //获取当前页数
        String page=request.getParameter("page")==null?"1":request.getParameter("page");
        String cameraName=request.getParameter("cameraName")==null?"":request.getParameter("cameraName");
        Map<String,Object> parameters=new HashMap<>();
        Integer start=0;//开始位置
        Integer end=(Integer.parseInt(page))*20;//结束位置
        parameters.put("cameraName",cameraName);
        parameters.put("pagestart",start);
        parameters.put("pagesize",end);
        List<Camera> cameras=commService.selectAll(parameters);
        //获取总数据信息
        Map<String,Object> parmeter=new HashMap<>();
        parmeter.put("cameraName",cameraName);
        Integer totalPage=0;//总页数
        Integer maxResult=20;
        Integer total=commService.selectTotal(parmeter)==null?0:commService.selectTotal(parmeter);
        totalPage = (total + maxResult -1) / maxResult;
        map.put("data",cameras);
        map.put("totalPage",totalPage);
        return map;
    }

    /**
     * 通过主键查询数据信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectCarmerById",method ={RequestMethod.GET,RequestMethod.POST})
    public Camera selectCarmerById(HttpServletRequest request){
        String id=request.getParameter("id")==null?"":request.getParameter("id");
        Camera camera = commService.selectCameraById(Integer.parseInt(id));
        return camera;
    }

    /**
     * 更新为百度的经纬度信息
     */
    @RequestMapping(value = "/updateLngLat",method ={RequestMethod.GET,RequestMethod.POST})
    public void updateLngLat(){
        List<Camera> cameras=commService.selectBaseInfo();
        List<Camera> data=Change(cameras);
        //调用批量更新
        System.out.println("开始更新数据信息");
        commService.updateBatchBaseCarmera(data);
        System.out.println("结束数据更新信息");
    }

    /**
     * GPS经纬度转换为百度地图
     */
    public List<Camera> Change(List<Camera> data){
        String coords=null;
        String result=null;
        JSONObject object=null;
        List<Camera> list=new ArrayList<>();
        for (Camera camera:data){
            coords=camera.getLng()+","+camera.getLat();
            result = AppSendUtils.connectURL("http://api.map.baidu.com/geoconv/v1/?coords="+coords+"&from=1&to=5&output=json&ak=e3ZohdqyB0RL98hFOiC29xqh","");
            if(result!=null){
                object= JSONObject.parseObject(result);
                if(Integer.parseInt(object.get("status").toString())==0){
                    JSONArray arr = JSONArray.parseArray(object.get("result")+"");
                    for (int i=0;i<arr.size();i++){
                        camera.setBdLng(Double.parseDouble(arr.getJSONObject(i).get("x").toString()));
                        camera.setBdLat(Double.parseDouble(arr.getJSONObject(i).get("y").toString()));
                    }
                }
            }
            list.add(camera);
        }
        return list;
    }

    /**
     * 保留数据信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveCameraInfo",method ={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> saveCameraInfo(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        String carmerNum=request.getParameter("carmerNum");
        String cameraName=request.getParameter("cameraName")==null?"":request.getParameter("cameraName");
        try {
            double lng=Double.parseDouble(request.getParameter("lng"));
            double lat=Double.parseDouble(request.getParameter("lat"));
            String regionName=request.getParameter("regionName");
            Camera camera=new Camera();
            camera.setCameraName(carmerNum+" "+cameraName);
            camera.setLng(lng);
            camera.setLat(lat);
            camera.setRegionName(regionName);
            List<Camera> list=new ArrayList<>();
            list.add(camera);
            Camera cm = this.Change(list).get(0);
            commService.addCameraInfo(cm);
            //-----对redis中的数据信息重新更新  --------------------
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dealData();
                    }
                });
                thread.start();
            //------对redis中的数据信息重新更新 -----------------------
            map.put("flag",true);
            map.put("msg","保存成功！");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            map.put("flag",false);
            map.put("msg","保存失败！");
        }
        return map;
    }

    /**
     * 更新数据信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateCameraInfo",method ={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> updateCameraInfo(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        String cameraName=request.getParameter("cameraName");
        String id=request.getParameter("id");
        try {
            double lng=Double.parseDouble(request.getParameter("lng"));
            double lat=Double.parseDouble(request.getParameter("lat"));
            Camera camera=new Camera();
            camera.setId(Integer.parseInt(id));
            camera.setCameraName(cameraName);
            camera.setLng(lng);
            camera.setLat(lat);
            List<Camera> list=new ArrayList<>();
            list.add(camera);
            Camera cm = this.Change(list).get(0);
            commService.updateCamera(cm);
            //-----对redis中的数据信息重新更新  --------------------
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    dealData();
                }
            });
            thread.start();
            //------对redis中的数据信息重新更新 -----------------------
            map.put("result",true);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            map.put("result",false);
        }
        return map;
    }


    @ResponseBody
    @RequestMapping(value ="/selectCarmersByIds",method ={RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> selectCarmersByIds(HttpServletRequest  request){
        List<Camera> data=new ArrayList<>();
        List<Integer> idList=new ArrayList<>();
        map=new HashMap<>();
        try {
            String ids=request.getParameter("ids")==null?"":request.getParameter("ids").
                    replaceAll("\\[","").replaceAll("\\]","");
            List<String> list = Arrays.asList(ids.split(","));
            for(String s:list){
                idList.add(Integer.parseInt(s));
            }
            //调用后台数据信息
            data = commService.selectCarmersByIds(idList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        map.put("data",data);
        return map;
    }

    private void dealData(){
        //存放数据信息
        ListOperations<String, CameraEntity> listOps = (ListOperations<String, CameraEntity>) redisTemplate.opsForList();
        SetOperations<String, String> set = (SetOperations<String, String>)redisTemplate.opsForSet();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(redisTemplate.hasKey(Constant.KEY_LIST_CARMERAS)){
            //1.删除redis中缓存的数据信息
            Boolean isDelete = redisTemplate.delete(Constant.KEY_LIST_CARMERAS);
            if(isDelete){//删除成功了
                List<CameraEntity> cameras = commService.selectCarmers();
                //存放进去
                listOps.leftPushAll(Constant.KEY_LIST_CARMERAS,cameras);
            }
        }else{
            List<CameraEntity> cameras = commService.selectCarmers();
            //存放进去
            listOps.leftPushAll(Constant.KEY_LIST_CARMERAS,cameras);
        }
        //判断当前时间的数据是否存在
        if(redisTemplate.hasKey(Constant.CURRENTTIMR)){
            //1.删除redis中缓存的数据信息
            Boolean deleteFlag= redisTemplate.delete(Constant.CURRENTTIMR);
            if(deleteFlag){
                set.add(Constant.CURRENTTIMR,sdf.format(new Date()));
            }
        }else{
            set.add(Constant.CURRENTTIMR,sdf.format(new Date()));
        }
    }
}
