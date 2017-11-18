package NcpCodeDeploy.Deploy;

import NcpCodeDeploy.Config.Config;
import com.ncloud.api.connection.NcloudApiRequest;

public interface Deploy {
    void deploy(NcloudApiRequest ncloudApiRequest, Config config);
}
