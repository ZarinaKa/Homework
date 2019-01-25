package com.utilities;

import org.testng.Assert;

public class BrowserUtils {

    public static void VerifyTextMatches (String MatchText1, String MatchText2){
        Assert.assertEquals(MatchText1,MatchText2);

    }

    public static void VerifyTextContains (String ContainText1, String ContainText2 ){
        Assert.assertTrue(ContainText1.contains(ContainText2));
    }

}
