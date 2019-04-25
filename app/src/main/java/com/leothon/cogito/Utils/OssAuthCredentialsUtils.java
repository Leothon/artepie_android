package com.leothon.cogito.Utils;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.utils.IOUtils;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OssAuthCredentialsUtils extends OSSFederationCredentialProvider {

    private String mAuthServerUrl;
    private OSSAuthCredentialsProvider.AuthDecoder mDecoder;

    public OssAuthCredentialsUtils(String authServerUrl) {
        this.mAuthServerUrl = authServerUrl;
    }

    /**
     * set auth server url
     *
     * @param authServerUrl
     */
    public void setAuthServerUrl(String authServerUrl) {
        this.mAuthServerUrl = authServerUrl;
    }

    /**
     * set response data decoder
     *
     * @param decoder
     */
    public void setDecoder(OSSAuthCredentialsProvider.AuthDecoder decoder) {
        this.mDecoder = decoder;
    }

    @Override
    public OSSFederationToken getFederationToken() throws ClientException {
        OSSFederationToken authToken;
        String authData;
        try {
            URL stsUrl = new URL(mAuthServerUrl);
            HttpURLConnection conn = (HttpURLConnection) stsUrl.openConnection();
            conn.setConnectTimeout(10000);
            InputStream input = conn.getInputStream();
            authData = IOUtils.readStreamAsString(input, OSSConstants.DEFAULT_CHARSET_NAME);
            if (mDecoder != null) {
                authData = mDecoder.decode(authData);
            }
            JSONObject jsonObj = new JSONObject(authData);
            int statusCode = jsonObj.getInt("status");
            if (statusCode == 200) {
                String ak = jsonObj.getString("AccessKeyId");
                String sk = jsonObj.getString("AccessKeySecret");
                String token = jsonObj.getString("SecurityToken");
                String expiration = jsonObj.getString("Expiration");
                authToken = new OSSFederationToken(ak, sk, token, expiration);
            } else {
                String errorCode = jsonObj.getString("ErrorCode");
                String errorMessage = jsonObj.getString("ErrorMessage");
                throw new ClientException("ErrorCode: " + errorCode + "| ErrorMessage: " + errorMessage);
            }
            return authToken;
        } catch (Exception e) {
            throw new ClientException(e);
        }
    }

    public interface AuthDecoder {
        String decode(String data);
    }
}
