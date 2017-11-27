package NcpCodeDeploy.Deploy;

import NcpCodeDeploy.Config.Config;
import com.ncloud.api.connection.NcloudApiRequest;
import com.roka.NcpPipe.NcpExtendManager.AutoScalingGroupExtendManager;
import com.roka.NcpPipe.NcpExtendManager.InstanceImgExtendManager;
import com.roka.NcpPipe.NcpExtendManager.LaunchConfigurationExtendManager;
import com.roka.NcpPipe.NcpExtendManager.ServerExtendManager;
import com.roka.NcpPipe.NcpManager.AutoScalingGroupManager;
import com.roka.NcpPipe.NcpManager.ServerImageManager;
import com.roka.NcpPipe.NcpManager.ServerManager;
import com.roka.NcpPipe.OtherManager.BuildManager;
import com.roka.NcpPipe.OtherManager.GitCloneManager;
import com.roka.NcpPipe.OtherManager.LogManager;
import com.roka.NcpPipe.OtherManager.UploadFileManager;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

public class BlueGreenDeploy implements Deploy {



    @Override
    public void deploy(NcloudApiRequest ncloudApiRequest, Config config, Executor executor) {
        LogManager logManager = new LogManager();
        GitCloneManager gitCloneManager = new GitCloneManager();
        UploadFileManager uploadFileManager = new UploadFileManager();
        ServerExtendManager instanceManage = new ServerExtendManager(ncloudApiRequest);
        InstanceImgExtendManager instanceImgManage = new InstanceImgExtendManager(ncloudApiRequest);
        LaunchConfigurationExtendManager launchConfigurationManager = new LaunchConfigurationExtendManager(ncloudApiRequest);
        AutoScalingGroupExtendManager autoScalingGroupExtendManager = new AutoScalingGroupExtendManager(ncloudApiRequest);


        CompletableFuture<Function<Object, Object>> completableFuture = CompletableFuture.supplyAsync(()->logManager.log("start"), executor);

        if (config.isUseGit()) {
            completableFuture.thenApplyAsync(gitCloneManager.clone(config.getGitRepositoryPath(), config.getGitDownLoadPath(), config.getGitUserName(), config.getGitUserPassword()), executor).join();
        }

        if (config.isUseBuild()) {
            BuildManager buildManager = new BuildManager();
            if (config.getBuildType().equals("gradle")) {
                completableFuture.thenApplyAsync(buildManager.gradleBuild(config.getBuildPath()), executor).join();
            } else if (config.getBuildType().equals("maven")) {

            }
        }
         completableFuture
                .thenApplyAsync(instanceManage.startServerInstancesForJob(Collections.singletonList(config.getBaseInstanceNo())), executor)

                .thenApplyAsync(instanceManage.waitChangedServerStatusForJob(Collections.singletonList(config.getBaseInstanceNo()), ServerManager.HealthCheckType.NSTOP, ServerManager.HealthCheckType.RUN, 10000), executor)

                .thenApplyAsync(uploadFileManager.uploadFile(config.getSshUserName(), config.getSshHost(), config.getSshPort(), config.getSshPasswd(), config.getSshFilePath(), config.getSshUploadPath()), executor).join();


        if (config.isUseGit()) {
            completableFuture.thenApplyAsync(gitCloneManager.removeDir(config.getGitDownLoadPath())).join();
        }

        completableFuture
                .thenApplyAsync(instanceManage.stopServerInstancesForJob(Collections.singletonList(config.getBaseInstanceNo())), executor)

                .thenApplyAsync(instanceManage.waitChangedServerStatusForJob(Collections.singletonList(config.getBaseInstanceNo()), ServerManager.HealthCheckType.RUN, ServerManager.HealthCheckType.NSTOP, 10000), executor)

                .thenApplyAsync(instanceImgManage.createMemberServerImageForJob(config.getBaseServerImgName(), null, config.getBaseInstanceNo()), executor)

                .thenApplyAsync(instanceImgManage.waitChangedServerImgStatusOfStaticImgNoForJob(ServerImageManager.HealthCheckType.INIT, ServerImageManager.HealthCheckType.CREAT, 30000), executor)

                .thenApplyAsync(launchConfigurationManager.createLaunchConfigurationStaticWithImgNoForJob(config.getBaseServerImgName(), null, config.getServerProductCode(), Collections.singletonList(config.getAcgNo()), config.getLoginKeyName(), null), executor)

                .thenApplyAsync(autoScalingGroupExtendManager.createAutoScalingGroupForJob(config.getBaseServerImgName(), config.getBaseServerImgName(), config.getDesiredCapacityOfAutoScaling(),
                config.getMinSizeOfAutoScaling(), config.getMaxSizeOfAutoScaling(), config.getDefalutCoolDownOfAutoScaling(), AutoScalingGroupManager.AutoScalingHealthCheckType.LOADB, config.getHealthCheckGracePeriodOfAutoScaling(),
                Collections.singletonList(config.getRegionNo()), Collections.singletonList(config.getLoadBalancerName())), executor)

                .thenApplyAsync(autoScalingGroupExtendManager.checkServerStatusOfAutoScalingInServiceForJob(config.getBaseServerImgName(), 10000, 10000), executor).join();


        if (null != config.getPreviousAutoScalingGroupName()) {
            completableFuture
                    .thenApplyAsync(autoScalingGroupExtendManager.setDesiredCapacityForJob(config.getPreviousAutoScalingGroupName(), 0), executor)
                    .thenApplyAsync(autoScalingGroupExtendManager.checkServerStatusOfAutoScalingInTerminatedForJob(config.getPreviousAutoScalingGroupName(), 10000, 30000), executor).join();

        }

    }

}
