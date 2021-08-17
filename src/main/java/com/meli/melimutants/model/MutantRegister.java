package com.meli.melimutants.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamoDBTable(tableName = "dna-mutants")
public class MutantRegister {
    @DynamoDBHashKey
    private String iddna;
    @DynamoDBAttribute
    private String dnaChain;
    @DynamoDBAttribute
    private Boolean isMutant;
}
