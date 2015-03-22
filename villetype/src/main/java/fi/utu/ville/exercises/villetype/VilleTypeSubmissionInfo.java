package fi.utu.ville.exercises.villetype;

import fi.utu.ville.exercises.model.SubmissionInfo;

public class VilleTypeSubmissionInfo implements SubmissionInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8702870727095225372L;

	private final String answer;

	public VilleTypeSubmissionInfo(String answer) {
		this.answer = answer;
	}

	public String getAnswer() {
		return answer;
	}

}
