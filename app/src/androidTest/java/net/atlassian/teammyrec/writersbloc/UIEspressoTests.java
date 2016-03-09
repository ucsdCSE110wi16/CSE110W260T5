package net.atlassian.teammyrec.writersbloc;


import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
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
        device.wait(Until.hasObject(By.descContains("WritersBloc")), 3000);
        UiObject2 writersBlocApp = device.findObject(By.descContains("WritersBloc"));
        writersBlocApp.click();
        device.waitForIdle(3000);


    }

    public void test1SignInButton() throws Exception {

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

    public void test2CreateProject() throws Exception {
        device.wait(Until.hasObject(By.text("Projects")), 3000);
        device.wait(Until.hasObject(By.desc("Add Category")), 3000);
        UiObject2 addButton = device.findObject(By.desc("Add Category"));
        addButton.click();

        device.wait(Until.hasObject(By.text("Name:")), 3000);
        UiObject2 nameField = device.findObject(By.text("Name:"));
        nameField.setText("Test Project");
        device.wait(Until.hasObject(By.text("Add")), 3000);
        UiObject2 saveButt = device.findObject(By.text("Add"));
        saveButt.click();

        device.waitForIdle(3000);

        device.wait(Until.hasObject(By.text("Test Project")), 3000);
        UiObject2 testProj = device.findObject(By.text("Test Project"));

        assertTrue(testProj != null);
    }

    public void test3AddCategory() throws Exception {
        device.wait(Until.hasObject(By.text("Test Project")), 3000);
        UiObject2 testProj = device.findObject(By.text("Test Project"));
        testProj.click();

        device.wait(Until.hasObject(By.text("Categories")), 3000);
        device.wait(Until.hasObject(By.desc("Add Category")), 3000);
        UiObject2 addCategory = device.findObject(By.desc("Add Category"));
        addCategory.click();
        device.wait(Until.hasObject(By.text("Name:")), 3000);
        UiObject2 nameField = device.findObject(By.text("Name:"));
        nameField.setText("Test Category");

        UiObject2 addButt = device.findObject(By.text("Add"));
        addButt.click();

        device.wait(Until.hasObject(By.text("Test Category")), 3000);
        UiObject2 testCat = device.findObject(By.text("Test Category"));

        assertTrue(testCat != null);
    }

    public void test4AddPage() throws Exception {
        device.wait(Until.hasObject(By.text("Test Category")), 3000);
        UiObject2 testCat = device.findObject(By.text("Test Category"));
        testCat.click();

        device.wait(Until.hasObject(By.text("Pages")), 3000);
        UiObject2 newPage = device.findObject(By.desc("Add Category"));
        newPage.click();

        device.wait(Until.hasObject(By.text("Name:")), 3000);
        UiObject2 pageField = device.findObject(By.text("Name:"));
        pageField.setText("Test Page");
        UiObject2 addPage = device.findObject(By.text("Add"));
        addPage.click();

        device.wait(Until.hasObject(By.text("Test Page")), 3000);
        UiObject2 testPage = device.findObject(By.text("Test Page"));

        if (testPage != null) {
            testPage.click();
            device.wait(Until.hasObject(By.res("net.atlassian.teammyrec.writersbloc", "editText")), 3000);
            UiObject2 pageText = device.findObject(By.res("net.atlassian.teammyrec.writersbloc", "editText"));
            pageText.setText("This is a Test Page");
        }

        assertTrue(testPage != null);
    }


    public void test5ReferenceLink() throws Exception {

        device.pressBack();
        device.wait(Until.hasObject(By.text("Character")), 3000);
        UiObject2 charCat = device.findObject(By.text("Character"));
        charCat.click();

        device.wait(Until.hasObject(By.text("Pages")), 3000);
        UiObject2 newPage = device.findObject(By.desc("Add Category"));
        newPage.click();

        device.wait(Until.hasObject(By.text("Name:")), 3000);
        UiObject2 pageField = device.findObject(By.text("Name:"));
        pageField.setText("Test Character");
        UiObject2 addPage = device.findObject(By.text("Add"));
        addPage.click();

        device.wait(Until.hasObject(By.text("Test Character")), 3000);
        UiObject2 testPage = device.findObject(By.text("Test Character"));
        testPage.click();

        device.wait(Until.hasObject(By.res("net.atlassian.teammyrec.writersbloc", "editText")), 3000);
        UiObject2 pageText = device.findObject(By.res("net.atlassian.teammyrec.writersbloc", "editText"));
        pageText.setText("This is a test for reference linking: Test Page");
        device.pressBack();
        device.pressBack();

        device.wait(Until.hasObject(By.text("Test Character")), 3000);
        testPage = device.findObject(By.text("Test Character"));
        testPage.click();

        device.wait(Until.hasObject(By.res("net.atlassian.teammyrec.writersbloc", "editText")), 3000);
        device.click(300, 95);

        device.wait(Until.hasObject(By.text("Test Page")), 3000);
        UiObject2 testpg = device.findObject(By.text("Test Page"));

        assertTrue(testpg != null);
    }

    public void test6DeletingProject() {
        device.pressBack();
        device.pressBack();

        device.wait(Until.hasObject(By.text("Projects")), 3000);
        UiObject2 proj = device.findObject(By.text("Test Project"));
        proj.swipe(Direction.LEFT, .5f);

        device.wait(Until.hasObject(By.res("net.atlassian.teammyrec.writersbloc", "trash_button")), 3000);
        UiObject2 deleteButt = device.findObject(By.res("net.atlassian.teammyrec.writersbloc", "trash_button"));
        deleteButt.click();

        device.waitForIdle(3000);

        UiObject2 checkproj = device.findObject(By.text("Test Project"));

        assertTrue(checkproj == null);
    }

}
