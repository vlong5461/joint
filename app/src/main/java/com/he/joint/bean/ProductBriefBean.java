package com.he.joint.bean;

import java.util.List;

public class ProductBriefBean extends BaseBean {

	private static final long serialVersionUID = -1657459499503816014L;
	
	public String TravelID;
	public String CountryID;
	public String CityID;
	public String Title;
	public String IsBigSale;
	public String IsConfirm;
	public String ViewImage;
	public String NowPrice;
	public String OrgPrice;
	public String Location;
	public String Location_Hint;
	public DaoDaoPointNean DaoDaoPoint;
	public String PurchaseNo;
	public String comments_num;
	public List<RecomLabelsBean> RecomLabels;
	public int IsHaveVideo;

	public static class DaoDaoPointNean extends BaseBean{

		private static final long serialVersionUID = -8630609005608052409L;
		
		public String point;
		public String img;
	}
	public static class RecomLabelsBean extends BaseBean {

		private static final long serialVersionUID = 761002417008006762L;
		public String RecomCode;
		public String RecomLabelTitle;
		public String Title;
		public String Viewers;

	}
}
