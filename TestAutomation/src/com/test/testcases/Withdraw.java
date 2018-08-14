package com.test.testcases;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import com.test.base.TestBase;
import com.test.page.HomePage;
import com.test.page.WithdrawPage;
import com.test.util.Assertion;
import com.test.util.Log;

public class Withdraw extends TestBase {
	@Test(dataProvider = "providerMethod")
	public void testsubmitUnionCard(Map<String, String> param) throws InterruptedException {
		Assertion.flag = true;
		this.goTo(param.get("url_test")); // 打开浏览器并输入地址回车
		Log.logInfo("1.打开测试地址：" + param.get("url_test"));
		HomePage hp = new HomePage(driver);
		if (hp.Login(param.get("username_test"), param.get("password"))) {
			Log.logInfo(param.get("username_test") + "登录成功");
			hp.getElement("aKscz2").click();
			Log.logInfo("点击提款专区");
			if (!hp.isExist(hp.getElementNoWait("info_tips"))) {
				WebDriver dDriver = getCurrentWindowHandle();
				WithdrawPage wPage = new WithdrawPage(dDriver);
				Log.logInfo(dDriver.getTitle());
				//wPage.submitUnionCard(param.get("jymm"));  //银联卡提款
				wPage.submitAstroPay(param.get("jymm"));    //A卡提款
				//wPage.addBankCard();   //添加银行卡
				//wPage.RemoveBankCard();   //删除银行卡
				
			} else {
				Log.logInfo(hp.getElementNoWait("info_tips").getText());
			}

		}
	}
}
