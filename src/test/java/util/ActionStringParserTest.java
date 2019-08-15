package util;

import entities.Action;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.Matchers.containsString;

@RunWith(JUnit4.class)
public class ActionStringParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // test that null strings don't work
    @Test
    public void parseActionString_whenPassedNullArgument_throwsIllegalArgument() throws Exception {

        // that looks bad
        String testCase = null;

        // better get one of these
        thrown.expect(IllegalArgumentException.class);
        // optional check for specific message
        thrown.expectMessage(containsString("empty"));

        // run it
        ActionStringParser.parseActionString(testCase);

    }

    // test that empty strings don't work
    @Test
    public void parseActionString_whenPassedEmptyArgument_throwsIllegalArgument() throws Exception {

        // that looks empty
        String testCase = "";

        // better get one of these
        thrown.expect(IllegalArgumentException.class);

        // run it
        ActionStringParser.parseActionString(testCase);

    }

    // test that crap strings don't work
    @Test
    public void parseActionString_whenPassedCrapJson_throwsIllegalArgument() throws Exception {

        // that doesn't even look like JsON yo.
        String testCase = "fizz\"buzz}:";

        // better get one of these
        thrown.expect(IllegalArgumentException.class);

        // run it
        ActionStringParser.parseActionString(testCase);

    }

    // test that plain old JsON strings don't work
    @Test
    public void parseActionString_whenPassedEmptyJson_throwsIllegalArgument() throws Exception {

        // hey nice Json. How 'bout some fields?
        String testCase = "{}";

        // better get one of these
        thrown.expect(IllegalArgumentException.class);

        // run it
        ActionStringParser.parseActionString(testCase);

    }

    // test good json missing the action and time fails
    @Test
    public void parseActionString_whenPassedNonActionJson_throwsIllegalArgument() throws Exception {

        // well, that's Json alright. Not an action, but good try.
        String testCase = "{ \"field\":value }";

        // better get one of these
        thrown.expect(IllegalArgumentException.class);

        // run it
        ActionStringParser.parseActionString(testCase);

    }

    // test good json missing JUST the action fails
    @Test
    public void parseActionString_whenPassedMissingNameJson_throwsIllegalArgument() throws Exception {

        // Json with just a time value, action is missing
        String testCase = "{ \"" + Action.FIELD_TIME +"\":123 }";

        // better get one of these
        thrown.expect(IllegalArgumentException.class);

        // run it
        ActionStringParser.parseActionString(testCase);

    }

    // test good json missing JUST the time fails
    @Test
    public void parseActionString_whenPassedMissingTimeJson_throwsIllegalArgument() throws Exception {

        // JsON with just a name value
        String testCase = "{ \"" + Action.FIELD_NAME +"\":\"value\" }";

        // better get one of these
        thrown.expect(IllegalArgumentException.class);

        // run it
        ActionStringParser.parseActionString(testCase);

    }

    // test good Action JsON but empty value for name, fails
    @Test
    public void parseActionString_whenPassedValidJsonMissingNameValue_throwsIllegalArgument() throws Exception {

        // make good Json with the known fields
        // but don't pass any values
        String testCase = "{ " +
                    "\"" + Action.FIELD_NAME +"\":\"\"," +
                    "\"" + Action.FIELD_TIME +"\":\"\" " +
                "}";

        // better get one of these
        thrown.expect(IllegalArgumentException.class);

        // run it
        ActionStringParser.parseActionString(testCase);

    }

    // test good Action JsON but empty value for time, fails
    @Test
    public void parseActionString_whenPassedValidJsonMissingTimeValue_throwsIllegalArgument() throws Exception {

        // make good Json
        // but pass no value for the time
        String testCase = "{ " +
                "\"" + Action.FIELD_NAME +"\":\"jump\"," +
                "\"" + Action.FIELD_TIME +"\":\"\" " +
                "}";

        // better get one of these
        thrown.expect(IllegalArgumentException.class);

        // run it
        ActionStringParser.parseActionString(testCase);

    }

    // test good Action JsON but empty value for time, fails
    @Test
    public void parseActionString_whenPassedValidJsonBadTimeValue_throwsIllegalArgument() throws Exception {

        // make good JsON
        // but pass a negative time,
        // which is disallowed
        String testCase = "{ " +
                "\"" + Action.FIELD_NAME +"\":\"jump\"," +
                "\"" + Action.FIELD_TIME +"\":-123 " +
                "}";

        // better get one of these
        thrown.expect(IllegalArgumentException.class);

        // run it
        ActionStringParser.parseActionString(testCase);

    }

    // test good Action JsON with a zero time, which is allowed
    @Test
    public void parseActionString_whenPassedActionWithZeroTime_works() throws Exception {

        // Good Json with zero time
        String testCase = "{ " +
                "\"" + Action.FIELD_NAME +"\":\"jump\"," +
                "\"" + Action.FIELD_TIME +"\":0" +
                "}";

        // run it - no exception expected
        ActionStringParser.parseActionString(testCase);

    }

    // test good Action JsON with a reasonable time, which is allowed
    @Test
    public void parseActionString_whenPassedActionWithGoodTime_works() throws Exception {

        // Good JsON with good time
        String testCase = "{ " +
                "\"" + Action.FIELD_NAME +"\":\"jump\"," +
                "\"" + Action.FIELD_TIME +"\":123" +
                "}";

        // run it - no exception expected
        ActionStringParser.parseActionString(testCase);

    }

    // test good Action JsON with a time too big for an integer, fails
    @Test
    public void parseActionString_whenPassedActionWithBigTime_works() throws Exception {

        // Good JsON with very large time integer
        String testCase = "{ " +
                "\"" + Action.FIELD_NAME +"\":\"jump\"," +
                "\"" + Action.FIELD_TIME +"\":" + Integer.MAX_VALUE +
                "}";

        // run it - no exception expected
        ActionStringParser.parseActionString(testCase);

    }

    // test good Action JsON with a time too big for an integer, fails
    @Test
    public void parseActionString_whenPassedActionWithTooBigTime_throwsException() throws Exception {

        // Good JsON with out-of-range integer
        String testCase = "{ " +
                "\"" + Action.FIELD_NAME +"\":\"jump\"," +
                "\"" + Action.FIELD_TIME +"\":" + Integer.MAX_VALUE + "0" +
                "}";

        // better get one of these
        thrown.expect(IllegalArgumentException.class);

        // run it
        ActionStringParser.parseActionString(testCase);

    }


}
