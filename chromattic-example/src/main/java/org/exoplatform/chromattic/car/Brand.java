package org.exoplatform.chromattic.car;

import org.chromattic.api.annotations.Name;
import org.chromattic.api.annotations.OneToMany;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Property;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@PrimaryType(name = "car:brand")
public abstract class Brand
{
   @Name
   public abstract String getName();

   @Property(name = "establishedDate")
   public abstract Date getEstablishedDate();

   public abstract void setEstablishedDate(Date establishedDate);

   @OneToMany
   public abstract Collection<Model> getModels();

   public abstract void setModels(List<Model> categories);
}
