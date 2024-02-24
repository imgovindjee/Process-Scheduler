import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShortestJobFirstServe extends CPUScheduler{
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
            List<Row> availableRows = new ArrayList<>();
            for(Row row:rows){
                if(row.getArrivalTime() <= time){
                    availableRows.add(row);
                }
            }

//            After the First Sorting,
//            Sorting is done on the basic of the BurstTime of the process
//            Lower the BurstTime, executes the first
            Collections.sort(availableRows, (Object a, Object b) -> {
                if (((Row) a).getBurstTime() == ((Row) b).getBurstTime()) {
                    return 0;
                } else if (((Row) a).getBurstTime() < ((Row) b).getBurstTime()) {
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

        for(Row row:this.getRows()){
            row.setWaitingTime(this.getEvent(row).getStartTime() - row.getArrivalTime());
            row.setTurnAroundTime(row.getWaitingTime() + row.getBurstTime());
        }
    }
}
