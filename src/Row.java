public class Row {
    private String processName;
    private int arrivalTime;
    private int burstTime;
    private int priorityLevel;
    private int waitingTime;
    private int turnAroundTime;

//    CONSTRUCTORS..
    private Row(String processName, int arrivalTime, int burstTime, int priorityLevel, int waitingTime, int turnAroundTime){
        this.processName = processName;
        this.waitingTime = waitingTime;
        this.burstTime = burstTime;
        this.priorityLevel = priorityLevel;
        this.arrivalTime = arrivalTime;
        this.turnAroundTime = turnAroundTime;
    }

    public Row(String processName, int arrivalTime, int burstTime, int priorityLevel){
        this(processName, arrivalTime, burstTime, priorityLevel, 0, 0);
    }

    public Row(String processName, int arrivalTime, int burstTime){
        this(processName, arrivalTime, burstTime, 0, 0, 0);
    }



//    All the setter function
    public void setBurstTime(int burstTime){
        this.burstTime = burstTime;
    }
    public void setTurnAroundTime(int turnAroundTime){
        this.turnAroundTime = turnAroundTime;
    }
    public void setWaitingTime(int waitingTime){
        this.waitingTime = waitingTime;
    }

//    All the Getter function
    public String getProcessName(){
        return this.processName;
    }
    public int getArrivalTime(){
        return this.arrivalTime;
    }
    public int getBurstTime(){
        return this.burstTime;
    }
    public int getPriorityLevel(){
        return this.priorityLevel;
    }
    public int getWaitingTime(){
        return this.waitingTime;
    }
    public int getTurnAroundTime(){
        return this.turnAroundTime;
    }
}
