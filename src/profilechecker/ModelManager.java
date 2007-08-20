package profilechecker;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages all the models created and manipulated. A collection with all
 * the models is placed here. 
 * @author moises
 *
 */
public class ModelManager {
	
	/** Map with all the models. The key is the fileName */
	private Map<String, Model> models;
	
	/**
	 * Initializes a new map for the models of this manager.
	 */
	public ModelManager(){
		models = new HashMap<String,Model>();
	}
	
	/**
	 * Returns a model from this manager.
	 * @param fileName the name of the file that was used to generate the model
	 * @return a model from this manager according to the key received
	 */
	public Model getModel(String fileName){
		return models.get(fileName);
	}
	
	/**
	 * Adds a model to the manager.
	 * @param fileName the name of the file that was used to generate the model
	 * @param m the model to be added
	 */
	public void addModel(String fileName, Model m){
		// TODO if the key already exists
		models.put(fileName, m);
	}
}
