package abominableFramework;

public abstract class Controller implements Subscriber {
	protected Publisher model;
	protected Subscriber view;

	public Controller() {

	}

	public void mapModelWithView(Publisher modelClass, Subscriber view) {
		this.model = modelClass;
		this.view = view;
		modelClass.suscribe(this);
		modelClass.suscribe(view);
	}

	/**
	 * @see Subscriber#update(Model)
	 */
	public void update() {

	}

}
