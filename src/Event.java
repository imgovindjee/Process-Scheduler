public class Event {
    private String processName;
    private int startTime;
    private int finishTime;

    public Event(String processName, int startTime, int finishTime){
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.processName = processName;
    }


    public String getProcessName(){
        return processName;
    }
    public int getStartTime(){
        return startTime;
    }
    public int getFinishTime(){
        return finishTime;
    }
    public void setFinishTime(int finishTime){
        this.finishTime = finishTime;
    }
}
