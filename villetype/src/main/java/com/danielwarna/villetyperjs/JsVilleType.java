package com.danielwarna.villetyperjs;

import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Danno on 21/03/2015.
 */
@com.vaadin.annotations.JavaScript({ "js/raphael.js","js/villeType.js",
         "js/villeTypeHandler.js" })
public class JsVilleType extends AbstractJavaScriptComponent {
    private String points;
    private String tries;

    public JsVilleType(JSONObject config) throws JSONException {
        //getState().jsonConfig = new JSONObject("{ \"mode\":\"type\", \"timeToMaxSpeed\":\"15\", \"timer\":\"20\", \"questions\": [ {q:\"20+5\", a:\"25\"}, {q:\"90-10\", a:\"80\"}, {q:\"70-5\", a:\"65\"}, {q:\"first\", a:\"first\"}, {q:\"second\", a:\"second\"}, {q:\"third\", a:\"third\"}, {q:\"fourth\", a:\"fourth\"}, {q:\"fifth\", a:\"fifth\"}, {q:\"11\", a:\"11\"}, {q:\"11\", a:\"11\"}, {q:\"11\", a:\"11\"}, {q:\"11\", a:\"11\"}, {q:\"11\", a:\"11\"}, {q:\"11\", a:\"11\"}, {q:\"11\", a:\"11\"}, {q:\"11\", a:\"11\"}, {q:\"11\", a:\"11\"}, {q:\"11\", a:\"11\"} ] }");
        //JSONObject js = new JSONObject();

        getState().testString = "Testing string";
        getState().jsonConfig = config;
        addFunction("sendResults", new JavaScriptFunction() {

            @Override
            public void call(JSONArray results) throws JSONException {
                /*
                JSONArray realArgs = arguments.getJSONArray(0);
                JSONObject args = realArgs.getJSONObject(0);
                getState().points = args.get("points").toString();
                getState().tries = args.get("tries").toString();
                points = getState().points;
                tries = getState().tries;*/

                getState().result = results.getJSONObject(0).getString("sc");
            }
        });
    }
    /**

   /* public JsCards(final ArrayList<VilleType> cards, int pointsPerAnswer) {

        this.points = "0";
        getState().points = "0"; // default
        getState().pointsPerAnswer = pointsPerAnswer;
        getState().questions = cards;

        // Lisätään callback-funktio sendResults, jotta asiakaspää
        // voi lähettää tietoja Vaatimelle
        addFunction("sendResults", new JavaScriptFunction() {

            @Override
            public void call(JSONArray arguments) throws JSONException {
                JSONArray realArgs = arguments.getJSONArray(0);
                JSONObject args = realArgs.getJSONObject(0);
                getState().points = args.get("points").toString();
                getState().tries = args.get("tries").toString();
                points = getState().points;
                tries = getState().tries;
            }
        });
    }*/
    @Override
    public JsVilleTypeState getState() {
        return (JsVilleTypeState) super.getState();
    }
}

