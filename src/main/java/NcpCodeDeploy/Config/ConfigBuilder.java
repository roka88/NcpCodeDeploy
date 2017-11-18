package NcpCodeDeploy.Config;

import org.apache.commons.lang.StringUtils;

public class ConfigBuilder {



    private String baseServerImgName;
    private String previousAutoScalingGroupName;
    private String baseInstanceNo;

    private String sshUserName;
    private String sshHost;
    private int sshPort;
    private String sshPasswd;
    private String sshFilePath;
    private String sshUploadPath;

    private int desiredCapacityOfAutoScaling;
    private int minSizeOfAutoScaling;
    private int maxSizeOfAutoScaling;
    private int defalutCoolDownOfAutoScaling;
    private int healthCheckGracePeriodOfAutoScaling;

    private String acgNo;
    private String loginKeyName;
    private String regionNo;
    private String loadBalancerName;

    private String serverProductCode;

    private Boolean isUseBuild = false;
    private String buildType;
    private String buildPath;

    private Boolean isUseGit = false;
    private String gitRepositoryPath;
    private String gitDownLoadPath;
    private String gitUserName;
    private String gitUserPassword;


    public ConfigBuilder baseServerImgName(String baseServerImgName) {
        this.baseServerImgName = baseServerImgName;
        return this;
    }

    public ConfigBuilder previousAutoScalingGroupName(String previousAutoScalingGroupName) {
        this.previousAutoScalingGroupName = previousAutoScalingGroupName;
        return this;
    }

    public ConfigBuilder baseInstanceNo(String baseInstanceNo) {
        this.baseInstanceNo = baseInstanceNo;
        return this;
    }

    public ConfigBuilder sshUserName(String sshUserName) {
        this.sshUserName = sshUserName;
        return this;
    }

    public ConfigBuilder sshHost(String sshHost) {
        this.sshHost = sshHost;
        return this;
    }

    public ConfigBuilder sshPort(String sshPort) {
        if (StringUtils.isNotEmpty(sshPort)) {
            this.sshPort = Integer.parseInt(sshPort);
        }
        return this;
    }

    public ConfigBuilder sshPasswd(String sshPasswd) {
        this.sshPasswd = sshPasswd;
        return this;
    }

    public ConfigBuilder sshFilePath(String sshFilePath) {
        this.sshFilePath = sshFilePath;
        return this;
    }

    public ConfigBuilder sshUploadPath(String sshUploadPath) {
        this.sshUploadPath = sshUploadPath;
        return this;
    }

    public ConfigBuilder desiredCapacityOfAutoScaling(String desiredCapacityOfAutoScaling) {
        if (StringUtils.isNotEmpty(desiredCapacityOfAutoScaling)) {
            this.desiredCapacityOfAutoScaling = Integer.parseInt(desiredCapacityOfAutoScaling);
        }
        return this;
    }

    public ConfigBuilder minSizeOfAutoScaling(String minSizeOfAutoScaling) {
        if (StringUtils.isNotEmpty(minSizeOfAutoScaling)) {
            this.minSizeOfAutoScaling = Integer.parseInt(minSizeOfAutoScaling);
        }
        return this;
    }

    public ConfigBuilder maxSizeOfAutoScaling(String maxSizeOfAutoScaling) {
        if (StringUtils.isNotEmpty(maxSizeOfAutoScaling)) {
            this.maxSizeOfAutoScaling = Integer.parseInt(maxSizeOfAutoScaling);
        }
        return this;
    }

    public ConfigBuilder defalutCoolDownOfAutoScaling(String defalutCoolDownOfAutoScaling) {
        if (StringUtils.isNotEmpty(defalutCoolDownOfAutoScaling)) {
            this.defalutCoolDownOfAutoScaling = Integer.parseInt(defalutCoolDownOfAutoScaling);
        }
        return this;
    }

    public ConfigBuilder healthCheckGracePeriodOfAutoScaling(String healthCheckGracePeriodOfAutoScaling) {
        if (StringUtils.isNotEmpty(healthCheckGracePeriodOfAutoScaling)) {
            this.healthCheckGracePeriodOfAutoScaling = Integer.parseInt(healthCheckGracePeriodOfAutoScaling);
        }
        return this;
    }

    public ConfigBuilder acgNo(String acgNo) {
        this.acgNo = acgNo;
        return this;
    }

    public ConfigBuilder loginKeyName(String loginKeyName) {
        this.loginKeyName = loginKeyName;
        return this;
    }

    public ConfigBuilder regionNo(String regionNo) {
        this.regionNo = regionNo;
        return this;
    }

    public ConfigBuilder loadBalancerName(String loadBalancerName) {
        this.loadBalancerName = loadBalancerName;
        return this;
    }



    public ConfigBuilder serverProductCode(String serverProductCode) {
        this.serverProductCode = serverProductCode;
        return this;
    }

    public ConfigBuilder useBuild(String useBuild) {
        if (StringUtils.isNotEmpty(useBuild)) {
            isUseBuild = Boolean.parseBoolean(useBuild);
        }
        return this;
    }

    public ConfigBuilder buildType(String buildType) {
        this.buildType = buildType;
        return this;
    }

    public ConfigBuilder buildPath(String buildPath) {
        this.buildPath = buildPath;
        return this;
    }

    public ConfigBuilder useGit(String useGit) {
        if (StringUtils.isNotEmpty(useGit)) {
            isUseGit = Boolean.parseBoolean(useGit);
        }
        return this;
    }

    public ConfigBuilder gitRepositoryPath(String gitRepositoryPath) {
        this.gitRepositoryPath = gitRepositoryPath;
        return this;
    }

    public ConfigBuilder gitDownLoadPath(String gitDownLoadPath) {
        this.gitDownLoadPath = gitDownLoadPath;
        return this;
    }

    public ConfigBuilder gitUserName(String gitUserName) {
        this.gitUserName = gitUserName;
        return this;
    }

    public ConfigBuilder gitUserPassword(String gitUserPassword) {
        this.gitUserPassword = gitUserPassword;
        return this;
    }


    public Config build() {
        Config config = new Config();
        if (null == baseServerImgName) {
            baseServerImgName = "instance-" + System.currentTimeMillis();
        }
        config.setBaseServerImgName(baseServerImgName);
        config.setPreviousAutoScalingGroupName(previousAutoScalingGroupName);
        config.setBaseInstanceNo(baseInstanceNo);
        config.setSshUserName(sshUserName);
        config.setSshHost(sshHost);
        config.setSshPort(sshPort);
        config.setSshPasswd(sshPasswd);
        config.setSshFilePath(sshFilePath);
        config.setSshUploadPath(sshUploadPath);

        config.setDesiredCapacityOfAutoScaling(desiredCapacityOfAutoScaling);
        config.setMinSizeOfAutoScaling(minSizeOfAutoScaling);
        config.setMaxSizeOfAutoScaling(maxSizeOfAutoScaling);
        config.setDefalutCoolDownOfAutoScaling(defalutCoolDownOfAutoScaling);
        config.setHealthCheckGracePeriodOfAutoScaling(healthCheckGracePeriodOfAutoScaling);

        config.setAcgNo(acgNo);
        config.setLoginKeyName(loginKeyName);
        config.setRegionNo(regionNo);
        config.setLoadBalancerName(loadBalancerName);
        config.setServerProductCode(serverProductCode);

        config.setUseBuild(isUseBuild);
        config.setBuildType(buildType);
        config.setBuildPath(buildPath);

        config.setUseGit(isUseGit);
        config.setGitRepositoryPath(gitRepositoryPath);
        config.setGitDownLoadPath(gitDownLoadPath);
        config.setGitUserName(gitUserName);
        config.setGitUserPassword(gitUserPassword);

        return config;
    }
}
