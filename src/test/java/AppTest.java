import org.sql2o.*;
import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Band Tracker");
    assertThat(pageSource()).contains("Bands");
    assertThat(pageSource()).contains("Venues");
  }

  @Test
  public void bandDoesNotExistTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Bands"));
    assertThat(pageSource()).contains("Currently, no band in databases");
  }

  @Test
  public void bandIsCreatedAndDisplayedTest() {
    goTo("http://localhost:4567/bands");
    fill("#input_band").with("U2");
    submit("#add_button");
    assertThat(pageSource()).contains("U2");
  }

  @Test
  public void blankInputFieldIsAddedTest() {
    goTo("http://localhost:4567/bands");
    fill("#input_band").with("");
    submit("#add_button");
    assertThat(pageSource()).doesNotContain("U2");
  }

  @Test
  public void bandIsDeletedTest() {
    goTo("http://localhost:4567/bands");
    fill("#input_band").with("U2");
    submit("#add_button");
    click("option", withText("U2"));
    submit("#delete_button");
    assertThat(pageSource()).doesNotContain("U2");
  }

  // @Test
  // public void venueIsCreatedAndSavedTest() {
  //   goTo("http://localhost:4567/");
  //   fill("#venue_input").with("Park Block");
  //   submit(".btn");
  //   assertEquals(1, Venue.all().size());
  // }
  //
  // @Test
  // public void venueIsAddedToBandTest() {
  //   Band testBand = new Band("U2");
  //   testBand.save();
  //   String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
  //   goTo(url);
  //   fill("#venue_input").with("Park Block");
  //   submit("#add_venue");
  //   assertThat(pageSource()).contains("Park Block");
  // }
  //
  // @Test
  // public void bandNameIsUpdatedTest() {
  //   Band testBand = new Band("U2");
  //   testBand.save();
  //   String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
  //   goTo(url);
  //   fill("#update_band").with("U2 Classic");
  //   submit("#update_band_button");
  //   goTo(url);
  //   assertThat(pageSource()).contains("U2 Classic");
  // }
  //
  // @Test
  // public void bandNameIsDeletedTest() {
  //   Band testBand = new Band("U2");
  //   testBand.save();
  //   String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
  //   goTo(url);
  //   click("a", withText("Delete"));
  //   assertThat(pageSource()).doesNotContain("U2");
  // }
}
