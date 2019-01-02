package com.leothon.cogito.Utils;

import com.leothon.cogito.Bean.TokenValid;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACVerifier;

import net.minidev.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class tokenUtils {

    private static final byte[] secret = "autogynephiliaperfectismleothonw".getBytes();

    //解析一个token

    public static Map<String,Object> valid(String token) throws ParseException, JOSEException {

//        解析token

        JWSObject jwsObject = JWSObject.parse(token);

        //获取到载荷
        Payload payload=jwsObject.getPayload();

        //建立一个解锁密匙
        JWSVerifier jwsVerifier = new MACVerifier(secret);

        Map<String, Object> resultMap = new HashMap<>();
        //判断token
        if (jwsObject.verify(jwsVerifier)) {
            resultMap.put("Result", 0);
            //载荷的数据解析成json对象。
            JSONObject jsonObject = payload.toJSONObject();
            resultMap.put("data", jsonObject);

            //判断token是否过期
            if (jsonObject.containsKey("exp")) {
                Long expTime = (Long)jsonObject.get("exp");
                Long nowTime = new Date().getTime();
                //判断是否过期
                if (nowTime > expTime) {
                    //已经过期
                    resultMap.clear();
                    resultMap.put("Result", 2);

                }
            }
        }else {
            resultMap.put("Result", 1);
        }
        return resultMap;

    }

    //处理解析的业务逻辑
    public static TokenValid ValidToken(String token) {
        //解析token

        TokenValid tokenValid = new TokenValid();
        try {
            if (token != null) {

                Map<String, Object> validMap = valid(token);
                int i = (int) validMap.get("Result");
                if (i == 0) {
                    tokenValid.setExpired(false);
                    JSONObject jsonObject = (JSONObject) validMap.get("data");
                    tokenValid.setUid(jsonObject.get("uid").toString());

                    tokenValid.setSta(jsonObject.get("sta").toString());

                    tokenValid.setExp(jsonObject.get("exp").toString());

                } else if (i == 2) {
                    tokenValid.setExpired(true);
                }

                return tokenValid;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return null;
    }
}
