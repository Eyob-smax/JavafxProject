package eyob.ai_project.controller.ComboItem;

public class ComboBox {
    private final String id;
    private final String label;

    public ComboBox(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}

