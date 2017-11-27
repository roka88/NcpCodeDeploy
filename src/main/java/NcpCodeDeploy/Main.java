package NcpCodeDeploy;


import NcpCodeDeploy.Config.Config;
import NcpCodeDeploy.Config.ConfigurationFileParser;
import NcpCodeDeploy.Deploy.BlueGreenDeploy;
import NcpCodeDeploy.Deploy.Deploy;
import com.ncloud.api.connection.NcloudApiRequest;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main {



    public static void main(String[] args) {

        if (args.length == 0) {
            LoggerFactory.getLogger("main").error("Please Set Parameter ncp_deploy.conf Path, ex) /home/Users");
            return;
        }

        Deploy blueGreanDeploy = new BlueGreenDeploy();

        try {
            ConfigurationFileParser configurationFileParser = new ConfigurationFileParser();
            configurationFileParser.read(args[0]);

            NcloudApiRequest ncloudApiRequest = configurationFileParser.getNcloudApiReqeust();
            Config config = configurationFileParser.getConfig();

            Executor executors = Executors.newFixedThreadPool(2, new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    return thread;
                }
            });

            blueGreanDeploy.deploy(ncloudApiRequest, config, executors);

        } catch (IOException e) {
            LoggerFactory.getLogger("main").info("ncp_deploy.conf is not found");
        }

    }



}
