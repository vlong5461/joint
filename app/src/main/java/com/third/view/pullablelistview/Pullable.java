package com.third.view.pullablelistview;

public interface Pullable {
	/**
	 * 
	 * @return true如果可以下拉否则返回false
	 */
	boolean canPullDown();

	/**
	 * 
	 * @return true如果可以上拉否则返回false
	 */
	boolean canPullUp();
}
