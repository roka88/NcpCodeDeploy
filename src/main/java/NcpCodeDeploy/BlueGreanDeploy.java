package NcpCodeDeploy;

import com.ncloud.api.connection.NcloudApiRequest;
import com.roka.NcpDeploy.NcpExtendManager.AutoScalingGroupExtendManager;
import com.roka.NcpDeploy.NcpExtendManager.InstanceImgExtendManager;
import com.roka.NcpDeploy.NcpExtendManager.LaunchConfigurationExtendManager;
import com.roka.NcpDeploy.NcpExtendManager.ServerExtendManager;
import com.roka.NcpDeploy.NcpManager.AutoScalingGroupManager;
import com.roka.NcpDeploy.NcpManager.ServerImageManager;
import com.roka.NcpDeploy.NcpManager.ServerManager;
import com.roka.NcpDeploy.Observer.LogSubscriber;
import com.roka.NcpDeploy.Observer.Subscriber;
import com.roka.NcpDeploy.OtherManager.BuildManager;
import com.roka.NcpDeploy.OtherManager.GitCloneManager;
import com.roka.NcpDeploy.OtherManager.LogManager;
import com.roka.NcpDeploy.OtherManager.UploadFileManager;
import com.roka.NcpDeploy.PipeLine.OperationPipeLine;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.concurrent.Executors;

public class BlueGreanDeploy implements Deploy {
    @Override
    public void deploy(NcloudApiRequest ncloudApiRequest, Config config) {

        LogManager logManager = new LogManager();

        OperationPipeLine operationPipeLine = new OperationPipeLine()
                .job(logManager.log("JobSchedule Start"));

        addObserverPipeLine(operationPipeLine);

        if (config.isUseGit()) {
            addGitPipeLine(operationPipeLine, config);
        }

        if (config.isUseBuild()) {
            addBuildPipeLine(operationPipeLine, config);
        }

        addUploadPipeLine(operationPipeLine, config, ncloudApiRequest);

        if (config.isUseGit()) {
            addGitCloneRemoveDir(operationPipeLine, config);
        }

        addServerImgClonePipeLine(operationPipeLine, config, ncloudApiRequest);
        addLaunchConfigrationPipeLine(operationPipeLine, config, ncloudApiRequest);
        addAutoScalingGroupPipeLine(operationPipeLine, config, ncloudApiRequest);

        if (null != config.getPreviousAutoScalingGroupName()) {
            addPreviousAutoScalingGroupDisusePipeLine(operationPipeLine, config, ncloudApiRequest);
        }

        operationPipeLine.job(logManager.log("JobSchedule Complete"))
                .operation();
    }



    private void addObserverPipeLine(OperationPipeLine operationPipeLine) {
        Subscriber logSubscriber = new LogSubscriber();
//        logSubscriber.add((result) -> LoggerFactory.getLogger(this.getClass()).info("Job Complete"));

        operationPipeLine
                .subscribe(logSubscriber)
                .observerWorker(Executors.newSingleThreadExecutor());

    }

    private void addGitPipeLine(OperationPipeLine operationPipeLine, Config config) {
        GitCloneManager gitCloneManager = new GitCloneManager();
        operationPipeLine.job(gitCloneManager.clone(config.getGitRepositoryPath(), config.getGitDownLoadPath(), config.getGitUserName(), config.getGitUserPassword()));
    }

    private void addBuildPipeLine(OperationPipeLine operationPipeLine, Config config) {
        BuildManager buildManager = new BuildManager();
        if (config.getBuildType().equals("gradle")) {
            operationPipeLine.job(buildManager.gradleBuild(config.getBuildPath()));
        } else if (config.getBuildType().equals("maven")) {

        }
    }

    private void addGitCloneRemoveDir(OperationPipeLine operationPipeLine, Config config) {
        GitCloneManager gitCloneManager = new GitCloneManager();
        operationPipeLine.job(gitCloneManager.removeDir(config.getGitDownLoadPath()));
    }

    private void addUploadPipeLine(OperationPipeLine operationPipeLine, Config config, NcloudApiRequest ncloudApiRequest) {
        ServerExtendManager instanceManage = new ServerExtendManager(ncloudApiRequest);
        LogManager logManager = new LogManager();
        UploadFileManager uploadFileManager = new UploadFileManager();
        operationPipeLine
                .job(instanceManage.startServerInstancesForJob(Collections.singletonList(config.getBaseInstanceNo())))
                .job(logManager.log("startServerInstancesForJob Complete"))

                .job(instanceManage.waitChangedServerStatus(Collections.singletonList(config.getBaseInstanceNo()), ServerManager.HealthCheckType.NSTOP, ServerManager.HealthCheckType.RUN, 10000))
                .job(logManager.log("waitChangedServerStatus Complete"))

                .job(uploadFileManager.uploadFile(config.getSshUserName(), config.getSshHost(), config.getSshPort(), config.getSshPasswd(), config.getSshFilePath(), config.getSshUploadPath()));

    }

    private void addServerImgClonePipeLine(OperationPipeLine operationPipeLine, Config config, NcloudApiRequest ncloudApiRequest) {
        ServerExtendManager instanceManage = new ServerExtendManager(ncloudApiRequest);
        LogManager logManager = new LogManager();
        InstanceImgExtendManager instanceImgManage = new InstanceImgExtendManager(ncloudApiRequest);

        operationPipeLine
                .job(instanceManage.stopServerInstancesForJob(Collections.singletonList(config.getBaseInstanceNo())))
                .job(logManager.log("stopServerInstancesForJob Complete"))

                .job(instanceManage.waitChangedServerStatus(Collections.singletonList(config.getBaseInstanceNo()), ServerManager.HealthCheckType.RUN, ServerManager.HealthCheckType.NSTOP, 10000))
                .job(logManager.log("waitChangedServerStatus Complete"))

                .job(instanceImgManage.createMemberServerImageForJob(config.getBaseServerImgName(), null, config.getBaseInstanceNo()))
                .job(logManager.log("createMemberServerImageForJob Complete"))

                .job(instanceImgManage.waitChangedServerImgStatusOfStaticImgNo(ServerImageManager.HealthCheckType.INIT, ServerImageManager.HealthCheckType.CREAT, 30000))
                .job(logManager.log("waitChangedServerImgStatusOfStaticImgNo Complete"));

    }

    private void addLaunchConfigrationPipeLine(OperationPipeLine operationPipeLine, Config config, NcloudApiRequest ncloudApiRequest) {
        LaunchConfigurationExtendManager launchConfigurationManager = new LaunchConfigurationExtendManager(ncloudApiRequest);
        LogManager logManager = new LogManager();
        operationPipeLine
                .job(launchConfigurationManager.createLaunchConfigurationStaticWithImgNo(config.getBaseServerImgName(), null, config.getServerProductCode(), Collections.singletonList(config.getAcgNo()), config.getLoginKeyName(), null))
                .job(logManager.log("createLaunchConfigurationStaticWithImgNo Complete"));

    }

    private void addAutoScalingGroupPipeLine(OperationPipeLine operationPipeLine, Config config, NcloudApiRequest ncloudApiRequest) {
        LogManager logManager = new LogManager();
        AutoScalingGroupExtendManager autoScalingGroupExtendManager = new AutoScalingGroupExtendManager(ncloudApiRequest);

        operationPipeLine
                .job(autoScalingGroupExtendManager.createAutoScalingGroupForJob(config.getBaseServerImgName(), config.getBaseServerImgName(), config.getDesiredCapacityOfAutoScaling(), config.getMinSizeOfAutoScaling(), config.getMaxSizeOfAutoScaling(), config.getDefalutCoolDownOfAutoScaling(), AutoScalingGroupManager.AutoScalingHealthCheckType.LOADB, config.getHealthCheckGracePeriodOfAutoScaling(), Collections.singletonList(config.getRegionNo()), Collections.singletonList(config.getLoadBalancerName())))
                .job(logManager.log("createAutoScalingGroupForJob Complete"))

                .job(autoScalingGroupExtendManager.checkServerStatusOfAutoScalingInService(config.getBaseServerImgName(), 10000, 10000))
                .job(logManager.log("checkServerStatusOfAutoScalingInService Complete"));

    }

    private void addPreviousAutoScalingGroupDisusePipeLine(OperationPipeLine operationPipeLine, Config config, NcloudApiRequest ncloudApiRequest) {
        LogManager logManager = new LogManager();
        AutoScalingGroupExtendManager autoScalingGroupExtendManager = new AutoScalingGroupExtendManager(ncloudApiRequest);

        operationPipeLine.job(autoScalingGroupExtendManager.setDesiredCapacityForJob(config.getPreviousAutoScalingGroupName(), 0))
                .job(logManager.log("setDesiredCapacityForJob Complete"))
                .job(autoScalingGroupExtendManager.checkServerStatusOfAutoScalingInTerminated(config.getPreviousAutoScalingGroupName(), 10000, 30000))
                .job(logManager.log("checkServerStatusOfAutoScalingInTerminated Complete"));
    }
}
