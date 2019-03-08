//package com.alany.other.kafka;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.concurrent.ThreadPoolExecutor;
//
//
///**
// * Created by yinxing on 2018/8/2.
// */
//@RestController
//@RequestMapping(value = "/api/problem")
//public class UserActionApiController {
//
//    protected final Log logger = LogFactory.getLog(getClass());
//
//    //@Autowired
//    //KafkaTemplate<String, String> kafkaTemplate;
//    @Autowired
////    @Qualifier(value="UserActionRecordServiceImpl")
//    private UserActionRecordService userActionRecordService;
//
//
//    @RequestMapping(value = "/userLog")
//    public void addLog(String userAction) {
//        Map<String, Object> userActionMap = JSON.parseObject(userAction);
//        System.out.println("map:" + userActionMap);
//        JSONArray messageJson = (JSONArray) userActionMap.get("message");
//        if (messageJson == null) {
//            return;
//        }
//        String username = (String) userActionMap.get("username");
//        Integer meshType = (Integer) userActionMap.get("meshType");
//        Integer meshId = (Integer) userActionMap.get("meshId");
//        String meshName = (String) userActionMap.get("username");
//        Date createTime = new Date();
//        Date updateTime = new Date();
//
//        Iterator iterator = messageJson.iterator();
//        while (iterator.hasNext()) {
//            try {
//                JSONObject jsonObject = (JSONObject) iterator.next();
//                UserActionRecord userActionRecord = new UserActionRecord();
//                userActionRecord.setUsername(username);
//                userActionRecord.setMeshType(meshType);
//                userActionRecord.setMeshId(meshId);
//                userActionRecord.setMeshName(meshName);
//                userActionRecord.setCreateTime(createTime);
//                userActionRecord.setUpdateTime(updateTime);
//                if (StringUtils.isEmpty(jsonObject.getString("editRecord"))) {
//                    continue;
//                }
//                userActionRecord.setFiledLocation(jsonObject.getInteger("fieldLocation"));
//                userActionRecord.setEditType(jsonObject.getString("editType"));
//                userActionRecord.setEditRecord(jsonObject.getString("editRecord"));
//                userActionRecordService.insert(userActionRecord);
//            }catch (Exception e){
//                logger.info("异常信息："+e.getMessage());
//            }
//
//            System.out.println("新增数据");  ///todo 测试完删除
//        }
//        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor();
//        System.out.println("循环结束！");    ///todo 测试完删除
//    }
//
////    /**
////     * 生产者
////     */
////    @RequestMapping(value = "/test")
////    public void test(String userAction) {
////        try {
////            System.out.println("userAction:" + userAction);
////            Map<String, Object> message = new HashMap<String, Object>();
////            message.put("description", "kafka 消息测试");
////            message.put("topic", "主题是 kafka");
////            message.put("timestamp", System.currentTimeMillis() / 1000);
////            String stringValue = JSONObject.toJSONString(message);
////            kafkaTemplate.send("bootkafka", stringValue);//主题，消息
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//}
