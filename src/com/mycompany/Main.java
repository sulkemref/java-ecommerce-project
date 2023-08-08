package com.mycompany;

import com.mycompany.balance.*;
import com.mycompany.category.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static void main(String[] args)  {

        DataGenerator.createCustomer();
        DataGenerator.createCategory();
        DataGenerator.createProduct();
        DataGenerator.createBalance();
        DataGenerator.createDiscount();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Select com.mycompany.Customer: ");
        for(int i = 0; StaticConstants.CUSTOMER_LIST.size()>i;i++ ){
            System.out.println("Type "+i +" for customer:"+ StaticConstants.CUSTOMER_LIST.get(i).getUserName());
        }


        Customer customer = StaticConstants.CUSTOMER_LIST.get(scanner.nextInt());

        Cart cart = new Cart(customer);

        while(true){

            System.out.println("What would you like to do? Just type id for selection");

            for(int i = 0; i < prepareMenuOptions().length; i++){
                System.out.println(i+" - " +prepareMenuOptions()[i]);
            }


            int menuSelection = scanner.nextInt();

            switch (menuSelection){
                case 0:
                   for(Category category: StaticConstants.CATEGORY_LIST){
                       System.out.println("Category Code: "+category.generateCategoryCode()+" com.mycompany.category name: "+category.getName());
                   }
                    break;
                case 1:
                    try{
                        for(Product product: StaticConstants.PRODUCT_LIST){
                            System.out.println("com.mycompany.Product Name: " +product.getName()+ " com.mycompany.Product Category Name: "+ product.getCategoryName());
                        }
                    }catch (Exception e){
                        System.out.println("com.mycompany.Product could not printed because com.mycompany.category not found for product name "+ e.getMessage().split(",")[1]);
                    }
                    break;
                case 2:
                    for(int i = 0 ; i<StaticConstants.DISCOUNT_LIST.size(); i++){
                        System.out.println("Discount Name: "+StaticConstants.DISCOUNT_LIST.get(i).getName() + "com.mycompany.discount threshold amount: " + StaticConstants.DISCOUNT_LIST.get(i).getThresholdAmount());
                    }
                    break;
                case 3: // customer com.mycompany.balance and gift card com.mycompany.balance
                   // System.out.println(customer.getBalance());

                    CustomerBalance cBalance = findCustomerBalance(customer.getId());
                    GiftCardBalance gBalance = findGistCardBalance(customer.getId());
                    double totalBalance = cBalance.getBalance()+gBalance.getBalance();

                    System.out.println("Total Balance:"+ totalBalance);
                    System.out.println("com.mycompany.Customer Balance:"+ cBalance.getBalance());
                    System.out.println("Gift Card Balance:"+ gBalance.getBalance());
                    break;
                case 4:

                    CustomerBalance customerBalance = findCustomerBalance(customer.getId());
                    GiftCardBalance giftCardBalance = findGistCardBalance(customer.getId());

                    System.out.println("Which Account would you like to add?");
                    System.out.println("Type 1 for com.mycompany.Customer Balance:" + customerBalance.getBalance());
                    System.out.println("Type 2 for Gift Card Balance:" + giftCardBalance.getBalance());
                    int balanceAccountSelection = scanner.nextInt();
                    System.out.println("How much you would like to add?");
                    double additionalAmount = scanner.nextInt();

                    switch (balanceAccountSelection){
                        case 1:
                            customerBalance.addBalance( additionalAmount );
                            System.out.println("New com.mycompany.Customer Balance:"+customerBalance.getBalance());
                            break;
                        case 2:
                            giftCardBalance.addBalance( additionalAmount );
                            System.out.println("New com.mycompany.Customer Gift Card Balance:"+giftCardBalance.getBalance());
                            break;
                    }
                    break;
                case 5:
                    Map<Product,Integer> map = new HashMap<>();
                    cart.setProductMap(map);
                    while (true){
                        System.out.println("Which product you want to add to your cart. For exit selection Type : exit");
                        for (Product product: StaticConstants.PRODUCT_LIST){
                            try {
                                System.out.println("Id:" +product.getId() +
                                        " price:"+product.getPrice()+
                                        " product com.mycompany.category:"+product.getCategoryName()+
                                        " stock:"+product.getRemainingStock()+
                                        " product delivery due:"+product.getDeliveryDyeDate());
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        String productId = scanner.next();

                        try {
                            Product product = findProductById(productId);
                            if(!putItemCartIfStockAvailable(cart,product)){
                                System.out.println("Stock is insufficient. Please try again");
                                continue;
                            }

                        } catch (Exception e) {
                            System.out.println("com.mycompany.Product doesn't exist. Please try again");
                            continue;
                        }

                        System.out.println("Do you want to add more product. Type Y for adding more, N for exit");
                        String decision = scanner.next();

                        if(!decision.equals("Y")){
                            break;
                        }




                    }

                    break;
                case 6: break;
                case 7: break;
                case 8: break;
                case 9: System.exit(0);break;


            }





        }

   }

    private static boolean putItemCartIfStockAvailable(Cart cart,Product product){
        System.out.println("Please provide product count:");
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
       Integer cartCount = cart.getProductMap().get(product);

        if(cartCount != null&& product.getRemainingStock()> cartCount+count){

            cart.getProductMap().put(product, cartCount+count);
            return true;

        }else  if(product.getRemainingStock()>=count){

            cart.getProductMap().put(product, count);
            return true;
        }

        return false;
    }



    private static Product findProductById(String productId) throws Exception{
        for(Product product : StaticConstants.PRODUCT_LIST){
            if(product.getId().toString().equals(productId)){
                return product;
            }
        }

        throw new Exception("com.mycompany.Product not found");

    }

   private static CustomerBalance findCustomerBalance(UUID customerId){
        for(Balance customerBalance: StaticConstants.CUSTOMER_BALANCE_LIST){
            if(customerBalance.getCustomerId().toString().equals(customerId.toString())){
                return (CustomerBalance)customerBalance;
            }
        }
       /*
        com.mycompany.StaticConstants.CUSTOMER_BALANCE_LIST.add(new CustomerBalance( customerId, 0d));
       return (CustomerBalance) (com.mycompany.StaticConstants.CUSTOMER_BALANCE_LIST.get(com.mycompany.StaticConstants.CUSTOMER_BALANCE_LIST.size()-1)) ;
        */

       CustomerBalance customerBalance = new CustomerBalance( customerId, 0d);
       StaticConstants.CUSTOMER_BALANCE_LIST.add(customerBalance);


       return customerBalance;
   }

    private static GiftCardBalance findGistCardBalance(UUID customerId) {
        for (Balance giftCardBalance : StaticConstants.GIFT_CARD_BALANCE_LIST) {
            if (giftCardBalance.getCustomerId().toString().equals(customerId.toString())) {
                return (GiftCardBalance) giftCardBalance;
            }
        }

        GiftCardBalance giftCardBalance = new GiftCardBalance( customerId, 0d);
        StaticConstants.GIFT_CARD_BALANCE_LIST.add(giftCardBalance);

        return giftCardBalance;

    }

   private static String[] prepareMenuOptions(){
       return new String[] {"List Categories", "List Products", "List Discount", "See Balance", "Add Balance",
       "Place an order", "See com.mycompany.Cart", "See order details", "See your address", "Close App"};


   }

}
