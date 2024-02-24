import java.util.*;

public class FirstComeFirstServe extends CPUScheduler {
    @Override
    public void process() {
//        First Sorting is done on the basic of the Arrival Time
        Collections.sort(this.getRows(), (Object a,Object b)->{
           if(((Row) a).getArrivalTime() == ((Row) b).getArrivalTime()){
               return 0;
           } else if(((Row) a).getArrivalTime() < ((Row) b).getArrivalTime()){
               return -1;
           } else{
               return 1;
           }
        });

        List<Event> timeline = this.getTimeline();
        for(Row row:this.getRows()){
            if(timeline.isEmpty()){
                timeline.add(new Event(row.getProcessName(), row.getArrivalTime(), row.getArrivalTime()+row.getBurstTime()));
            } else{
                Event e = timeline.get(timeline.size()-1);
                timeline.add(new Event(row.getProcessName(), e.getFinishTime(), e.getFinishTime()+row.getBurstTime()));
            }
        }

        for(Row row:this.getRows()){
            row.setWaitingTime(this.getEvent(row).getStartTime() - row.getArrivalTime());
            row.setTurnAroundTime(row.getWaitingTime() + row.getBurstTime());
        }
    }
}
