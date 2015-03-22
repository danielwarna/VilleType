package fi.utu.ville.exercises.villetype;

import fi.utu.ville.exercises.model.ExerciseData;
import fi.utu.ville.standardutils.AbstractFile;
import org.json.JSONObject;

public class VilleTypeExerciseData implements ExerciseData {

    /**
     *
     */
    private static final long serialVersionUID = -716445297446246493L;

    private final String question;
    public String timeToMaxSpeed;
    public String config;
    public JSONObject jsonConfig;
    public String jsonString;

    public final String te = "This is a test string";

    public VilleTypeExerciseData(String question, String t) {
        this.question = question;
        this.timeToMaxSpeed = t;
    }

    public String getQuestion() {
        return question;
    }

    public String getTe() {
        return te;
    }
}

