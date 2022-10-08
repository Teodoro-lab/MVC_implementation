package libraries.abominableFramework;

import libraries.CSVManager;

public abstract class Model extends Publisher {

	private static final PersistenceManager persistenceManager = new CSVManager();

	public void store() {
		persistenceManager.store(this);
		notifySuscribers();
	}

	public void delete() {
		persistenceManager.delete(this);
		notifySuscribers();
	}

	public void update() {
		persistenceManager.update(this);
		notifySuscribers();
	}

	public Model get(String id) {
		Model result = persistenceManager.get(id);
		return result;
	}

	public Model[] getAll() {
		Model[] result = persistenceManager.getAll(this);
		return result;
	}

	public static void setDataBaseLocation(String dataBaseLocation) {
		persistenceManager.setRoot(dataBaseLocation);
	}

}
