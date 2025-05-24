package ssproject.labels;

import snitch.annotations.labels.LabelParameter;
import snitch.annotations.labels.SecurityLabel;
import snitch.annotations.labels.FlowsTo;
import snitch.labels.SimpleLabel;

@SecurityLabel
public class Employee extends SimpleLabel {

    @LabelParameter
    private final int employeeId;

    public Employee() {
        employeeId = -1;
    }

    public Employee(int employeeId) {
        this.employeeId = employeeId;
    }
}