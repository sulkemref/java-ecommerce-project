package com.mycompany;

import java.util.List;
import java.util.UUID;

public class Customer {

    private UUID id;
    private String userName;
    private String email;
    private List<Address> addresses;

    public Customer(UUID id, String userName, String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
    }

    public Customer(UUID id, String userName, String email, List<Address> addresses) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.addresses = addresses;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public UUID getId() {
        return id;
    }
    /*
    public String getBalance(){
        String customerBalance = "", giftCardBalance = "";
        for ( Balance com.mycompany.balance: com.mycompany.StaticConstants.CUSTOMER_BALANCE_LIST ){
            if(com.mycompany.balance.getCustomerId().toString().equals(getId().toString())){
                customerBalance="com.mycompany.Customer com.mycompany.balance is: "+com.mycompany.balance.getBalance().toString();
            }
        }
        for ( Balance com.mycompany.balance: com.mycompany.StaticConstants.GIFT_CARD_BALANCE_LIST){
            if(com.mycompany.balance.getCustomerId().toString().equals(getId().toString())){
                giftCardBalance="Gift Card com.mycompany.balance is: "+com.mycompany.balance.getBalance().toString();
            }
        }
        if(customerBalance.isEmpty()){
            customerBalance="com.mycompany.Customer com.mycompany.balance is: 0.0";
        }

        if(giftCardBalance.isEmpty()){
            giftCardBalance = "Gift Card com.mycompany.balance is: 0.0";
        }

        return customerBalance+" "+ giftCardBalance;

    }

     */
}
