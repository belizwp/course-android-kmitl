package com.project.demorecord;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * โดยไม่กรอก Name และ Age กดปุ่ม ADDED
     * จะต้องเจอ Please Enter user info
     */
    @Test
    public void case01() {
        onView(withId(R.id.editTExtName)).perform(clearText());
        onView(withId(R.id.editTextAge)).perform(clearText());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"))).perform(click());
        onView(withText("Please Enter user info")).check(matches(isDisplayed()));
    }

    /**
     * โดยไม่กรอก Name และ Age=20 กดปุ่ม ADDED
     * จะต้องเจอ Please Enter user info
     */
    @Test
    public void case02() {
        onView(withId(R.id.editTExtName)).perform(clearText());
        onView(withId(R.id.editTextAge)).perform(replaceText("20"), closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"))).perform(click());
        onView(withText("Please Enter user info")).check(matches(isDisplayed()));
    }

    /**
     * ยังไม่มีการเพิ่ม UserInfo และกด GO TO LIST
     * จะเจอ Not Found
     */
    @Test
    public void case03() {
        onView(allOf(withId(R.id.buttonGotoList), withText("GO TO LIST"))).perform(click());
        onView(withText("Not Found")).check(matches(isDisplayed()));
    }

    /**
     * โดยไม่กรอก Age และ Name=Ying กดปุ่ม ADDED
     * จะต้องเจอ Please Enter user info
     */
    @Test
    public void case04() {
        onView(withId(R.id.editTExtName)).perform(replaceText("Ying"), closeSoftKeyboard());
        onView(withId(R.id.editTextAge)).perform(clearText());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"))).perform(click());
        onView(withText("Please Enter user info")).check(matches(isDisplayed()));
    }

    /**
     * โดยกรอก Name=Ying และ Age=20 กดปุ่ม ADDED และกด GO TO LIST
     * จะต้องเจอ Ying อายุ 20 เป็นตัวแรก
     */
    @Test
    public void case05() {
        onView(withId(R.id.editTExtName)).perform(replaceText("Ying"));
        onView(withId(R.id.editTextAge)).perform(replaceText("20"), closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"))).perform(click());
        onView(allOf(withId(R.id.buttonGotoList), withText("GO TO LIST"))).perform(click());
        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.textName))
                .check(matches(withText("Ying")));
        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.textAge))
                .check(matches(withText("20")));
    }

    /**
     * โดยกรอก Name=Ladarat และ Age=20 กดปุ่ม ADDED และกด GO TO LIST
     * จะต้องเจอ Ladarat อายุ 20 ใน ListView ลำดับที่ 2
     */
    @Test
    public void case06() {
        onView(withId(R.id.editTExtName)).perform(replaceText("Ladarat"));
        onView(withId(R.id.editTextAge)).perform(replaceText("20"), closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"))).perform(click());
        onView(allOf(withId(R.id.buttonGotoList), withText("GO TO LIST"))).perform(click());
        onView(withRecyclerView(R.id.list).atPositionOnView(1, R.id.textName))
                .check(matches(withText("Ladarat")));
        onView(withRecyclerView(R.id.list).atPositionOnView(1, R.id.textAge))
                .check(matches(withText("20")));
    }

    /**
     * โดยกรอก Name=Somkait และ Age=80 กดปุ่ม ADDED และกด GO TO LIST
     * จะต้องเจอ Somkait อายุ 80 ใน ListView ลำดับที่ 3
     */
    @Test
    public void case07() {
        onView(withId(R.id.editTExtName)).perform(replaceText("Somkait"));
        onView(withId(R.id.editTextAge)).perform(replaceText("80"), closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"))).perform(click());
        onView(allOf(withId(R.id.buttonGotoList), withText("GO TO LIST"))).perform(click());
        onView(withRecyclerView(R.id.list).atPositionOnView(2, R.id.textName))
                .check(matches(withText("Somkait")));
        onView(withRecyclerView(R.id.list).atPositionOnView(2, R.id.textAge))
                .check(matches(withText("80")));
    }

    /**
     * โดยกรอก Name=Prayoch และ Age=60 กดปุ่ม ADDED และกด GO TO LIST
     * จะต้องเจอ Prayoch อายุ 60 ใน ListView ลำดับที่ 4
     */
    @Test
    public void case08() {
        onView(withId(R.id.editTExtName)).perform(replaceText("Prayoch"));
        onView(withId(R.id.editTextAge)).perform(replaceText("60"), closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"))).perform(click());
        onView(allOf(withId(R.id.buttonGotoList), withText("GO TO LIST"))).perform(click());
        onView(withRecyclerView(R.id.list).atPositionOnView(3, R.id.textName))
                .check(matches(withText("Prayoch")));
        onView(withRecyclerView(R.id.list).atPositionOnView(3, R.id.textAge))
                .check(matches(withText("60")));
    }

    /**
     * โดยกรอก Name=Prayoch และ Age=50 กดปุ่ม ADDED และกด GO TO LIST
     * จะต้องเจอ Prayoch อายุ 50 ใน ListView ลำดับที่ 5
     */
    @Test
    public void case09() {
        onView(withId(R.id.editTExtName)).perform(replaceText("Prayoch"));
        onView(withId(R.id.editTextAge)).perform(replaceText("50"), closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"))).perform(click());
        onView(allOf(withId(R.id.buttonGotoList), withText("GO TO LIST"))).perform(click());
        onView(withRecyclerView(R.id.list).atPositionOnView(4, R.id.textName))
                .check(matches(withText("Prayoch")));
        onView(withRecyclerView(R.id.list).atPositionOnView(4, R.id.textAge))
                .check(matches(withText("50")));
    }

    private static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
