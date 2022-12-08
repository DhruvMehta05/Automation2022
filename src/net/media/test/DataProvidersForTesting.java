package net.media.test;

import net.media.assignment2.FormElementActions;
import org.testng.annotations.DataProvider;

public class DataProvidersForTesting {

    FormElementActions actionObject = new FormElementActions();


    @DataProvider
    public Object[][] inputFieldData() {
        return new Object[][]{
                {"1", "1", false},

        };
    }

}
