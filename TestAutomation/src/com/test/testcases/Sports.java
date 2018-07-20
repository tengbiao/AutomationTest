package com.test.testcases;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.testng.annotations.Test;

import com.test.base.TestBase;
import com.test.page.HomePage;
import com.test.page.SportPage;
import com.test.util.Assertion;
import com.test.util.Log;

public class Sports extends TestBase {

	private static int Count = 1;

	@Test(dataProvider = "providerMethod")
	public void testLogin(Map<String, String> param) throws InterruptedException {
		Assertion.flag = true;
		this.goTo(param.get("url")); // 打开浏览器并输入地址回车
		Log.logInfo("1.打开测试地址：" + param.get("url"));
		HomePage lp = new HomePage(driver);
		// Log.logInfo("此时是未登录状态");
		if (lp.Login(param.get("username"), param.get("password"))) {
			lp.getElement("九卅体育").click(); // 点击九卅体育
			Log.logInfo("4.点击九卅体育");
			Thread.sleep(15000);
			WebDriver driverSport = getCurrentWindowHandle();
			driverSport.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			SportPage sp = new SportPage(driverSport);

			Log.logInfo("6.账号" + param.get("username") + "的等级是："
					+ (sp.getIframeElement("等级", "ifIndex", "topFrame").getText()));
			// 找到所有赔率的a标签
			List<WebElement> plLinks = sp.getIframeElements("plLink", new String[] { "ifIndex", "mainFrame" });
			// 循环点击查看是否可以下注
			Log.logInfo("plLinks count:" + plLinks.size());
			for (WebElement pllink : plLinks) {
				Log.logInfo("---------------"+Count+"-------------------");
				sp.swithIframe("ifIndex", "mainFrame");
				intelligentWait(10, pllink);
				pllink.click(); // 点击赔率
				Log.logInfo("7.点击赔率：" + pllink.getText());
				String limit = sp.getIframeElement("BettingLimit", new String[] { "ifIndex", "leftFrame" }).getText();
				String[] limitMoney = limit.split("-");
				int limitS = Integer.parseInt(limitMoney[0].trim());
				int limitM = Integer.parseInt(limitMoney[1].trim());
				Log.logInfo("金额区间为：" + limit);
				if (limitS < limitM) {
					sp.getIframeElement("投注金额", "ifIndex", "leftFrame").sendKeys(String.valueOf(limitS)); // 输入最小金额
					Log.logInfo("7.输入投注金额" + String.valueOf(limitS));
					sp.getIframeElement("确认下注", "ifIndex", "leftFrame").click(); // 确认下注
					Log.logInfo("8.点击确认下注");
					sp.getIframeElement("确定", "ifIndex", "mainFrame").click(); // 提示框点击确定
					intelligentWait(10, sp.getIframeElement("btn_OK", "ifIndex", "mainFrame"));
					sp.takeScreenshot();  //截图
					sp.getIframeElement("btn_OK", "ifIndex", "mainFrame").click();
					Log.logInfo("9." + param.get("username") + "下注成功");
					// sp.closeWindow(); //关闭当前体育页面
					// lp.switchWindowByIndex(0);//切换窗口焦点至首页

					// 判断是否已经登录，有登录先退出 lp.Logout();
					// Log.logInfo("当前是已登录状态，点击登出！");
					Log.logInfo("第" + Count + "次下注已完成");
					Count++;
					 //break;
				}
			}
		}

		// Log.logInfo("在首页点击登录按钮");
		// fp.getElement("login_link").click();
		// LoginPage lp = new LoginPage(driver);
		// Log.logInfo("登录用户名为:"+param.get("username"));
		// lp.getElement("login_name").sendKeys(param.get("username"));
		// Log.logInfo("登录密码为:"+param.get("password"));
		// lp.getElement("login_pwd").sendKeys(param.get("password"));
		// lp.getElement("login_button").click();
		// String errorMsg = lp.getElement("loginpwd_error").getText().trim();
		// Log.logInfo("错误信息为:'"+errorMsg+"'");
		// Assert.assertEquals(errorMsg, "账户名与密码不匹配，请重新输入");

	}
}
