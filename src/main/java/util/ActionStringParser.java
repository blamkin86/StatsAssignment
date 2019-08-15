package util;

import entities.Action;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

/**
 * Take a string that represents an Action, and parse it
 *
 */
public class ActionStringParser {

    // what json

    /**
     * take a string, make an action
     *
     * @param jsonAction
     * @return
     * @throws Exception
     */
    public static Action parseActionString(String jsonAction) throws Exception {

        if (StringUtils.isEmpty(jsonAction)) {
            throw new IllegalArgumentException("Cannot parse empty action");
        }

        try {

            // get whatever that is
            JSONObject obj = new JSONObject(jsonAction);

            // see if it's an action
            String actionName = obj.getString(Action.FIELD_NAME);
            if (StringUtils.isEmpty(jsonAction)) {
                throw new IllegalArgumentException("Input JsON must contain field: " + Action.FIELD_NAME);
            }

            // see if the time is present
            Integer actionTime = obj.getInt(Action.FIELD_TIME);
            if (actionTime==null) {
                throw new IllegalArgumentException("Input JsON must contain field: " + Action.FIELD_TIME);
            }

            // see if time is a natural number
            if (actionTime<0) {
                throw new IllegalArgumentException("Input JsON field: " + Action.FIELD_TIME + " must be >=0");
            }

            // sweet
            return new Action(actionName, actionTime);

        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid JsON action string: " + jsonAction);
        }

    }
}
