package ro.pub.cs.systems.eim.practicaltest01var03;

public interface Constants {
    String FIRST_OPERAND = "firstOperand";
    String SECOND_OPERAND = "secondOperand";
    String OPERATION_RESULT = "operationResult";
    String OPERATION_AND_RESULT = "operationAndResult";
    int SECONDARY_ACTIVITY_REQUEST_CODE = 1;

    String[] actionTypes = {
            "ro.pub.cs.systems.eim.practicaltest01var03.addition",
            "ro.pub.cs.systems.eim.practicaltest01var03.subtraction"
    };

    String PROCESSING_THREAD_TAG = "[Processing Thread]";

    String BROADCAST_RECEIVER_EXTRA = "message";
    String BROADCAST_RECEIVER_TAG = "[Message]";
}
