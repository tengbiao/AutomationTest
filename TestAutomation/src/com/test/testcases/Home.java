package com.test.testcases;

import static org.testng.Assert.assertEquals;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.test.base.TestBase;
import com.test.page.HomePage;
import com.test.util.Assertion;
import com.test.util.Log;

public class Home extends TestBase{
	
	@Test(dataProvider = "providerMethod")
	public void checkLogin(Map<String, String> param) throws InterruptedException{
		Assertion.flag = true;
		this.goTo(param.get("url")); // 打开浏览器并输入地址回车
		Log.logInfo("1.打开测试地址：" + param.get("url"));
		HomePage hp=new HomePage(driver);
		if(hp.Login(param.get("username"), param.get("password"))){
			Log.logInfo(param.get("username")+"登录成功");
			assertEquals(hp.getElement("account").getText(), param.get("username"));  
		}
		//Thread.sleep(3000);
		/*hp.goSports();
		Thread.sleep(10000);
		WebDriver driverSport = getCurrentWindowHandle();
		Log.logInfo("跳转到"+driverSport.getTitle());
		assertEquals(driverSport.getTitle(), "九卅体育");*/
		
		

		hp.goCMD(Integer.parseInt(param.get("money")));
		WebDriver driverSport = getCurrentWindowHandle();
		Log.logInfo("跳转到"+driverSport.getTitle());
		assertEquals(driverSport.getTitle(), "亚投体育");
		
		/*hp.goSportBBIN(Integer.parseInt(param.get("money")));
		WebDriver driverBBIN=getCurrentWindowHandle();
		Log.logInfo("跳转到"+driverBBIN.getTitle());
		assertEquals(driverBBIN.getTitle(), "BBIN体育");
		*/
		
		//hp.goSportAG(Integer.parseInt(param.get("money")));
		//WebDriver driverBBIN=getCurrentWindowHandle();
	}
	
}
