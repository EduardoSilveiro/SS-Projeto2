package ssproject.labels;

import snitch.annotations.labels.LabelParameter;
import snitch.labels.SimpleLabel;
import snitch.annotations.labels.SecurityLabel;


@SecurityLabel
public class Auditor extends SimpleLabel {
	
	@LabelParameter
    private final int auditorId;
	
    public Auditor(){
		auditorId = -1;
	}
	
	public Auditor(int auditorId){

        this.auditorId = auditorId;
	}
}