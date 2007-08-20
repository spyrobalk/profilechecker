package profilechecker;

import java.util.HashMap;
import java.util.Map;

public class ModelManager {
	
	private Map<String, Model> models;
	
	public ModelManager(){
		models = new HashMap<String,Model>();
	}
	
	public Model getModel(String fileName){
		return models.get(fileName);
	}
	
	public void addModel(String fileName, Model m){
		// TODO if the key already exists
		models.put(fileName, m);
	}
}
