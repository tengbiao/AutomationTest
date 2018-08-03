package com.test.testcases;

import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.server.handler.GetElementDisplayed;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.test.base.TestBase;
import com.test.page.DepositPage;
import com.test.page.HomePage;
import com.test.util.Assertion;
import com.test.util.Log;

import bsh.Variable;

public class Deposit extends TestBase {
	
	@Test(dataProvider = "providerMethod")
	public void testOnlineDeposit(Map<String, String> param) throws InterruptedException {
		Assertion.flag = true;
		this.goTo(param.get("url_test")); // 打开浏览器并输入地址回车
		Log.logInfo("1.打开测试地址：" + param.get("url_test"));
		HomePage hp = new HomePage(driver);
		// 
		if (hp.Login(param.get("username_test"), param.get("password"))) {
			Log.logInfo(param.get("username_test") + "登录成功");
			hp.getElement("aKscz").click();
			Log.logInfo("点击存款专区");
			
			WebDriver dDriver = getCurrentWindowHandle();
			DepositPage dPage = new DepositPage(dDriver);
			//dPage.onlineDeposit();
			//dPage.qqWallet();
			//dPage.onlineWeChat();
			//dPage.weChatMan();
			//dPage.alipayAuto();
			//dPage.alipayMan();
			 //dPage.aliBank();
			//dPage.onlineFastPay();
			//dPage.quickPassDepositUnion();
			//dPage.quickPassDepositBaidu();
			//dPage.quickPassDepositJD();
			 // dPage.cardPay();
			dPage.Czzq_1();
		/*int windowCount = driver.getWindowHandles().size();
			if (windowCount > 1) {
				WebDriver dDriver = getCurrentWindowHandle();
				DepositPage dPage = new DepositPage(dDriver);
				Log.logInfo(dDriver.getTitle());
				Assert.assertEquals(dDriver.getTitle(), "会员中心--存款专区");

				int despositTypeCount = dPage.getElements("IntoDeposit").size();
				
				for (int i = 0; i < despositTypeCount; i++) {
					String text = dPage.getElements("TextPosition").get(i).getText();
					if(text.equals("敬请期待"))
						continue;
					if (!dPage.getElementsNoWait("TopMaintain").get(i).isDisplayed()) {
						dPage.getElements("IntoDeposit").get(i).click();
						String url = dDriver.getCurrentUrl();
						if (url.contains("OnlineDeposit-cn.aspx")) {
							// 执行在线网银存款操作
							dPage.onlineDeposit();
						}
						if (url.contains("QQWallet.aspx")) {
							// 执行qq扫码存款操作
							dPage.qqWallet();
						}
						if (url.contains("WeChat_new.aspx")) {
							// 执行微信存款操作
							dPage.weChat();
						}
						if (url.contains("Alipay.aspx")) {
							// 执行支付宝存款操作
							dPage.alipay();
						}
						if (url.contains("OnlineFastPay.aspx")) {
							// 执行快捷支付存款操作
							dPage.onlineFastPay();
						}
						if (url.contains("QuickPassDeposit.aspx")) {
							// 执行扫码支付存款操作
							dPage.quickPassDeposit();
						}
						if (url.contains("CardPay.aspx")) {
							// 执行点卡支付存款操作
							dPage.cardPay();
						}
						if (url.contains("Czzq_1.aspx")) {
							// 执行网络银行存款操作
							dPage.cardPay();
						}
						//driver = getCurrentWindowHandle();
						//hp.setDriver(driver);
						//hp.getElement("aKscz").click();
					} else {
						Log.logInfo(text + "维护中");
					}
				}
			} else {
				Log.logInfo(hp.getElement("info_tips").getText());
			}
			*/
		}
	}
}
