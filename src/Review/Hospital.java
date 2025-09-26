package Review;

public class Hospital {
    private Object[] records;
    private int next = 0;

    public Hospital(int size) {
        records = new Object[size];
    }

    public void add(Object obj) {
        if (next < records.length) {
            records[next] = obj;
            next++;
        } else {
            System.out.println("Hospital records full, cannot add" + obj);
        }
    }

    //inner class 
    private class HospitalSelector implements Selector {
        private int i = 0;

        public boolean end() {
            return i == next;
        }

        public Object current() {
            return records[i];
        }

        public void next() {
            if (i < next) i++;
        }
    }

    public Selector getSelector() {
        return new HospitalSelector();
    }


}

