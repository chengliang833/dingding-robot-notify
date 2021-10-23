package top.ulane.dingdingrobotnotify.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import wang.ulane.email.SendEmail;
import wang.ulane.limitalgorithm.slide.NumSlider;

@RestController
//@Slf4j
public class AjaxController {
	private static final Logger log = LoggerFactory.getLogger(AjaxController.class);
	
	@Autowired
	@Qualifier("sendMsgNumSlider")
	private NumSlider sendMsgNumSlider;
	@Autowired
	@Qualifier("sendEmailNumSlider")
	private NumSlider sendEmailNumSlider;
	@Value("${email.key1}")
	private String emailkey1;
	@Value("${email.key2}")
	private String emailkey2;
	@Value("${mail.user}")
	private String mailUser;
	@Autowired
	private EmailService emailService;
	
	@RequestMapping("/sendMsg")
	public ResponseData sendMsg(@RequestParam(value = "msg", required = false) String msg, @RequestBody(required = false) JSONObject data){
		if(StringUtils.isEmpty(msg)){
			//优先拿param
			if(data == null || StringUtils.isEmpty(data.getString("msg"))){
				return ResponseData.error("参数不可为空");
			}else{
				msg = data.getString("msg");
			}
		}
		log.info("msg:"+msg);
		if(msg.length() > 512){
			return ResponseData.error("最大允许512字符");
		}
		
		String errmsg = null;
		if(sendMsgNumSlider.pass(System.currentTimeMillis())){
			try {
				Thread.sleep(2000);
//				String response = "{\"errcode\":0,\"errmsg\":\"ok\"}";
				String response = SendMsgOnly.sendText(msg);
				JSONObject rObj = JSON.parseObject(response);
				if("0".equals(rObj.getString("errcode"))){
					return ResponseData.success();
				}else{
					errmsg = rObj.getString("errmsg");
				}
			} catch (Exception e) {
				errmsg = e.getMessage();
				log.error(e.getMessage(), e);
			}
		}else{
			errmsg = sendMsgNumSlider.getTimeLimit()/1000+"秒内仅可请求"+ sendMsgNumSlider.getMaxSize()+"次";
		}
		log.info("errmsg:"+errmsg);
		return ResponseData.error(errmsg);
	}
	
	@RequestMapping("checkEmail")
	public ResponseData checkEmail(@RequestParam String email){
		//这个验证有问题...dns对其有影响
		if(CheckEmailValidityUtil.isEmailValid(mailUser, email)){
			return ResponseData.success();
		}
		return ResponseData.error("邮箱验证失败");
	}
	
	@RequestMapping("/sendEmail")
	public ResponseData sendEmail(@RequestParam(value = "key1", required = true) String key1, @RequestBody(required = true) JSONObject data){
		if(!emailkey1.equals(key1) || !emailkey2.equals(data.getString("key2"))){
			return ResponseData.error("验证失败");
		}
		
		String to = data.getString("to");
		String title = data.getString("title");
		String cont = data.getString("cont");
		if(StringUtils.isEmpty(to) || StringUtils.isEmpty(title) || StringUtils.isEmpty(cont)){
			return ResponseData.error("参数不完整");
		}
		
		String errmsg = null;
		if(sendEmailNumSlider.pass(System.currentTimeMillis())){
			emailService.sendEmail(to, title, cont);
		}else{
			errmsg = "邮件发送全平台限制："+sendEmailNumSlider.getTimeLimit()/1000+"秒内仅可发送"+ sendMsgNumSlider.getMaxSize()+"次";
		}
		if(errmsg == null){
			log.info("success. send to[{}], cont:[{}]{}", to, title, cont);
			return ResponseData.success();
		}else{
			log.info("fail. send to[{}], cont:[{}]{}", to, title, cont);
			log.info("to[{}]:{}", to, errmsg);
			return ResponseData.error(errmsg);
		}
	}
	
	
	@RequestMapping("/postTest")
	public JSONObject sendMsg(@RequestBody JSONObject json){
		log.info(json.toJSONString());
		return json;
	}
	
}
