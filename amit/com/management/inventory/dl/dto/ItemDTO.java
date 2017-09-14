package com.management.inventory.dl.dto;
import com.management.inventory.dl.dto.interfaces.*;
class ItemDTO implements ItemDTOInterface
{
//properties
private java.lang.String unitOfMeasurement;
private int code;
private int openingStock;
private java.lang.String name;
//setter methods
public void setUnitOfMeasurement(java.lang.String unitOfMeasurement)
{
this.unitOfMeasurement=unitOfMeasurement;
}
public void setCode(int code)
{
this.code=code;
}
public void setOpeningStock(int openingStock)
{
this.openingStock=openingStock;
}
public void setName(java.lang.String name)
{
this.name=name;
}
//getter methods
public java.lang.String getUnitOfMeasurement()
{
return this.unitOfMeasurement;
}
public int getCode()
{
return this.code;
}
public int getOpeningStock()
{
return this.openingStock;
}
public java.lang.String getName()
{
return this.name;
}
//other methods
public int compareTo(com.management.inventory.dl.dto.interfaces.ItemDTOInterface arg1)
{
return 0;
}
}
