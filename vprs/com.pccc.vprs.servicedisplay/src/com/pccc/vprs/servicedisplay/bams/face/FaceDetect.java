//package com.pccc.vprs.servicedisplay.bams.face;
//
//
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import org.apache.commons.lang.StringUtils;
//import com.eos.foundation.database.DatabaseExt;
//import com.eos.system.annotation.Bizlet;
//import com.primeton.btp.api.core.exception.BTPRuntimeException;
//import com.primeton.btp.api.core.logger.ILogger;
//import com.primeton.btp.api.core.logger.LoggerFactory;
//
//
//import com.pccc.vprs.servicecustom.common.Constants;
//import com.pccc.vprs.servicecustom.common.ReturnCode;
////import com.pccc.vprs.servicecustom.constants.BamsConstant;
//import com.pccc.vprs.servicecustom.util.BAMSUtils;
//import com.pccc.vprs.servicecustom.util.ImgFileUtils;
//import com.pccc.vprs.servicedisplay.bams.face.service.FaceFactory;
//import com.pccc.vprs.servicedisplay.bams.face.service.faceService;
//import com.pccc.vprs.servicedisplay.bams.model.TransDetailModel;
//import com.pccc.vprs.servicedisplay.bams.model.TransDetailModelVO;
//import com.pccc.vprs.servicedisplay.bams.model.TransFlowConstant;
//import com.pccc.vprs.servicedisplay.bams.model.UserInfo;
//import com.pccc.vprs.servicedisplay.bams.util.BAMSDaoUtils;
//import com.pccc.vprs.servicedisplay.bams.util.TwoTuple;
//
//import commonj.sdo.DataObject;
//
//
//
//@Bizlet("人脸识别管理服务")
//public class FaceDetect {
//	private static ILogger logger = LoggerFactory.getLogger(FaceDetect.class);
//
//	@Bizlet("添加用户信息表") 
//	public DataObject insertUserInfo(DataObject inMessage,DataObject outMessage,UserInfo userInfo){	
//		logger.info("进入添加用户信息");
//		String para[]={"channel","certType","closeImg","certNo","userName","userType"};
//		for (int i = 0; i < para.length; i++) {
//			if(StringUtils.isBlank(inMessage.getString(para[i]))){
//				outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//				outMessage.set("returnMsg", "输入参数必输项丢失");
//				return outMessage;
//			}
//		}
//		String certHeadImg=userInfo.getCertHeadImg();
//		BigDecimal score=userInfo.getScore();
//		BigDecimal certFaceScore=userInfo.getCertFaceScore();
//		//创建一个新的对象
//		userInfo=new UserInfo();	
//		String channel=inMessage.getString("channel");
//		userInfo.setCrtChannel(channel);
//		userInfo.setUserName(inMessage.getString("userName"));
//		userInfo.setCertType(inMessage.getString("certType"));
//		userInfo.setCertNo(inMessage.getString("certNo"));
//		userInfo.setScore(score);
//		userInfo.setCertFaceScore(certFaceScore);
//		String closeImg=inMessage.getString("closeImg");
//		if(StringUtils.isNotBlank(closeImg)){
//			try {
//				String directory = ImgFileUtils.crtImgPathByUUID(BAMSUtils.getUUID(),channel);
//				logger.info("个人近照照片存储路径："+directory);
//				ImgFileUtils.convertBase64DataToImage(closeImg, directory);	
//				userInfo.setCloseImg(directory);
//				userInfo.setCloseImgLstUpdTime(new Date());
//			} catch (Exception e) {
//				logger.error("==== FaceDetect.insertUserInfo操作数据字典closeImg时异常  ====",e);		
//				logger.info("存储图片文件到文件服务器出错");
//				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
//				outMessage.set("returnMsg", "存储图片文件到文件服务器出错");
//				return outMessage;
//			}		
//		}		
//		if(StringUtils.isNotBlank( inMessage.getString("certImg"))){
//			try {
//				String certImg=inMessage.getString("certImg");
//				String directory = ImgFileUtils.crtImgPathByUUID(BAMSUtils.getUUID(),channel);
//				logger.info("个人证件照照片存储路径："+directory);
//				ImgFileUtils.convertBase64DataToImage(certImg, directory);	
//				userInfo.setCertImg(directory);
//				userInfo.setCertChipImgLstUpdTime(new Date());
//			} catch (Exception e) {
//				logger.error("==== FaceDetect.insertUserInfo操作数据字典certImg时异常  ====",e);			
//			}		
//		}
//		if(StringUtils.isNotBlank(inMessage.getString("certFaceImg"))){
//			try {
//				String certFaceImg=inMessage.getString("certFaceImg");
//				String directory = ImgFileUtils.crtImgPathByUUID(BAMSUtils.getUUID(),channel);
//				logger.info("个人证件正面照照片存储路径："+directory);
//				ImgFileUtils.convertBase64DataToImage(certFaceImg, directory);	
//				userInfo.setCertFaceImg(directory);
//				userInfo.setCertImgLstUpdTime(new Date());
//			} catch (Exception e) {
//				logger.error("==== FaceDetect.insertUserInfo操作数据字典certFaceImg时异常  ====",e);			
//			}		
//		}
//		if(StringUtils.isNotBlank(inMessage.getString("certBackImg"))){
//			try {
//				String certBackImg=inMessage.getString("certBackImg");
//				String directory = ImgFileUtils.crtImgPathByUUID(BAMSUtils.getUUID(),channel);
//				logger.info("个人证件反面照照片存储路径："+directory);
//				ImgFileUtils.convertBase64DataToImage(certBackImg, directory);	
//				userInfo.setCertBackImg(directory);
//			} catch (Exception e) {
//				logger.error("====FaceDetect.insertUserInfo操作数据字典certBackImg时异常  ====",e);			
//			}		
//		}
//		
//		if(StringUtils.isNotBlank(certHeadImg)){
//			try {
//				String directory = ImgFileUtils.crtImgPathByUUID(BAMSUtils.getUUID(),channel);
//				logger.info("个人证件照照片存储路径："+directory);
//				ImgFileUtils.convertBase64DataToImage(certHeadImg, directory);	
//				userInfo.setCertHeadImg(directory);
//				userInfo.setCertHeadImgLstUpdTime(new Date());
//			} catch (Exception e) {
//				logger.error("==== FaceDetect.insertUserInfo操作数据字典certHeadImg  ====",e);	
//				logger.info("存储图片文件到文件服务器出错");
//				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
//				outMessage.set("returnMsg", "存储图片文件到文件服务器出错");
//				return outMessage;
//			}		
//		}
//		userInfo.setStatus("0");
//		String insertSqlId = "com.pccc.touda.bams.servicecustom.sql.FaceDetect.insertUserInfo";
//
//		try {
//			DatabaseExt.executeNamedSql("default", insertSqlId, userInfo);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//			outMessage.set("returnCode",ReturnCode.DATABASE_SYSTEM_ERROR );
//			outMessage.set("returnMsg", "插入用户信息表失败！");
//			logger.error("插入用户信息表数据库操作异常，异常信息:" + e1.getMessage(), e1);
//			throw new BTPRuntimeException("插入用户信息表数据库操作异常，异常信息:" + e1.getMessage(), e1);	
//		}
//		
//		outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
//		outMessage.set("returnMsg", "添加用户信息成功！");
//		return outMessage;
//	}
//
//
//	@Bizlet("人脸质量分析服务")
//	public DataObject qualityAnalyCheck(DataObject inMessage,DataObject outMessage,UserInfo userInfo){
//		logger.info("进入人脸质量分析服务");
//		if(StringUtils.isBlank(inMessage.getString("closeImg"))||StringUtils.isBlank(inMessage.getString("channel"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}	
//		try{
//			faceService f = FaceFactory.getInstance(BamsConstant.FACE_SERVICE_PROVIDER);
//			String img=inMessage.getString("closeImg");
//			BigDecimal threshold=BigDecimal.ZERO;
////			if(threshold==null||threshold.compareTo(new BigDecimal(0))==0){
////				outMessage.set("returnCode", ReturnCode.THRESHOLD_GET_ERROR);
////				outMessage.set("returnMsg", "获取阈值异常");
////				return outMessage;
////			}
//			Boolean flag=false;
//			BigDecimal score=BigDecimal.valueOf(0.00);
//			userInfo.setScore(score);
//			if (f != null) {	
//				TwoTuple<Boolean,BigDecimal> obj=f.qualityAnaly(img, threshold);
//				flag=obj.first;
//				score=obj.second;
//			}
//			if(flag){
//				userInfo.setScore(score);
//				outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
//				outMessage.set("returnMsg", "人脸质量分析通过!");
//			}else{
//				outMessage.set("returnCode", ReturnCode.QUALITY_ANALY_CHECK_ERROR);
//				outMessage.set("returnMsg", "人脸质量分析,图片不符合要求");
//			}	
//		}catch(Exception e){
//			outMessage.set("returnCode", ReturnCode.GENERIC_EXTERNAL_SYSTEM_CALL_EXCEPTION);
//			outMessage.set("returnMsg", "调用人脸质量分析服务异常:"+e.getMessage());
//		}
//		return outMessage;
//	}
//
//
//	@Bizlet("人脸登录校验")
//	public DataObject login(DataObject inMessage,DataObject outMessage,UserInfo userInfo){
//		logger.info("进入人脸登录校验服务");
//		if(StringUtils.isBlank(inMessage.getString("closeImg"))||StringUtils.isBlank(inMessage.getString("channel"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		if(userInfo==null||StringUtils.isBlank(userInfo.getCloseImg())){
//			outMessage.set("returnCode", ReturnCode.USER_NOT_EXIT);
//			outMessage.set("returnMsg", "用户不存在");
//			return outMessage;
//		}
//		try{
//			faceService f = FaceFactory.getInstance(BamsConstant.FACE_SERVICE_PROVIDER);
//			String imgA=inMessage.getString("closeImg");
//			String imgBPath=userInfo.getCloseImg();
//			String imgB=ImgFileUtils.getImageFile(imgBPath);
//			if(StringUtils.isBlank(imgB)){
//				logger.info("数据库图片存储路径转换Base64位失败");
//				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
//				outMessage.set("returnMsg", "数据库图片存储路径转换Base64位失败");
//				return outMessage;
//			}
//			BigDecimal threshold=BAMSDaoUtils.queryThreshold(inMessage.getString("channel"), inMessage.getString("businessType"), "face",Constants.COMPARE_CLEAR_WITH_CLEAR);
//			if(threshold==null||threshold.compareTo(BigDecimal.ZERO)==0){
//				outMessage.set("returnCode", ReturnCode.THRESHOLD_GET_ERROR);
//				outMessage.set("returnMsg", "获取阈值异常");
//				return outMessage;
//			}
//			if (f != null) {
//				outMessage=f.compareByBase64Imgs(imgA, imgB, outMessage,threshold);
//			}
//		}catch(Exception e){
//			outMessage.set("returnCode", ReturnCode.GENERIC_EXTERNAL_SYSTEM_CALL_EXCEPTION);
//			outMessage.set("returnMsg", "调用人脸比较服务异常:"+e.getMessage());
//		}
//		return outMessage;
//	}
//
//	@Bizlet("根据证件类型和证件号查询用户信息")
//	public UserInfo queryUserInfo(DataObject inMessage,UserInfo userInfo){
//		logger.error("进入根据证件类型和证件号查询用户信息");
//		String certType=inMessage.getString("certType");
//		String certNo=inMessage.getString("certNo");
//		if(StringUtils.isBlank(certType)||StringUtils.isBlank(certNo)){
//			return new UserInfo();
//		}
//		userInfo.setCertType(certType);
//		userInfo.setCertNo(certNo);
//		
//		String sqlId="com.pccc.touda.bams.servicecustom.sql.FaceDetect.queryUserInfo";
//		try{
//
//			Object[] objs = DatabaseExt.queryByNamedSql("default", sqlId, userInfo);
//			if(objs!=null&&objs.length>0){
//				for (Object obj : objs) {
//					userInfo=(UserInfo)obj;
//				}
//			}else{
//				userInfo = new UserInfo();
//			}
//		}catch(Exception e){
//			logger.error("根据用户编号或者工号查询用户信息数据库操作异常，异常信息:" + e.getMessage(), e);
//			throw new BTPRuntimeException("根据用户编号或者工号查询用户信息数据库操作异常，异常信息:" + e.getMessage(), e);
//		}
//		return  userInfo;
//	}
//
//
//	@Bizlet("脸脸比对接口")
//	public DataObject facesCompare(DataObject inMessage,DataObject outMessage){
//		logger.info("进入脸脸比对接口服务");
//		if(StringUtils.isBlank(inMessage.getString("imgA"))||StringUtils.isBlank(inMessage.getString("imgB"))||StringUtils.isBlank(inMessage.getString("channel"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		if(StringUtils.isBlank(inMessage.getString("imgTypeA"))||StringUtils.isBlank(inMessage.getString("imgTypeB"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		faceService f = FaceFactory.getInstance(BamsConstant.FACE_SERVICE_PROVIDER);
//		String imgA=inMessage.getString("imgA");
//		String imgB=inMessage.getString("imgB");
//		String typeA=inMessage.getString("imgTypeA");
//		String typeB=inMessage.getString("imgTypeB");
//		String compareType = Constants.COMPARE_WATERMARK_WITH_WATERMARK;
//		//照片类型  1-高清   2-水印
//        if((!typeA.equals(typeB))){
//        	//高清水印照对比
//        	compareType = Constants.COMPARE_CLEAR_WITH_WATERMARK;
//        }else{
//        	//高清照对比
//        	compareType = Constants.COMPARE_CLEAR_WITH_CLEAR;
//        }
//		BigDecimal threshold=BAMSDaoUtils.queryThreshold(inMessage.getString("channel"), inMessage.getString("businessType"), Constants.BIOMETRIC_TYPE_FACE,compareType);
//		logger.info("获取到阈值："+threshold);
//		if(threshold==null||threshold.compareTo(new BigDecimal(0))==0){
//			outMessage.set("returnCode", ReturnCode.THRESHOLD_GET_ERROR);
//			outMessage.set("returnMsg", "获取阈值异常");
//			return outMessage;
//		}
//		if (f != null) {
//			outMessage=f.multiCompareByBase64Imgs(imgA, typeA, imgB, typeB, outMessage,threshold);
//		}
//
//		return outMessage;
//	}
//
//
//	@Bizlet("人脸人证合一校验")
//	public static DataObject faceAndCertCompare(DataObject inMessage,DataObject outMessage,UserInfo  userInfo){
//		logger.info("进入人证合一校验服务");
//		String certHeadImg=userInfo.getCertHeadImg();
//		if(StringUtils.isBlank(inMessage.getString("closeImg"))||StringUtils.isBlank(certHeadImg)||StringUtils.isBlank(inMessage.getString("channel"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		faceService f = FaceFactory.getInstance(BamsConstant.FACE_SERVICE_PROVIDER);
//		String imgA=inMessage.getString("closeImg");
//		String imgB=certHeadImg;
//		BigDecimal threshold=BAMSDaoUtils.queryThreshold(inMessage.getString("channel"), inMessage.getString("businessType"), Constants.BIOMETRIC_TYPE_FACE,Constants.COMPARE_CLEAR_WITH_WATERMARK);
//		if(threshold==null||threshold.compareTo(new BigDecimal(0))==0){
//			outMessage.set("returnCode", ReturnCode.THRESHOLD_GET_ERROR);
//			outMessage.set("returnMsg", "获取阈值异常");
//			return outMessage;
//		}
//		if (f != null) {
//			outMessage = f.multiCompareByBase64Imgs(imgA, Constants.IMAGE_TYPE_HIGH, imgB, Constants.IMAGE_TYPE_WATERMASK, outMessage, threshold);
//		}
//		BigDecimal certFaceScore=BigDecimal.ZERO;
//		try{		
//			if(StringUtils.isNotBlank((String)outMessage.get("info/similarity"))){
//				certFaceScore= new BigDecimal((String)outMessage.get("info/similarity"));				
//			}
//			userInfo.setCertFaceScore(certFaceScore);
//		}catch (Exception e) {
//			outMessage.set("returnCode", ReturnCode.QUALITY_ANALY_CHECK_ERROR);
//			outMessage.set("returnMsg", "比对不通过！图片质量有问题！");
//			return outMessage;
//		}
//		return outMessage;
//	}
//
//
//	@Bizlet("是否需要调用PCUS服务判断")
//	public  DataObject isCallPCUS(DataObject inMessage,DataObject outMessage){
//		if(StringUtils.isBlank(inMessage.getString("userName"))||StringUtils.isBlank(inMessage.getString("certType"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		if(StringUtils.isBlank(inMessage.getString("certNo"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		if(StringUtils.isBlank(inMessage.getString("channel"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		outMessage.set("returnCode", ReturnCode.TOUDA_SUCCESS);
//		outMessage.set("returnMsg", "需要调用PCUS服务!");
//		return outMessage;
//
//	}
//
//
//	@Bizlet("人脸更新注册照片")
//	public DataObject updateUserInfo(DataObject inMessage,UserInfo userInfo,DataObject outMessage){
//		logger.info("进入人脸更新注册照片");
//		if(StringUtils.isBlank(inMessage.getString("closeImg"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}    
//		if(StringUtils.isBlank(inMessage.getString("certType"))&&StringUtils.isBlank(inMessage.getString("certNo"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		BigDecimal score=userInfo.getScore();
//		try{
//			faceService f = FaceFactory.getInstance(BamsConstant.FACE_SERVICE_PROVIDER);
//			String img=inMessage.getString("closeImg");
//			BigDecimal threshold=score;//用现有数据库的图片分数作为阀值。
//			Boolean flag=false;
//
//			userInfo.setScore(score);
//			if (f != null) {	
//				TwoTuple<Boolean,BigDecimal> obj=f.qualityAnaly(img, threshold);
//				flag=obj.first;
//				score=obj.second;
//			}
//			if(flag){
//				UserInfo userInfo1=new  UserInfo();
//				userInfo1.setScore(score);
//				String imgbase64= inMessage.getString("closeImg");
//				userInfo1.setCertType(inMessage.getString("certType"));
//				userInfo1.setCertNo(inMessage.getString("certNo"));
//				userInfo1.setCloseImgLstUpdTime(new Date());
//				String directory = userInfo.getCloseImg();
//				try {
//					ImgFileUtils.convertBase64DataToImage(imgbase64, directory);
//				} catch (IOException e) {
//					logger.info("Base64位图片存储失败");
//					outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
//					outMessage.set("returnMsg", "Base64位图片存储失败");
//					return outMessage;
//				}
//				String updateSqlId = "com.pccc.touda.bams.servicecustom.sql.FaceDetect.updateUserInfo";
//				try{
//					DatabaseExt.executeNamedSql("default", updateSqlId, userInfo1);
//				}catch(Exception e){
//					outMessage.set("returnCode",ReturnCode.DATABASE_SYSTEM_ERROR );
//					outMessage.set("returnMsg","，更新人脸注册照片失败！" );
//					logger.error("更新人脸注册照片数据库操作异常，异常信息:" + e.getMessage(), e);
//					throw new BTPRuntimeException("更新人脸注册照片数据库操作异常，异常信息:" + e.getMessage(), e);
//				}
//				
//			}else{
//				outMessage.set("returnCode", ReturnCode.QUALITY_ANALY_CHECK_ERROR);
//				outMessage.set("returnMsg", "人脸质量分析,图片不符合要求");
//				return outMessage;
//			}	
//		}catch(Exception e){
//			outMessage.set("returnCode", ReturnCode.GENERIC_EXTERNAL_SYSTEM_CALL_EXCEPTION);
//			outMessage.set("returnMsg", "调用人脸质量分析服务异常:"+e.getMessage());
//			return outMessage;
//		}
//				
//		outMessage.set("returnCode",ReturnCode.TOUDA_SUCCESS );
//		outMessage.set("returnMsg","更新人脸注册照片成功" );
//		return outMessage;
//	}
//
//
//	@Bizlet("人脸服务是否开通查询服务")
//	public DataObject qryFacedService(DataObject inMessage,UserInfo userInfo,DataObject outMessage) {
//		String qrySqlId = "com.pccc.touda.bams.servicecustom.sql.FaceDetect.queryUserInfo";
//		if(StringUtils.isBlank(inMessage.getString("channel"))
//				||StringUtils.isBlank(inMessage.getString("businessType"))
//				||StringUtils.isBlank(inMessage.getString("certNo"))
//				||StringUtils.isBlank(inMessage.getString("certType"))
//		){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		userInfo.setCertType(inMessage.getString("certType"));
//		userInfo.setCertNo(inMessage.getString("certNo"));
//		try{
//			Object[] objs = DatabaseExt.queryByNamedSql("default", qrySqlId, userInfo);
//			if(objs!=null&&objs.length>0){
//				for (Object obj : objs) {
//					userInfo=(UserInfo)obj;
//				}
//				if(userInfo!=null&&Constants.STATUS_OPEN.equals(userInfo.getStatus())){
//					outMessage.set("returnCode",ReturnCode.TOUDA_SUCCESS );
//					outMessage.set("returnMsg","人脸服务已开通！" );
//				}else if(userInfo!=null&&!Constants.STATUS_OPEN.equals(userInfo.getStatus())){
//					outMessage.set("returnCode",ReturnCode.USER_STATUS_ERROR );
//					outMessage.set("returnMsg","用户人脸服务状态异常，请重新设置！" );
//				}else{
//					outMessage.set("returnCode",ReturnCode.USER_NOT_EXIT);
//					outMessage.set("returnMsg","用户不存在！");
//				}
//			}else{				
//				outMessage.set("returnCode",ReturnCode.USER_NOT_EXIT);
//				outMessage.set("returnMsg","用户不存在！");				
//			}
//		}catch(Exception e){
//			outMessage.set("returnCode",ReturnCode.DATABASE_SYSTEM_ERROR );
//			outMessage.set("returnMsg",e.getMessage());
//			logger.error("查询用户信息表数据库操作异常，异常信息:" + e.getMessage(), e);
//			throw new BTPRuntimeException("查询用户信息表数据库操作异常，异常信息:" + e.getMessage(), e);
//		}
//		return outMessage;
//	}
//
//
//	@Bizlet("人脸视屏校验")
//	public  DataObject facesAndVideoCompare(DataObject inMessage,DataObject outMessage){
//		logger.info("进入人脸视屏校验服务");
//		if(StringUtils.isBlank(inMessage.getString("video"))||StringUtils.isBlank(inMessage.getString("channel"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		String img=inMessage.getString("closeImg");
//		if(inMessage.getString("checkType").equals("1")) //视屏与本地证件照比对校验
//		{
//			if(StringUtils.isBlank(inMessage.getString("certImg"))&&StringUtils.isBlank(inMessage.getString("closeImg"))){
//				outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//				outMessage.set("returnMsg", "输入参数必输项丢失");
//				return outMessage;
//			}
//			if(StringUtils.isBlank(inMessage.getString("closeImg"))){
//				img=inMessage.getString("certImg");
//			}
//		}
//		faceService f = FaceFactory.getInstance(BamsConstant.FACE_SERVICE_PROVIDER);
//		String video=inMessage.getString("video");
//		BigDecimal threshold=BAMSDaoUtils.queryThreshold(inMessage.getString("channel"), inMessage.getString("businessType"), Constants.BIOMETRIC_TYPE_FACE,Constants.COMPARE_CLEAR_WITH_CLEAR);
//		if(threshold==null||threshold.compareTo(new BigDecimal(0))==0){
//			outMessage.set("returnCode", ReturnCode.THRESHOLD_GET_ERROR);
//			outMessage.set("returnMsg", "获取阈值异常");
//			return outMessage;
//		}
//		if (f != null) {
//			outMessage=f.videoAndImgCompare(img, video, outMessage,threshold);
//		}
//
//		return outMessage;
//	}
//	@Bizlet("证件头像照视屏校验")
//	public DataObject certAndVideoCompare(DataObject inMessage,DataObject outMessage,UserInfo  userInfo){
//		logger.info("进入人脸视屏校验服务");
//		String certHeadImg=userInfo.getCertHeadImg();
//		if(StringUtils.isBlank(inMessage.getString("video"))||StringUtils.isBlank(certHeadImg)||StringUtils.isBlank(inMessage.getString("channel"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		faceService f = FaceFactory.getInstance(BamsConstant.FACE_SERVICE_PROVIDER);
//		String img=certHeadImg;
//		String video=inMessage.getString("video");
//		BigDecimal threshold=BAMSDaoUtils.queryThreshold(inMessage.getString("channel"), inMessage.getString("businessType"), Constants.BIOMETRIC_TYPE_FACE,Constants.COMPARE_CLEAR_WITH_WATERMARK);
//		if(threshold==null||threshold.compareTo(new BigDecimal(0))==0){
//			outMessage.set("returnCode", ReturnCode.THRESHOLD_GET_ERROR);
//			outMessage.set("returnMsg", "获取阈值异常");
//			return outMessage;
//		}
//		if (f != null) {
//			outMessage=f.videoAndImgCompare(img, video, outMessage,threshold);
//		}
//
//		return outMessage;
//	}
//	@Bizlet("渠道交易流水号查询")
//	public DataObject queryChannelSeqInfo(DataObject inMessage,DataObject outMessage,DataObject pageCond){
//		logger.info("渠道交易流水号查询");
//		String qrySqlId = "com.pccc.touda.bams.servicecustom.sql.TransFlowSqlMap.qureyTransDetail";
//		String queryRegisterTable = "com.pccc.touda.bams.servicecustom.sql.TransFlowSqlMap.queryRegisterTable";
//		String queryLoginTable = "com.pccc.touda.bams.servicecustom.sql.TransFlowSqlMap.queryLoginTable";
//		String queryFaceTable = "com.pccc.touda.bams.servicecustom.sql.TransFlowSqlMap.queryFaceTable";
//		String queryCertTable = "com.pccc.touda.bams.servicecustom.sql.TransFlowSqlMap.queryCertTable";
//		String queryVideoTable = "com.pccc.touda.bams.servicecustom.sql.TransFlowSqlMap.queryVideoTable";
//		String queryMultiTable = "com.pccc.touda.bams.servicecustom.sql.TransFlowSqlMap.queryMultiTable";
//		TransDetailModelVO transDetailModelextend=new TransDetailModelVO();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		if (StringUtils.isNotBlank(inMessage.getString("startDate"))) {		
//			transDetailModelextend.setStartDate(sdf.format(inMessage.getDate("startDate")));
//		}
//		if (StringUtils.isNotBlank(inMessage.getString("endDate"))) {	
//			transDetailModelextend.setEndDate(sdf.format(inMessage.getDate("endDate")));				
//		}
//
//		if (StringUtils.isNotBlank(inMessage.getString("certNo"))) {
//			transDetailModelextend.setCertNo(inMessage.getString("certNo"));
//		}
//		if (StringUtils.isNotBlank(inMessage.getString("channel"))) {
//			transDetailModelextend.setChannel(inMessage.getString("channel"));
//		}
//		if (StringUtils.isNotBlank(inMessage.getString("certType"))) {
//			transDetailModelextend.setCertType(inMessage.getString("certType"));
//		}
//		if (StringUtils.isNotBlank(inMessage.getString("channelSeqNo"))) {
//			transDetailModelextend.setChannelSeqNo(inMessage.getString("channelSeqNo"));
//		}
//		if (StringUtils.isNotBlank(inMessage.getString("businessType"))) {
//			transDetailModelextend.setBusinessType(inMessage.getString("businessType"));
//		}
//		if (StringUtils.isNotBlank(inMessage.getString("userUniqueId"))) {
//			transDetailModelextend.setUserUniqueId(inMessage.getString("userUniqueId"));
//		}
//		
//		Object[] objs = null;
//		try {
//			if(StringUtils.isNotBlank(inMessage.getString("transCode"))){
//				transDetailModelextend.setTransCode(inMessage.getString("transCode"));
//				if(TransFlowConstant.J_BAMS_001_0001.equals(transDetailModelextend.getTransCode()) ){
//					objs = DatabaseExt.queryByNamedSqlWithPage("default", queryRegisterTable, pageCond, transDetailModelextend);
//				}
//				else if(TransFlowConstant.J_BAMS_001_0002.equals(transDetailModelextend.getTransCode())){
//					objs = DatabaseExt.queryByNamedSqlWithPage("default", queryLoginTable, pageCond, transDetailModelextend);;
//				}
//				else if(TransFlowConstant.J_BAMS_001_0003.equals(transDetailModelextend.getTransCode())){
//					objs = DatabaseExt.queryByNamedSqlWithPage("default", queryFaceTable, pageCond, transDetailModelextend);
//				}
//				else if(TransFlowConstant.J_BAMS_001_0004.equals(transDetailModelextend.getTransCode()) || TransFlowConstant.X_BAMS_001_0001.equals(transDetailModelextend.getTransCode()) ){
//					objs = DatabaseExt.queryByNamedSqlWithPage("default", queryCertTable, pageCond, transDetailModelextend);
//				}
//				else if(TransFlowConstant.J_BAMS_001_0007.equals(transDetailModelextend.getTransCode())){
//					objs = DatabaseExt.queryByNamedSqlWithPage("default", queryVideoTable, pageCond, transDetailModelextend);
//				}
//				else if(TransFlowConstant.J_BAMS_001_0009.equals(transDetailModelextend.getTransCode())){
//					objs = DatabaseExt.queryByNamedSqlWithPage("default", queryMultiTable, pageCond, transDetailModelextend);
//				}
//				else{
//					objs = DatabaseExt.queryByNamedSqlWithPage("default", qrySqlId, pageCond, transDetailModelextend);
//				}
//			}
//		}catch(Exception e){
//			outMessage.set("returnCode",ReturnCode.DATABASE_SYSTEM_ERROR );
//			outMessage.set("returnMsg",e.getMessage());
//			logger.error("查询渠道交易流水表数据库操作异常，异常信息:" + e.getMessage(), e);
//			throw new BTPRuntimeException("查询渠道交易流水表数据库操作异常，异常信息:" + e.getMessage(), e);
//		}
//		int i = 1;
//		if(objs!=null&&objs.length>0){
//			for (Object obj : objs) {
//				TransDetailModel transDetailModelextends=(TransDetailModel)obj;
//				outMessage.set("info[" + i+ "]/channelSeqNo",transDetailModelextends.getChannelSeqNo());
//				outMessage.set("info[" + i+ "]/channel",transDetailModelextends.getChannel());
//				outMessage.set("info[" + i+ "]/businessType",transDetailModelextends.getBusinessType());
//				outMessage.set("info[" + i+ "]/transDate",transDetailModelextends.getTransDate());
//				outMessage.set("info[" + i+ "]/transTime",transDetailModelextends.getTransTime());
//				outMessage.set("info[" + i+ "]/certType",transDetailModelextends.getCertType());
//				outMessage.set("info[" + i+ "]/certNo",transDetailModelextends.getCertNo());
//				outMessage.set("info[" + i+ "]/userName",transDetailModelextends.getUserName());
//				if(StringUtils.isNotBlank(transDetailModelextends.getCloseImg())){
//					outMessage.set("info[" + i+ "]/closeImg",ImgFileUtils.getImageFile(transDetailModelextends.getCloseImg()));
//				}
//				if(StringUtils.isNotBlank(transDetailModelextends.getCertImg())){
//					outMessage.set("info[" + i+ "]/certImg",ImgFileUtils.getImageFile(transDetailModelextends.getCertImg()));
//				}
//				if(StringUtils.isNotBlank(transDetailModelextends.getCertFaceImg())){
//					outMessage.set("info[" + i+ "]/certFaceImg",ImgFileUtils.getImageFile(transDetailModelextends.getCertFaceImg()));
//				}
//				if(StringUtils.isNotBlank(transDetailModelextends.getCertBackImg())){
//					outMessage.set("info[" + i+ "]/certBackImg",ImgFileUtils.getImageFile(transDetailModelextends.getCertBackImg()));
//				}
//				if(StringUtils.isNotBlank(transDetailModelextends.getCertHeadImg())){
//					outMessage.set("info[" + i+ "]/certHeadImg",ImgFileUtils.getImageFile(transDetailModelextends.getCertHeadImg()));
//				}							
//				if(StringUtils.isNotBlank(transDetailModelextends.getVideo())){
//					outMessage.set("info[" + i+ "]/video",ImgFileUtils.getImageFile(transDetailModelextends.getVideo()));
//				}
//				outMessage.set("info[" + i+ "]/transRequestMsg",transDetailModelextends.getTransRequestMsg());
//				outMessage.set("info[" + i+ "]/transResponseMsg",transDetailModelextends.getTransResponseMsg());
//				outMessage.set("info[" + i+ "]/status",transDetailModelextends.getStatus());
//				outMessage.set("info[" + i+ "]/lstUpdUser",transDetailModelextends.getLstUpdUser());
//				outMessage.set("info[" + i+ "]/userUniqueId",transDetailModelextends.getUserUniqueId());
//				i++;
//			}
//		}else{				
//			outMessage.set("returnCode",ReturnCode.QUERY_ISNULL);
//			outMessage.set("returnMsg","查询渠道交易流水表记录数为零！");
//			return outMessage;
//		}
//		outMessage.set("returnCode",ReturnCode.TOUDA_SUCCESS );
//		outMessage.set("returnMsg","渠道交易流水查询成功" );
//		return outMessage;
//
//	}   
//	
//	@Bizlet("根据用证件类型和证件号查询用户信息")
//	public static UserInfo queryUserInfoByCert(DataObject inMessage,UserInfo userInfo){
//		logger.error("根据用证件类型和证件号查询用户信息");
//		logger.error("进入根据证件类型和证件号查询用户信息");
//		String certType=inMessage.getString("certType");
//		String certNo=inMessage.getString("certNo");
//		if(StringUtils.isBlank(certType)||StringUtils.isBlank(certNo)){
//			return new UserInfo();
//		}
//		userInfo.setCertType(certType);
//		userInfo.setCertNo(certNo);
//		
//		String sqlId="com.pccc.touda.bams.servicecustom.sql.FaceDetect.queryUserInfo";
//		try{
//
//			Object[] objs = DatabaseExt.queryByNamedSql("default", sqlId, userInfo);
//			if(objs!=null&&objs.length>0){
//				for (Object obj : objs) {
//					userInfo=(UserInfo)obj;
//				}
//			}else{
//				userInfo = new UserInfo();
//			}
//		}catch(Exception e){
//			logger.error("根据用户编号或者工号查询用户信息数据库操作异常，异常信息:" + e.getMessage(), e);
//			throw new BTPRuntimeException("根据用户编号或者工号查询用户信息数据库操作异常，异常信息:" + e.getMessage(), e);
//		}
//		return  userInfo;
//	}
//	
//
//	@Bizlet("人脸本地库个人近照校验校验")
//	public static DataObject localCompare(DataObject inMessage,UserInfo userInfo,DataObject outMessage){
//		logger.info("进入人脸登录校验服务");
//		userInfo=queryUserInfoByCert(inMessage, userInfo);	
//		if(StringUtils.isBlank(inMessage.getString("closeImg"))||StringUtils.isBlank(inMessage.getString("channel"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		if(userInfo==null||StringUtils.isBlank(userInfo.getCertNo())||StringUtils.isBlank(userInfo.getCloseImg())){
//			outMessage.set("returnCode", ReturnCode.USER_NOT_EXIT);
//			outMessage.set("returnMsg", "用户不存在");
//			return outMessage;
//		}
//		try{
//			
//			faceService f = FaceFactory.getInstance(BamsConstant.FACE_SERVICE_PROVIDER);
//			String imgA=inMessage.getString("closeImg");
//			String imgBPath=userInfo.getCloseImg();
//			String imgB=ImgFileUtils.getImageFile(imgBPath);
//			if(StringUtils.isBlank(imgB)){
//				logger.info("数据库图片存储路径转换Base64位失败");
//				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
//				outMessage.set("returnMsg", "数据库图片存储路径转换Base64位失败");
//				return outMessage;
//			}
//			BigDecimal threshold=BAMSDaoUtils.queryThreshold(inMessage.getString("channel"), inMessage.getString("businessType"), Constants.BIOMETRIC_TYPE_FACE,Constants.COMPARE_CLEAR_WITH_CLEAR);
//			if(threshold==null||threshold.compareTo(new BigDecimal(0))==0){
//				outMessage.set("returnCode", ReturnCode.THRESHOLD_GET_ERROR);
//				outMessage.set("returnMsg", "获取阈值异常");
//				return outMessage;
//			}
//			if (f != null) {
//				outMessage=f.compareByBase64Imgs(imgA, imgB, outMessage,threshold);
//			}
//		}catch(Exception e){
//			outMessage.set("returnCode", ReturnCode.GENERIC_EXTERNAL_SYSTEM_CALL_EXCEPTION);
//			outMessage.set("returnMsg", "调用人脸比较服务异常:"+e.getMessage());
//		}
//		return outMessage;
//	}
//	
//	@Bizlet("人脸本地库证件照校验校验")
//	public static DataObject localCertCompare(DataObject inMessage,UserInfo userInfo,DataObject outMessage){
//		logger.info("进入人脸本地库证件照校验校验服务");
//		userInfo=queryUserInfoByCert(inMessage, userInfo);	
//		if(StringUtils.isBlank(inMessage.getString("closeImg"))||StringUtils.isBlank(inMessage.getString("channel"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		if(userInfo==null||StringUtils.isBlank(userInfo.getCertNo())){
//			outMessage.set("returnCode", ReturnCode.USER_NOT_EXIT);
//			outMessage.set("returnMsg", "用户不存在");
//			return outMessage;
//		}
//		try{
//			
//			faceService f = FaceFactory.getInstance(BamsConstant.FACE_SERVICE_PROVIDER);
//			String imgA=inMessage.getString("closeImg");
//			String imgBPath=userInfo.getCloseImg();
//			if(StringUtils.isNotBlank(userInfo.getCertImg())){
//				imgBPath=userInfo.getCertImg();
//			}else if(StringUtils.isNotBlank(userInfo.getCertFaceImg())){
//				imgBPath=userInfo.getCertFaceImg();
//			}else{
//				outMessage.set("returnCode", ReturnCode.USER_STATUS_ERROR);
//				outMessage.set("returnMsg", "用户已存在，本地库图片为空！");
//				return outMessage;
//			}
//			String imgB=ImgFileUtils.getImageFile(imgBPath);
//			if(StringUtils.isBlank(imgB)){
//				logger.info("数据库图片存储路径转换Base64位失败");
//				outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
//				outMessage.set("returnMsg", "数据库图片存储路径转换Base64位失败");
//				return outMessage;
//			}
//			BigDecimal threshold=BAMSDaoUtils.queryThreshold(inMessage.getString("channel"), inMessage.getString("businessType"), "face",Constants.COMPARE_CLEAR_WITH_WATERMARK);
//			if(threshold==null||threshold.compareTo(new BigDecimal(0))==0){
//				outMessage.set("returnCode", ReturnCode.THRESHOLD_GET_ERROR);
//				outMessage.set("returnMsg", "获取阈值异常");
//				return outMessage;
//			}
//			if (f != null) {
//				outMessage= f.multiCompareByBase64Imgs(imgA, Constants.IMAGE_TYPE_HIGH, imgB, Constants.IMAGE_TYPE_WATERMASK, outMessage, threshold);
//			}
//		}catch(Exception e){
//			outMessage.set("returnCode", ReturnCode.GENERIC_EXTERNAL_SYSTEM_CALL_EXCEPTION);
//			outMessage.set("returnMsg", "调用人脸比较服务异常:"+e.getMessage());
//		}
//		return outMessage;
//	}
//	
//	
//
//	@Bizlet("人脸服务开启关闭")
//	public DataObject updateUserInfoStatus(DataObject inMessage,UserInfo userInfo,DataObject outMessage){
//		logger.info("进入人脸更新注册照片");
//		if(StringUtils.isBlank(inMessage.getString("status"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}  
//		if(StringUtils.isBlank(inMessage.getString("channel"))||StringUtils.isBlank(inMessage.getString("businessType"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		if(StringUtils.isBlank(inMessage.getString("certType"))&&StringUtils.isBlank(inMessage.getString("certNo"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		userInfo.setCertType(inMessage.getString("certType"));
//		userInfo.setCertNo(inMessage.getString("certNo"));
//		String sqlId="com.pccc.touda.bams.servicecustom.sql.FaceDetect.queryUserInfo";
//		try{
//
//			Object[] objs = DatabaseExt.queryByNamedSql("default", sqlId, userInfo);
//			if(objs!=null&&objs.length>0){
//				for (Object obj : objs) {
//					userInfo=(UserInfo)obj;
//				}
//			}else{
//				userInfo = null;
//				outMessage.set("returnCode", ReturnCode.USER_NOT_EXIT);
//				outMessage.set("returnMsg", "用户不存在");
//				return outMessage;
//			}
//		}catch(Exception e){
//			logger.error("根据用户编号或者工号查询用户信息数据库操作异常，异常信息:" + e.getMessage(), e);
//			throw new BTPRuntimeException("根据用户编号或者工号查询用户信息数据库操作异常，异常信息:" + e.getMessage(), e);
//		}
//		
//		userInfo.setCertType(inMessage.getString("certType"));
//		userInfo.setCertNo(inMessage.getString("certNo"));
//		userInfo.setStatus(inMessage.getString("status"));
//		String updateSqlId = "com.pccc.touda.bams.servicecustom.sql.FaceDetect.updateUserInfoStatus";
//		try{
//			DatabaseExt.executeNamedSql("default", updateSqlId, userInfo);
//		}catch(Exception e){
//			outMessage.set("returnCode",ReturnCode.DATABASE_SYSTEM_ERROR );
//			outMessage.set("returnMsg","，更新人脸服务状态失败！" );
//			logger.error("更新人脸服务状态数据库操作异常，异常信息:" + e.getMessage(), e);
//			throw new BTPRuntimeException("更新人脸服务状态数据库操作异常，异常信息:" + e.getMessage(), e);
//		}
//		outMessage.set("returnCode",ReturnCode.TOUDA_SUCCESS );
//		outMessage.set("returnMsg","更新人脸服务状态成功" );
//		return outMessage;
//	}
//	
//	@Bizlet("比对现场照与数据库库中所存照片")
//	public static DataObject compareCloseImgWithLocalImg(DataObject inMessage,UserInfo userInfo,DataObject outMessage){
//		logger.error("比对现场照与数据库库中所存照片");
//		if(StringUtils.isBlank(inMessage.getString("certNo"))
//			||StringUtils.isBlank(inMessage.getString("closeImg"))
//			||StringUtils.isBlank(inMessage.getString("channel"))
//			||StringUtils.isBlank(inMessage.getString("businessType"))
//		){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return null;
//		}
//			
//		String imgPath = userInfo.getCloseImg();
//		if(StringUtils.isBlank(imgPath)){
//			userInfo = null;
//			outMessage.set("returnCode", ReturnCode.FILE_NOT_FOUND_EXCEPTION);
//			outMessage.set("returnMsg", "用户的近照为空");
//			return outMessage;
//		}
//		
//		//获取所需比较的两张图片
//		String imgA = inMessage.getString("closeImg");
//		String imgB = ImgFileUtils.getImageFile(imgPath);
//		if(StringUtils.isBlank(imgB)){
//			userInfo = null;
//			outMessage.set("returnCode", ReturnCode.FILE_NOT_FOUND_EXCEPTION);
//			outMessage.set("returnMsg", "用户的近照为空");
//			return outMessage;
//		}
//		
//		//查询阈值
//		BigDecimal threshold = BAMSDaoUtils.queryThreshold(inMessage.getString("channel"), inMessage.getString("businessType"), Constants.BIOMETRIC_TYPE_FACE, Constants.COMPARE_CLEAR_WITH_CLEAR);
//		if(threshold==null||threshold.compareTo(new BigDecimal(0))==0){
//			outMessage.set("returnCode", ReturnCode.THRESHOLD_GET_ERROR);
//			outMessage.set("returnMsg", "获取阈值异常");
//			return outMessage;
//		}
//		
//		//调用底层算法比较两张人脸
//		faceService f = FaceFactory.getInstance(BamsConstant.FACE_SERVICE_PROVIDER);
//		if(null != f){
//			outMessage = f.compareByBase64Imgs(imgA, imgB, outMessage, threshold);
//			
//		}
//		return outMessage;
//	}
//
//	
//	@Bizlet("是否需要更新个人近照判断")
//	public  Boolean isNeedUpdate(UserInfo a,UserInfo b ){
//		Boolean flag =false;
//		if(a.getScore().compareTo(b.getScore())>0){
//			flag=true;
//		}		
//		
//		return flag;
//
//	}
//	
//	
//	@Bizlet("用户视频存储")
//	public  DataObject storeUserVideo(DataObject inMessage,DataObject outMessage){
//		logger.error("进入用户视频存储");
//		String para[]={"userType","userName","certType","certNo","video","channel","businessType","userUniqueId"};
//		for (int i = 0; i < para.length; i++) {
//			if(StringUtils.isBlank(inMessage.getString(para[i]))){
//				outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//				outMessage.set("returnMsg", "输入参数必输项丢失");
//				return outMessage;
//			}
//		}
//		
//		String channel = inMessage.getString("channel");
//		String video = inMessage.getString("video");
//		
//		//设置视频保存路径
//		String directory = ImgFileUtils.crtVideoPathByUUID(BAMSUtils.getUUID(), channel);
//		logger.info("个人视频存储路径："+directory);
//		try {
//			ImgFileUtils.convertBase64DataToImage(video, directory);
//		} catch (IOException e) {
//			logger.info("Base64位视频存储失败");
//			outMessage.set("returnCode", ReturnCode.FILE_SYSTEM_EXCEPTION);
//			outMessage.set("returnMsg", "Base64位视频存储失败");
//			return outMessage;
//		}	
//		
//		//设置User信息
//		UserInfo userInfo=new UserInfo();
//		userInfo.setUserUniqueId(inMessage.getString("userUniqueId"));
//		userInfo.setUserName(inMessage.getString("userName"));
//		userInfo.setCertType(inMessage.getString("certType"));
//		userInfo.setCertNo(inMessage.getString("certNo"));
//		userInfo.setVideo(directory);
//		userInfo.setCrtChannel(channel);
//		
//		//保存用户视频
//		String insertSqlId = "com.pccc.touda.bams.servicecustom.sql.FaceDetect.insertUserVideo";
//		try{
//			DatabaseExt.executeNamedSql("default", insertSqlId, userInfo);
//		}catch(Exception e){
//			outMessage.set("returnCode",ReturnCode.DATABASE_SYSTEM_ERROR );
//			outMessage.set("returnMsg","，保存用户视频失败！" );
//			logger.error("保存用户视频数据库操作异常，异常信息:" + e.getMessage(), e);
//			throw new BTPRuntimeException("保存用户视频数据库操作异常，异常信息:" + e.getMessage(), e);
//		}
//		outMessage.set("returnCode",ReturnCode.TOUDA_SUCCESS );
//		outMessage.set("returnMsg","保存用户视频成功" );
//		return outMessage;
//	}
//	
//	@Bizlet("身份证OCR接口")
//	public DataObject certCardOCR(DataObject inMessage,DataObject outMessage){
//		logger.info("进入身份证OCR接口服务");
//		if(StringUtils.isBlank(inMessage.getString("channel"))||StringUtils.isBlank(inMessage.getString("businessType"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		if(StringUtils.isBlank(inMessage.getString("certImg"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数证件照必输");
//			return outMessage;
//		}
//		faceService f = FaceFactory.getInstance(BamsConstant.FACE_SERVICE_PROVIDER);
//		String img=inMessage.getString("certImg");
//		String getFace=inMessage.getString("getFaceFlag");
//		if (f != null) {
//			outMessage=f.certCardOCRService( img, getFace, outMessage);
//		}
//
//		return outMessage;
//	}
//	
//	@Bizlet("防骇客活体检测接口")
//	public DataObject faceLiveness(DataObject inMessage,DataObject outMessage){
//		logger.info("进入防骇客活体检测接口服务");
//		if(StringUtils.isBlank(inMessage.getString("channel"))||StringUtils.isBlank(inMessage.getString("businessType"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "输入参数必输项丢失");
//			return outMessage;
//		}
//		if(StringUtils.isBlank(inMessage.getString("param"))){
//			outMessage.set("returnCode", ReturnCode.REQUIRED_FIELD_ISEMPTY_ERROR);
//			outMessage.set("returnMsg", "SDK参数信息必输");
//			return outMessage;
//		}
//		faceService f = FaceFactory.getInstance(BamsConstant.FACE_SERVICE_PROVIDER);
//		String param=inMessage.getString("param");
//		if (f != null) {
//			outMessage=f.faceLivenessService( param, outMessage);
//		}
//
//		return outMessage;
//	}
//
//}
//
