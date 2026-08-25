package State;

public class ParseState {
    int captureGroupCount = 1;

    public int getCaptureGrpCnt() {
        return this.captureGroupCount;
    }

    public int getAndIncrCaptureGrpCnt() {
        return this.captureGroupCount++;
    }

    public void incrCaptureGrpCnt() {
        this.captureGroupCount++;
    }
}
