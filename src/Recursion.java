package src;

public class Recursion {
     private String type;      // "Hospital", "Department", "Room", "Bed", "Patient"
    private String name;
    private int patientId;
    private List<Node> children = new ArrayList<>();

    public Node(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public Node(String type, String name, int patientId) { // dành cho bệnh nhân
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

    // Đệ quy tìm bệnh nhân
    public Node findPatientById(int id) {
        if (this.type.equals("Patient") && this.patientId == id) {
            return this;
        }
        for (Node child : children) {
            Node found = child.findPatientById(id);
            if (found != null) return found;
        }
        return null;
    }
}
