package com.test.page;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.test.base.Page;
import com.test.util.Log;

public class MypursePage extends Page {

	public MypursePage(WebDriver driver) {
		super(driver);
	}

	public boolean waitMaskShow(String key) {
		return waitElementToBeDisplayed(getElement(key));
	}

	public boolean waitMaskHide(String key) {
		return waitElementToBeNonDisplayed(getElement(key));
	}

	/****
	 * 全部转回主帐户  111
	 */
	public void ReturnToMain() {
		// 等待页面遮罩层隐藏后才能点击全部转回主账户按钮
		waitMaskHide("mask"); 
		List<WebElement> returnBtn = getElementsNoWait("btnRewind");
		if (!returnBtn.equals(null) && returnBtn.size() > 0) {
			getElement("点数全部转回主帐户").click();
			Log.logInfo("点击全部转回主帐户");
			getElement("confirm_ok").click();
			if (waitMaskShow("loading")) {
				if (waitMaskHide("loading"))
					Log.logInfo(getElement("info_text").getText());
					getElement("info_ok").click();
			}
		}
	}

	/***
	 * 获得可以进行互转的平台
	 * 
	 * @param clickKey
	 * @param optoionKey
	 * @return
	 */
	private List<WebElement> getPlatformOption(String clickKey, String optoionKey) {
		clickPlatformSelectShow(clickKey);
		return getElements(optoionKey);
	}

	private void clickPlatformSelectShow(String key) {
		getElement(key).click();
		waitElementToBeDisplayed(
				key.equals("outSelect") ? getElement("outAccountPannel") : getElement("inAccountPannel"));
	}

	private void clickPlatformSelectHide(String key) {
		getElement(key).click();
		waitElementToBeNonDisplayed(
				key.equals("outSelect") ? getElement("outAccountPannel") : getElement("inAccountPannel"));
	}

	/***
	 * 单个平台互转
	 */
	public void PlatformTransfer(String money) {
		waitMaskHide("mask"); // 等待页面加载完成
		List<WebElement> outPlatform = getPlatformOption("outSelect", "outAccountOption");
		for (WebElement outOption : outPlatform) {
			String outOPtionText = outOption.getText();
			outOption.click(); // 选择转入帐户
			List<WebElement> inPlatform = getPlatformOption("inSelect", "inAccountOption");
			inPlatform.remove(0);
			for (WebElement inOption : inPlatform) {
				String inOptionText = inOption.getText();
				inOption.click(); // 选择转出帐户
				// 输入金额
				getElement("转帐金额").sendKeys(money);
				Log.logInfo("转帐金额为：" + money);
				// Log.logInfo("转帐金额为："+);
				// 确认送出已经确认提示框
				getElement("确认送出").click();
				Log.logInfo("点击确认送出");
				getElement("confirm_ok").click(); // 提示框点击确定
				Log.logInfo("【" + outOPtionText + "】转到【" + inOptionText + "】金额【" + money + "】点成功！");
				// 互转等待遮罩层，并且刷新页面
				// waitMaskShow("loading");
				// waitMaskHide("loading");
				getElement("info_ok").click();
				// break;
				//
			}
			// Log.logInfo(inPlatform.size());
			break;
		}
	}

	/***
	 * 单个平台互转
	 * 
	 * @throws InterruptedException
	 */
	public void PlatformTransfer_new(int money) {
		waitMaskHide("mask"); // 等待页面加载完成
		List<WebElement> outPlatform = getPlatformOption("outSelect", "outAccountOption");
		int outPlatformSize = outPlatform.size();

		for (int i = 0; i < outPlatformSize; i++) {
			int transferMoney = money;
			if (i != 0)
				outPlatform = getPlatformOption("outSelect", "outAccountOption");
			else
				transferMoney = money * 50;
			String outOPtionText = outPlatform.get(i).getText();
			outPlatform.get(i).click(); // 选择转入帐户
			List<WebElement> inPlatform = getPlatformOption("inSelect", "inAccountOption");
			int inPlatformSize = inPlatform.size();
			for (int j = 0; j < inPlatformSize; j++) {
				if (j == 0)
					continue;
				if (j != 1)
					inPlatform = getPlatformOption("inSelect", "inAccountOption");
				String inOptionText = inPlatform.get(j).getText();
				inPlatform.get(j).click(); // 选择转入帐户
				// 输入金额
				getElement("转帐金额").sendKeys(String.valueOf(transferMoney));
				getElement("确认送出").click();
				// Log.logInfo("点击确认送出");
				getElement("confirm_ok").click(); // 提示框点击确定
				if (getElement("info_text").getText().equals("平台转帐成功！")) {
					Log.logInfo("【" + outOPtionText + "】转到【" + inOptionText + "】金额【" + transferMoney + "】点成功！");
				} else {
					Log.logError("【" + outOPtionText + "】转到【" + inOptionText + "】金额【" + transferMoney + "】点失败！提示："
							+ getElement("info_text").getText());
				}
				// 互转等待遮罩层，并且刷新页面
				getElement("info_ok").click();

				if (waitMaskHide("mask")) {
					getPlatformOption("outSelect", "outAccountOption").get(i).click();
				}

			}
		}
	}

	/****
	 * 单个转回主帐户
	 * @throws InterruptedException 
	 */
	public void RewindToMainAccount() throws InterruptedException {
		waitMaskHide("mask");
		List<WebElement> projects = getElements("projectBox");
		boolean isReload = false;
		for (int i = 0; i < projects.size(); i++) {
			if(isReload) waitMaskHide("mask");
			String text = getElements("rewindText").get(i).getText();
			try {
				WebElement rewind = getElements("projectBox").get(i).findElement(By.className("rewind"));
				rewind.click();// 点击转回按钮
				getElement("confirm_ok").click(); // 提示框点击确定
				if (getElement("info_text").getText().equals("平台转帐成功！")) {
					isReload = true;
					Log.logInfo(text + "转回主帐户成功！");
				} else {
					isReload = false;
					Log.logInfo(text + "转回主帐户失败！");
				}
				getElement("info_ok").click(); // 互转成功提示点击确定
			} catch (NoSuchElementException e) {
				isReload = false;
				Log.logInfo(text + "没有额度可转回");
			}
		}
	}
	
	public void MypurseChangeList() throws InterruptedException
	{
	
		getElement("changeList").click(); //点击转帐记录
		List<WebElement> options=geSelecttAllOptions(getElement("ddlType"));  //得到平台下拉选择框的所有选项
		Log.logInfo(options.size());
		//遍历下拉框
		for(int i=1;i<options.size();i++)
		{
			selectByIndex(getElement("ddlType"), i);
			getElement("btnSearch").click();;
			Log.logInfo("根据"+getCurrentSelectText(getElement("ddlType"))+"查询");
			//Thread.sleep(10000);
		}
			
	}
}
