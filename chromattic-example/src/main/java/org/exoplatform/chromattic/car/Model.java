package org.exoplatform.chromattic.car;

import org.chromattic.api.annotations.Name;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Property;

@PrimaryType(name = "car:model")
public abstract class Model
{
   @Name
   public abstract String getName();

   @Property(name = "price")
   public abstract double getPrice();

   public abstract double setPrice(double price);
}
