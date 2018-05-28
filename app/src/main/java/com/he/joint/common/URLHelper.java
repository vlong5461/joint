package com.he.joint.common;

public class URLHelper {

	public static final String BaseURL = "http://hz.ecthink.cn/api";
	public static final String loginURL = BaseURL+"index/login"; //登录
	public static final String logoutURL = BaseURL+"index/logout"; //退出登录
	public static final String weiXinLoginURL = BaseURL+"index/weiXinLogin"; //微信登录
	public static final String OpenLoginURL = BaseURL+"index/openLogin"; //微信登录
	public static final String updatePwdURL = BaseURL+"index/updatePwd"; //修改密码
	public static final String resetPwdURL = BaseURL+"index/resetPwd"; //忘记密码
	public static final String registerURL = BaseURL+"index/register"; //注册
	public static final String feedbackAddURL = BaseURL+"feedback/add"; //用户反馈
	public static final String recommendListURL = BaseURL+"document/recommendList"; //推荐列表
	public static final String newsFlashListURL = BaseURL+"document/newsFlashList"; //快迅（列表 )
	public static final String detailURL = BaseURL+"document/detail"; //新闻详细
	public static final String collectURL = BaseURL+"favorite/collect"; //收藏/取消收藏
	public static final String playList = BaseURL+"document/playList"; //图片轮播
	public static final String reportURL = BaseURL+"document/report"; //首页报告
	public static final String adListURL = BaseURL+"ad/lists"; //广告列表
	public static final String newsFlashOneURL = BaseURL+"document/newsFlashOne"; //快迅（一条）
	public static final String reportListURL = BaseURL+"document/reportList"; //报告列表
	public static final String reportSearch = BaseURL+"document/reportSearch"; //报告搜索
	public static final String materialCategoryURL = BaseURL+"document/materialCategory"; //资料分类
	public static final String materialListURL = BaseURL+"document/materialList"; //资料列表
	public static final String reportDetailURL = BaseURL+"document/reportDetail"; //报告详情
	public static final String categorySearchURL = BaseURL+"category/search"; //搜索（返回分类）
	public static final String documentSearchURL = BaseURL+"document/search"; //搜索（返回列表）
	public static final String expertListURL = BaseURL+"user/expertList"; //专家列表
	public static final String expertDetailURL = BaseURL+"user/expertDetail"; //专家详情
	public static final String questionlistsURL = BaseURL+"question/lists"; //问题列表
	public static final String questionDetailURL = BaseURL+"question/detail"; //问题详情
	public static final String questionaddURL = BaseURL+"question/add"; //立即提问
	public static final String uploadURL = BaseURL+"upload/upload"; //图片/音频上传
	public static final String replyURL = BaseURL+"answer/reply"; //回复问题
	public static final String expertReplyListURL = BaseURL+"answer/expertReplyList"; //专家回复列表（某个专家）
	public static final String noticeListURL = BaseURL+"message/noticeList"; //通知
	public static final String messagelistsURL = BaseURL+"message/lists"; //问答
	public static final String usereProfileURL = BaseURL+"user/profile"; //我的资料
	public static final String usereditURL = BaseURL+"user/edit"; //编辑资料
	public static final String favoritelistsURL = BaseURL+"favorite/lists"; //我的收藏
	public static final String attentionlistsURL = BaseURL+"attention/lists"; //我的关注
	public static final String attentionattentURL = BaseURL+"attention/attent"; //取消/关注专家
	public static final String myQuestionListURL = BaseURL+"question/myQuestionList"; //我的问答
	public static final String sendSmsURL = BaseURL+"sms/sendSms"; //发送短信
	public static final String checkSmsURL = BaseURL+"sms/checkSms"; //检查短信
}
