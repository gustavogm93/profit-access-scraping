package ar.com.pa.model.queue;

import java.io.Serializable;

import ar.com.pa.model.props.Region;

public class SystemMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String source;
    private String message;
    private Region region;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    

    public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public SystemMessage(String source, String message) {
		super();
		this.source = source;
		this.message = message;
	}

	@Override
    public String toString() {
        return "SystemMessage{" +
                "source='" + source + '\'' +
                ", message='" + message + '\'' +
                ", Region='" + region + '\'' ;
    }
}