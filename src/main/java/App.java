import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {

    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("template", "templates/bands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String input_band = request.queryParams("input_band");
      Band newBand = new Band(input_band);
      if ((newBand.getName()).trim().length() != 0) {
        newBand.save();
      }
      response.redirect("/bands");
      return null;
    });

    post("/bands/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String delete_band = request.queryParams("delete_band");
      if (!(delete_band.equals(""))) {
        Band band = Band.find(Integer.parseInt(delete_band));
        band.delete();
      }
      response.redirect("/bands");
      return null;
    });

    post("/bands/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String selectedBand = request.queryParams("update_band");
      String inputUpdate = request.queryParams("input_update_band");
      if (!(selectedBand.equals(""))) {
        Band band = Band.find(Integer.parseInt(selectedBand));
        band.update(inputUpdate);
      }
      response.redirect("/bands");
      return null;
    });

    get("/bands/:band_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":band_id")));
      model.put("band", band);
      model.put("allVenues", Venue.all());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
