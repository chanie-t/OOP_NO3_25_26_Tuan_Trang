package src;
import java.util.ArrayList;
import java.util.List;

public class Node {
    private String type;
    private String name;
    private int patientId;
    private List<Node> children = new ArrayList<>();

    public Node(String type, String name) {
        this.type = type;
        this.name = name;
        this.patientId = -1; // Không phải bệnh nhân
    }

    public Node(String type, String name, int patientId) {
        this.type = type;
        this.name = name;
        this.patientId = patientId;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public List<Node> getChildren() {
        return children;
    }

    public String getType() { return type; }
    public String getName() { return name; }
    public int getPatientId() { return patientId; }

    public Node findPatientById(int id) {
        if ("Patient".equals(this.type) && this.patientId == id) {
            return this;
        }
        for (Node child : children) {
            Node found = child.findPatientById(id);
            if (found != null) return found;
        }
        return null;
    }
}
