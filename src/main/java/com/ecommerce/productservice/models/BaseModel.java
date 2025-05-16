package com.ecommerce.productservice.models;

import lombok.Data;
import java.util.Date;

@Data
public abstract class BaseModel {
    Long id;
    Date createdAt;
    Date modifiedAt;
}
