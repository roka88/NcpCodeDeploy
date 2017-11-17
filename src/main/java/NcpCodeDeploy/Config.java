package NcpCodeDeploy;

public class Config {

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

    private boolean isUseBuild;
    private String buildType;
    private String buildPath;

    private boolean isUseGit;
    private String gitRepositoryPath;
    private String gitDownLoadPath;
    private String gitUserName;
    private String gitUserPassword;




    public int getHealthCheckGracePeriodOfAutoScaling() {
        return healthCheckGracePeriodOfAutoScaling;
    }

    public void setHealthCheckGracePeriodOfAutoScaling(int healthCheckGracePeriodOfAutoScaling) {
        this.healthCheckGracePeriodOfAutoScaling = healthCheckGracePeriodOfAutoScaling;
    }

    public String getServerProductCode() {
        return serverProductCode;
    }

    public void setServerProductCode(String serverProductCode) {
        this.serverProductCode = serverProductCode;
    }


    public int getDefalutCoolDownOfAutoScaling() {
        return defalutCoolDownOfAutoScaling;
    }

    public void setDefalutCoolDownOfAutoScaling(int defalutCoolDownOfAutoScaling) {
        this.defalutCoolDownOfAutoScaling = defalutCoolDownOfAutoScaling;
    }

    public String getBaseServerImgName() {
        return baseServerImgName;
    }

    public void setBaseServerImgName(String baseServerImgName) {
        this.baseServerImgName = baseServerImgName;
    }

    public String getPreviousAutoScalingGroupName() {
        return previousAutoScalingGroupName;
    }

    public void setPreviousAutoScalingGroupName(String previousAutoScalingGroupName) {
        this.previousAutoScalingGroupName = previousAutoScalingGroupName;
    }

    public String getBaseInstanceNo() {
        return baseInstanceNo;
    }

    public void setBaseInstanceNo(String baseInstanceNo) {
        this.baseInstanceNo = baseInstanceNo;
    }



    public int getDesiredCapacityOfAutoScaling() {
        return desiredCapacityOfAutoScaling;
    }

    public void setDesiredCapacityOfAutoScaling(int desiredCapacityOfAutoScaling) {
        this.desiredCapacityOfAutoScaling = desiredCapacityOfAutoScaling;
    }

    public int getMinSizeOfAutoScaling() {
        return minSizeOfAutoScaling;
    }

    public void setMinSizeOfAutoScaling(int minSizeOfAutoScaling) {
        this.minSizeOfAutoScaling = minSizeOfAutoScaling;
    }

    public int getMaxSizeOfAutoScaling() {
        return maxSizeOfAutoScaling;
    }

    public void setMaxSizeOfAutoScaling(int maxSizeOfAutoScaling) {
        this.maxSizeOfAutoScaling = maxSizeOfAutoScaling;
    }

    public String getAcgNo() {
        return acgNo;
    }

    public void setAcgNo(String acgNo) {
        this.acgNo = acgNo;
    }

    public String getLoginKeyName() {
        return loginKeyName;
    }

    public void setLoginKeyName(String loginKeyName) {
        this.loginKeyName = loginKeyName;
    }

    public String getRegionNo() {
        return regionNo;
    }

    public void setRegionNo(String regionNo) {
        this.regionNo = regionNo;
    }

    public String getLoadBalancerName() {
        return loadBalancerName;
    }

    public void setLoadBalancerName(String loadBalancerName) {
        this.loadBalancerName = loadBalancerName;
    }



    public String getSshUserName() {
        return sshUserName;
    }

    public void setSshUserName(String sshUserName) {
        this.sshUserName = sshUserName;
    }

    public String getSshHost() {
        return sshHost;
    }

    public void setSshHost(String sshHost) {
        this.sshHost = sshHost;
    }

    public int getSshPort() {
        return sshPort;
    }

    public void setSshPort(int sshPort) {
        this.sshPort = sshPort;
    }

    public String getSshPasswd() {
        return sshPasswd;
    }

    public void setSshPasswd(String sshPasswd) {
        this.sshPasswd = sshPasswd;
    }

    public String getSshFilePath() {
        return sshFilePath;
    }

    public void setSshFilePath(String sshFilePath) {
        this.sshFilePath = sshFilePath;
    }

    public String getSshUploadPath() {
        return sshUploadPath;
    }

    public void setSshUploadPath(String sshUploadPath) {
        this.sshUploadPath = sshUploadPath;
    }


    public boolean isUseBuild() {
        return isUseBuild;
    }

    public void setUseBuild(boolean useBuild) {
        isUseBuild = useBuild;
    }

    public String getBuildType() {
        return buildType;
    }

    public void setBuildType(String buildType) {
        this.buildType = buildType;
    }

    public String getBuildPath() {
        return buildPath;
    }

    public void setBuildPath(String buildPath) {
        this.buildPath = buildPath;
    }

    public boolean isUseGit() {
        return isUseGit;
    }

    public void setUseGit(boolean useGit) {
        isUseGit = useGit;
    }

    public String getGitRepositoryPath() {
        return gitRepositoryPath;
    }

    public void setGitRepositoryPath(String gitRepositoryPath) {
        this.gitRepositoryPath = gitRepositoryPath;
    }

    public String getGitDownLoadPath() {
        return gitDownLoadPath;
    }

    public void setGitDownLoadPath(String gitDownLoadPath) {
        this.gitDownLoadPath = gitDownLoadPath;
    }

    public String getGitUserName() {
        return gitUserName;
    }

    public void setGitUserName(String gitUserName) {
        this.gitUserName = gitUserName;
    }

    public String getGitUserPassword() {
        return gitUserPassword;
    }

    public void setGitUserPassword(String gitUserPassword) {
        this.gitUserPassword = gitUserPassword;
    }

}
