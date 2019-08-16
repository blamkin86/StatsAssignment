package com.blamkin.jumpcloud.assignment.util;

import com.blamkin.jumpcloud.assignment.entities.TimeTotalCount;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.blamkin.jumpcloud.assignment.entities.Action;
import com.blamkin.jumpcloud.assignment.entities.ActionAverage;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

/**
 * Take a string that represents an Action, and parse it
 *
 */
public class JsonUtils {

    // what json

    /**
     * take a string, make an action
     *
     * @param jsonAction
     * @return
     * @throws Exception
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

    // Given a key and a TimeTotalCount
    // concoct the appropriate JsON return object
    // invalid data -> return empty object rather than throw exception
    public static String avgAction(String key, TimeTotalCount timeTotalCount) {

        // valid data only
        if (!StringUtils.isEmpty(key) && timeTotalCount !=null) {

            // make the return object
            // and calculate the average within
            ActionAverage average = new ActionAverage(key, timeTotalCount.getCount(), timeTotalCount.getTotal());

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writeValueAsString(average);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Unable to calculate average for " + timeTotalCount);
            }
        }

        // default
        return "{}";
    }

    // validate according to our rules
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
