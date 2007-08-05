package profilechecker;

import java.io.Serializable;

public enum VisibilityType implements Serializable {
	
	publicV, privateV, protectedV, packageV;

	public static VisibilityType toValue(String value) {
		if ("public".equalsIgnoreCase(value)) {
			return publicV;
		}
		if ("private".equalsIgnoreCase(value)) {
			return privateV;
		}
		if ("protected".equalsIgnoreCase(value)) {
			return protectedV;
		}
		if ("package".equalsIgnoreCase(value)) {
			return packageV;
		}
		return null;
	}
	
}