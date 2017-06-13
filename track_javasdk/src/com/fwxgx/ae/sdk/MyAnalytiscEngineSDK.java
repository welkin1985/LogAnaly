package com.fwxgx.ae.sdk;

import com.fwxgx.ae.commen.SDKConstants;
import com.fwxgx.ae.utils.URLOutbox;

import java.util.logging.Logger;

/**
 *
 */
public class MyAnalytiscEngineSDK {
    public static final Logger log = Logger.getGlobal();
    public static final String accessURL = SDKConstants.ACCESS_URL;
    public static final String platformName = SDKConstants.PLATFORM_NAME;
    public static final String sdkName = SDKConstants.SDK_NAME;
    public static final String version = SDKConstants.VERSION;
    public static final String success = SDKConstants.CHARGE_SUCCESS;

    public static boolean onChargeSuccess(String memberID, String ordID) {

        //校验数据有效性
        if (memberID == null || "".equals(memberID.trim()) || ordID == null || "".equals(ordID)) {
            log.warning("会员ID或订单编码丢失");
            return false;
        } else {
            String strURL = accessURL +
                    "?u_mid=" + memberID.trim() +
                    "&oid=" + ordID.trim() +
                    "&c_time=" + String.valueOf(System.currentTimeMillis()) +
                    "&ver=" + version +
                    "&en=" + success +
                    "&pl=" + platformName +
                    "sdk=" + sdkName;
            URLOutbox.addUrl(strURL);
            return true;
        }
    }
}
