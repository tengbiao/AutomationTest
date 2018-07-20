package com.test.page;

import org.openqa.selenium.WebDriver;

import com.test.base.Page;
import com.test.util.*;

public class DepositPage extends Page {

	public DepositPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * 在线网银
	 */
	public void onlineDeposit() {
		Log.logInfo("onlineDeposit");
	}

	/**
	 * QQ扫码支付
	 */
	public void qqWallet() {
		Log.logInfo("qqWallet");
	}

	/**
	 * 微信支付
	 */
	public void weChat() {
		Log.logInfo("weChat");
	}

	/**
	 * 支付宝钱包
	 */
	public void alipay() {
		Log.logInfo("alipay");
	}

	/**
	 * 快捷支付
	 */
	public void onlineFastPay() {
		Log.logInfo("onlineFastPay");
	}

	/**
	 * 扫码支付
	 */
	public void quickPassDeposit() {
		Log.logInfo("quickPassDeposit");
	}

	/**
	 * 点卡支付
	 */
	public void cardPay() {
		Log.logInfo("cardPay");
	}
	/**
	 * 网络银行
	 */
	public void Czzq_1() {
		Log.logInfo("Czzq_1");
	}

}