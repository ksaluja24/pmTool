package com.management.inventory.dl.dto.interfaces;
public interface ItemDTOInterface extends java.io.Serializable,java.lang.Comparable<ItemDTOInterface>
{
//Methods
public void setCode(int code);
public int getCode();
public void setName(java.lang.String name);
public java.lang.String getName();
public void setUnitOfMeasurement(java.lang.String unitOfMeasurement);
public java.lang.String getUnitOfMeasurement();
public void setOpeningStock(int openingStock);
public int getOpeningStock();
}
