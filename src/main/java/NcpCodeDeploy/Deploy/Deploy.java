package NcpCodeDeploy.Deploy;

import NcpCodeDeploy.Config.Config;
import com.ncloud.api.connection.NcloudApiRequest;

import java.util.concurrent.Executor;

public interface Deploy {
    void deploy(NcloudApiRequest ncloudApiRequest, Config config, Executor executor);
}
