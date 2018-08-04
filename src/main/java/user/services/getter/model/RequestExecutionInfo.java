package user.services.getter.model;

import java.util.Collection;

public class RequestExecutionInfo {

    private Integer requestId;
    private Collection<String> nfFiles;
    private String message;
    private Integer id;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Collection<String> getNfFiles() {
        return nfFiles;
    }

    public void setNfFiles(Collection<String> nfFiles) {
        this.nfFiles = nfFiles;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
