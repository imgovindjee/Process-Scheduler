import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriorityNonPreemptiveScheduling extends CPUScheduler{
    @Override
    public void process() {
//        First Sorting is done on the basic of the Arrival Time
        Collections.sort(this.getRows(), (Object a, Object b) -> {
            if (((Row) a).getArrivalTime() == ((Row) b).getArrivalTime()) {
                return 0;
            } else if (((Row) a).getArrivalTime() < ((Row) b).getArrivalTime()) {
                return -1;
            } else {
                return 1;
            }
        });

        List<Row> rows = Utility.deepCopy(this.getRows());
        int time = rows.get(0).getArrivalTime();

        while (!rows.isEmpty()){
            List<Row> availableRows = new ArrayList();
            for(Row r:rows){
                if(r.getArrivalTime() <= time){
                    availableRows.add(r);
                }
            }

//            After the First Sorting,
//            Sorting is done on the basic of the PriorityLevel of the process
//            Either on the basic of the Higher the priority higher the preference
            Collections.sort(availableRows, (Object a, Object b)->{
                if(((Row) a).getPriorityLevel() == ((Row) b).getPriorityLevel()){
                    return 0;
                } else if(((Row) a).getPriorityLevel() < ((Row) b).getPriorityLevel()){
                    return -1;
                } else {
                    return 1;
                }
            });

            Row row = availableRows.get(0);
            this.getTimeline().add(new Event(row.getProcessName(), time, time+row.getBurstTime()));
            time += row.getBurstTime();

            for(int i=0; i<rows.size(); i++){
                if(rows.get(i).getProcessName().equals(row.getProcessName())){
                    rows.remove(i);
                    break;
                }
            }
        }

        for(Row row : this.getRows()){
            row.setWaitingTime(this.getEvent(row).getStartTime() - row.getArrivalTime());
            row.setTurnAroundTime(row.getWaitingTime() + row.getBurstTime());
        }
    }
}
