package net.atlassian.teammyrec.writersbloc;


import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.test.InstrumentationTestCase;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by williamchiu on 3/1/16.
 */
public class UIEspressoTests extends InstrumentationTestCase {

    private UiDevice device;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        device = UiDevice.getInstance(getInstrumentation());
        device.pressHome();
        //device.swipe(5, 5, 25, 100, 1);
        //device.wait(Until.hasObject(By.text("Apps")), 3000);
        //UiObject2 appsButton = device.findObject(By.text("Apps"));
        //appsButton.click();
        device.wait(Until.hasObject(By.descContains("WritersBloc")), 3000);
        UiObject2 writersBlocApp = device.findObject(By.descContains("WritersBloc"));
        writersBlocApp.click();
        device.waitForIdle(3000);
    }

    public void testSignInButton() throws Exception {

        device.wait(Until.hasObject(By.text("Login:")), 3000);
        device.wait(Until.hasObject(By.clazz("EditText")), 3000);
        UiObject2 userText = device.findObject(By.res("net.atlassian.teammyrec.writersbloc", "username_edit_activity_login"));
        userText.setText("demouser");

        UiObject2 passText = device.findObject(By.res("net.atlassian.teammyrec.writersbloc", "password_edit_activity_login"));
        passText.setText("demouser");

        device.wait(Until.hasObject(By.text("Submit")), 3000);
        UiObject2 buttonSubmit = device.findObject(By.text("Submit"));
        buttonSubmit.click();

        device.waitForIdle(3000);

        device.wait(Until.hasObject(By.text("Projects")), 3000);
        UiObject2 listView = device.findObject(By.text("Projects"));

        device.waitForIdle(3000);

        assertTrue(listView != null);
    }

    /*
    @Test
    public void signUpButtonPopsViewOver() {
        onView(withId(R.id.button2)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void onTapSpecificProject() {
        onView(withId(R.id.projects_list_view)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void onTapCreateNewProject() {
        onView(withId(R.id.overlayAddProject)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void onTapCreateNewCategory() {
        onView(withId(R.id.overlayAddCategory)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void onTapCreateNewPage() {
        onView(withId(R.id.overlayAddPage)).perform(click()).check(matches(isDisplayed()));
    }
    */
}
