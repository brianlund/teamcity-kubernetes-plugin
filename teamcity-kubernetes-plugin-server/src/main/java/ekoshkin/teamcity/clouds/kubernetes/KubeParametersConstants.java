package ekoshkin.teamcity.clouds.kubernetes;

import jetbrains.buildServer.clouds.CloudImageParameters;

/**
 * Created by ekoshkin (koshkinev@gmail.com) on 28.05.17.
 */
public class KubeParametersConstants {
    public static final String API_SERVER_URL = "api-server-url";
    public static final String SERVICE_ACCOUNT_NAME = "service-account-name";
    public static final String SERVICE_ACCOUNT_TOKEN = "service-account-token";
    public static final String KUBERNETES_NAMESPACE = "kubernetes-namespace";
    public static final String PROFILE_INSTANCE_LIMIT = "profile-instance-limit";
    public static final String DOCKER_IMAGE = "dockerImage";
    public static final String DOCKER_ARGS = "dockerArgs";
    public static final String DOCKER_CMD = "dockerCmd";
    public static final String IMAGE_PULL_POLICY = "imagePullPolicy";

    public String getApiServerUrl() {
        return API_SERVER_URL;
    }

    public String getSeviceAccountName() {
        return SERVICE_ACCOUNT_NAME;
    }

    public String getSeviceAccountToken() {
        return SERVICE_ACCOUNT_TOKEN;
    }

    public String getKubernetesNamespace() {
        return KUBERNETES_NAMESPACE;
    }

    public String getProfileInstanceLimit() {
        return PROFILE_INSTANCE_LIMIT;
    }

    public String getAgentPoolIdField() {
        return CloudImageParameters.AGENT_POOL_ID_FIELD;
    }

    public String getDockerImage() {
        return DOCKER_IMAGE;
    }

    public String getDockerArguments() {
        return DOCKER_ARGS;
    }

    public String getDockerCommand() {
        return DOCKER_CMD;
    }

    public String getImagePullPolicy() {
        return IMAGE_PULL_POLICY;
    }
}