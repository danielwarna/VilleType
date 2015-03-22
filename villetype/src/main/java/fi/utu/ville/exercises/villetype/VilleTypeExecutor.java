package fi.utu.ville.exercises.villetype;

import com.danielwarna.villetyperjs.JsVilleType;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import fi.utu.ville.exercises.helpers.ExerciseExecutionHelper;
import fi.utu.ville.exercises.model.ExecutionSettings;
import fi.utu.ville.exercises.model.ExecutionState;
import fi.utu.ville.exercises.model.ExecutionStateChangeListener;
import fi.utu.ville.exercises.model.Executor;
import fi.utu.ville.exercises.model.ExerciseException;
import fi.utu.ville.exercises.model.SubmissionListener;
import fi.utu.ville.exercises.model.SubmissionType;
import fi.utu.ville.standardutils.Localizer;
import fi.utu.ville.standardutils.TempFilesManager;
import org.json.JSONException;

public class VilleTypeExecutor extends VerticalLayout implements
		Executor<VilleTypeExerciseData, VilleTypeSubmissionInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2682119786422750060L;

	private final ExerciseExecutionHelper<VilleTypeSubmissionInfo> execHelper =

	new ExerciseExecutionHelper<VilleTypeSubmissionInfo>();

	private final TextField answerField = new TextField();

    private JsVilleType villeType;
    private VilleTypeExerciseData data;

	public VilleTypeExecutor() {

	}

	@Override
	public void initialize(Localizer localizer,
			VilleTypeExerciseData exerciseData, VilleTypeSubmissionInfo oldSubm,
			TempFilesManager materials, ExecutionSettings fbSettings)
			throws ExerciseException {
		answerField.setCaption(localizer.getUIText(VilleTypeUiConstants.ANSWER));
		doLayout(exerciseData, oldSubm != null ? oldSubm.getAnswer() : "");
	}

	private void doLayout(VilleTypeExerciseData exerciseData, String oldAnswer) {

		//this.addComponent(new Label(exerciseData.getQuestion()));
        //this.addComponent(new Label("This is some random text " + exerciseData.getTe()));

        //this.addComponent(new Label("CONFIG: " + exerciseData.jsonString));
		//answerField.setValue(oldAnswer);
		//this.addComponent(answerField);

        Label htmlLabel = new Label("<div id=\"canvas\"> </div>", ContentMode.HTML);
        addComponent(htmlLabel);

        try {
            //villeType = new JsVilleType(exerciseData.jsonConfig);
            villeType = new JsVilleType(exerciseData.jsonConfig);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        addComponent(villeType);
        setComponentAlignment(villeType, Alignment.MIDDLE_CENTER);

    }

	@Override
	public void registerSubmitListener(
			SubmissionListener<VilleTypeSubmissionInfo> submitListener) {
		execHelper.registerSubmitListener(submitListener);
	}

	@Override
	public Layout getView() {
		return this;
	}

	@Override
	public void shutdown() {
		// nothing to do here
	}

	@Override
	public void askReset() {
		// nothing to do here
	}

	@Override
	public ExecutionState getCurrentExecutionState() {
		return execHelper.getState();
	}

	@Override
	public void askSubmit(SubmissionType submType) {
		double corr = 1.0;

		//String answer = answerField.getValue();
        String answer = villeType.getState().result;
        String r = answer;
		execHelper.informOnlySubmit(Double.parseDouble(r)*0.000000, new VilleTypeSubmissionInfo(answer),
				submType, null);

	}

	@Override
	public void registerExecutionStateChangeListener(
			ExecutionStateChangeListener execStateListener) {
		execHelper.registerExerciseExecutionStateListener(execStateListener);

	}

}
