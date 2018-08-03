package com.test.base;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.*;

import com.opera.core.systems.scope.protos.ExecProtos.ActionList.Action;
import com.test.util.Log;

public class Page extends Locator{
	
	public Page(WebDriver driver) {		
		super(driver);		
		this.setYamlFile(this.getClass().getSimpleName());
		this.getYamlFile();
		//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
	public Actions getAction(){
		return new Actions(driver);
	}
	
	/***
	 * 切换浏览器句柄至index
	 * @param index
	 */
	public void switchWindowByIndex(int index){
		Object[] handles = driver.getWindowHandles().toArray();
		if(index>handles.length){
			return;
		}
		driver.switchTo().window(handles[index].toString());
	}
	
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	/***
	 * 判断元素element是否存在
	 * @param element
	 * @return
	 */
	public boolean isExist(WebElement element){
		if(element==null){
			return false;
		}else{
			return true;
		}
	}
	
	/***
	 * 关闭浏览器窗口
	 */
	public void closeWindow() {
		if(driver!=null)
			driver.close();
	}
	

	/**
	 * 清除输入框操作
	 * */
	public void clear(WebElement element) {
		try {
			element.clear();
		} catch (Exception e) {
			Log.logError("清除元素 [" + element + "] 上的内容失败!");
		}
		Log.logInfo("清除元素 [" + element  + "]上的内容");
	}
	
	
	/**
	 * 该方法为示例方法
	 */
	public void test(){
		driver.navigate().to("");
	}
	
	/**
	 * 获得当前select选择的值
	 * */
	public List<WebElement> getCurrentSelectValue(WebElement element){
		List<WebElement> options = null;
		Select s = new Select(element);
			options =  s.getAllSelectedOptions();
			return options;
	}
	/**
	 * 获得当前select选择的值
	 * */
	public String getCurrentSelectText(WebElement element){
		String text = null;
		Select s = new Select(element);
		text = s.getAllSelectedOptions().get(0).getText();
			return text;
	}
	/**
	 * 获得所有select的选项
	 * */
	public List<WebElement> geSelecttAllOptions(WebElement element){
		List<WebElement> options = null;
		Select s = new Select(element);
			options =s.getOptions();
			return options;
	}
	
	/**
	 * 选择下拉选项 -根据index角标
	 * */
	public void selectByIndex(WebElement element, int index) {
		Select s = new Select(element);
		s.selectByIndex(index);
	}
	/**
	 * 选择下拉选项 -根据value
	 * */
	public void selectByValue(WebElement element, String value) {
		Select s = new Select(element);
		s.selectByValue(value);
		
	}
	/** 检查checkbox是不是勾选 */
	public boolean doesCheckboxSelected(WebElement element) {
		if (element.isSelected() == true) {
			Log.logInfo("元素"+element+" 被勾选");
			return true;
		} else
			Log.logInfo("元素"+element+" 未勾选");
		return false;

	}

	/** 不能点击时候重试点击操作 */
	public void clickTheClickable(WebElement element, long startTime, int timeOut) throws Exception {
		
		try {
			element.click();
		} catch (Exception e) {
			if (System.currentTimeMillis() - startTime > timeOut) {
				Log.logWarn(element+ " is unclickable");
				throw new Exception(e);
			} else {
				Thread.sleep(500);
				Log.logWarn(element + " is unclickable, try again");
				clickTheClickable(element, startTime, timeOut);
			}
		}
	}
	/**
	 * 在给定的时间内去查找元素，如果没找到则超时，抛出异常
	 * */
	public void waitForElementToLoad(int timeOut, final String key) {
		Log.logInfo("开始查找元素[" + getElement(key) + "]");
		try {
			(new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver driver) {
					WebElement element=driver.findElement(By.xpath(key));
					return element.isDisplayed();
				}
			});
		} catch (TimeoutException e) {
			Log.logError("超时!! " + timeOut + " 秒之后还没找到元素 [" + getElement(key) + "]");
			Assert.fail("超时!! " + timeOut + " 秒之后还没找到元素 [" + getElement(key) + "]");

		}
		Log.logInfo("找到了元素 [" +getElement(key)+ "]");
	}
	/***
	 * 从元素属性值中正则提取字符串中的数字
	 * @param element  
	 * @param s 
	 * @return
	 */
   public String  getAttributeValue(String s) {
	   String regEx="[^0-9]"; 
	   Pattern p = Pattern.compile(regEx);  
	   Matcher m = p.matcher(s);  
	   return m.replaceAll("").trim();
}
		
	
	

}
