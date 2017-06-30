package ekoshkin.teamcity.clouds.kubernetes.auth;

import ekoshkin.teamcity.clouds.kubernetes.KubeCloudException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ekoshkin (koshkinev@gmail.com) on 14.06.17.
 */
public class KubeAuthStrategyProviderImpl implements KubeAuthStrategyProvider {
    private final Map<String, KubeAuthStrategy> myIdToStrategyMap = new HashMap<>();

    public KubeAuthStrategyProviderImpl() {
        registerStrategy(new UserPasswdAuthStrategy());
        registerStrategy(new DefaultServiceAccountAuthStrategy());
        registerStrategy(new UnauthorizedAccessStrategy());
        //registerStrategy(new ClientCertificateAuthStrategy());
        registerStrategy(new TokenAuthStrategy());
    }

    @NotNull
    @Override
    public Collection<KubeAuthStrategy> getAll() {
        return myIdToStrategyMap.values();
    }

    @Nullable
    @Override
    public KubeAuthStrategy find(@Nullable String strategyId) {
        return myIdToStrategyMap.get(strategyId);
    }

    @NotNull
    @Override
    public KubeAuthStrategy get(@Nullable String id) {
        KubeAuthStrategy authStrategy = find(id);
        if(authStrategy == null) throw new KubeCloudException("Unknown auth strategy " + id);
        return authStrategy;
    }

    private void registerStrategy(KubeAuthStrategy authStrategy){
        myIdToStrategyMap.put(authStrategy.getId(), authStrategy);
    }
}
