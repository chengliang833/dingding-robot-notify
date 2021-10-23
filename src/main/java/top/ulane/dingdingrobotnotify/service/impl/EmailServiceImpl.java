package top.ulane.dingdingrobotnotify.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.ulane.dingdingrobotnotify.service.EmailService;
import wang.ulane.email.SendEmail;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private SendEmail sendEmail;
	
	@Override
	@Async
	public void sendEmail(String to, String title, String cont) {
		try {
			log.info("send... to[{}]", to);
			sendEmail.send(to, title, cont);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
