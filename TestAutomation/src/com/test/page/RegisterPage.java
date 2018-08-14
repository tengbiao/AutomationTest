package com.test.page;

import org.openqa.selenium.WebDriver;
import com.test.base.Page;
import com.test.util.Log;
import com.test.util.RandomUtil;

public class RegisterPage extends Page{
	RandomUtil util=new RandomUtil(); //实例化随机数生成类
	public RegisterPage(WebDriver driver) {
		super(driver);		
	}
	public void  SubmitRegister(){
		getElement("btnRegister").click();  //点击免费注册按钮
		Log.logInfo("1.点击免费注册");
		String iframeName = getElement("iframe").getAttribute("name");
		swithIframe(iframeName);   //进入注册页面的iframe
		String txtAccounts=util.getRandomString(10);
		getElement("txtAccounts").sendKeys(txtAccounts);//随机生成10位数的字符串作为帐号并输入
		Log.logInfo("2.输入帐号："+txtAccounts);
		String txtTitle=util.getRandomString(5);
		getElement("txtTitle").sendKeys(util.getRandomString(5));//随机生成5位数的字符串作为昵称并输入
		Log.logInfo("3.输入昵称："+txtTitle);
		getElement("txtPassword").sendKeys("dd1234");  //密码固定dd1234
		Log.logInfo("4.输入密码：dd1234");
		String phone ="189"+ util.getRandomNum(8);   //拼接手机号码，189+随机生成的8位数字
		getElement("txtPhoto").sendKeys(phone);    //输入手机号码
		Log.logInfo("4.输入手机号码："+phone);
		getElement("btnSendcode").click();  //点击发送验证码
		Log.logInfo("5.发送验证码");
		getElement("txtPhoneCode").sendKeys("7788");  //输入万能验证码
		Log.logInfo("6.输入验证码:7788");
		getElement("btnSend").click();      //点击送出
		Log.logInfo("7.送出验证码");
		getElement("btnSubmit").click();	//提交注册
		Log.logInfo("8.提交注册");
		if(isExist(getElement("btn_deposit"))){
			Log.logInfo("----------帐号："+txtAccounts+"注册成功！-------");
			getElement("btn_deposit").click();
			Log.logInfo("9.点击我要存款");
			String rname=util.getRandomRemittanceName(3);
			getElement("txtRemittanceName").sendKeys(rname); //输入真实户名
			Log.logInfo("10.输入户名:"+rname);
			getElement("txtVoicePassword").sendKeys("dd4321");   //密码固定输入dd4321
			Log.logInfo("11.输入提款密码:dd4321");
			getElement("btn_SentOut").click();   //点击确认送出按钮
			Log.logInfo("12.点击确认送出");
			getElement("confirm_ok").click();    //户名确认提示点击确定
			Log.logInfo("13.提示框点击确定");
			if(isExist(getElement("info_ok"))){
				Log.logInfo(getElement("info_text").getText());
				getElement("info_ok").click();
			}
			
		}else {
			Log.logInfo("----------"+txtAccounts+"注册失败！-------");
		}
		
	}
  

}
