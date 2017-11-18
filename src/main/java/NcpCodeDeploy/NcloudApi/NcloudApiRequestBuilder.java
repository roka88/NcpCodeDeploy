package NcpCodeDeploy.NcloudApi;

import com.ncloud.api.connection.NcloudApiRequest;

public class NcloudApiRequestBuilder {


    private String requestURL;
    private String consumerKey;
    private String consumerSecret;
    private String responseFormatType;
    private boolean isDebug = true;


    public NcloudApiRequestBuilder requestURL(String requestURL) {
        this.requestURL = requestURL;
        return this;
    }

    public NcloudApiRequestBuilder consumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
        return this;
    }

    public NcloudApiRequestBuilder consumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
        return this;
    }

    public NcloudApiRequestBuilder responseFormatType(String responseFormatType) {
        this.responseFormatType = responseFormatType;
        return this;
    }

    public NcloudApiRequestBuilder debug(String isDebug) {
        if (null != isDebug) {
            this.isDebug = Boolean.parseBoolean(isDebug);
        }
        return this;
    }

    public NcloudApiRequest build() {
        NcloudApiRequest ncloudApiRequest = new NcloudApiRequest();
        if (null != consumerKey) {
            ncloudApiRequest.setConsumerKey(consumerKey);
        } else {

        }
        if (null != consumerSecret) {
            ncloudApiRequest.setConsumerSecret(consumerSecret);
        } else {

        }

        if (null != requestURL) {
            ncloudApiRequest.setRequestURL(requestURL);
        } else {
            ncloudApiRequest.setRequestURL("https://api.ncloud.com");
        }

        ncloudApiRequest.setDebug(isDebug);

        ncloudApiRequest.setResponseFormatType(responseFormatType);

        return ncloudApiRequest;
    }
}
