package profilechecker.parser;

import java.beans.IntrospectionException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import profilechecker.Profile;

public class XMIParserFacade {

	private Map<String, Profile> profiles;

	public XMIParserFacade() {
		reset();
	}
	
	public void parse(String file) throws Exception {
		XMIParser parser = new XMIParser(new File(file));
		profiles = parser.parse();
	}
	
	public void reset() {
		profiles = null;
	}
	
	public int getNumberOfProfiles() {
		return profiles.size();
	}
	
	public String getProfileProperty(String profile, String property) throws IntrospectionException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return BeanUtils.getProperty(profiles.get(profile), property);
	}
	
	public int getNumberOfStereotypes(String profile) {
		return profiles.get(profile).getStereotypes().size();
	}

	public String getStereotypeProperty(String profile, String stereotype, String property) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return BeanUtils.getProperty(profiles.get(profile).getStereotypes().get(stereotype), property);
	}
	
	public boolean isStereotypeType(String profile, String stereotype, String type) {
		return profiles.get(profile).getStereotypes().get(stereotype).getTypes().contains(type);
	}

	public int getStereotypeTypeSize(String profile, String stereotype) {
		return profiles.get(profile).getStereotypes().get(stereotype).getTypes().size();
	}
	
}