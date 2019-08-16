package com.blamkin.jumpcloud.assignment.util;

import com.blamkin.jumpcloud.assignment.entities.Action;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

/**
 * Take a string that represents an Action, and parse it
 *
 */
public class JsonUtils {

    // what json

    /**
     * take a string, make an action, or throw an exception
     *
     * @param jsonAction
     * @return
     * @throws IllegalArgumentException
     */
    public static Action parseActionString(String jsonAction) throws IllegalArgumentException {

        try {

            // are we good?
            validateInputActionString(jsonAction);

            // convert and return
            ObjectMapper om = new ObjectMapper();
            return  om.readValue(jsonAction, Action.class);

        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid JsON action string: " + jsonAction);
        }

    }

    // validate according to our rules
    // separated from parseActionString for testing
    public static final void validateInputActionString(String actionString) throws IllegalArgumentException {

        if (StringUtils.isEmpty(actionString)) {
            throw new IllegalArgumentException("Cannot parse empty action");
        }

        // Use of this class is just to be more helpful to input strings
        try {

            JSONObject obj = new JSONObject(actionString);

            // see if it's an action
            String actionName = obj.getString(Action.FIELD_ACTION);
            if (StringUtils.isEmpty(actionString)) {
                throw new IllegalArgumentException("Input JsON must contain field: " + Action.FIELD_ACTION);
            }

            // see if the time is present
            Integer actionTime = obj.getInt(Action.FIELD_TIME);
            if (actionTime == null) {
                throw new IllegalArgumentException("Input JsON must contain field: " + Action.FIELD_TIME);
            }

            // see if time is a natural number
            if (actionTime < 0) {
                throw new IllegalArgumentException("Input JsON field: " + Action.FIELD_TIME + " must be >=0");
            }
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid Action Json string");
        }
    }

}
