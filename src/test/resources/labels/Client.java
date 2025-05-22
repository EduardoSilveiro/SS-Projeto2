package ssproject.labels;

import snitch.annotations.labels.FlowsTo;
import snitch.annotations.labels.DependentSecurityLabel;
import snitch.annotations.labels.LabelParameter;
import snitch.labels.DependentLabel;

@FlowsTo(Employee.class)
@DependentSecurityLabel
public class Client extends DependentLabel<Client> {
	
	@LabelParameter
    private final int clientId;
	
    public Client(){
		clientId = -1;
	}
	
	public Client(int clientId){
		this.clientId = clientId;
	}
	
	@Override
    protected Client getSelf() {
        return this;
    }

    @Override
    protected boolean isGreaterThan(Client other) {
         return this.isTop() && !other.isTop() || !this.isBottom() && other.isBottom();
    }

    @Override
    protected boolean isEqualTo(Client other) {
        return this.isTop() == other.isTop() && this.isBottom() == other.isBottom() && this.clientId == other.clientId;
    }

    @Override
    protected boolean isLowerThan(Client other) {
        return this.isBottom() && !other.isBottom() || !this.isTop() && other.isTop();
    }
}