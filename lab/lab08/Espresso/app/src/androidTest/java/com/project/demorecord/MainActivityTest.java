package com.project.demorecord;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        /**
         * โดยไม่กรอก Name และ Age กดปุ่ม ADDED
         * จะต้องเจอ Please Enter user info
         */
        onView(withId(R.id.editTExtName)).perform(clearText());
        onView(withId(R.id.editTextAge)).perform(clearText());
        onView(withText("ADDED")).perform(click());
        onView(withText("Please Enter user info")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());

        /**
         * โดยไม่กรอก Name และ Age=20 กดปุ่ม ADDED
         * จะต้องเจอ Please Enter user info
         */
        onView(withId(R.id.editTExtName)).perform(clearText());
        onView(withId(R.id.editTextAge)).perform(replaceText("20"), closeSoftKeyboard());
        onView(withText("ADDED")).perform(click());
        onView(withText("Please Enter user info")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());

        /**
         * ยังไม่มีการเพิ่ม UserInfo และกด GO TO LIST
         * จะเจอ Not Found
         */
        onView(withText("GO TO LIST")).perform(click());
        onView(withText("Not Found")).check(matches(isDisplayed()));
        pressBack();

        /**
         * โดยไม่กรอก Age และ Name=Ying กดปุ่ม ADDED
         * จะต้องเจอ Please Enter user info
         */
        onView(withId(R.id.editTExtName)).perform(replaceText("Ying"), closeSoftKeyboard());
        onView(withId(R.id.editTextAge)).perform(clearText());
        onView(withText("ADDED")).perform(click());
        onView(withText("Please Enter user info")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());

        /**
         * โดยกรอก Name=Ying และ Age=20 กดปุ่ม ADDED และกด GO TO LIST
         * จะต้องเจอ Ying อายุ 20 เป็นตัวแรก
         */
        onView(withId(R.id.editTExtName)).perform(replaceText("Ying"));
        onView(withId(R.id.editTextAge)).perform(replaceText("20"), closeSoftKeyboard());
        onView(withText("ADDED")).perform(click());
        onView(withText("GO TO LIST")).perform(click());
        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.textName))
                .check(matches(withText("Ying")));
        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.textAge))
                .check(matches(withText("20")));
        pressBack();

        /**
         * โดยกรอก Name=Ladarat และ Age=20 กดปุ่ม ADDED และกด GO TO LIST
         * จะต้องเจอ Ladarat อายุ 20 ใน ListView ลำดับที่ 2
         */
        onView(withId(R.id.editTExtName)).perform(replaceText("Ladarat"));
        onView(withId(R.id.editTextAge)).perform(replaceText("20"), closeSoftKeyboard());
        onView(withText("ADDED")).perform(click());
        onView(withText("GO TO LIST")).perform(click());
        onView(withRecyclerView(R.id.list).atPositionOnView(1, R.id.textName))
                .check(matches(withText("Ladarat")));
        onView(withRecyclerView(R.id.list).atPositionOnView(1, R.id.textAge))
                .check(matches(withText("20")));
        pressBack();

        /**
         * โดยกรอก Name=Somkait และ Age=80 กดปุ่ม ADDED และกด GO TO LIST
         * จะต้องเจอ Somkait อายุ 80 ใน ListView ลำดับที่ 3
         */
        onView(withId(R.id.editTExtName)).perform(replaceText("Somkait"));
        onView(withId(R.id.editTextAge)).perform(replaceText("80"), closeSoftKeyboard());
        onView(withText("ADDED")).perform(click());
        onView(withText("GO TO LIST")).perform(click());
        onView(withRecyclerView(R.id.list).atPositionOnView(2, R.id.textName))
                .check(matches(withText("Somkait")));
        onView(withRecyclerView(R.id.list).atPositionOnView(2, R.id.textAge))
                .check(matches(withText("80")));
        pressBack();

        /**
         * โดยกรอก Name=Prayoch และ Age=60 กดปุ่ม ADDED และกด GO TO LIST
         * จะต้องเจอ Somkait อายุ 60 ใน ListView ลำดับที่ 4
         */
        onView(withId(R.id.editTExtName)).perform(replaceText("Prayoch"));
        onView(withId(R.id.editTextAge)).perform(replaceText("60"), closeSoftKeyboard());
        onView(withText("ADDED")).perform(click());
        onView(withText("GO TO LIST")).perform(click());
        onView(withRecyclerView(R.id.list).atPositionOnView(3, R.id.textName))
                .check(matches(withText("Somkait")));
        onView(withRecyclerView(R.id.list).atPositionOnView(3, R.id.textAge))
                .check(matches(withText("60")));
    }

    private static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
