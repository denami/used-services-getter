package user.services.getter.services;

import java.util.Collection;
import java.util.Set;

public interface NetworksInfoService {
    public Collection<Set<String>> getInternalNetworks();
    public Collection<Set<String>> getExternalNetworks();
    public boolean isInternalAddress(String ip);
    public boolean isInternalAddress(Long ip);
    public boolean isExternalLAddress(String ip);
    public boolean isExternalLAddress(Long ip);

}
