package com.softserveinc.edu.ita.locators;


import org.openqa.selenium.By;
//TODO remove Locators from name
public final class AdministrationPageLocators {

    private AdministrationPageLocators() {
    }

    public static final By CREATE_NEW_USER_LINK = By.xpath(".//*[@id='list']/a[contains(text(), 'Create New User')]");
    public static final By QUANTITY_OF_TABLE_PAGES = By.xpath(".//*[@id='pageCount']");
    public static final By TABLE_ROWS = By.xpath(".//*[@id='table']//tr");
    public static final By ROW_CELLS = By.tagName("td");
    public static final By FIRST_BUTTON = By.xpath(".//*[@id='first']");
    public static final By NEXT_BUTTON = By.xpath(".//input[@id='next']");
    public static final String TABLE_COLUMN = ".//*[@id='table']/thead/tr/th/a[contains(text(), '%s')]";
}
