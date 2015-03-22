package fi.utu.ville.exercises.villetype;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.vaadin.ui.*;

import fi.utu.ville.exercises.model.Editor;
import fi.utu.ville.exercises.model.EditorHelper;
import fi.utu.ville.exercises.model.EditorHelper.EditedExerciseGiver;
import fi.utu.ville.standardutils.AFFile;
import fi.utu.ville.standardutils.AbstractFile;
import fi.utu.ville.standardutils.Localizer;
import fi.utu.ville.standardutils.SimpleFileUploader;
import fi.utu.ville.standardutils.StandardUIFactory;
import fi.utu.ville.standardutils.SimpleFileUploader.UploaderListener;
import fi.utu.ville.standardutils.ui.AbstractEditorLayout;
import fi.utu.ville.exercises.model.VilleContent;
import fi.utu.ville.exercises.model.VilleUI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VilleTypeEditor extends VilleContent implements
		Editor<VilleTypeExerciseData> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4600841604409240872L;

	private static final int MAX_FILE_SIZE_KB = 1024;
	
	private static final String MIME_FILTER = "^image/.*$";
	
	private EditorHelper<VilleTypeExerciseData> editorHelper;

	private TextArea questionText;
    private TextField timeToMaxSpeed;
    private TextField timer;
    private List<TextField> qList;

    private final Button addRowButton = new Button("Add row");

    private AbstractFile currImgFile;

	private Localizer localizer;
	
	private AbstractEditorLayout layout;


	public VilleTypeEditor() {
		super(null);
	}

	@Override
	public Layout getView() {
		return this;
	}

	@Override
	public void initialize(VilleUI ui, Localizer localizer, VilleTypeExerciseData oldData,
			EditorHelper<VilleTypeExerciseData> editorHelper) {
		this.init(ui);
		this.localizer = localizer;

		this.editorHelper = editorHelper;

		editorHelper.getTempManager().initialize();

        try {
            doLayout(oldData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

	private VilleTypeExerciseData getCurrentExercise() {
        JSONObject json = new JSONObject();
        try {
            json.put("mode","type");
            json.put("timeToMaxSpeed",timeToMaxSpeed.getValue());
            json.put("timer", timer.getValue());

            JSONArray qArr = new JSONArray(questionText.getValue());

            json.put("questions", qArr);

            System.out.println(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        VilleTypeExerciseData data = new VilleTypeExerciseData(questionText.getValue(), timeToMaxSpeed.getValue());

        data.config = questionText.getValue();

        data.jsonConfig = json;
        data.jsonString = json.toString();

        return data;
        //return new VilleTypeExerciseData(questionText.getValue(), timeToMaxSpeed.getValue());
	}

	@Override
	public boolean isOkToExit() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void doLayout() {
	}
	
	public void doLayout(VilleTypeExerciseData oldData) throws JSONException {

		this.setMargin(false);
		this.setSpacing(false);
		this.setWidth("100%");

		layout = StandardUIFactory.getTwoColumnView();
		addComponent(layout);
		
		layout.setTitle("VilleTypeEditor");
		
		String oldQuestion;
		String oldMax;
        String oldTimer;
        if (oldData != null) {
			oldQuestion = oldData.getQuestion();
            oldMax = oldData.jsonConfig.getString("timeToMaxSpeed");
            oldTimer = oldData.jsonConfig.getString("timer");
		} else {
			oldQuestion = "";
		    oldMax = "";
            oldTimer = "";
        }


		layout.addToLeft(editorHelper.getInfoEditorView());

		layout.addToTop(editorHelper
				.getControlbar(new EditedExerciseGiver<VilleTypeExerciseData>() {

					@Override
					public VilleTypeExerciseData getCurrExerData(
							boolean forSaving) {
						return getCurrentExercise();
					}
				}));


		final VerticalLayout editlayout = new VerticalLayout();

		Label questionTextCapt = new Label(
				localizer.getUIText(VilleTypeUiConstants.QUESTION));
		questionTextCapt.addStyleName(VilleTypeThemeConsts.TITLE_STYLE);
		questionText = new TextArea(null, oldQuestion);
        timeToMaxSpeed = new TextField(oldMax);
        timeToMaxSpeed.setCaption("timetomax");

        timer = new TextField(oldTimer);
        timer.setCaption("timer");

		editlayout.addComponent(questionTextCapt);
		editlayout.addComponent(questionText);
        editlayout.addComponent(timeToMaxSpeed);
        editlayout.addComponent(timer);
        editlayout.addComponent(addRowButton);

        qList = new LinkedList<TextField>();

        TextField tf = new TextField();
        Boolean a = qList.add(tf);
        editlayout.addComponent(tf);

        addRowButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                addRowButton.setCaption("CLicketyclick");
                TextField tf2 = new TextField();
                tf2.setCaption("Question");
                editlayout.addComponent(tf2);
            }
        });


        //CustomLayout custom = new CustomLayout("<h1> Trolodjfhdhjgdjgklolol</h1>");
        //layout.addToRight(custom);
        layout.addToRight(editlayout);


//        System.out.println(custom.isVisible());
	}
	
	@Override
	public String getViewName(){
		return "StubExercise";
	}
}
