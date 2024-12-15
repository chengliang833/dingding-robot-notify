package top.ulane.dingdingrobotnotify.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.ulane.dingdingrobotnotify.service.EmailService;
import top.ulane.dingdingrobotnotify.util.SendMsgOnly;
import top.ulane.dingdingrobotnotify.vo.ResponseData;
import wang.ulane.email.CheckEmailValidityUtil;
import wang.ulane.limitalgorithm.slide.NumSlider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
@Slf4j
@RequestMapping("/textTemp")
public class TextTempController {

	ConcurrentHashMap<Integer, String> tempMap = new ConcurrentHashMap<>();

	@RequestMapping("/getText")
	public ResponseData sendMsg(@RequestBody(required = true) JSONObject data){
		Integer idx = data.getInteger("idx");
		if(idx == null){
			idx = 0;
		}
		return ResponseData.success(tempMap.get(idx));
	}

	@RequestMapping("/saveText")
	public ResponseData saveText(@RequestBody(required = true) JSONObject data){
		Integer idx = data.getInteger("idx");
		if(idx == null){
			idx = 0;
		}
		tempMap.put(idx, data.getString("text"));
		return ResponseData.success();
	}

}
