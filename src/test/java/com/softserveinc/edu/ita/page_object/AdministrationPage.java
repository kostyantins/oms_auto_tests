package com.softserveinc.edu.ita.page_object;

import com.softserveinc.edu.ita.dao_jdbc.domains.UserFromView;
import com.softserveinc.edu.ita.enums.UsersTable;
import com.softserveinc.edu.ita.locators.AdministrationPageLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;

/**
 * This class describes "Administration" page according to "Page Object" pattern.
 */
public class AdministrationPage extends LogOutBase {

    public AdministrationPage(WebDriver driver) {
        super(driver);
    }

    /**
     * A method to get "Administration" table from "Administration" page of web-application.
     */
    public List<UserFromView> getTableFromView() {
        List<UserFromView> usersList = new LinkedList<>();
        int pagination = 0;
        do {
            if (driver.findElements(AdministrationPageLocators.TABLE_ROWS).size() <= 1) {
            } else {
                List<WebElement> rowsList = driver.findElements(AdministrationPageLocators.TABLE_ROWS);
                for (int j = 1; j < rowsList.size(); j++) {
                    List<WebElement> cellsList = rowsList.get(j).findElements(AdministrationPageLocators.ROW_CELLS);
                    usersList.add(new UserFromView.UserFromViewBuilder()
                            .withFirstName(cellsList.get(0).getText())
                            .withLastName(cellsList.get(1).getText())
                            .withLogin(cellsList.get(2).getText())
                            .withRole(cellsList.get(3).getText())
                            .withRegion(cellsList.get(4).getText())
                            .build());
                }
            }
            pagination++;
            clickNextButton();
        } while (pagination < getQuantityOfTablePages());
        clickFirstButton();
        return usersList;
    }

    /**
     * A method to click "First" button below "Ordering" table of "Ordering" page.
     */
    public void clickFirstButton() {
        driver.findElement(AdministrationPageLocators.FIRST_BUTTON).click();
    }

    /**
     * A method to click "Next" button below "Ordering" table of "Ordering" page.
     */
    public void clickNextButton() {
        driver.findElement(AdministrationPageLocators.NEXT_BUTTON).click();
    }

    /**
     * A method to click one of "Administration" table headers to make sorting actions in the table.
     */
    public void clickAdministrationTableColumn(UsersTable tableColumn) {
        driver.findElement(By.xpath(String.format(AdministrationPageLocators.TABLE_COLUMN, tableColumn))).click();
    }

    /**
     * A method to count quantity of pages with "Administration" table.
     */
    public int getQuantityOfTablePages() {
        return Integer.valueOf(getElementText(AdministrationPageLocators.QUANTITY_OF_TABLE_PAGES));
    }
}
