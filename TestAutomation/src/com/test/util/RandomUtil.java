package com.test.util;

public class RandomUtil {
	
	/**
	 * 返回一个0-count（包含count）的随机数
	 * @param count
	 * @return
	 */
	public int getRandom(int count) {
        return (int) Math.round(Math.random() * (count));
    }
	
	private String string = "0123456789abcdefghijklmnopqrstuvwxyz";
	private String number = "0123456789";
	private String RemittanceName="宿雾薄荷岛是菲律宾多个岛屿中最美的一这里的沙滩可以媲美马尔代夫这里海水清是世界潜水爱好者的天堂这里有奇特的巧克力山世界最小的迷你眼镜猴";
	
	/**
	 * 从0123456789abcdefghijklmnopqrstuvwxyz中选随机生成长度为length的字符串
	 * @param length
	 * @return
	 */
	public String getRandomRemittanceName(int length){
		StringBuffer sb = new StringBuffer();
		int len = RemittanceName.length();
		for (int i = 0; i < length; i++) {
			sb.append(RemittanceName.charAt(this.getRandom(len-1)));
		}
		return sb.toString();
	}
	/**
	 * 从0123456789中选随机生成长度为length的字符串
	 * @param length
	 * @return
	 */
	public String getRandomNum(int length){
		StringBuffer sb = new StringBuffer();
		int len = number.length();
		for (int i = 0; i < length; i++) {
			sb.append(string.charAt(this.getRandom(len-1)));
		}
		return sb.toString();
	}
	/**
	 * 从0123456789中选随机生成长度为length的字符串
	 * @param length
	 * @return
	 */
	public String getRandomString(int length){
		StringBuffer sb = new StringBuffer();
		int len = number.length();
		for (int i = 0; i < length; i++) {
			sb.append(string.charAt(this.getRandom(len-1)));
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		RandomUtil ru = new RandomUtil();
		for (int i = 0; i < 10; i++) {
			Log.logInfo(ru.getRandomRemittanceName(6));
		}
		
	}	
}
