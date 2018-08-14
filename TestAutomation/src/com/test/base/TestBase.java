package com.test.base;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.dom4j.Element;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import com.test.bean.Config;
import com.test.bean.Global;
import com.test.util.Log;
import com.test.util.ParseXml;
import com.test.util.Util;

public class TestBase {

	protected WebDriver driver;

	private ParseXml px;

	private Map<String, String> commonMap;

	private void initialPx() {
		if (px == null) {
			boolean isMobile = this.getClass().getPackage().getName().contains("mobile");
			px = new ParseXml("test-data/" + (isMobile ? "mobile/" : "pc/") + this.getClass().getSimpleName() + ".xml");
		}
	}

	private void getCommonMap() {
		if (commonMap == null) {
			Element element = px.getElementObject("/*/common");
			commonMap = px.getChildrenInfoByElement(element);
		}
	}

	private Map<String, String> getMergeMapData(Map<String, String> map1, Map<String, String> map2) {
		Iterator<String> it = map2.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = map2.get(key);
			if (!map1.containsKey(key)) {
				map1.put(key, value);
			}
		}
		return map1;
	}

	@DataProvider
	public Object[][] providerMethod(Method method) {
		this.initialPx();
		this.getCommonMap();
		String methodName = method.getName();
		List<Element> elements = px.getElementObjects("/*/" + methodName);
		Object[][] object = new Object[elements.size()][];
		for (int i = 0; i < elements.size(); i++) {
			Map<String, String> mergeCommon = this.getMergeMapData(px.getChildrenInfoByElement(elements.get(i)),
					commonMap);
			Map<String, String> mergeGlobal = this.getMergeMapData(mergeCommon, Global.global);
			Object[] temp = new Object[] { mergeGlobal };
			object[i] = temp;
		}
		return object;
	}

	@BeforeClass
	public void initialDriver() {
		SeleniumDriver selenium = new SeleniumDriver();
		driver = selenium.getDriver();
	}

	@AfterClass
	public void closeDriver() {
		if (driver != null) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// driver.close();
			// driver.quit();
		}
	}

	public void goTo(String url) {
		driver.get(url);
		if (Config.browser.equals("chrome")) {
			Util.sleep(1.0);
		}
	}

	/**
	 * 获得当前窗口句柄
	 * 
	 * @return
	 */
	public WebDriver getCurrentWindowHandle() {
		String currentWindow = driver.getWindowHandle(); // 获取当前窗口的句柄
		Set<String> handles = driver.getWindowHandles(); // 获取所有窗口的句柄
		Iterator<String> it = handles.iterator();
		while (it.hasNext()) {
			String handle = it.next();
			if (!handle.equals(currentWindow)) {
				return driver.switchTo().window(handle); // 切换到新的句柄所指向的窗口
			}
		}
		return driver;
	}

	/**
	 * 判断文本是不是和需求要求的文本一致
	 **/
	public void isTextCorrect(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			Log.logInfo("期望的文字是 [" + expected + "] 但是找到了 [" + actual + "]");
			Assert.fail("期望的文字是 [" + expected + "] 但是找到了 [" + actual + "]");

		}
		Log.logInfo("找到了期望的文字: [" + expected + "]");
	}

	// webdriver中可以设置很多的超时时间
	/** implicitlyWait。识别对象时的超时时间。过了这个时间如果对象还没找到的话就会抛出NoSuchElement异常 */
	public void implicitlyWait(int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}

	public void intelligentWait(int timeOut, final WebElement element) {
		try {
			(new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return element.isDisplayed();
				}
			});
		} catch (TimeoutException e) {
			Assert.fail("超时!! " + timeOut + " 秒之后还没找到元素 [" + element + "]", e);
		}
	}

	/**
	 * pageLoadTimeout。页面加载时的超时时间。因为webdriver会等页面加载完毕在进行后面的操作，
	 * 所以如果页面在这个超时时间内没有加载完成，那么webdriver就会抛出异常
	 */

	public void waitForPageLoading(int pageLoadTime) {
		driver.manage().timeouts().pageLoadTimeout(pageLoadTime, TimeUnit.SECONDS);
	}

	/**
	 * 获得页面的标题
	 */
	public String getTitle() {
		return driver.getTitle();
	}

}
