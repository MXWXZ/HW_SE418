import java.util.ArrayDeque;
import java.util.Deque;

class Request {
    private String req;
    private long ts;

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}

public class Container {
    private static int wall;
    private static int timeout;
    private static Deque<Request> container = new ArrayDeque<>();

    public static int getWall() {
        return wall;
    }

    public static void setWall(int wall) {
        Container.wall = wall;
    }

    public static int getTimeout() {
        return timeout;
    }

    public static void setTimeout(int timeout) {
        Container.timeout = timeout;
    }

    public static synchronized void push(String req) {
        Request obj = new Request();
        long ts = System.currentTimeMillis();
        obj.setReq(req);
        obj.setTs(ts);
        container.addLast(obj);
        System.out.println("Add to container: " + req + "," + ts + " Size: " + container.size());
    }

    public static synchronized String pop() {
        long ts = System.currentTimeMillis();
        while (!container.isEmpty() && ts - container.peekFirst().getTs() >= timeout) {
            Request ret = container.pop();
            System.out.println("Pop timeout: " + ret.getReq());
        }
        if (container.isEmpty()) {
            System.out.println("Container is null");
            return "null";
        }

        String ret;
        if (container.size() > wall) {
            ret = container.pollLast().getReq();
            System.out.println("LIFO consume " + ret + " Size: " + container.size());
        } else {
            ret = container.pollFirst().getReq();
            System.out.println("FIFO consume " + ret + " Size: " + container.size());
        }
        return ret;
    }
}
