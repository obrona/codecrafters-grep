package State;

public class ParseState {
    int captureGroupCount = 0;

    public int getCaptureGroupCnt() {
        return this.captureGroupCount;
    }

    public void incrCaptureGroupCount() {
        this.captureGroupCount++;
    }
}
