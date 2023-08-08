package com.mycompany.category;


import java.time.LocalDateTime;
import java.util.UUID;

public class SkinCare extends Category{

    public SkinCare(UUID id, String name) {
        super(id, name);
    }

    @Override
    public LocalDateTime findDeliveryDueDate() {
        LocalDateTime localDateTime =  LocalDateTime.now();
        return localDateTime;
    }
}
