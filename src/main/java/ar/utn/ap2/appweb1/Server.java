package ar.utn.ap2.appweb1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.JavalinRenderer;

public class Server {

	public static void main(String[] args) {

		JavalinRenderer.register(

				(path, model, context) -> { // Función que renderiza el template
					Handlebars handlebars = new Handlebars();
					Template template = null;
					try {
						template = handlebars.compile( "templates/" +  path.replace(".hbs",""));
						return template.apply(model);
					} catch (IOException e) {
						//e.printStackTrace();
						context.status(HttpStatus.NOT_FOUND);
						return "No se encuentra la pagina indicada...";
					}
					
				}, ".hbs" // Extensión del archivo de template
		);
		
		Javalin app = Javalin.create(config -> {
			config.staticFiles.add("web", Location.CLASSPATH);
		}).start(7070);
		
		app.get("/template", ctx -> {
            Map<String, Object> model = new HashMap<>();
            model.put("mensaje", "Hola, mundo!");
            ctx.render("index.hbs", model);
        });
		
		
		app.get("/", ctx -> {
			String unTexto = "Hola Mundo";
			ctx.result(unTexto);
		});
		app.get("/personas", ctx -> {
			String unTexto = "aca hay personas";
			ctx.result(unTexto);
		});
		

	}

}
