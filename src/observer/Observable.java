package observer;

public interface Observable {
    public void addObserver(Observer o);
    public void notifyObservers();
}
