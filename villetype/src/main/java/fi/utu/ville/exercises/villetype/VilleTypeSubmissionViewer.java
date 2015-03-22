package fi.utu.ville.exercises.villetype;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import fi.utu.ville.exercises.model.ExerciseException;
import fi.utu.ville.exercises.model.SubmissionVisualizer;
import fi.utu.ville.standardutils.Localizer;
import fi.utu.ville.standardutils.TempFilesManager;

public class VilleTypeSubmissionViewer extends VerticalLayout implements
		SubmissionVisualizer<VilleTypeExerciseData, VilleTypeSubmissionInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6260031633710031462L;
	private VilleTypeExerciseData exer;
	private VilleTypeSubmissionInfo submInfo;

	private Localizer localizer;
	
	public VilleTypeSubmissionViewer() {
	}

	@Override
	public void initialize(VilleTypeExerciseData exercise,
			VilleTypeSubmissionInfo dataObject, Localizer localizer,
			TempFilesManager tempManager) throws ExerciseException {
		this.localizer = localizer;
		this.exer = exercise;
		this.submInfo = dataObject;
		doLayout();
	}

	private void doLayout() {
		this.addComponent(new Label(localizer.getUIText(VilleTypeUiConstants.QUESTION) + 
				": " + exer.getQuestion()));
		Label answ = new Label(localizer.getUIText(VilleTypeUiConstants.ANSWER) + 
				": "  + submInfo.getAnswer());
		answ.addStyleName(VilleTypeThemeConsts.ANSWER_STYLE);
		this.addComponent(answ);
	}

	@Override
	public Component getView() {
		return this;
	}

	@Override
	public String exportSubmissionDataAsText() {
		return localizer.getUIText(VilleTypeUiConstants.QUESTION, "\n", 
				exer.getQuestion(), submInfo.getAnswer());
		
	}

}
