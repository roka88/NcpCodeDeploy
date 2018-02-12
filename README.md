# NcpCodeDeploy

NcpDeployPipeLine 라이브러리를 사용하여 Jar 실행파일을 만듬. 환경설정 파일이 필요함
<br />
NcpCodeDeploy를 직접 만들고 싶을 시 NcpDeployPipeLine 라이브러리를 이용하면 된다.
<br />

### 변경점
<pre>
0.0.4 NcpPipe 0.2 버전업, ncloud-api-1.0.2-SNAPSHOT-jar-with-dependencies.jar 의존성 추가
0.0.3 NcpDeploy 0.2 의존성 제거, NcpPipe 의존성 추가. 내부적으로 PipeLine을 CompletableFuture로 변경
0.0.2 NcpDeploy 0.2 버전 의존
0.0.1 초기버전
</pre>

### 의존성


* NcpPipe가 가져야 할 의존성

<pre>
compile files('libs/ncloud-api-1.0.2-SNAPSHOT-jar-with-dependencies.jar')
compile group: 'org.apache.commons', name: 'commons-exec', version: '1.3'
compile group: 'com.jcraft', name: 'jsch', version: '0.1.44-1'
compile group: 'org.eclipse.jgit', name: 'org.eclipse.jgit', version: '4.9.0.201710071750-r'
</pre>

* NcpCodeDeploy가 가져야 할 의존성
<pre>
compile files('libs/NcpPipe-0.0.2.jar')
</pre>

### 지원
1. 현재 Blue/Grean 방식만 지원
![alt text](https://github.com/roka88/NcpCodeDeploy/blob/master/ncp_deploy_0.png)



### NcpCodeDeploy 파이프 라인

1. 단독형

![alt text](https://github.com/roka88/NcpCodeDeploy/blob/master/ncp_deploy_1.png)

2. Jenkins

![alt text](https://github.com/roka88/NcpCodeDeploy/blob/master/ncp_deploy_2.png)



### 실행방법

* 단독형으로 실행 시
<pre>

java -jar NcpCodeDeploy-*.jar /conf_file_path
</pre>

* Jenkins 이용 시
<pre>

jenkins의 빌드 후 Post Build task를 이용. 
Script 부분에 java -jar NcpCodeDeploy-*.jar /conf_file_path를 실행해준다.
중요한 것은 Jenkins가 실행파일과 Conf 파일에 대해 실행 권한이 있어야 한다.
</pre>

![alt text](https://github.com/roka88/NcpCodeDeploy/blob/master/ncp_deploy_3.png)




### ncp_deploy.conf

꼭 주의 깊게 읽어봐야 한다.

<pre>

# 필수 consumer_key, consumer_secretkey
# 필수적으로 NCP의 ConsumerKey와 SecretKey가 있어야 한다.
# debug 모드를 true할 시 응답에 대한 로그가 찍힌다. default는 false
<br />

[auth]
#consumer_key=pwGP4oPJFG5IWrWlkCd1
#consumer_secretkey=EBlbbvWcc3xlRC2nX1Zbc3n8UbggAeko7E1JIX10
#debug=true
<br />



# 필수 base_instance_no
# 필수적으로 Base 서버 이미지를 만들려면 복사할 인스턴스가 있어야하며, 중지중이어야 한다.
# request_url을 작성하지 않아도 되며 디폴트는 https://api.ncloud.com
<br />

[base]
#base_instance_no=507693
#request_url=https://api.ncloud.com
<br />




# server_img_name을 설정하면 해당 서버이미지, LaunchConfiguration, AutoScalingGroup이름이 동일하게 설정된다.
# 설정하지 않으면 instance-(currentmills)로 이름이 자동으로 지정된다.
<br />

[serverImg]
#server_img_name=api-instance1
<br />


# 필수 server_product_code
# acg_no를 설정하지 않으면, 기본 ACG로 설정된다.
# login_key를 설정하지 않으면 최근에 만든 LoginKey로 설정된다.
<br />

[launchConfiguration]
#server_product_code=SPSVRSSD00000002
#acg_no=23872
#login_key=test-key
<br />





# 필수 desired_capacity, min_size, max_size, region_no, load_balancer_name
# 새로운 AutoScalingGroup이 만들어지고 새로운 인스턴스가 정상적으로 운영중이 될 때 이전의 AutoScalingGroup의 desired_capacity를 0으로 만들 previous_autoscaling_name 을 작성한다.
# 작성하지 않을 시 기존의 AutoScalingGroup을 건드리지 않는다.
# min_size는 0이하가 될 수 없으며, desired_capacity는 max_size보다 높을 수 없다.
# cool_down 또는 health_check_grace_period을 정하지 않거나 600보다 작을 경우 기본 값은 600이 된다.
<br />



[autoscaling]

#previous_autoscaling_name=instance-1510893288928
#desired_capacity=1
#min_size=0
#max_size=2
#cool_down=600
#health_check_grace_period=600
#region_no=2
#load_balancer_name=test-loadbalancer
<br />



# 필수 user_name, host, port, password, file_path, upload_path
# 베이스 인스턴스에 업로드 시킬 파일을 설정한다.
# file_path 작성 시 업로드할 파일 경로,명을 설정한다. Build를 사용할 시 빌드된 파일 경로,명을 설정한다.
# upload_path 는 Base-Instance에 전송시킬 디렉토리 경로를 설정한다.
<br />

[ssh]
#user_name=roka
#host=123.123.123.123
#port=12345
#password=ncpncp123
#file_path=/home/project/build/libs/sever-1.0.0.jar
#upload_path=/home/user
<br />





# git에서 파일을 Clone할 수 있다.default는 false다.
# git을 true로 하면 Pipeline에 git이 추가된다. 
# git을 true로 하면 repository_path, download_dir_path, user_name, user_password를 작성해주어야 한다.
# Jenkins 또는 다른 빌드 봇을 사용시에는 굳이 직접 Clone 하지 된다.(CI에서 Clone 후 빌드 -> 파일전송)
<br />

[git]
#git=true
#repository_path=https://roka@bitbucket.org/project/project.git
#download_dir_path=/home/project
#user_name=roka
#user_password=123456
<br />



# build를 true로 하면 PipeLine에 build가 추가된다. defalut는 false다.
# git을 true로 하면 repository_path, download_dir_path, user_name, user_password를 작성해주어야 한다.
# 빌드를 한다
# build_type을 정할 수 있다. 현재는 gradle만 지원한다.
# Jenkins 또는 다른 빌드 봇을 사용시에는 굳이 설정안해도 된다.(CI에서 빌드 후 파일전송으로)
<br />

[build]
#build=true
#build_type=gradle
#build_path=/home/project
<br />

</pre>


### NcpPipe-*.jar

<https://github.com/roka88/NcpPipe>

### NcpCodeDeploy-*.jar 실행파일

<https://github.com/roka88/NcpCodeDeployFile>