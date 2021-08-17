package com.meli.melimutants.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "dna-stats")
public class StatsRegister {
    @DynamoDBHashKey
    private String idstats;
    @DynamoDBAttribute
    private Integer humanDnaValidated;
    @DynamoDBAttribute
    private Integer mutantDnaValidated;
    @DynamoDBAttribute
    private Double ratio;
}
