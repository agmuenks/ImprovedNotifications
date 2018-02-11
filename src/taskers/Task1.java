/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskers;

import javafx.application.Platform;

/**
 *
 * @author dalemusser
 * 
 * This example uses an object passed in with a notify()
 * method that gets called when a notification is to occur.
 * To accomplish this the Notifiable interface is needed.
 * 
 */
public class Task1 extends Thread {
    
    private int maxValue, notifyEvery;
    boolean exit = false;
    RunningState runningState = RunningState.NOTRUNNING;
    
    private Notifiable notificationTarget;
    
    public Task1(int maxValue, int notifyEvery)  {
        this.maxValue = maxValue;
        this.notifyEvery = notifyEvery;
    }
    
    @Override
    public void run() {
        this.runningState = RunningState.RUNNING;
        doNotify("Task1 start.", runningState);
        for (int i = 0; i < maxValue; i++) {
            
            if (i % notifyEvery == 0) {
                doNotify("It happened in Task1: " + i, runningState);
            }
            
            if (exit) {
                this.runningState = RunningState.NOTRUNNING;
                return;
            }
        }
        this.runningState = RunningState.NOTRUNNING;
        doNotify("Task1 done.", runningState);
    }
    
    public void end() {
        exit = true;
    }
    
    public void setNotificationTarget(Notifiable notificationTarget) {
        this.notificationTarget = notificationTarget;
    }
    
    private void doNotify(String message, RunningState runningState) {
        // this provides the notification through a method on a passed in object (notificationTarget)
        if (notificationTarget != null) {
            Platform.runLater(() -> {
                notificationTarget.notify(message, runningState);
            });
        }
    }
}
