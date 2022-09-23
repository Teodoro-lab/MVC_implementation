package abominableFramework;

public abstract interface PersistenceManager {

	public void store(Model model);

	public void delete(Model model);

	public void update(Model model);

	public Model get(String id);

	public Model[] getAll(Model model);

	public abstract void parseModel();

}
