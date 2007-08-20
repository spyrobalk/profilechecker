package profilechecker.controller;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import profilechecker.model.Member;
import profilechecker.model.Model;
import profilechecker.model.Package;
import profilechecker.model.Profile;
import profilechecker.model.Stereotype;
import profilechecker.model.StereotypeApplication;

/**
 * This will validate if a model and its stereotype applications are valid.
 * 
 * @author Matheus
 */
public class StereotypeApplicationValidation {

	/**
	 * Main method to validate a stereotype application.
	 * 
	 * @param model
	 *            Model to be validated.
	 * @return Set of exceptions with validation fails.
	 */
	public Set<ValidationException> validate(Model model) {

		Set<ValidationException> result = new LinkedHashSet<ValidationException>();

		Map<String, Profile> profiles = model.getProfiles();
		Map<String, Package> packages = model.getPackages();
		for (StereotypeApplication stereotypeApp : model.getApplications()) {

			String profile = stereotypeApp.getProfile();
			String stereotype = stereotypeApp.getStereotype();
			String base = stereotypeApp.getBase();
			String baseId = stereotypeApp.getBaseId();

			if (!profiles.containsKey(profile)) {
				result.add(new ValidationException("Profile not found: "
						+ profile));
				continue;
			}

			Map<String, Stereotype> stereotypes = profiles.get(profile)
					.getStereotypes();

			if (!stereotypes.containsKey(stereotype)) {
				result.add(new ValidationException("Stereotype not found: "
						+ stereotype));
			}

			Stereotype stereotypeImpl = stereotypes.get(stereotype);

			if (!stereotypeImpl.getTypes().contains(base)) {
				result.add(new ValidationException("Type not valid: " + base));
			}

			for (Package package1 : packages.values()) {
				Map<String, Member> members = package1.getMembers();
				if (members.containsKey(baseId)) {
					Member member = members.get(baseId);
					if (member.getType() == null) {
						result.add(new ValidationException(
								"Member type cannot be null."));
						break;
					}
					if (!member.getType().equals(base)) {
						result
								.add(new ValidationException(
										"Class type cannot be different from application."));
						break;
					}
				}
			}

		}

		return result;

	}

}