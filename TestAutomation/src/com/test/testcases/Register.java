package com.test.testcases;

import java.util.Map;

import org.testng.annotations.Test;

import com.test.base.TestBase;
import com.test.page.RegisterPage;
import com.test.util.Log;

public class Register extends TestBase
{

	@Test(dataProvider = "providerMethod")
	public void TestSubmitRegister(Map<String, String> param) {
		this.goTo(param.get("url")); // 打开浏览器并输入地址回车
		Log.logInfo("1.打开测试地址：" + param.get("url"));
		RegisterPage rPage=new RegisterPage(driver);
		rPage.SubmitRegister();
	}
}
