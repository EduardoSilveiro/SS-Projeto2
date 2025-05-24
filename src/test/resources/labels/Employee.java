package ssproject.labels;

import snitch.annotations.labels.DependentSecurityLabel;
import snitch.annotations.labels.LabelParameter;
import snitch.annotations.labels.SecurityLabel;
import snitch.annotations.labels.FlowsTo;
import snitch.labels.DependentLabel;
import snitch.labels.SimpleLabel;

@FlowsTo(Auditor.class)
@DependentSecurityLabel
public class Employee extends DependentLabel<Employee> {

    @LabelParameter
    private final int employeeId;

    public Employee() {
        employeeId = -1;
    }

    public Employee(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    protected Employee getSelf() {
        return this;
    }

    @Override
    protected boolean isGreaterThan(Employee other) {
        return this.isTop() && !other.isTop() || !this.isBottom() && other.isBottom();
    }

    @Override
    protected boolean isEqualTo(Employee other) {
        return this.isTop() == other.isTop() && this.isBottom() == other.isBottom() && this.employeeId == other.employeeId;
    }

    @Override
    protected boolean isLowerThan(Employee other) {
        return this.isBottom() && !other.isBottom() || !this.isTop() && other.isTop();
    }

}