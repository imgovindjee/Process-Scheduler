import java.util.*;

public class ShortestRemainingTimeFirst extends CPUScheduler{
    @Override
    public void process(){
//        First Sorting is done on the basic of the Arrival Time
        Collections.sort(this.getRows(), (Object a, Object b)->{
            if(((Row) a).getArrivalTime() == ((Row) b).getArrivalTime()){
                return 0;
            } else if(((Row) a).getArrivalTime() < ((Row) b).getArrivalTime()){
                return -1;
            } else {
                return 1;
            }
        });


        List<Row> rows = Utility.deepCopy(this.getRows());
        int time = rows.get(0).getArrivalTime();

        while (!rows.isEmpty()){
            List<Row> availableRows = new ArrayList<>();
            for(Row r:rows){
                if(r.getArrivalTime() <= time){
                    availableRows.add(r);
                }
            }


//            After the First Sorting,
//            Sorting is done on the basic of the BurstTime of the process
//            Lower the BurstTime, executes the first
            Collections.sort(availableRows, (Object a, Object b)->{
                if(((Row) a).getBurstTime() == ((Row) b).getBurstTime()){
                    return 0;
                } else if(((Row) a).getBurstTime() < ((Row) b).getBurstTime()){
                    return -1;
                } else {
                    return 1;
                }
            });

            Row row = availableRows.get(0);
            this.getTimeline().add(new Event(row.getProcessName(), time, time++));
            row.setBurstTime(row.getBurstTime()-1);

            if(row.getBurstTime() == 0){
                for(int i=0; i<rows.size(); i++){
                    if(rows.get(i).getProcessName().equals(row.getProcessName())){
                        rows.remove(i);
                        break;
                    }
                }
            }
        }

        for(int i= this.getTimeline().size()-1; i>0; i--){
            List<Event> timeline = this.getTimeline();
            if(timeline.get(i-1).getProcessName().equals(timeline.get(i).getProcessName())){
                timeline.get(i-1).setFinishTime(timeline.get(i).getFinishTime());
                timeline.remove(i);
            }
        }

        Map map = new HashMap();
        for(Row row:this.getRows()){
            map.clear();
            for(Event e:this.getTimeline()){
                if(e.getProcessName().equals((row.getProcessName()))){
                    if(map.containsKey(e.getProcessName())){
                        int wait = e.getStartTime() - (int) map.get(e.getProcessName());
                        row.setWaitingTime(row.getWaitingTime() + wait);
                    } else{
                        row.setWaitingTime(e.getStartTime() - row.getArrivalTime());
                    }

                    map.put(e.getProcessName(), e.getFinishTime());
                }
            }
            row.setTurnAroundTime(row.getWaitingTime() +row.getBurstTime());
        }
    }
}
