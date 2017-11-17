package NcpCodeDeploy;


import com.ncloud.api.connection.NcloudApiRequest;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {



    public static void main(String[] args) {

        if (args.length == 0) {
            LoggerFactory.getLogger("main").error("Please Set Parameter ncp_deploy.conf Path, ex) /home/Users");
            return;
        }

        Deploy blueGreanDeploy = new BlueGreanDeploy();

        try {
            ConfigurationFileParser configurationFileParser = new ConfigurationFileParser();
            configurationFileParser.read(args[0]);

            NcloudApiRequest ncloudApiRequest = configurationFileParser.getNcloudApiReqeust();
            Config config = configurationFileParser.getConfig();


            blueGreanDeploy.deploy(ncloudApiRequest, config);

        } catch (IOException e) {
            LoggerFactory.getLogger("main").info("ncp_deploy.conf is not found");
        }

    }



}
