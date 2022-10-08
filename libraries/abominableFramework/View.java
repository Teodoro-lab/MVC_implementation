package libraries.abominableFramework;

public abstract interface View extends Subscriber {

	public abstract void render();

	/**
	 * @see Subscriber#update(Model)
	 */
	public abstract void update();

}
