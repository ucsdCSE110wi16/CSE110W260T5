package net.atlassian.teammyrec.writersbloc;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by williamchiu on 3/1/16.
 */
public class UIEspressoTests {

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
}
