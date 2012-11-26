package org.exoplatform.chromattic.car;

import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticBuilder;
import org.chromattic.api.ChromatticSession;

import java.util.Date;

import junit.framework.TestCase;

public class BrandTest extends TestCase
{
   public void testBrandCreation()
   {
      ChromatticBuilder chromatticBuilder = ChromatticBuilder.create();

      chromatticBuilder.add(Brand.class);
      chromatticBuilder.add(Model.class);

      Chromattic chromattic = chromatticBuilder.build();
      ChromatticSession session = chromattic.openSession();
      try
      {
         Brand brand = session.insert(Brand.class, "Toyota");
         brand.setEstablishedDate(new Date(598770000000L));

         Model camry = session.create(Model.class, "camry");
         brand.getModels().add(camry);

         Model yaris = session.create(Model.class, "yaris");
         brand.getModels().add(yaris);

         session.save();

         assertTrue(brand.getModels().contains(yaris));
      }
      finally
      {
         session.close();
      }
   }
}
