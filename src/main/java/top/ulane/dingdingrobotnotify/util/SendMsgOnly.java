package top.ulane.dingdingrobotnotify.util;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SendMsgOnly {
	
	private static String url;
	private static String secret;
	
	@Value("${util.sendMsgOnly.url}")
	public void setUrl(String url) {
		SendMsgOnly.url = url;
	}
	@Value("${util.sendMsgOnly.secret}")
	public void setSecret(String secret) {
		SendMsgOnly.secret = secret;
	}
	
	public static String sendText(String msg) throws Exception {
		OapiRobotSendRequest request = new OapiRobotSendRequest();
		request.setMsgtype("text");
		OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
		text.setContent(msg);
		request.setText(text);
		return sendExecute(request);
	}
	
	public static String sendExecute(OapiRobotSendRequest request) throws Exception {
		long timestamp = System.currentTimeMillis();
		String sign = UrlSign.getSign(timestamp, secret);
		
		DingTalkClient client = new DefaultDingTalkClient(url+"&timestamp="+timestamp+"&sign="+sign);
		OapiRobotSendResponse response = client.execute(request);
		System.out.println(JSON.toJSONString(response));
		return response.getBody();
	}
	
	/**
	 * 连接器事件发送
	 * {"body":"{\"errcode\":0,\"errmsg\":\"ok\"}","errcode":0,"errmsg":"ok","errorCode":"0","msg":"ok","params":{"msgtype":"text","text":"{\"content\":\"新的消息1631932044255\"}"},"subCode":"","subMsg":"","success":true}
	 * {"errcode":0,"errmsg":"ok"}
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// 获取access_token
//			String access_token = AccessTokenUtil.getToken();
			
			System.out.println(sendText("新的消息"+System.currentTimeMillis()));
			
			//@某个或全部
//			OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
//			at.setAtMobiles(Arrays.asList("15579155987"));
// 			//isAtAll类型如果不为Boolean，请升级至最新SDK
//			//at.setIsAtAll(true);
//			//at.setAtUserIds(Arrays.asList("109929","32099"));
//			request.setAt(at);
			
//			request.setMsgtype("link");
//			OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
//			link.setMessageUrl("https://www.dingtalk.com/");
//			link.setPicUrl("");
//			link.setTitle("时代的火车向前开");
//			link.setText("这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林");
//			request.setLink(link);

//			request.setMsgtype("markdown");
//			OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
//			markdown.setTitle("杭州天气");
//			markdown.setText("#### 杭州天气 @156xxxx8827\n" +
//					"> 9度，西北风1级，空气良89，相对温度73%\n\n" +
//					"> ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)\n"  +
//					"> ###### 10点20分发布 [天气](http://www.thinkpage.cn/) \n");
//			request.setMarkdown(markdown);
			
//			System.out.println(sendExecute(request));
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}