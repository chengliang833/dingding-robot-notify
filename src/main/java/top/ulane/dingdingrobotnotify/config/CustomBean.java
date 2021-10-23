package top.ulane.dingdingrobotnotify.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wang.ulane.email.SendEmail;

@Configuration
public class CustomBean {
	
	@Value("${mail.smtp.host}")
	private String mailHost;
	@Value("${mail.smtp.port}")
	private String mailPort;
	@Value("${mail.user}")
	private String mailUser;
	@Value("${mail.password}")
	private String mailPass;
	
	@Bean
	public SendEmail getSendEmailBean(){
		return new SendEmail(mailUser, mailPass, mailHost, mailPort);
	}
	
}
