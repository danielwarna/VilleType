package fi.utu.ville.exercises.villetype;

import com.vaadin.server.Resource;

import fi.utu.ville.exercises.helpers.GsonPersistenceHandler;
import fi.utu.ville.exercises.model.ExerciseTypeDescriptor;
import fi.utu.ville.exercises.model.PersistenceHandler;
import fi.utu.ville.exercises.model.SubmissionStatisticsGiver;
import fi.utu.ville.exercises.model.SubmissionVisualizer;
import fi.utu.ville.standardutils.Localizer;
import fi.utu.ville.standardutils.StandardIcon;
import fi.utu.ville.standardutils.StandardIcon.IconVariant;

public class VilleTypeDescriptor implements
		ExerciseTypeDescriptor<VilleTypeExerciseData, VilleTypeSubmissionInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5743225101617556960L;

	public static final VilleTypeDescriptor INSTANCE = new VilleTypeDescriptor();

	private VilleTypeDescriptor() {

	}

	@Override
	public PersistenceHandler<VilleTypeExerciseData, VilleTypeSubmissionInfo> newExerciseXML() {
		// You can also implement your own PersistenceHandler if you want (see JavaDoc for more info)
		return new GsonPersistenceHandler<VilleTypeExerciseData, VilleTypeSubmissionInfo>(
				getTypeDataClass(), getSubDataClass());
	}

	@Override
	public VilleTypeExecutor newExerciseExecutor() {
		return new VilleTypeExecutor();
	}

	@Override
	public VilleTypeEditor newExerciseEditor() {
		return new VilleTypeEditor();
	}

	@Override
	public Class<VilleTypeExerciseData> getTypeDataClass() {
		return VilleTypeExerciseData.class;
	}

	@Override
	public Class<VilleTypeSubmissionInfo> getSubDataClass() {
		return VilleTypeSubmissionInfo.class;
	}

	@Override
	public SubmissionStatisticsGiver<VilleTypeExerciseData, VilleTypeSubmissionInfo> newStatisticsGiver() {
		return new VilleTypeStatisticsGiver();
	}

	@Override
	public SubmissionVisualizer<VilleTypeExerciseData, VilleTypeSubmissionInfo> newSubmissionVisualizer() {
		return new VilleTypeSubmissionViewer();
	}

	@Override
	public String getTypeName(Localizer localizer) {
		return localizer.getUIText(VilleTypeUiConstants.NAME);
	}

	@Override
	public String getTypeDescription(Localizer localizer) {
		return localizer.getUIText(VilleTypeUiConstants.DESC);
	}

	@Override
	public Resource getSmallTypeIcon() {
		return VilleTypeIcon.SMALL_TYPE_ICON.getIcon();
	}

	@Override
	public Resource getMediumTypeIcon() {
		return VilleTypeIcon.SMALL_TYPE_ICON.getIcon();
	}

	@Override
	public Resource getLargeTypeIcon() {
		return VilleTypeIcon.SMALL_TYPE_ICON.getIcon();
	}
	
	@Override
	public String getHTMLIcon(){
		return StandardIcon.RawIcon.dribbble.variant(IconVariant.ORANGE);
	}
	
	@Override
	public boolean isManuallyGraded() {
		return false;
	}

}