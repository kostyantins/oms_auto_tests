package com.softserveinc.edu.ita.tests;

import com.softserveinc.edu.ita.dao_jdbc.classes.User;
import com.softserveinc.edu.ita.locators.UserInfoPageLocators;
import com.softserveinc.edu.ita.page_object.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.softserveinc.edu.ita.dataproviders.DataProviders;


/**
 * Test information on User Info tab for any role User (Ticket IFAA-14)
 */
public class UserInfoTest extends TestRunner {

    Logger log = Logger.getLogger(UserInfoTest.class);

    @Test(dataProvider = "getAllRoles", dataProviderClass = DataProviders.class)
    public void testMethod(User user) {

        log.info(String.format("Test started: %s [%s %s]: %s, %s", user.getLogin(),
                user.getFirstName(), user.getLastName(),
                user.getCustomerType(), user.getRoleName()));

        HomePage homePage = new HomePage(driver);
        UserInfoPage userInfoPage = homePage.logIn(user.getLogin(), user.getPassword());

        Assert.assertEquals(user.getFirstName(), userInfoPage.getElementText(UserInfoPageLocators.FIRST_NAME_LABEL));
        Assert.assertEquals(user.getLastName(), userInfoPage.getElementText(UserInfoPageLocators.LAST_NAME_LABEL));
        Assert.assertEquals(user.getCustomerType(), userInfoPage.getElementText(UserInfoPageLocators.CUSTOMER_TYPE_LABEL));
        Assert.assertEquals(user.getRoleName(), userInfoPage.getElementText(UserInfoPageLocators.USER_ROLE_LABEL));

        userInfoPage.clickLogOutButton();
        log.info("Test finished");
    }
}
