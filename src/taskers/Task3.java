/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskers;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javafx.application.Platform;

/**
 *
 * @author dalemusser
 * 
 * This example uses PropertyChangeSupport to implement
 * property change listeners.
 * 
 */
public class Task3 extends Thread {
    
    private int maxValue, notifyEvery;
    boolean exit = false;
    RunningState runningState = RunningState.NOTRUNNING;
    
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    public Task3(int maxValue, int notifyEvery)  {
        this.maxValue = maxValue;
        this.notifyEvery = notifyEvery;
    }
    
    @Override
    public void run() {
        this.runningState = RunningState.RUNNING;
        doNotify("Task3 start.", runningState);
        
        for (int i = 0; i < maxValue; i++) {
            
            if (i % notifyEvery == 0) {
                doNotify("It happened in Task3: " + i, runningState);
            }
            
            if (exit) {
                this.runningState = RunningState.NOTRUNNING;
                return;
            }
        }
        this.runningState = RunningState.NOTRUNNING;
        doNotify("Task3 done.", runningState);
    }
    
    public void end() {
        exit = true;
    }
    
    // the following two methods allow property change listeners to be added
    // and removed
    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
         pcs.addPropertyChangeListener(name, listener);
     }

     public void removePropertyChangeListener(String name, PropertyChangeListener listener) {
         pcs.removePropertyChangeListener(name, listener);
     }
    
    private void doNotify(String message, RunningState runningState){//, RunningState runningState) {
        // this provides the notification through the property change listener
        Platform.runLater(() -> {
            // I'm choosing not to send the old value (second param).  Sending "" instead.
            pcs.firePropertyChange("message", "", message);
            pcs.firePropertyChange("state", "", runningState);
        });
    }
    
}