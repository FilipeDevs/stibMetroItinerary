package model;

import view.Observer;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private final List<Observer> observers;

    /**
     * Construct an Observable with zero Observers.
     */
    public Observable() {
        observers = new ArrayList<>();
    }

    /**
     * Adds an observer to the set of observers for this object, provided that
     * it is not the same as some observer already in the set. The order in
     * which notifications will be delivered to multiple observers is not
     * specified. See the class comment.
     *
     * @param observer an observer to be added.
     * @throws NullPointerException if the parameter o is null.
     */
    public void addObserver(Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Deletes an observer from the set of observers of this object. Passing
     * {@code null} to this method will have no effect.
     *
     * @param observer the observer to be deleted.
     */
    public synchronized void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * If this object has changed, as indicated by the {@code hasChanged}
     * method, then notify all of its observers and then call the
     * {@code clearChanged} method to indicate that this object has no longer
     * changed.
     * <p>
     * Each observer has its {@code update} method called with two arguments:
     * this observable object and {@code null}. In other words, this method is
     * equivalent to:
     * <blockquote>{@code
     * notifyObservers(null)}</blockquote>
     *
     * @see java.util.Observable#hasChanged()
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void notifyObservers() {
        notifyObservers(null);
    }

    /**
     * If this object has changed, as indicated by the {@code hasChanged}
     * method, then notify all of its observers and then call the
     * {@code clearChanged} method to indicate that this object has no longer
     * changed.
     * <p>
     * Each observer has its {@code update} method called with two arguments:
     * this observable object and the {@code arg} argument.
     *
     * @param arg any object.
     * @see java.util.Observable#hasChanged()
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void notifyObservers(Object arg) {
        for (Observer observer : observers) {
            observer.update(this, arg);
        }
    }

    /**
     * Returns the number of observers of this {@code Observable} object.
     *
     * @return the number of observers of this object.
     */
    public synchronized int countObservers() {
        return observers.size();
    }

}
