package com.rbc.app;

import org.junit.runner.RunWith;

import com.googlecode.junittoolbox.SuiteClasses;
import com.googlecode.junittoolbox.WildcardPatternSuite;

@RunWith(WildcardPatternSuite.class)
@SuiteClasses({ "**/*Test.class", "!**/*IntegrationTest.class" })
public class AllUnitTests {
	//Run all tests
}