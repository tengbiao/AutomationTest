package com.test.page;

import static org.testng.Assert.assertEquals;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.test.base.Page;
import com.test.util.Log;
import com.test.util.RandomUtil;

public class DepositPage extends Page {
	RandomUtil util = new RandomUtil(); // 实例化随机数生成类

	public DepositPage(WebDriver driver) {
		super(driver);
	}


	/**
	 * 在线网银
	 * 
	 * @throws InterruptedException
	 */
	public void onlineDeposit(){
		// Log.logInfo("onlineDeposit");
		// if (isExist(getElementNoWait("OnlineDeposit"))) {
		// 进入在线支付
		Log.logInfo("---------进入在线支付-------------");
		// getElement("OnlineDeposit").click();
		// 获取最低存款金额
		String online = GetMinMoney(getElement("txtMoney"), "placeholder");
		// 输入存款金额
		getElement("txtMoney").sendKeys(online);
		Log.logInfo("输入金额：" + online);
		// 点击确认送出
		getElement("btnOnLineSendOut").click();
		Log.logInfo("点击确认送出");
		if (!isExist("InfoLoading", "info_ok")) {
			List<WebElement> banks = getElements("selectBank");
			int index = RandomUtil.getRandom(0, banks.size());
			Log.logInfo("选择银行：" + banks.get(index).getText());
			// 随机选择银行
			banks.get(index).click();
			Log.logInfo("点击下一步");
			getElement("iptNext").click();
			if (!isExist("InfoLoading", "info_ok")) {
				Log.logInfo("成功跳转到第三方: " + driver.getCurrentUrl());
			} else {
				Log.logInfo("在线支付下一步提交失败！提示【" + getElement("info_text").getText() + "】");
				getElement("info_ok").click();
			}
			// return;
		} else {
			Log.logInfo("在线支付确认送出失败！提示【" + getElement("info_text").getText() + "】");
			getElement("info_ok").click();
		}
	}

	/**
	 * QQ扫码支付
	 * 
	 * 
	 */
	public void qqWallet() {
		// Log.logInfo("qqWallet");
		// if (isExist(getElementNoWait("QQWallet"))) {
		// 进入QQ扫码支付
		Log.logInfo("---------进入QQ扫码支付-------------");
		// getElement("QQWallet").click();
		// 输入金额
		String qq = GetMinMoney(getElement("txtMoney"), "placeholder");
		getElement("txtMoney").sendKeys(qq);
		Log.logInfo("输入金额：" + qq);
		// 点击确认送出
		getElement("btn_QQWalletSub").click();
		Log.logInfo("点击确认送出");
		if (!isExist("InfoLoading", "info_ok")) {
			assertEquals("二维码页面", driver.getTitle());
			Log.logInfo("QQ扫码支付--提交成功！");
			if (isExist(getElement("tijiaoBtn"))) {
				getElement("tijiaoBtn").click();
				Log.logInfo("点击重新提交");
				if (!isExist("InfoLoading", "info_ok"))
					Log.logInfo("QQ扫码支付--重新提交成功！");
			} else {
				// 判断提示框是否存在，输出提示信息
				Log.logInfo("提示【" + getElement("info_text").getText() + "】");
				getElement("info_ok").click();
			}
		} else {
			// 判断提示框是否存在，输出提示信息
			Log.logInfo("提示【" + getElement("info_text").getText() + "】");
			getElement("info_ok").click();
		}
	}

	/**
	 * 微信支付之九卅微信、在线微信
	 * 
	 */
	public void weChat(){
		// 进入微信支付
		Log.logInfo("---------进入微信支付-------------");
		String domain = driver.getCurrentUrl();
		domain = domain.substring(0, domain.indexOf("/Aspx"));
		if (isExist(getElementNoWait("weChatAuto"))) {
			// 输入金额
			String onlineWechat = GetMinMoney(getElement("txtMoney"), "placeholder");
			getElement("txtMoney").sendKeys(onlineWechat + "0");
			Log.logInfo("输入金额：" + onlineWechat + "0");
			// 点击确认送出
			getElement("btn_onlineWechatSub").click();
			Log.logInfo("点击确认送出");
			if (isExist("InfoLoading", "info_ok")) {
				Log.logInfo("提示【" + getElement("info_text").getText() + "】");
				getElement("info_ok").click();
			} else {
				assertEquals("二维码页面", driver.getTitle());
				getElement("tijiaoBtn").click();
				Log.logInfo("重新提交");
				if (isExist("InfoLoading", "info_ok")) {
					// 判断提示框是否存在，输出提示信息
					Log.logInfo("提示【" + getElement("info_text").getText() + "】");
					getElement("info_ok").click();
				} else {
					Log.logInfo("在线微信--重新提交成功！");
				}
			}
		} else {
			Log.logInfo("在线微信不开放！");
		}
		// 重新进入微信支付页面
		driver.get(domain + "/Aspx/WeChat_new.aspx");
		Log.logInfo("------重新进入微信支付----");
		//Thread.sleep(5000);
		// 点击九卅微信
		if (isExist(getElementNoWait("weChatMan"))) {
			getElementNoWait("weChatMan").click();
			Log.logInfo("选择九卅微信");
			// 输入金额
			String weChatManMoney = GetMinMoney(getElement("weChatManMoney"), "placeholder");
			getElement("weChatManMoney").sendKeys(weChatManMoney);
			Log.logInfo("输入金额：" + weChatManMoney);
			// 点击确认送出
			getElement("weChatManOut").click();
			Log.logInfo("点击确认送出");
			if (!isExist("InfoLoading", "info_ok")) {
				getElement("btn_MagSure").click();
				// 判断提示框是否存在，输出提示信息
				Log.logInfo("提示【" + getElement("info_text").getText() + "】");
				getElement("info_ok").click();
			} else {
				Log.logInfo("提示【" + getElement("info_text").getText() + "】");
				getElement("info_ok").click();
			}
		} else {
			Log.logInfo("九卅微信不开放！");
		}
	}

	/**
	 * 支付宝钱包之在线支付宝
	 */
	public void alipayAuto() {
		Log.logInfo("--------进入支付宝支付------------");
		getElement("Alipay").click();
		if (isExist(getElement("alipayAuto"))) {
			Log.logInfo("选择在线支付宝");
			// 输入金额
			String alipayAutoMoney = GetMinMoney(getElement("txtMoney"), "placeholder");
			getElement("txtMoney").sendKeys(alipayAutoMoney);
			Log.logInfo("输入金额：" + alipayAutoMoney);
			// 点击确认送出
			getElement("okAlipaySubmit").click();
			Log.logInfo("点击确认送出");
			if (isExist(getElement("info_ok"))) {
				// 判断提示框是否存在，输出提示信息
				Log.logInfo("提示【" + getElement("info_text").getText() + "】");
				getElement("info_ok").click();
			} else {
				assertEquals("二维码页面", driver.getTitle());
				if (isExist(getElement("tijiaoBtn"))) {
					getElement("tijiaoBtn").click();
					Log.logInfo("重新提交");
					if (isExist(getElement("info_ok"))) {
						// 判断提示框是否存在，输出提示信息
						Log.logInfo("提示【" + getElement("info_text").getText() + "】");
						getElement("info_ok").click();
					}
				}
			}
		} else {
			Log.logInfo("在线支付宝不开放！");
		}

	}

	/**
	 * 支付宝钱包之专属支付宝
	 */
	public void alipayMan() {
		Log.logInfo("--------进入支付宝支付------------");
		getElement("Alipay").click();
		if (isExist(getElement("alipayMan"))) {
			getElement("alipayMan").click();
			Log.logInfo("选择专属支付宝");
			getElement("btnNext").click();
			if (isExist(getElement("selectOptions"))) {
				// List<WebElement>
				// accounts=geSelecttAllOptions(getElement("selectOptions"));
				// Log.logInfo(accounts.size());
				// 选择支付宝账户
				selectByIndex(getElement("selectOptions"), 0);
				Log.logInfo("选择支付宝账户");
				// 输入金额
				String txtAlipayManMoney = GetMinMoney(getElement("txtAlipayManMoney"), "placeholder");
				getElement("txtAlipayManMoney").sendKeys(txtAlipayManMoney);
				Log.logInfo("输入金额：" + txtAlipayManMoney);
				getElement("lastFiveNo").sendKeys(util.getRandomString(5));
				Log.logInfo("输入订单号");
				// 点击确认送出
				getElement("btnAlipayManOut").click();
				Log.logInfo("点击确认送出");
				if (isExist(getElement("alipaySubmit"))) {
					// 点击我已汇款
					getElement("alipaySubmit").click();
					Log.logInfo("点击我已汇款");
					if (isExist(getElement("apipayManSuccessMsg"))) {
						Log.logInfo(getElement("apipayManSuccessMsg").getText());
						getElement("alipayManOK").click();
					} else {
						// 判断提示框是否存在，输出提示信息
						Log.logInfo("提示【" + getElement("info_text").getText() + "】");
						getElement("info_ok").click();
					}
				}
			} else {
				// 判断提示框是否存在，输出提示信息
				Log.logInfo("提示【" + getElement("info_text").getText() + "】");
				getElement("info_ok").click();
			}

		} else {
			Log.logInfo("专属支付宝不开放！");
		}
	}

	/**
	 * 支付宝钱包之转到银行卡
	 */
	public void aliBank() {
		Log.logInfo("--------进入支付宝支付------------");
		getElement("Alipay").click();
		if (isExist(getElementNoWait("aliBank"))) {
			// 选择支付宝转银行卡
			getElement("aliBank").click();
			Log.logInfo("选择转到银行卡");
			List<WebElement> banks = geSelecttAllOptions(getElement("ddlBanks"));
			if (banks.size() > 0) {
				selectByIndex(getElement("ddlBanks"), 1);
				// 输入金额
				String txtMoneyapiBank = GetMinMoney(getElement("txtMoneyapiBank"), "placeholder");
				getElement("txtMoneyapiBank").sendKeys(txtMoneyapiBank);
				Log.logInfo("输入金额：" + txtMoneyapiBank);
				getElement("btnapiBankManOut").click();
				if (isExist(getElement("btn_MagSureNew"))) {
					getElement("btn_MagSureNew").click();
					Log.logInfo(getElement("info_text").getText());
					getElement("info_ok").click();
				} else {
					// 判断提示框是否存在，输出提示信息
					Log.logInfo("提示【" + getElement("info_text").getText() + "】");
					getElement("info_ok").click();
				}
			} else {
				Log.logInfo("没有可以选择的银行！");
			}

		} else {
			Log.logInfo("支付宝转到银行卡不开放！");
		}
	}

	/**
	 * 
	 * 快捷支付
	 * 
	 * @throws InterruptedException
	 */
	public void onlineFastPay() throws InterruptedException {
		// 输入金额
		String fastPayMoney = GetMinMoney(getElement("txtMoney"), "placeholder");
		getElement("txtMoney").sendKeys(fastPayMoney);
		Log.logInfo("输入金额：" + fastPayMoney);
		// 点击确认送出
		getElement("btn_onlineFastSubmit").click();
		Log.logInfo("点击确认送出");
		if (!isExist("InfoLoading", "info_ok")) {
			// 获取所有开放银行
			List<WebElement> banks = getElements("selectBank");
			int index = RandomUtil.getRandom(0, banks.size());
			Log.logInfo("选择银行：" + banks.get(index).getText());
			// 随机选择银行
			banks.get(index).click();
			Log.logInfo("点击下一步");
			getElement("iptNext").click();
			if (!isExist("InfoLoading", "info_ok")) {
				Log.logInfo("成功跳转到第三方支付: " + driver.getCurrentUrl());
			} else {
				// 判断提示框是否存在，输出提示信息
				Log.logInfo("快捷支付下一步提交失败！提示【" + getElement("info_text").getText() + "】");
				getElement("info_ok").click();
			}
		} else {
			// 判断提示框是否存在，输出提示信息
			Log.logInfo("快捷支付确认送出失败！提示【" + getElement("info_text").getText() + "】");
			getElement("info_ok").click();
		}
	}

	/**
	 * 扫码支付之银联扫码
	 */
	public void quickPassDepositUnion() {
		if (isExist(getElementNoWait("QuickPassDeposit"))) {
			getElement("QuickPassDeposit").click();
			if (isExist(getElement("divUnion"))) {
				Log.logInfo("选择银联扫码");
				// 输入金额
				String unionMoney = GetMinMoney(getElement("txtMoney"), "placeholder");
				getElement("txtMoney").sendKeys(unionMoney);
				Log.logInfo("输入金额：" + unionMoney);
				// 点击确认送出
				getElement("btn_QuickPassSub").click();
				Log.logInfo("点击确认送出");
				if (isExist(getElement("tijiaoBtn"))) {
					assertEquals("二维码页面", driver.getTitle());
					getElement("tijiaoBtn").click();
					Log.logInfo("重新提交");
					if (isExist(getElement("info_ok"))) {
						// 判断提示框是否存在，输出提示信息
						Log.logInfo("提示【" + getElement("info_text").getText() + "】");
						getElement("info_ok").click();
					}
				} else {
					// 判断提示框是否存在，输出提示信息
					Log.logInfo("提示【" + getElement("info_text").getText() + "】");
					getElement("info_ok").click();
				}
			} else {
				Log.logInfo("银联扫码不开放！");
			}
		} else {
			Log.logInfo("银联扫码不开放！");
		}

	}

	/**
	 * 扫码支付之百度钱包
	 */
	public void quickPassDepositBaidu() {
		if (isExist(getElementNoWait("QuickPassDeposit"))) {
			getElement("QuickPassDeposit").click();
			if (isExist(getElementNoWait("divBaidu"))) {
				getElement("divBaidu").click();
				Log.logInfo("选择百度钱包");
				// 输入金额
				String unionMoney = GetMinMoney(getElement("txtMoney"), "placeholder");
				getElement("txtMoney").sendKeys(unionMoney);
				Log.logInfo("输入金额：" + unionMoney);
				// 点击确认送出
				getElement("btn_QuickPassSub").click();
				Log.logInfo("点击确认送出");
				if (isExist(getElementNoWait("tijiaoBtn"))) {
					// 断言是否跳转成功
					assertEquals("二维码页面", driver.getTitle());
					getElement("tijiaoBtn").click();
					Log.logInfo("重新提交");
					if (isExist(getElementNoWait("info_ok"))) {
						// 判断提示框是否存在，输出提示信息
						Log.logInfo("提示【" + getElement("info_text").getText() + "】");
						getElement("info_ok").click();
					}
				} else {
					// 判断提示框是否存在，输出提示信息
					Log.logInfo("提示【" + getElement("info_text").getText() + "】");
					getElement("info_ok").click();

				}
			} else {
				Log.logInfo("百度钱包不开放！");
			}
		} else {
			Log.logInfo("银联扫码不开放！");
		}

	}

	/**
	 * 扫码支付之京东钱包
	 */
	public void quickPassDepositJD() {
		if (isExist(getElementNoWait("QuickPassDeposit"))) {
			getElement("QuickPassDeposit").click();
			if (isExist(getElementNoWait("divJd"))) {
				getElement("divJd").click();
				Log.logInfo("选择京东钱包");
				// 输入金额
				String unionMoney = GetMinMoney(getElement("txtMoney"), "placeholder");
				getElement("txtMoney").sendKeys(unionMoney);
				Log.logInfo("输入金额：" + unionMoney);
				// 点击确认送出
				getElement("btn_QuickPassSub").click();
				Log.logInfo("点击确认送出");
				if (isExist(getElement("tijiaoBtn"))) {
					// assertEquals("二维码页面", driver.getTitle());
					getElement("tijiaoBtn").click();
					Log.logInfo("重新提交");
					if (!isExist(getElementNoWait("info_ok"))) {
						Log.logInfo("京东扫码--重新提交成功");
					} else {
						// 判断提示框是否存在，输出提示信息
						Log.logInfo("提示【" + getElement("info_text").getText() + "】");
						getElement("info_ok").click();
					}
				} else {
					// 判断提示框是否存在，输出提示信息
					Log.logInfo("提示【" + getElement("info_text").getText() + "】");
					getElement("info_ok").click();
				}
			} else {
				Log.logInfo("京东钱包不开放！");
			}
		} else {
			Log.logInfo("银联扫码不开放！");
		}
	}

	/**
	 * 点卡支付
	 * 
	 * @throws InterruptedException
	 */
	public void cardPay() throws InterruptedException {
		String cardNum = util.getRandomNum(16);
		// 输入16位卡号
		getElement("txtCardNum").sendKeys(cardNum);
		Log.logInfo("输入卡号:" + cardNum);
		String cardCode = util.getRandomNum(4);
		// 输入4位cvv
		getElement("txtCardCode").sendKeys(cardCode);
		Log.logInfo("输入CVV:" + cardCode);
		// 选择月份
		selectByIndex(getElement("selExpMonth"), 0);
		// 选择年份
		selectByIndex(getElement("selExpYear"), 0);
		// 输入金额cardPayMoney
		String cardPayMoney = GetMinMoney(getElement("txtMoney"), "placeholder");
		getElement("txtMoney").sendKeys(cardPayMoney);
		Log.logInfo("输入金额：" + cardPayMoney);
		// 点击确认送出
		getElement("btnCardSubmit").click();
		Log.logInfo("点击确认送出");
		if (isExist("InfoLoading", "info_ok")) {
			// 判断提示框是否存在，输出提示信息
			Log.logInfo("点卡支付---提交失败！提示【" + getElement("info_text").getText() + "】");
			getElement("info_ok").click();
		} else {
			Log.logInfo("点卡支付---提交成功！跳转【" + driver.getCurrentUrl() + "】");
		}

	}

	/**
	 * 网络银行
	 */
	public void Czzq_1() {
		    //获取所有可以选择的银行
			List<WebElement> banks = geSelecttAllOptions(getElement("ddlBanks"));
			if (banks.size() > 0) {
				// 选择银行
				selectByIndex(getElement("ddlBanks"), 1);
				// 输入金额
				String bankMoney = GetMinMoney(getElement("contentBody_txtMoney"), "placeholder");
				getElement("contentBody_txtMoney").sendKeys(bankMoney);
				Log.logInfo("输入金额：" + bankMoney);
				// 点击确认送出
				getElement("btnField_1").click();
				Log.logInfo("点击确认送出");
				if (isExist("InfoLoading", "btn_MagSureNew")) 
				{
					//弹出框点击确认
					getElement("btn_MagSureNew").click();
					Log.logInfo("弹出框点击确认");
					if (isExist("InfoLoading", "info_ok")) {
						Log.logInfo("网络银行确认送出提交成功！提示【"+getElement("info_text").getText()+"】");
						getElement("info_ok").click();
					}else {
						Log.logInfo("网络银行提交失败！");
					}
					
				} else {
					// 判断提示框是否存在，输出提示信息
					Log.logInfo("提示【" + getElement("info_text").getText() + "】");
					getElement("info_ok").click();
				}
			} else {
				Log.logInfo("没有开放银行！！");
			}
	}

}