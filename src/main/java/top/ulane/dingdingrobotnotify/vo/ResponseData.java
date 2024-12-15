package top.ulane.dingdingrobotnotify.vo;

import java.io.Serializable;

public class ResponseData <T> implements Serializable {
	private int code = 0;
	private String msg;
	private T data;
	
	public ResponseData() {
		super();
	}
	
	public ResponseData(T data) {
		super();
		this.msg = "success";
		this.data = data;
	}
	
	public ResponseData(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public static ResponseData success(){
		return new ResponseData();
	}
	public static <T> ResponseData success(T data){
		return new ResponseData(data);
	}

	public static ResponseData error(String msg){
		return new ResponseData(9999, msg);
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

}
