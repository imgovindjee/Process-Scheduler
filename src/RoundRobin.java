import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundRobin extends  CPUScheduler{
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
        int Quantum_time = this.getTimeQuantum();
        while (!rows.isEmpty()){
            Row row = rows.get(0);
            int burst_time = row.getBurstTime() < Quantum_time ? row.getBurstTime():Quantum_time;
            this.getTimeline().add(new Event(row.getProcessName(), time, time+burst_time));
            time += burst_time;
            rows.remove(0);

            if(row.getBurstTime() > Quantum_time){
                row.setBurstTime(row.getBurstTime() - Quantum_time);
                for(int i=0; i< rows.size(); i++){
                    if(rows.get(i).getArrivalTime() > time){
                        rows.add(i, row);
                        break;
                    } else if(i == rows.size()-1){
                        rows.add(row);
                        break;
                    }
                }
            }
        }

        Map map = new HashMap();
        for(Row row:this.getRows()){
            map.clear();
            for(Event e : this.getTimeline()){
                if(e.getProcessName().equals(row.getProcessName())){
                    if(map.containsKey(e.getProcessName())){
                        int wait = e.getStartTime() - (int) map.get(e.getProcessName());
                        row.setWaitingTime(row.getWaitingTime() + wait);
                    } else{
                        row.setWaitingTime(e.getStartTime() - row.getArrivalTime());
                    }
                    map.put(e.getProcessName(), e.getFinishTime());
                }
            }
            row.setTurnAroundTime(row.getWaitingTime() + row.getBurstTime());
        }
    }
}
