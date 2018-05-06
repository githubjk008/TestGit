package com.qingyu.demo;

import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.conn.ConnectTimeoutException;

import com.alibaba.fastjson.JSONObject;
import com.qingyu.utils.DateUtils;
import com.qingyu.utils.ChannelHttpClient;
import com.qingyu.utils.SecurityUtil;

public class Merchant {
	
public static void main(String[] args) {
	
	  JSONObject dto = new JSONObject();
	  dto.put("phone", "13512104679");
	  dto.put("accountNo", "6228480038605703578");
	  dto.put("accountName", "孙文志");
	  dto.put("certNo", "340822198810301419");
	  dto.put("bankCode", "103290005138");
	  dto.put("bankName", "中国农业银行股份有限公司上海大场支行");
	  dto.put("rugrLruid", "DLS0800001");
	  dto.put("merchantNo", "12357345334");
	  dto.put("jfRateValue", "0.006");
	  dto.put("kjRateValue", "0.006");
	  dto.put("dhRateValue", "0.006");
	  dto.put("t0Fee", "2.0");
	  String signChk = SecurityUtil.encryptMd5(dto.toJSONString() + "5F32DBCCDEEA60E3C19F5C256C2C11E8");
	  Map<String, String> da = new LinkedHashMap<String, String>();
	  da.put("params", dto.toJSONString());
	  da.put("timestamp", DateUtils.getYYYYmmddHHmmss(new Date()));
	  da.put("sign", signChk);
	  try {
		String result = ChannelHttpClient.postParameters("http://112.124.6.222:9090/channel/v1/merchant/register", da);
		System.out.println("------------"+result);
	} catch (ConnectTimeoutException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SocketTimeoutException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
}
}


{"success":true,"responseCode":"00000","responseDesc


":"成功",

"result":{"businessNo":"ME100001035",
"businessKey":"b5a94e5e904402535b601ba2648b0c34",
"businessPhoneNo":"13217648467",
"corporationCardNo":"43012119900526852"
,"accountName":"袁维","bankNo":"6217232706000966610"}}