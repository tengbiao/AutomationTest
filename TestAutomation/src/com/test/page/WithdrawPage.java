package com.test.page;

import java.util.List;

import org.apache.poi.hssf.util.HSSFColor.LIGHT_ORANGE;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.google.protobuf.Message;
import com.test.base.Page;
import com.test.util.Log;
import com.test.util.RandomUtil;

public class WithdrawPage extends Page {
	RandomUtil util = new RandomUtil();

	public WithdrawPage(WebDriver driver) {
		super(driver);
	}

	/***
	 * 提交银行卡取款
	 */
	public void submitUnionCard(String jymm) {
		if (isExist(getElementNoWait("UnionCard"))) {
			getElement("UnionCard").click();
			Log.logInfo("-------------进入【" + getElement("UnionCard").getText() + "】提款---------");
			List<WebElement> banks = getElementsNoWait("cardNum");
			if (banks.size() > 0) {
				// 选择银行卡
				banks.get(0).click();
				Log.logInfo("银行卡尾号为【" + banks.get(0).getText() + "】");
				// 输入金额
				String bankMoney = getAttributeValue(getElement("txtMoney").getAttribute("placeholder"));
				getElement("txtMoney").sendKeys(bankMoney);
				Log.logInfo("输入提款金额：【" + bankMoney + "】");
				// 判断是否启用提款密码
				if (isExist(getElementNoWait("txtJymm"))) {
					// 启用则输入提款密码
					getElement("txtJymm").sendKeys(jymm);
				}
				// 确认送出
				getElement("btnSend").click();
				Log.logInfo("点击确认送出");
				if (isExist("InfoLoading", "info_ok")) {
					Log.logInfo(getElement("info_text").getText());
					getElement("info_ok").click();
				} else {
					Log.logInfo(getElement("UnionCard").getText() + "提款失败！");
				}
			} else {
				Log.logInfo(banks.size());
			}
		}
	}

	/***
	 * 提交A卡取款
	 */
	public void submitAstroPay(String jymm) {
		//判断A卡取款是否开放
		if (isExist(getElementNoWait("AstroPay"))) {
			getElement("AstroPay").click();
			Log.logInfo("-------------进入【" + getElement("AstroPay").getText() + "】提款---------");
			// 取存款区间最小值作为金额输入
			String bankMoney = GetMinMoney(getElement("txtMoney"),"placeholder");
			getElement("txtMoney").sendKeys(bankMoney);
			Log.logInfo("输入提款金额：【" + bankMoney + "】");
			// 判断是否启用提款密码
			if (isExist(getElementNoWait("txtJymm"))) {
				// 启用则输入提款密码
				getElement("txtJymm").sendKeys(jymm);
			}
			// 确认送出
			getElement("btnSend").click();
			Log.logInfo("点击确认送出");
			
			
		}else {
			Log.logInfo(getElement("AstroPay").getText()+"提款不开放！");
		}
	}

	/***
	 * 添加银行卡
	 */
	public void addBankCard() {
			// 获取添加银行卡图标个数
			List<WebElement> adds = getElements("addBank");
			if (adds.size() > 0) {
				int index = RandomUtil.getRandom(0, adds.size());
				// 随机选择添加银行卡并点击
				adds.get(index).click();
				Log.logInfo("点击添加银行卡");
				String account = "62309101990597" + util.getRandomNum(5);
				getElement("txtRemittanceAccount").sendKeys(account);
				Log.logInfo("输入银行帐号【" + account + "】");
				getElement("btn_SendPhoneCode").click();
				Log.logInfo("点击送出验证码");
				getElement("txtAddBankCardCode").sendKeys("7788");
				waitElementEnabledClick("btn_CheckCode");
				Log.logInfo("输入验证码并送出");
				waitElementEnabledClick("addBank_sbumit");
				Log.logInfo("点击确认");
				Log.logInfo("提示【" + getElement("info_text").getText() + "】");
				getElement("info_ok").click();
		}else {
			Log.logInfo("银行卡已满五组，无法再新增！");
		}
		
	}
    /***
     * 删除银行卡
     */
	public void RemoveBankCard() {
		if (isExist(getElement("addBank"))) {
			// 判断删除按钮是否存在
			if (isExist(getElementNoWait("deleteBank"))) {
				List<WebElement> removeBanks = getElements("deleteBank");
				// 判断银行卡数量如果大于1
				if (removeBanks.size() > 1) {
					Log.logInfo("删除索引为1的卡号");
					// 删除索引为2的银行卡
					removeBanks.get(1).click();
					if (isExist(getElement("confirm_ok"))) {
						Log.logInfo("提示框点击确定");
						getElement("confirm_ok").click();
						Log.logInfo("提示【" + getElement("info_text").getText() + "】");
						// 删除成功或失败提示点击确定
						getElement("info_ok").click();
					} else {
						Log.logInfo("银行卡删除异常！");
					}

				} else {
					Log.logInfo("只剩一组银行卡，无法删除！");
				}
			} else {
				Log.logInfo("银行卡删除开关关闭状态！");
			}
		}
	}
}
