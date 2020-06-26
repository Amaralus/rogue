package amaralus.apps.rogue.services;

import java.util.HashMap;
import java.util.Map;

public final class ServiceLocator {

    private static ServiceLocator instance = new ServiceLocator();

    private Map<Class<?>, Object> services = new HashMap<>();

    private ServiceLocator() {}

    public static ServiceLocator serviceLocator() {
        return instance;
    }

    public static <S> S getService(Class<S> serviceClass) {
        return instance.get(serviceClass);
    }

    public void register(Object service) {
        services.put(service.getClass(), service);
    }

    public <S> S get(Class<S> serviceClass) {
        return serviceClass.cast(services.get(serviceClass));
    }
}
