package user.services.getter.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import user.services.getter.services.NetworksInfoService;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Service
public class NetworksInfoServiceImpl implements NetworksInfoService {

    @Value("#{'${internal.networck.ranges}'.split(',')}")
    private String[] internalNetworck;

    @Value("#{'${external.networck.ranges}'.split(',')}")
    private String[] externalNetworck;

    private Map<Long, Long> internalRanges;
    private Map<Long, Long> ExternalRanges;

    private void updateExternalMap() {
        for (String i : internalNetworck) {




        }
    }

    private void updateInternalMap() {


    }

    @Override
    public Collection<Set<String>> getInternalNetworks() {
        return null;
    }

    @Override
    public Collection<Set<String>> getExternalNetworks() {
        return null;
    }

    @Override
    public boolean isInternalAddress(String ip) {
        return false;
    }

    @Override
    public boolean isInternalAddress(Long ip) {
        return false;
    }

    @Override
    public boolean isExternalLAddress(String ip) {
        return false;
    }

    @Override
    public boolean isExternalLAddress(Long ip) {
        return false;
    }
}
