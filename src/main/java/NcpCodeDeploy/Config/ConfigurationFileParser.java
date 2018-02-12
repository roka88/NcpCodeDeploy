package NcpCodeDeploy.Config;

import NcpCodeDeploy.NcloudApi.NcloudApiRequestBuilder;
import com.ncloud.api.connection.NcloudApiRequest;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;

public class ConfigurationFileParser {


    private HashMap<String, String> configMap;

    public HashMap read(String confFilePath) throws IOException {

        configMap = new HashMap<>();


        File file = new File(confFilePath + "/ncp_deploy.conf");
        if (file.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            LoggerFactory.getLogger(this.getClass()).info("-------------- Configuration -------------");
            while ((line = bufferedReader.readLine()) != null) {
                boolean isComment = line.trim().startsWith("#");

                if (!isComment) {
                    boolean isConfig = line.matches("^.+=.+$");
                    if (isConfig) {
                        String[] configTokens = line.split("=");
                        String configName = configTokens[0].trim();
                        String configValue = configTokens[1].trim();
                        configMap.put(configName, configValue);
                        LoggerFactory.getLogger(this.getClass()).info(configName + " is " + configValue);
                    }
                }
            }
            bufferedReader.close();
            LoggerFactory.getLogger(this.getClass()).info("-------------------------------------------");
        } else {
            throw new IOException("ncp_deploy.conf is not exist");
        }


        return configMap;
    }


    public Config getConfig() {

        ConfigBuilder configBuilder = new ConfigBuilder();

        configBuilder
                .baseServerImgName(configMap.get("server_img_name"))
                .baseInstanceNo(configMap.get("base_instance_no"))

                .serverProductCode(configMap.get("server_product_code"))
                .acgNo(configMap.get("acg_no"))
                .loginKeyName(configMap.get("login_key"))

                .sshUserName(configMap.get("user_name"))
                .sshHost(configMap.get("host"))
                .sshPort(configMap.get("port"))
                .sshPasswd(configMap.get("password"))
                .sshFilePath(configMap.get("file_path"))
                .sshUploadPath(configMap.get("upload_path"))

                .previousAutoScalingGroupName(configMap.get("previous_autoscaling_name"))
                .desiredCapacityOfAutoScaling(configMap.get("desired_capacity"))
                .minSizeOfAutoScaling(configMap.get("min_size"))
                .maxSizeOfAutoScaling(configMap.get("max_size"))
                .defalutCoolDownOfAutoScaling(configMap.get("cool_down"))
                .healthCheckGracePeriodOfAutoScaling(configMap.get("health_check_grace_period"))
                .regionNo(configMap.get("region_no"))
                .loadBalancerName(configMap.get("load_balancer_name"))

                .useBuild(configMap.get("build"))
                .buildType(configMap.get("build_type"))
                .buildPath(configMap.get("build_path"))

                .useGit(configMap.get("git"))
                .gitRepositoryPath(configMap.get("repository_path"))
                .gitDownLoadPath(configMap.get("download_dir_path"))
                .gitUserName(configMap.get("user_name"))
                .gitUserPassword(configMap.get("user_password"));


        return configBuilder.build();
    }

    public NcloudApiRequest getNcloudApiReqeust() {

        NcloudApiRequestBuilder ncloudApiRequestBuilder = new NcloudApiRequestBuilder();

        ncloudApiRequestBuilder
                .requestURL(configMap.get("request_url"))
                .consumerKey(configMap.get("consumer_key"))
                .consumerSecret(configMap.get("consumer_secretkey"))
                .debug(configMap.get("debug"));


        return ncloudApiRequestBuilder.build();
    }


}
