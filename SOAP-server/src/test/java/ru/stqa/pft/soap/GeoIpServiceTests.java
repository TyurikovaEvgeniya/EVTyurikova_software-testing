package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import org.testng.Assert;
import org.testng.annotations.Test;


public class GeoIpServiceTests {

  @Test
  public void testMyIp() {
    String geoIP = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("109.252.86.181");
    Assert.assertEquals(geoIP ,"<GeoIP><Country>RU</Country><State>48</State></GeoIP>");
  }

  @Test
  public void testInvalidIp() {
    String geoIP = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("109.252.86.щщщ");
    Assert.assertNotEquals(geoIP ,"<GeoIP><Country>RU</Country><State>48</State></GeoIP>");
  }
}
