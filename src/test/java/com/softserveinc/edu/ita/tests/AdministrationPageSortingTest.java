package com.softserveinc.edu.ita.tests;

import com.softserveinc.edu.ita.dao_jdbc.domains.UserFromView;
import com.softserveinc.edu.ita.enums.UsersTable;
import com.softserveinc.edu.ita.page_object.AdministrationPage;
import com.softserveinc.edu.ita.page_object.HomePage;
import com.softserveinc.edu.ita.page_object.UserInfoPage;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * This class is used to test sorting actions in 'Administration' table of 'Administration' page.
 */
public class AdministrationPageSortingTest extends TestRunner {

    HomePage homePage;
    UserInfoPage userInfoPage;
    AdministrationPage administrationPage;
    List<UserFromView> baseTableFromView;
    List<UserFromView> tableFromViewSortedAsc;
    List<UserFromView> tableFromViewSortedDesc;

    @Test(dataProvider = "getEnums")
    //todo rename userstable
    public void testSorting(UsersTable column) {

        administrationPage.clickAdministrationTableColumn(column);
        tableFromViewSortedAsc = administrationPage.getTableFromView();

        administrationPage.clickAdministrationTableColumn(column);
        tableFromViewSortedDesc = administrationPage.getTableFromView();

        Assert.assertTrue(verifyIntegrityOfTableAfterSorting(baseTableFromView, tableFromViewSortedAsc),
                String.format("Integrity of ascendant table after sorting by %s is saved.", column));
        Assert.assertTrue(verifyIntegrityOfTableAfterSorting(baseTableFromView, tableFromViewSortedDesc),
                String.format("Integrity of descendant table after sorting by %s is saved.", column));

        sortBaseTableBy(baseTableFromView, column);
        Assert.assertTrue(verifyEqualityOfTablesByColumn(baseTableFromView, tableFromViewSortedAsc, column),
                String.format("Ascendant table from view sorted by clicking '%s' column is equal to base table sorted by comparator.", column));

        Collections.reverse(baseTableFromView);
        Assert.assertTrue(verifyEqualityOfTablesByColumn(baseTableFromView, tableFromViewSortedDesc, column),
                String.format("Descendant table from view sorted by clicking '%s' column is equal to base table sorted by comparator.", column));

    }

    //todo move out
    @DataProvider(name = "getEnums")
    public Iterator<Object[]> getTestDataIterator() {
        final List<Object[]> testDataList = new ArrayList<>();
        Stream.of(UsersTable.values()).forEach(column -> testDataList.add(new Object[]{column}));
        return testDataList.iterator();
    }

    @BeforeMethod
    public void prepareTest() {
        homePage = new HomePage(driver);
        userInfoPage = homePage.logIn("iva", "qwerty");
        administrationPage = userInfoPage.clickAdministrationTab();
        baseTableFromView = administrationPage.getTableFromView();
    }

    @AfterMethod
    public void logOut() {
        administrationPage.clickLogOutButton();
    }

    /**
     * Interface with method used in method "verifyEqualityOfTablesByColumn".
     */
    private interface ComparisonCondition {
        String callMethod(UserFromView method);
    }

    /**
     * A method to verify equality of tables by given column.
     */
    public boolean verifyEqualityOfTablesByColumn(List<UserFromView> sortedBaseTableFromView, List<UserFromView> sortedTableByView, UsersTable header) {
        Map<UsersTable, ComparisonCondition> sortConditionsMap = new HashMap<>();
        sortConditionsMap.put(UsersTable.FIRST_NAME, UserFromView::getFirstName);
        sortConditionsMap.put(UsersTable.LAST_NAME, UserFromView::getLastName);
        sortConditionsMap.put(UsersTable.LOGIN, UserFromView::getLogin);
        sortConditionsMap.put(UsersTable.ROLE, UserFromView::getRole);
        sortConditionsMap.put(UsersTable.REGION, UserFromView::getRegion);
        Iterator baseTableIterator = sortedBaseTableFromView.iterator();
        Iterator tableIterator = sortedTableByView.iterator();
        int equalsCells = 0;
        while (baseTableIterator.hasNext() && sortConditionsMap.get(header).callMethod((UserFromView) baseTableIterator.next())
                .equals(sortConditionsMap.get(header).callMethod((UserFromView) tableIterator.next()))) {
            equalsCells++;
        }
        return (equalsCells == sortedBaseTableFromView.size());
    }

    /**
     * A method to sort base table by given column through comparator.
     */
    public void sortBaseTableBy(List<UserFromView> baseTableFromView, UsersTable header) {
        Map<UsersTable, Function<UserFromView, String>> sortConditionsMap = new HashMap<>();
        sortConditionsMap.put(UsersTable.FIRST_NAME, UserFromView::getFirstName);
        sortConditionsMap.put(UsersTable.LAST_NAME, UserFromView::getLastName);
        sortConditionsMap.put(UsersTable.LOGIN, UserFromView::getLogin);
        sortConditionsMap.put(UsersTable.ROLE, UserFromView::getRole);
        sortConditionsMap.put(UsersTable.REGION, UserFromView::getRegion);

        baseTableFromView.sort(Comparator.comparing(sortConditionsMap.get(header)));
    }

    /**
     * A method to verify integrity of table after sorting. The method says "All of the rows are(true)/aren't(false) intact after sorting".
     */
    public boolean verifyIntegrityOfTableAfterSorting(List<UserFromView> baseTable, List<UserFromView> tableAfterSorting) {
        int quantityOfIntactTableRowsAfterSorting = 0;
        Iterator tableIterator = tableAfterSorting.iterator();

        while (tableIterator.hasNext() && baseTable.toString().contains(tableIterator.next().toString())) {
            quantityOfIntactTableRowsAfterSorting++;
        }
        return (quantityOfIntactTableRowsAfterSorting == tableAfterSorting.size());
    }
}
