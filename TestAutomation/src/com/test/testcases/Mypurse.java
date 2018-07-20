package com.test.testcases;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.test.base.TestBase;
import com.test.page.HomePage;
import com.test.page.MypursePage;
import com.test.util.Assertion;
import com.test.util.Log;

public class Mypurse extends TestBase {

	@Test(dataProvider = "providerMethod")
	public void testGoMypurse(Map<String, String> param) throws InterruptedException {
		Assertion.flag = true;
		this.goTo(param.get("url")); // 打开浏览器并输入地址回车
		Log.logInfo("1.打开测试地址：" + param.get("url"));
		HomePage hp=new HomePage(driver);
	    //判断是否有转回，有转回则点击全部转回按钮
		if(hp.Login(param.get("username"), param.get("password"))){
			Log.logInfo(param.get("username")+"登录成功");
			hp.getElement("转").click();
			Log.logInfo("点击平台转账");
			WebDriver mDriver = getCurrentWindowHandle();
			MypursePage mPage = new MypursePage(mDriver);
			Assert.assertEquals(mDriver.getTitle(), "平台转帐");
			//mPage.ReturnToMain(); //全部转回主帐户
		    mPage.PlatformTransfer_new(Integer.parseInt(param.get("money")));// 单个互转
			//mPage.RewindToMainAccount(); //单个转回主帐户
			//mPage.MypurseChangeList();  //查询转帐记录
			
		}
	}
}
