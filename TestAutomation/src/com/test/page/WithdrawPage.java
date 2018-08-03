package com.test.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.test.base.Page;
import com.test.util.Log;
import com.test.util.RandomUtil;

public class WithdrawPage extends Page {
	RandomUtil util=new RandomUtil();
	public WithdrawPage(WebDriver driver) {
		super(driver);
	}
	/***
	 * 提交银行卡取款
	 */
	public void submitUnionCard(String jymm) {
		if(isExist(getElementNoWait("UnionCard"))){
			getElement("UnionCard").click();
			Log.logInfo("选择【"+getElement("UnionCard").getText()+"】提款");
			List<WebElement> banks=getElementsNoWait("cardNum");
			if(banks.size()>0){
				//选择银行卡
				banks.get(0).click();
				Log.logInfo("银行卡尾号为【"+banks.get(0).getText()+"】");
				//输入金额
				String bankMoney = getAttributeValue(getElement("txtMoney").getAttribute("placeholder"));
				getElement("txtMoney").sendKeys(bankMoney);
				Log.logInfo("输入金额【"+bankMoney+"】");
				//判断是否启用提款密码
				if(isExist(getElementNoWait("txtJymm"))){
					//启用则输入提款密码
					getElement("txtJymm").sendKeys(jymm);
				}
	            //确认送出
				getElement("btnSend").click();
				Log.logInfo(getElement("info_text").getText());
				getElement("info_ok").click();
 
			}else{
				Log.logInfo(banks.size());
			}
			
		}
	}
	/***
	 * 提交A卡取款
	 */
	public void submitAstroPay() {
		//
		//getElement("UnionCard").click();
	}
	/***
	 * 添加银行卡
	 */
	public void addBankCard() {
		
	}
	/***
	 * 删除银行卡
	 */
	public void deleteBankCard() {
		
	}
    
}
