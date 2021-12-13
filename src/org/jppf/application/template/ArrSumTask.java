package org.jppf.application.template;

import matrix.MyVector;
import org.jppf.node.protocol.AbstractTask;

import java.util.Vector;

public class ArrSumTask extends AbstractTask<Integer> {

    MyVector myVector;
    Integer action;

    public ArrSumTask(MyVector vector, Integer action) {
        this.myVector = vector;
        this.action = action;
    }

    @Override
    public void run() {
        int len = myVector.getLen();
        Integer resultNum = myVector.getElem(0);
        if (action == 1) {
            for (int i = 1; i < len; i++) {
                if (resultNum < myVector.getElem(i)) {
                    resultNum = myVector.getElem(i);
                }
            }
        } else {
            for (int i = 1; i < len; i++) {
                if (resultNum > myVector.getElem(i)) {
                    resultNum = myVector.getElem(i);
                }
            }
        }

        setResult(resultNum);
    }
}
