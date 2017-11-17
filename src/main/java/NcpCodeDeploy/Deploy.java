package NcpCodeDeploy;

import com.ncloud.api.connection.NcloudApiRequest;

public interface Deploy {
    void deploy(NcloudApiRequest ncloudApiRequest, Config config);
}
