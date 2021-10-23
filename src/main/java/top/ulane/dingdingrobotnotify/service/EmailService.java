package top.ulane.dingdingrobotnotify.service;

public interface EmailService {

	public void sendEmail(String to, String title, String cont);
}
