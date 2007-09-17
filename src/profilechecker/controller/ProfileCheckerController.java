package profilechecker.controller;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import profilechecker.model.Model;
import profilechecker.model.ValidationException;

/**
 * Provides a friendly facade to parser and validation class. Also it's
 * responsible for any model update.
 * 
 * @author Matheus
 */
public class ProfileCheckerController {

	private XMIParser parser;
	private StereotypeApplicationValidation validator;

	/**
	 * Creates the controller.
	 * 
	 * @throws ParserConfigurationException
	 *             If the parser fails to be created.
	 * @throws SAXException
	 *             If the parser fails to be created.
	 */
	public ProfileCheckerController() throws ParserConfigurationException,
			SAXException {
		parser = new XMIParser();
		validator = new StereotypeApplicationValidation();
	}

	/**
	 * Parse a XMI file and fill a model.
	 * 
	 * @param model
	 *            Model to be filled.
	 * @param file
	 *            File to be parser.
	 * @throws SAXException
	 *             If the parser fails.
	 * @throws IOException
	 *             If the file is not found or has a reading problem.
	 */
	public void parser(Model model, File file) throws SAXException, IOException {
		Model parsedModel = parser.parse(file);

		// TODO Doing the update the easy way (for now)
		model.setProfiles(parsedModel.getProfiles());
		model.setPackages(parsedModel.getPackages());
		model.setApplications(parsedModel.getApplications());
		model.setValidationExceptions( parsedModel.getValidationExceptions() );
	}

	/**
	 * Validate a model.
	 * 
	 * @param model
	 *            Model to be validated and updated with the found exceptions.
	 */
	public void validate(Model model) {
		Set<ValidationException> validate = validator.validate(model);
		model.setValidationExceptions(validate);

	}

}