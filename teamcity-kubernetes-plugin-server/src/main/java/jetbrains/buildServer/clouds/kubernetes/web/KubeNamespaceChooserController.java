package jetbrains.buildServer.clouds.kubernetes.web;

import jetbrains.buildServer.clouds.kubernetes.auth.KubeAuthStrategyProvider;
import jetbrains.buildServer.clouds.kubernetes.connector.KubeApiConnection;
import jetbrains.buildServer.clouds.kubernetes.connector.KubeApiConnectorImpl;
import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.controllers.BasePropertiesBean;
import jetbrains.buildServer.internal.PluginPropertiesUtil;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

import static jetbrains.buildServer.clouds.kubernetes.KubeParametersConstants.*;

/**
 * Created by Evgeniy Koshkin (evgeniy.koshkin@jetbrains.com) on 05.10.17.
 */
public class KubeNamespaceChooserController extends BaseController {
    private static final String URL = "kubeNaspaces.html";

    private final PluginDescriptor myPluginDescriptor;
    private KubeAuthStrategyProvider myAuthStrategyProvider;

    public KubeNamespaceChooserController(WebControllerManager web,
                                          PluginDescriptor pluginDescriptor,
                                          KubeAuthStrategyProvider authStrategyProvider) {
        myPluginDescriptor = pluginDescriptor;
        myAuthStrategyProvider = authStrategyProvider;
        web.registerController(getUrl(), this);
    }

    @NotNull
    public String getUrl() {
        return myPluginDescriptor.getPluginResourcesPath(URL);
    }

    @Nullable
    @Override
    protected ModelAndView doHandle(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse) throws Exception {
        BasePropertiesBean propsBean = new BasePropertiesBean(null);
        PluginPropertiesUtil.bindPropertiesFromRequest(httpServletRequest, propsBean, true);
        Map<String, String> props = propsBean.getProperties();

        KubeApiConnection apiConnection = new KubeApiConnection() {
            @NotNull
            @Override
            public String getApiServerUrl() {
                return props.get(API_SERVER_URL);
            }

            @Nullable
            @Override
            public String getNamespace() {
                return props.get(KUBERNETES_NAMESPACE);
            }

            @Nullable
            @Override
            public String getCustomParameter(@NotNull String parameterName) {
                return props.get(parameterName);
            }
        };
        String authStrategy = props.get(AUTH_STRATEGY);

        ModelAndView modelAndView = new ModelAndView(myPluginDescriptor.getPluginResourcesPath("kubeNamespaces.jsp"));
        try {
            KubeApiConnectorImpl apiConnector = KubeApiConnectorImpl.create(apiConnection, myAuthStrategyProvider.get(authStrategy));
            modelAndView.getModelMap().put("namespaces", apiConnector.listNamespaces());
            modelAndView.getModelMap().put("error","");
        } catch (Exception ex){
            modelAndView.getModelMap().put("namespaces", Collections.emptyList());
            modelAndView.getModelMap().put("error", ex.getLocalizedMessage());
        }
        return modelAndView;
    }
}