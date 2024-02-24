import java.util.List;

public class Main {
    public static void fcfs(){
        CPUScheduler fcfs = new FirstComeFirstServe();
        fcfs.add(new Row("p1", 0, 5));
        fcfs.add(new Row("p2", 2, 4));
        fcfs.add(new Row("p3", 4, 3));
        fcfs.add(new Row("p4", 6, 6));
        fcfs.add(new Row("p5", 1, 5));
        fcfs.process();
        display(fcfs);
    }

    public static void srtf(){
        CPUScheduler srtf = new ShortestRemainingTimeFirst();
        srtf.add(new Row("p1", 0, 5));
        srtf.add(new Row("p2", 2, 4));
        srtf.add(new Row("p3", 4, 3));
        srtf.add(new Row("p4", 6, 6));
        srtf.add(new Row("p5", 1, 5));
        srtf.process();
        display(srtf);
    }

    public static void sjfs(){
        CPUScheduler sjfs = new ShortestJobFirstServe();
        sjfs.add(new Row("p1", 0, 5));
        sjfs.add(new Row("p2", 2, 4));
        sjfs.add(new Row("p3", 4, 3));
        sjfs.add(new Row("p4", 6, 6));
        sjfs.add(new Row("p5", 1, 5));
        sjfs.process();
        display(sjfs);
    }

    public static void psn(){
        CPUScheduler psn = new PriorityNonPreemptiveScheduling();
        psn.add(new Row("p1", 0, 5));
        psn.add(new Row("p2", 2, 4));
        psn.add(new Row("p3", 4, 3));
        psn.add(new Row("p4", 6, 6));
        psn.add(new Row("p5", 1, 5));
        psn.process();
        display(psn);
    }

    public static void psp(){
        CPUScheduler psp = new PriorityPreemptiveScheduling();
        psp.add(new Row("p1", 0, 5));
        psp.add(new Row("p2", 2, 4));
        psp.add(new Row("p3", 4, 3));
        psp.add(new Row("p4", 6, 6));
        psp.add(new Row("p5", 1, 5));
        psp.process();
        display(psp);
    }

    public static void rr(){
        CPUScheduler rr = new RoundRobin();
        rr.add(new Row("p1", 0, 5));
        rr.add(new Row("p2", 2, 4));
        rr.add(new Row("p3", 4, 3));
        rr.add(new Row("p4", 6, 6));
        rr.add(new Row("p5", 1, 5));
        rr.process();
        display(rr);
    }


    public static void display(CPUScheduler cs){
        System.out.println("Process \t AT \t BT \t WT \t TAT");
        for(Row row:cs.getRows()){
            System.out.println(row.getProcessName() + '\t' +row.getArrivalTime() + '\t' +row.getBurstTime() + '\t' +row.getWaitingTime() + '\t' + row.getTurnAroundTime());
        }
        System.out.println();
        for(int i=0; i<cs.getTimeline().size(); i++){
            List<Event> timeline = cs.getTimeline();
            System.out.print(timeline.get(i).getStartTime() + '(' + timeline.get(i).getProcessName()+')');
            if(i == cs.getTimeline().size()-1){
                System.out.print(timeline.get(i).getFinishTime());
            }
        }
        System.out.println("\n\n Average Waiting TIme (AWT) : "+ cs.getAverageWaitingTime() +
                        "\n Average Turn Around TIme (ATAT) : " + cs.getAverageTurnAroundTime());
    }


//    Drive Code
    public static void main(String[] args) {
        System.out.println("..................First Come First Serve (FCFS) ................");
        fcfs();

        System.out.println("..................Shortest Job First Serve (SJFS) ................");
        sjfs();

        System.out.println("..................Shortest Remaining Time First (SRTF) ................");
        srtf();

        System.out.println("..................Priority Scheduling Non-Preemptive (PSN) ................");
        psn();

        System.out.println("..................Priority Scheduling Preemptive (PSP) ................");
        psp();

        System.out.println("..................Round Robin (RR) ................");
        rr();
    }
}