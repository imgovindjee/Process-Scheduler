import java.util.List;
import java.util.ArrayList;

public abstract class CPUScheduler {
    private final List<Row> rows;
    private final List<Event> timeline;
    private int timeQuantum;

    public CPUScheduler(){
        rows = new ArrayList<>();
        timeline = new ArrayList<>();
        timeQuantum = 1;
    }

    public boolean add(Row row){
        return rows.add(row);
    }

    public void setTimeQuantum(int timeQuantum){
        this.timeQuantum = timeQuantum;
    }
    public  int getTimeQuantum(){
        return timeQuantum;
    }

    public double getAverageWaitingTime(){
        double avg = 0.0;
        for(Row r:rows){
            avg += r.getWaitingTime();
        }
        return avg/rows.size();
    }

    public double getAverageTurnAroundTime(){
        double avg = 0.0;
        for(Row r:rows){
            avg += r.getTurnAroundTime();
        }
        return avg/rows.size();
    }

    public Event getEvent(Row row){
        for(Event e:timeline){
            if(row.getProcessName().equals(e.getProcessName())){
                return e;
            }
        }
        return null;
    }

    public Row getRow(String process){
        for(Row r:rows){
            if (r.getProcessName().equals(process)) {
                return r;
            }
        }
        return null;
    }

    public List<Row> getRows(){
        return rows;
    }

    public List<Event> getTimeline(){
        return timeline;
    }

    public abstract void process();
}
