package libraries.abominableFramework;

import java.util.ArrayList;

public class Publisher {

	private static ArrayList<Subscriber> suscribers = new ArrayList<>();

	public void suscribe(Subscriber s) {
		this.suscribers.add(s);
	}

	public void notifySuscribers() {
		for (Subscriber subscriber : suscribers) {
			subscriber.update();
		}
	}

}
