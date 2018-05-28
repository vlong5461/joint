package com.he.joint.common;

public class Consts {
	
	//小能SDK
	public static String siteid = "kf_9190";// 示例kf_9979,kf_8002,kf_3004,zf_1000,yy_1000
	public static String sdkkey = "B29549E9-8E3E-4605-AB1A-B9D5A6FE82A4";// 示例FB7677EF-00AC-169D-1CAD-DEDA35F9C07B
	public static String settingidafter = "kf_9190_1460957467413";// 售后客服组id
	public static String settingidbefore = "kf_9190_1460967023130";// 售前客服组id
	public static String groupNameafter = "售后";// 售后客服组默认名称
	public static String groupNamebefore = "售前";// 售前客服组默认名称
	
	public static final String City_ID = "city_id";
	public static final String City_Name = "City_Name";
	public static final String Type_ID = "Type_ID";
	public static final String Item_ID = "Item_ID";
	public static final String Search_Type = "Search_Type";
	public static final String Key_Word = "Key_Word";
	public static final String Product_Type = "Product_Type";
	public static final String Temperature_Unit_Type = "Temperature_Unit_Type";
	public static final String Order_ID = "Order_ID";
	public static final String Order_Comfirm= "Order_Comfirm";
	public static final String Cert_Data = "Cert_Data";
	public static final String Content_Data = "Content_Data";
	public static final String Html_Data = "Html_Data";
	public static final String POI_ID = "POI_ID";
	public static final String Travel_ID = "Travel_ID";
	public static final String Top_Title = "Top_Title";
	public static final String Country_ID = "Country_ID";
	public static final String Country_Name = "Country_Name";
	public static final String Country_Type = "Country_Type";
	public static final String Start_Date = "Start_Date";
	public static final String End_Date = "End_Date";
	public static final String Return_Money = "Return_Money";
	public static final String Norm_Info = "Norm_Info";
	public static final String TRADE_TYPE = "trade_type";
	public static final String Serial_ID = "Serial_ID";
	public static final String Book_Type = "Book_Type";
	public static final String Recommend_ID = "Recommend_ID";
	public static final String Temp_User_ID= "Temp_User_ID";
	public static final String Selected_Country_ID= "Selected_Country_ID";
	public static final String Selected_Country_Name= "Selected_Country_Name";
	public static final String Selected_Continent_ID= "Selected_Continent_ID";
	public static final String Selected_Continent_Name= "Selected_Continent_Name";
	public static final String Selected_App_Country_ID= "Selected_App_Country_ID";
	public static final String MoreType= "MoreType";
	public static final  String Video_Code= "Video_Code";
	public static final  String Video_Title= "Video_Title";
	public static final  String Product_Tid= "tid";
	public static final  String Product_Skid= "skid";
	public static final  String Location_Lat= "Location_Lat";
	public static final  String Location_Long= "Location_Long";
	public static final String Trip_Map = "Trip_Map";
    public static final String Check_Update_Flag_Local = "Check_Update_Flag_Local";
    public static final String Check_Update_Flag_Delicacy = "Check_Update_Flag_Delicacy";
    public static final String Check_Update_Flag_Traffic = "Check_Update_Flag_Traffic";
    public static final String Check_Update_Flag_Tourism = "Check_Update_Flag_Tourism";
    public static final String Check_Update_Flag_Transfer = "Check_Update_Flag_Transfer";
    public static final String Check_Update_Flag_Rental = "Check_Update_Flag_Rental";
	public static final String Video_ID_List= "Video_ID_List";
	public static final String Contient_ID = "Contient_ID";
	public static final String Good_IDs = "Good_IDs";
	public static final String ViewPager_Position = "ViewPager_Position";
	public static final String Home_Area_Bean = "Home_Area_Bean";
	public static final String Need_Load_Cache = "Need_Load_Cache";
	public static final String Login_Receiver_Action = "com.gf.rruu.Login_Receiver_Action";
	public static final String First_Load_App_V6 = "First_Load_App_V6";
	public static final  String Is_From_Kill= "Is_From_Kill";
	public static final  String SecKill_Id= "SecKill_Id";
	public static final String Public_WebView_Title = "title";
	public static final String Public_WebView_url = "url";
	public static final String DeviceID_Before_V6 = "DeviceID_Before_V6";
	public static final String API_Data_Type = "API_Data_Type";//数据类型
	public static final String API_Data_Test = "API_Data_Test";//测试数据
	public static final String API_Data_Form = "API_Data_Form";//正式数据
	//是否有行程引导
	public static final String Is_Have_Travel= "Is_Have_Travel";
	//是否有通知设置
	public static final String Is_Notification= "Is_Notification";
	//腾讯IM——APPid
	public static final String USER_SIG= "userSig";
	public static final int TX_SDKAPPID=1400012161;
	public static final String TX_AppidAt3rd="1400012161";
	public static final String TX_accountType="6311";
	public static final String First_View_Product_Detail_Video = "First_View_Product_Detail_Video";
	
	public interface TradeType {
		String WAITPAY = "WAITPAY";  //等待付款
		String WAITSEND = "WAITSEND";  //等待发放确信
		String SUCCESS = "SUCCESS";   //交易成功
		String EXPIRED = "EXPIRED";   //订单已过期
		String ABANDON="ABANDON";   //订单已放弃
		String CANCEL="CANCEL";   //订单已取消
		String ERROR="ERROR";     //订单状态错误
		String REFUND="REFUND";    //订单退款完成
		String COMPLETE="COMPLETE";  //订单发放凭证完成
		String CONFIRM = "CONFIRM";  //订单已确认
		String CANCELING = "CANCELING"; //订单取消中
	}
	
	public interface GetDataType {
		int Normal = 1;
		int Refresh = 2;
		int LoadMore = 3;
		int Background = 4;
	}
	
	public interface PaymentType {
		String Zhi_Fu_Bao = "0";
		String Wei_Xin = "1";
		String None = "-1";
	}
	public interface CarPayMentType {
		String Quan_E= "1";  //预付全额
		String Ding_jin = "2";    //预付定金
		String Dao_Dian_Fu = "3";      //到店支付
	}
	public interface NetworkType {
		int NetworkType_Non = 0;
		int NetworkType_Wifi = 1;
		int NetworkType_2G3G = 2;
	}
	
	public interface ResponseCode {
		int Success = 200;
	}
	
	public interface ContentCode {
		int Success = 1;
	}
	
	public interface RequestCode {
		int Register = 1;
		int Get_Verification_Code = 2;
		int ModifyPassword = 3;
		int GetMessageList = 4;
		int UpdateMessage = 5;
		
	}
	
	public interface SendCheckCode {
		String Register = "0";
		String FindPassword = "1";
	}
	
	public interface ActivityRequestCode {
		int Register = 1;
		int ModifyPassword = 2;
		
	}
	
	public interface PreferenceKeys {
		String MessageNotificationSwitch = "MessageNotificationSwitch";
		String outphone = "outphone";
	}
	
	public interface DistinationType {
		String Recommend = "1";
		String Country = "2";
		String State = "3";
	}
	
	public interface TransferType {
		String Pickup = "0";
		String Send = "1";
	}
	
	public interface SearchType {
		int Home = 1;
		int Distination = 2;
		int CityHome = 3;
	}
	
	public interface GoodListType {
		String LocalPlay = "1";
		String Communication = "5";
		String Travel = "7";
		String TravelMust = "16";
		String WIFI = "19";
		String Insurrance = "20";
		String Food = "8";
	}

	public interface ProductListSort {
		String Default = "0";
		String Price = "1";
		String Discount = "2";
		String Score = "3";
		String Distance = "4";
	}
	
	public interface LiveStatusType {
		String Waiting = "0";
		String Living = "1";
		String History = "2";
		String Over = "3";
		String Unknown = "4";
	}
	
	public interface HomeRequestType_V2 {
		String Live = "1";
		String Video_ID = "2";
		String Video_List = "3";
		String Product_List = "4";
		String H5 = "5";
		String CountDown = "6";
		String App_Choice = "7";
		String Live_List = "8";
	}
	
	public interface ProductType {
		String List_ID = "0";
		String List_Filter = "1";
		String Product_ID = "2";
		String Html = "3";
	}
	
	public interface ProductType_V2 {
		String POI = "0";
		String Product = "1";
	}

	public interface  ScrollDirection {
		int Up = 1;
		int Down = 2;
	}

	public interface LaunchAdsType_V6 {
		String Product_Detail = "0";
		String Live_Detail = "1";
		String Html5 = "2";
	}

//	public interface HomeAdsType_V6 extends HomeCategoryType_V6 {
//
//	}
//
//	public interface HomeOperationType_V6 extends HomeCategoryType_V6 {
//
//	}

	public interface HomeCategoryType_V6 {
		String Local_Play = "1";
		String Food = "8";
		String Transfer = "400";
		String Retail_Car = "300";
		String Traffic = "5";
		String Travel = "7";
		String WIFI = "19";
		String Insurance = "20";
		String Product_ID_List = "0";
		String Html = "3";
		String Product_Detail = "2";
	}

	public interface ShelvesStatusType {
		String DownShelves = "0";
		String UpShelves = "1";
	}
}
