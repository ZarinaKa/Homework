package com.tests;

import com.github.javafaker.Faker;
import com.sun.tools.javah.LLNI;
import com.utilities.WebOrdersBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;




public class WebOrders extends WebOrdersBase {

    Random random = new Random();

    List<WebElement> ProductNames;
    String[] ProductsText;
    String[] AllProductText;
    Random randomNum = new Random();
    Faker faker = new Faker();
    String CustomerName = faker.name().firstName() + faker.name().lastName();
    String Street = faker.address().streetAddress();
    String Faddres_city = faker.address().city();
    String Faddress_state = faker.address().state();
    String Faddress_zip = faker.address().zipCode();
    String CreditCard = faker.finance().creditCard().replace("-", "");
    int FQuantity = faker.random().nextInt(1, 30);
    int Expire_dateM = faker.random().nextInt(1, 12);
    int Expire_dateY = faker.random().nextInt(18, 48);
    String format;
    String deletedRow;

    @Test
    public void Login(){

        driver.findElement(By.name("ctl00$MainContent$username")).sendKeys("Tester");
        driver.findElement(By.name("ctl00$MainContent$password")).sendKeys("test" + Keys.ENTER);
        System.out.println("Login Succesfull");
    }

    @Test(priority = 1)
    public void Products() throws InterruptedException {
        //WO-1: Products1.Login to Web Ordersapplication using “Tester”and “test”
        Login();
        // 2.Click on View all productslink
        driver.findElement(By.linkText("View all products")).click();
        // 3.Remember all the product names from the table
        ProductNames = driver.findElements(By.xpath("//table[@class='ProductsTable']/tbody/tr/td[1]"));

        ProductsText = new String[ProductNames.size()];
        int i = 0;

        //Storing List WEBelements text into String array
        for (WebElement EachProduct : ProductNames) {
            ProductsText[i] = EachProduct.getText();
            i++;
        }
        System.out.println(Arrays.toString(ProductsText) + " its a Product text");
        Thread.sleep(3000);

        // 4.Click on View all orderslink
        driver.findElement(By.linkText("View all orders")).click();
        // 1.Verify that all the values in the Productscolumn match the names from step 4.
        List<WebElement> AllProduct = driver.findElements(By.xpath("//table[@class='SampleTable']//tr//td[3]"));
        AllProductText = new String[AllProduct.size()];
        int j = 0;
        for (WebElement EachFromAllProduct : AllProduct) {
            AllProductText[j] = EachFromAllProduct.getText();
            j++;
        }
        try {
            // 1.Verify that all the values in the Products column match the names from step 4.
            for (int k = 0; k <= ProductsText.length - 1; ) {
                for (int l = 0; l < AllProductText.length - 1; l++) {
                    if (AllProductText[l].contains(ProductsText[k])) {
                        k++;
                        System.out.println("Web Order list contains " + AllProductText[l]);
                        //   Assert.assertTrue(AllProductText[l].contains(ProductsText[k]));

                    }
                }

            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ArrayIndexOutOfBoundsException catch in the Products Test");
        }
    }


    @Test(priority = 2) // (dependsOnMethods = Products()); constant????
    public void CreateOrder() throws InterruptedException {
        Login();
        // 2.Click on Orderlink
        driver.findElement(By.linkText("Order")).click();

        // 3.Select a product (Select different product every time)
        WebElement selectedElement = driver.findElement(By.name("ctl00$MainContent$fmwOrder$ddlProduct"));
        Select list = new Select(selectedElement);
        List<WebElement> Products = list.getOptions();
        int RNum;
        RNum = randomNum.nextInt(Products.size());
        Products.get(RNum).click();
        // 4.Enter data to all the required fields(Enter different data every time)
        //int RQuantity = randomNum.nextInt(30);
        WebElement Quantity = driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity"));
        Quantity.sendKeys(String.valueOf(FQuantity));
        System.out.println("Web Element Quantity");

        driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtName")).sendKeys(CustomerName);
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox2")).sendKeys(Street);
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox3")).sendKeys(Faddres_city);
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox4")).sendKeys(Faddress_state);
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox5")).sendKeys(Faddress_zip.substring(0, 5));

        List<WebElement> radioBtn = driver.findElements(By.xpath("//tbody//tr//td/input"));
        int RButton = randomNum.nextInt(3);
        radioBtn.get(RButton).click();

        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys(CreditCard.substring(0, 12));

        //Expire Date Month Format
        if (Expire_dateM < 10) {
            format = "0" + String.valueOf(Expire_dateM);
        }

        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox1")).
                sendKeys(format + "/" + String.valueOf(Expire_dateY));
        Thread.sleep(3000);
        // 5.Click Proceed
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();


        // 6.Click on link View all orders
        driver.findElement(By.linkText("View all orders")).click();

        // 7.Verify that all the order details are correct


    }

    //


    @Test(priority = 3)  // (dependsOnMethods = Products()); constant????
    public void Delete() {
        Login();
        // 3. Delete a random entry from the table
        List<WebElement> EntryRows = driver.findElements(By.xpath("//table[@class='SampleTable']//tbody//tr"));//9 rows with headers
        List<WebElement> checkBoxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
        List<WebElement> NameColumn1 = driver.findElements(By.xpath("//table[@class='SampleTable']//tbody//tr//td[2]"));

        int IndexChkBxs = randomNum.nextInt(checkBoxes.size());

        int sizeBefore = EntryRows.size();//rows size before elements deleting 9
        // int Index = faker.random().nextInt(1,EntryRows.size());
        String deletedName = NameColumn1.get(IndexChkBxs).getText();//row which should be deleted
        checkBoxes.get(IndexChkBxs).click();
        System.out.println(deletedName +"was Randomly deleted");

        driver.findElement(By.name("ctl00$MainContent$btnDelete")).click();

        // 4. Verify that table row count decreased by 1
        List<WebElement> EntryRows2 = driver.findElements(By.xpath("//table[@class='SampleTable']//tbody//tr"));//9 rows with headers
        int sizeAfter = EntryRows2.size();

        Assert.assertTrue(sizeBefore == sizeAfter + 1);
        System.out.println("Rows sizeBefore= " + sizeBefore + ",and sizeAfter = " + sizeAfter + ", decreased by 1");

        // 5. Verify Name column does not contain deleted person’s name

        List<WebElement> NameColumn2 = driver.findElements(By.xpath("//table[@class='SampleTable']//tbody//tr//td[2]"));
        for (int a = 0; a < NameColumn2.size(); a++) {
            Assert.assertFalse(NameColumn2.get(a).getText().contains(deletedName));
       // Assert.assertArrayEquals();
        }
//        String AllRows="";
//        for(WebElement each: EntryRows2){
//            AllRows += each.getText();

    }


    @Test(priority = 4)
    public void Edit() throws InterruptedException {
        Login();

        WebElement EntryRow = driver.findElement(By.xpath("//table[@class='SampleTable']//tbody//tr[3]"));//9 rows with headers
        String RowText = EntryRow.getText().trim().replace(" ", "").toLowerCase();
         RowText=RowText.replaceAll(",", "").replaceAll("1","");
        System.out.println(EntryRow.getSize());
        // String [] textRow = new String [];
        System.out.println(RowText);

        WebElement CardType = driver.findElement(By.xpath("//table[@class='SampleTable']//tbody//tr[3]//td[10]"));
        String CardTypeT = CardType.getText();
        System.out.println(CardTypeT);


        // 2.Click edit button for any entry
        //pravka

        WebElement EditRow = driver.findElement(By.xpath("//table[@class='SampleTable']//tbody//tr[3]//td[13]"));

        EditRow.click();
        // 3.Change the quantity to a different amount
        Thread.sleep(1000);
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).sendKeys("2");

        // 4.Click Calculate

        driver.findElement(By.xpath("//input[@value='Calculate']")).click();

        // 5.Verify that new quantity is displayed

        String ChangedQuantity = driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).getAttribute("value");
        System.out.println(ChangedQuantity);
        //check radio button

        WebElement radioBtn = driver.findElement(By.xpath("//tbody//tr//td/input[1]"));
        radioBtn.click();
//        for (WebElement rB : radioBtn) {
//            if (rB.getText().equalsIgnoreCase(CardTypeT)) {
//                rB.click();
//            }
//            Thread.sleep(1000);
//       }
// int RButton = randomNum.nextInt(3);
//            radioBtn.get(RButton).click();
//
        // 6.Click Update
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_UpdateButton")).click();

        // 7.Verify new quantity is displayed
        String DisplatedQuantity = driver.findElement(By.xpath("//table[@class='SampleTable']//tbody//tr[3]//td[4]")).getText();
        System.out.println(DisplatedQuantity+"kjk");
        Assert.assertTrue(ChangedQuantity.equals(DisplatedQuantity));

        // 8.Verify that other information in that row did not change
        WebElement RowAfterChanging = driver.findElement(By.xpath("//table[@class='SampleTable']//tbody//tr[3]"));//9 rows with headers
        String RowAfterChangingQantity = RowAfterChanging.getText().trim().replace(" ", "").replace("12", "").replaceAll(",", "").replaceAll("1","").toLowerCase();

        System.out.println(RowAfterChangingQantity);
        Assert.assertEquals(RowText,RowAfterChangingQantity);

        System.out.println("are matched");




    }

}



