package handling;

/**
 * 
 * This is the Subject interface
 *
 */
public interface Subject {
	/**
	 * this method would add an observer
	 * 
	 * @param o
	 *            the observer passed
	 */
	public void addObserver(Observer o);

	/**
	 * this method would delete an observer
	 * 
	 * @param o
	 *            the observer passed
	 */
	public void removeObserver(Observer o);

	/**
	 * this method would notify the observers of a change
	 * 
	 * @param args
	 *            the object passed
	 */
	public void NotifyObservers(Object args);
}
