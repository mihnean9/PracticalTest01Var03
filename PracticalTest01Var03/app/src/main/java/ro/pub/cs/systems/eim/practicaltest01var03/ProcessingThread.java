package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;
    private boolean nextOperation = false;

    private int sum;
    private int diff;

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;

        sum = firstNumber + secondNumber;
        diff = firstNumber - secondNumber;
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started!");
        while (isRunning) {
            sendMessage();
            sleep();
            sendMessage();
            stopThread();
        }
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");

    }

    private void sendMessage() {
        Intent intent = new Intent();
        int data;
        if (nextOperation) {
            data = sum;
            intent.setAction(Constants.actionTypes[0]);
        }
        else {
            data = diff;
            intent.setAction(Constants.actionTypes[1]);
        }
        nextOperation = !nextOperation;

        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA, data);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
