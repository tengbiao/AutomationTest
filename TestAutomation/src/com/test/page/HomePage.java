package com.test.page;

import org.apache.xalan.templates.ElemApplyImport;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.test.base.Page;
import com.test.util.Log;

public class HomePage extends Page {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	/****
	 * 登录状态检查
	 * 
	 * @param user
	 *            登录帐号
	 * @param pwd
	 *            密码
	 * @return
	 */
	public boolean Login(String user, String pwd) {
		//clear(this.getElement("帐号"));// 清空帐号输入框
		this.getElement("帐号").sendKeys(user); // 输入账号
		//clear(this.getElement("密码"));// 清空密码输入框
		this.getElement("密码").sendKeys(pwd); // 输入密码
		Log.logInfo("2.输入账号：" + user + ",密码：" + pwd);
		this.getElement("登入按钮").click(); // 点击登入
		Log.logInfo("3.点击登入");
		return this.isExist(this.getElement("登录框"));

	}

	/****
	 * 登出
	 */
	public void Logout() {
		if (this.isExist(this.getElement("登出按钮"))) {
			this.getElement("登出按钮").click();
		}
	}

	public void smallTransform(int money) throws InterruptedException {
		String iframeName = getElement("iframe").getAttribute("name");
		// Log.logInfo(iframeName);
		getIframeElement("txtMoney", iframeName).sendKeys(String.valueOf(money));
		Thread.sleep(2000);
		getIframeElement("btnSendOK", iframeName).click();
		getIframeElement("confirm_ok", iframeName).click();
		if (!getIframeElement("info_text", iframeName).getText().equals("平台转帐成功！")) {
			Log.logInfo(getIframeElement("info_text", iframeName).getText());
		}
		getIframeElement("info_ok", iframeName).click();

		// Thread.sleep(5000);
	}

	// 九卅体育模块
	public void goSports() {
		getAction().moveToElement(getElement("九卅体育")).perform();
		Log.logInfo("鼠标移到" + getElement("九卅体育").getText());
		getElement("aBall").click();
	}

	// 亚投体育
	public void goCMD(int money) throws InterruptedException {
		getAction().moveToElement(getElement("九卅体育")).perform();
		Log.logInfo("鼠标移到" + getElement("九卅体育").getText());
		getElement("cmd").click();
		smallTransform(money);
	}

	// BBIN体育
	public void goSportBBIN(int money) throws InterruptedException {
		getAction().moveToElement(getElement("九卅体育")).perform();
		Log.logInfo("鼠标移到" + getElement("九卅体育").getText());
		getElement("sportBBIN").click();
		smallTransform(money);
	}
	//AG体育
		public void goSportAG(int money) throws InterruptedException {
			getAction().moveToElement(getElement("九卅体育")).perform();
			Log.logInfo("鼠标移到" + getElement("九卅体育").getText());
			getElement("sportAG").click();
			smallTransform(money);
		}
	// 九卅真人	
	// 真人游戏
	// 电子游戏
	// 全球彩票
	// 现场斗鸡
	// 捕鱼机

}
