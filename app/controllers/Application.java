package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.cache.Cache;
import play.data.Form;
import play.libs.F;
import play.libs.WS;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Application extends Controller {
    private static final Form<DirecteurCommercial> dcForm= Form.form(DirecteurCommercial.class);
    private static final Form<ChefDepartementDirect> cddForm = Form.form(ChefDepartementDirect.class);
    private static final Form<ChefDepartementIndirect> cdiForm = Form.form(ChefDepartementIndirect.class);
    private static final Form<ChefServiceDistribution> csdForm = Form.form(ChefServiceDistribution.class);
    private static final Form<ChefServicePdv> cspForm = Form.form(ChefServicePdv.class);
    private static final Form<ResponsableRegionalBoutique> rrbForm = Form.form(ResponsableRegionalBoutique.class);
    private static final Form<ResponsableRegionalDistribution> rrdForm = Form.form(ResponsableRegionalDistribution.class);
    private static final Form<ResponsableRegionalPdv> rrpForm = Form.form(ResponsableRegionalPdv.class);

    public static F.Promise<Result> wrapResultAsPromise(final Result result) {
        return F.Promise.promise(
                new F.Function0<Result>() {
                    public Result apply() {
                        return result;
                    }
                }
        );
    }

    public static Result choix() {
        return ok(choix.render());
    }

    public static Result menu() {
        String user = session("connected");
        if(user == null) {
            return unauthorized("vous devez vous connectez");
        }else{
            return ok(acceuil.render(user));
        }
    }

    public static Result deconnexion() {
        String user = session("connected");
        if(user != null) {
            session().remove("connected");
            return redirect(routes.Application.choix());
        }else{
            return unauthorized("Vous devez vous connecter");
        }
    }

    public static F.Promise<Result> objectif_dc(){
        String user = session("connected");
        final  List<String>  messages = new ArrayList<>();
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        if(user == null || !user.equals("dc")) {
            return  wrapResultAsPromise(unauthorized("vous devez vous connectez"));
        }else if(Cache.get("produits")!= null){
            List<Produit> produits = (ArrayList<Produit>) Cache.get("produits");
            return wrapResultAsPromise(ok(objectifDc.render(produits, messages)));
        }
        else{
            final F.Promise<Result> resultPromise = WS
                    .url("http://localhost:9001/objectifDc")
                    .get()
                    .map(new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            JsonNode json = response.asJson();
                            List<Produit> produits = new ArrayList<>();
                            JsonNode produitsNode = json.findPath("object0");
                            Iterator<JsonNode> iterator = produitsNode.elements();
                            while (iterator.hasNext()) {
                                JsonNode produitNode = iterator.next();
                                try {
                                    produits.add(Outil.readProduit(produitNode));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            }
                            Cache.set("produits", produits, 60 * 60 * 24 * 3);
                            return ok(objectifDc.render(produits, messages));
                        }
                    });
            return resultPromise;
        }
    }

    public static F.Promise<Result> traitement_objectif_dc(){
        String user = session("connected");
        if(user == null || !user.equals("dc")) {
            return  wrapResultAsPromise(unauthorized("vous devez vous connectez"));
        }else{
            Map<String,String[]> map= request().body().asFormUrlEncoded();
            String str = Outil.map(map);
            final F.Promise<Result> resultPromise = WS
                    .url("http://localhost:9001/objectifDc")
                    .setContentType("application/x-www-form-urlencoded").post(str)
                    .map(new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            JsonNode json = response.asJson();
                            List<String> messages = new ArrayList<>();
                            messages.add(json.findPath("object0").asText().trim());
                            messages.add(json.findPath("object1").asText().trim());
                            messages.add(json.findPath("object2").asText().trim());
                            messages.add(json.findPath("object3").asText().trim());
                            if (messages.get(0).equals("") && messages.get(1).equals("") && messages.get(2).equals("") && messages.get(3).equals("")) {
                                flash("success", "L'objectif a été saisi avec succés.");
                                return redirect(routes.Application.menu());
                            } else {
                                flash("error", "Please correct the form below.");
                                List<Produit> produits = (ArrayList<Produit>) Cache.get("produits");
                                return ok(objectifDc.render(produits, messages));
                            }
                        }
                    });
            return resultPromise;
        }
    }

    public static F.Promise<Result> objectif_cdd(){
        String user = session("connected");
        final  List<String>  messages = new ArrayList<>();
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        if(user == null || !user.equals("cdd")) {
            return  wrapResultAsPromise(unauthorized("vous devez vous connectez"));
        }else if(Cache.get("produits")!= null){
            List<Produit> produits = (ArrayList<Produit>) Cache.get("produits");
            return wrapResultAsPromise(ok(objectifCdd.render(produits, messages)));
        }
        else{
            final F.Promise<Result> resultPromise = WS
                    .url("http://localhost:9001/objectifCdd")
                    .get()
                    .map(new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            JsonNode json = response.asJson();
                            List<Produit> produits = new ArrayList<>();
                            JsonNode produitsNode = json.findPath("object0");
                            Iterator<JsonNode> iterator = produitsNode.elements();
                            while (iterator.hasNext()) {
                                JsonNode produitNode = iterator.next();
                                try {
                                    produits.add(Outil.readProduit(produitNode));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            }
                            Cache.set("produits", produits, 60 * 60 * 24 * 3);
                            return ok(objectifCdd.render(produits, messages));
                        }
                    });
            return resultPromise;
        }
    }

    public static F.Promise<Result> traitement_objectif_cdd(){
        String user = session("connected");
        if(user == null || !user.equals("cdd")) {
            return  wrapResultAsPromise(unauthorized("vous devez vous connectez"));
        }else{
            Map<String,String[]> map= request().body().asFormUrlEncoded();
            String str = Outil.map(map);
            final F.Promise<Result> resultPromise = WS
                    .url("http://localhost:9001/objectifCdd")
                    .setContentType("application/x-www-form-urlencoded").post(str)
                    .map(new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            JsonNode json = response.asJson();
                            List<String> messages = new ArrayList<>();
                            messages.add(json.findPath("object0").asText().trim());
                            messages.add(json.findPath("object1").asText().trim());
                            messages.add(json.findPath("object2").asText().trim());
                            messages.add(json.findPath("object3").asText().trim());
                            if(messages.get(0).equals("") && messages.get(1).equals("") && messages.get(2).equals("") && messages.get(3).equals("")){
                                flash("success", "L'objectif a été saisi avec succés.");
                                return redirect(routes.Application.menu());
                            }else{
                                flash("error", "Please correct the form below.");
                                List<Produit> produits = (ArrayList<Produit>) Cache.get("produits");
                                return ok(objectifCdd.render(produits, messages));
                            }
                        }
                    });
            return resultPromise;
        }

    }

    public static F.Promise<Result> objectif_cdi(){
        String user = session("connected");
        final  List<String>  messages = new ArrayList<>();
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        if(user == null || !user.equals("cdi")) {
            return  wrapResultAsPromise(unauthorized("vous devez vous connectez"));
        }else if(Cache.get("produits")!= null){
            List<Produit> produits = (ArrayList<Produit>) Cache.get("produits");
            return wrapResultAsPromise(ok(objectifCdi.render(produits, messages)));
        }
        else{
            final F.Promise<Result> resultPromise = WS
                    .url("http://localhost:9001/objectifCdi")
                    .get()
                    .map(new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            JsonNode json = response.asJson();
                            List<Produit> produits = new ArrayList<>();
                            JsonNode produitsNode = json.findPath("object0");
                            Iterator<JsonNode> iterator = produitsNode.elements();
                            while (iterator.hasNext()) {
                                JsonNode produitNode = iterator.next();
                                try {
                                    produits.add(Outil.readProduit(produitNode));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            }
                            Cache.set("produits", produits, 60 * 60 * 24 * 3);
                            return ok(objectifCdi.render(produits, messages));
                        }
                    });
            return resultPromise;
        }
    }

    public static F.Promise<Result> traitement_objectif_cdi(){
        String user = session("connected");
        if(user == null || !user.equals("cdi")) {
            return  wrapResultAsPromise(unauthorized("vous devez vous connectez"));
        }else{
            Map<String,String[]> map= request().body().asFormUrlEncoded();
            String str = Outil.map(map);
            final F.Promise<Result> resultPromise = WS
                    .url("http://localhost:9001/objectifCdi")
                    .setContentType("application/x-www-form-urlencoded").post(str)
                    .map(new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            JsonNode json = response.asJson();
                            List<String> messages = new ArrayList<>();
                            messages.add(json.findPath("object0").asText().trim());
                            messages.add(json.findPath("object1").asText().trim());
                            messages.add(json.findPath("object2").asText().trim());
                            messages.add(json.findPath("object3").asText().trim());
                            if(messages.get(0).equals("") && messages.get(1).equals("") && messages.get(2).equals("") && messages.get(3).equals("")){
                                flash("success", "L'objectif a été saisi avec succés.");
                                return redirect(routes.Application.menu());
                            }else{
                                flash("error", "Please correct the form below.");
                                List<Produit> produits = (ArrayList<Produit>) Cache.get("produits");
                                return ok(objectifCdi.render(produits, messages));
                            }
                        }
                    });
            return resultPromise;
        }
    }

    public static F.Promise<Result> objectif_csp(){
        String user = session("connected");
        final  List<String>  messages = new ArrayList<>();
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        if(user == null || !user.equals("csp")) {
            return  wrapResultAsPromise(unauthorized("vous devez vous connectez"));
        }else if(Cache.get("produits")!= null){
            List<Produit> produits = (ArrayList<Produit>) Cache.get("produits");
            return wrapResultAsPromise(ok(objectifCsp.render(produits, messages)));
        }
        else{
            final F.Promise<Result> resultPromise = WS
                    .url("http://localhost:9001/objectifCsp")
                    .get()
                    .map(new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            JsonNode json = response.asJson();
                            List<Produit> produits = new ArrayList<>();
                            JsonNode produitsNode = json.findPath("object0");
                            Iterator<JsonNode> iterator = produitsNode.elements();
                            while (iterator.hasNext()) {
                                JsonNode produitNode = iterator.next();
                                try {
                                    produits.add(Outil.readProduit(produitNode));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            }
                            Cache.set("produits", produits, 60 * 60 * 24 * 3);
                            return ok(objectifCsp.render(produits, messages));
                        }
                    });
            return resultPromise;
        }
    }

    public static F.Promise<Result> traitement_objectif_csp(){
        String user = session("connected");
        if(user == null || !user.equals("csp")) {
            return  wrapResultAsPromise(unauthorized("vous devez vous connectez"));
        }else{
            Map<String,String[]> map= request().body().asFormUrlEncoded();
            String str = Outil.map(map);
            final F.Promise<Result> resultPromise = WS
                    .url("http://localhost:9001/objectifCsp")
                    .setContentType("application/x-www-form-urlencoded").post(str)
                    .map(new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            JsonNode json = response.asJson();
                            List<String> messages = new ArrayList<>();
                            messages.add(json.findPath("object0").asText().trim());
                            messages.add(json.findPath("object1").asText().trim());
                            messages.add(json.findPath("object2").asText().trim());
                            messages.add(json.findPath("object3").asText().trim());
                            if(messages.get(0).equals("") && messages.get(1).equals("") && messages.get(2).equals("") && messages.get(3).equals("")){
                                flash("success", "L'objectif a été saisi avec succés.");
                                return redirect(routes.Application.menu());
                            }else{
                                flash("error", "Please correct the form below.");
                                List<Produit> produits = (ArrayList<Produit>) Cache.get("produits");
                                return ok(objectifCsp.render(produits, messages));
                            }
                        }
                    });
            return resultPromise;
        }
    }

    public static F.Promise<Result> objectif_csd(){
        String user = session("connected");
        final  List<String>  messages = new ArrayList<>();
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        if(user == null || !user.equals("csd")) {
            return  wrapResultAsPromise(unauthorized("vous devez vous connectez"));
        }else if(Cache.get("produits")!= null){
            List<Produit> produits = (ArrayList<Produit>) Cache.get("produits");
            return wrapResultAsPromise(ok(objectifCsd.render(produits, messages)));
        }
        else{
            final F.Promise<Result> resultPromise = WS
                    .url("http://localhost:9001/objectifCsd")
                    .get()
                    .map(new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            JsonNode json = response.asJson();
                            List<Produit> produits = new ArrayList<>();
                            JsonNode produitsNode = json.findPath("object0");
                            Iterator<JsonNode> iterator = produitsNode.elements();
                            while (iterator.hasNext()) {
                                JsonNode produitNode = iterator.next();
                                try {
                                    produits.add(Outil.readProduit(produitNode));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            }
                            Cache.set("produits", produits, 60 * 60 * 24 * 3);
                            return ok(objectifCsd.render(produits, messages));
                        }
                    });
            return resultPromise;
        }
    }

    public static F.Promise<Result> traitement_objectif_csd(){
        String user = session("connected");
        if(user == null || !user.equals("csd")) {
            return  wrapResultAsPromise(unauthorized("vous devez vous connectez"));
        }else{
            Map<String,String[]> map= request().body().asFormUrlEncoded();
            String str = Outil.map(map);
            final F.Promise<Result> resultPromise = WS
                    .url("http://localhost:9001/objectifCsd")
                    .setContentType("application/x-www-form-urlencoded").post(str)
                    .map(new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            JsonNode json = response.asJson();
                            List<String> messages = new ArrayList<>();
                            messages.add(json.findPath("object0").asText().trim());
                            messages.add(json.findPath("object1").asText().trim());
                            messages.add(json.findPath("object2").asText().trim());
                            messages.add(json.findPath("object3").asText().trim());
                            if(messages.get(0).equals("") && messages.get(1).equals("") && messages.get(2).equals("") && messages.get(3).equals("")){
                                flash("success", "L'objectif a été saisi avec succés.");
                                return redirect(routes.Application.menu());
                            }else{
                                flash("error", "Please correct the form below.");
                                List<Produit> produits = (ArrayList<Produit>) Cache.get("produits");
                                return ok(objectifCsd.render(produits, messages));
                            }
                        }
                    });
            return resultPromise;
        }
    }

    public static F.Promise<Result> objectif_rrb(){
        String user = session("connected");
        final  List<String>  messages = new ArrayList<>();
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        if(user == null || !user.startsWith("rgb")) {
            return  wrapResultAsPromise(unauthorized("vous devez vous connectez"));
        }else if(Cache.get("produits")!= null && Cache.get("boutiques")!= null && Cache.get("franchises")!= null){
            List<Produit> produits = (ArrayList<Produit>) Cache.get("produits");
            List<Boutique> boutiques = (ArrayList<Boutique>) Cache.get("boutiques");
            List<Franchise> franchises = (ArrayList<Franchise>) Cache.get("franchises");
            return wrapResultAsPromise(ok(objectifRrb.render(produits, messages, boutiques, franchises)));
        }
        else{
            final F.Promise<Result> resultPromise = WS
                    .url("http://localhost:9001/objectifRrb")
                    .get()
                    .map(new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            JsonNode json = response.asJson();
                            List<Produit> produits = new ArrayList<>();
                            List<Boutique> boutiques = new ArrayList<>();
                            List<Franchise> franchises = new ArrayList<>();
                            JsonNode produitsNode = json.findPath("object0");
                            JsonNode boutiquesNode = json.findPath("object1");
                            JsonNode franchisesNode = json.findPath("object2");
                            Iterator<JsonNode> iterator = produitsNode.elements();
                            while (iterator.hasNext()) {
                                JsonNode produitNode = iterator.next();
                                try {
                                    produits.add(Outil.readProduit(produitNode));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            }
                            iterator= boutiquesNode.elements();
                            while (iterator.hasNext()) {
                                JsonNode boutiqueNode = iterator.next();
                                try {
                                    boutiques.add(Outil.readBoutique(boutiqueNode));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            }
                            iterator= franchisesNode.elements();
                            while (iterator.hasNext()) {
                                JsonNode franchiseNode = iterator.next();
                                try {
                                    franchises.add(Outil.readFranchise(franchiseNode));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            }
                            Cache.set("produits", produits, 60 * 60 * 24 * 3);
                            Cache.set("boutiques", boutiques, 60 * 60 * 24 * 3);
                            Cache.set("franchises", franchises, 60 * 60 * 24 * 3);
                            return ok(objectifRrb.render(produits, messages, boutiques, franchises));
                        }
                    });
            return resultPromise;
        }
    }

    public static F.Promise<Result> traitement_objectif_rrb(){
        String user = session("connected");
        if(user == null || !user.startsWith("rgb")) {
            return  wrapResultAsPromise(unauthorized("vous devez vous connectez"));
        }else{
            Map<String,String[]> map= request().body().asFormUrlEncoded();
            String str = Outil.map(map);
            final F.Promise<Result> resultPromise = WS
                    .url("http://localhost:9001/objectifRrb")
                    .setContentType("application/x-www-form-urlencoded").post(str)
                    .map(new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            JsonNode json = response.asJson();
                            List<String> messages = new ArrayList<>();
                            messages.add(json.findPath("object0").asText().trim());
                            messages.add(json.findPath("object1").asText().trim());
                            messages.add(json.findPath("object2").asText().trim());
                            messages.add(json.findPath("object3").asText().trim());
                            if(messages.get(0).equals("") && messages.get(1).equals("") && messages.get(2).equals("") && messages.get(3).equals("")){
                                flash("success", "L'objectif a été saisi avec succés.");
                                return redirect(routes.Application.menu());
                            }else{
                                flash("error", "Please correct the form below.");
                                List<Produit> produits = (ArrayList<Produit>) Cache.get("produits");
                                List<Boutique> boutiques = (ArrayList<Boutique>) Cache.get("boutiques");
                                List<Franchise> franchises = (ArrayList<Franchise>) Cache.get("franchises");
                                return ok(objectifRrb.render(produits, messages, boutiques, franchises));
                            }
                        }
                    });
            return resultPromise;
        }
    }

    public static F.Promise<Result> objectif_rrd(){
        String user = session("connected");
        final  List<String>  messages = new ArrayList<>();
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        if(user == null || !user.startsWith("rgd")) {
            return  wrapResultAsPromise(unauthorized("vous devez vous connectez"));
        }else if(Cache.get("produits")!= null && Cache.get("distributeurs")!= null){
            List<Produit> produits = (ArrayList<Produit>) Cache.get("produits");
            List<Distributeur> distributeurs = (ArrayList<Distributeur>) Cache.get("distributeurs");
            return wrapResultAsPromise(ok(objectifRrd.render(produits, messages, distributeurs)));
        }
        else{
            final F.Promise<Result> resultPromise = WS
                    .url("http://localhost:9001/objectifRrd")
                    .get()
                    .map(new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            JsonNode json = response.asJson();
                            List<Produit> produits = new ArrayList<>();
                            List<Distributeur> distributeurs = new ArrayList<>();
                            JsonNode produitsNode = json.findPath("object0");
                            JsonNode distributeursNode = json.findPath("object1");
                            Iterator<JsonNode> iterator = produitsNode.elements();
                            while (iterator.hasNext()) {
                                JsonNode produitNode = iterator.next();
                                try {
                                    produits.add(Outil.readProduit(produitNode));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            }
                            iterator = distributeursNode.elements();
                            while (iterator.hasNext()) {
                                JsonNode distributeurNode = iterator.next();
                                try {
                                    distributeurs.add(Outil.readDistributeur(distributeurNode));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            }
                            Cache.set("produits", produits, 60 * 60 * 24 * 3);
                            Cache.set("distributeurs", distributeurs, 60 * 60 * 24 * 3);
                            return ok(objectifRrd.render(produits, messages, distributeurs));
                        }
                    });
            return resultPromise;
        }
    }

    public static F.Promise<Result> traitement_objectif_rrd(){
        String user = session("connected");
        if(user == null || !user.startsWith("rgd")) {
            return  wrapResultAsPromise(unauthorized("vous devez vous connectez"));
        }else{
            Map<String,String[]> map= request().body().asFormUrlEncoded();
            String str = Outil.map(map);
            final F.Promise<Result> resultPromise = WS
                    .url("http://localhost:9001/objectifRrd")
                    .setContentType("application/x-www-form-urlencoded").post(str)
                    .map(new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            JsonNode json = response.asJson();
                            List<String> messages = new ArrayList<>();
                            messages.add(json.findPath("object0").asText().trim());
                            messages.add(json.findPath("object1").asText().trim());
                            messages.add(json.findPath("object2").asText().trim());
                            messages.add(json.findPath("object3").asText().trim());
                            if (messages.get(0).equals("") && messages.get(1).equals("") && messages.get(2).equals("") && messages.get(3).equals("")) {
                                flash("success", "L'objectif a été saisi avec succés.");
                                return redirect(routes.Application.menu());
                            } else {
                                flash("error", "Please correct the form below.");
                                List<Produit> produits = (ArrayList<Produit>) Cache.get("produits");
                                List<Distributeur> distributeurs  = (ArrayList<Distributeur>) Cache.get("distributeurs");
                                return ok(objectifRrd.render(produits, messages, distributeurs));
                            }
                        }
                    });
            return resultPromise;
        }
    }

    public static F.Promise<Result> objectif_rrp(){
        String user = session("connected");
        final  List<String>  messages = new ArrayList<>();
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        if(user == null || !user.startsWith("rgp")) {
            return  wrapResultAsPromise(unauthorized("vous devez vous connectez"));
        }/*else if(Cache.get("produits")!= null && Cache.get("pdvs")!= null){
            List<Produit> produits = (ArrayList<Produit>) Cache.get("produits");
            List<Pdv> pdvs = (ArrayList<Pdv>) Cache.get("pdvs");
            return wrapResultAsPromise(ok(objectifRrp.render(produits, messages, pdvs)));
        }*/
        else{
            final F.Promise<Result> resultPromise = WS
                    .url("http://localhost:9001/objectifRrp")
                    .get()
                    .map(new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            JsonNode json = response.asJson();
                            List<Produit> produits = new ArrayList<>();
                            List<Pdv> pdvs = new ArrayList<>();
                            JsonNode produitsNode = json.findPath("object0");
                            JsonNode pdvsNode = json.findPath("object1");
                            Iterator<JsonNode> iterator = produitsNode.elements();
                            while (iterator.hasNext()) {
                                JsonNode produitNode = iterator.next();
                                try {
                                    produits.add(Outil.readProduit(produitNode));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            }
                            iterator  = pdvsNode.elements();
                            while (iterator.hasNext()) {
                                JsonNode pdvNode = iterator.next();
                                try {
                                    pdvs.add(Outil.readPdv(pdvNode));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            }
                            Cache.set("produits", produits, 60 * 60 * 24 * 3);
                            Cache.set("pdvs", pdvs, 60 * 60 * 24 * 3);
                            return ok(objectifRrp.render(produits, messages, pdvs));
                        }
                    });
            return resultPromise;
        }
    }

    public static F.Promise<Result> traitement_objectif_rrp(){
        String user = session("connected");
        if(user == null || !user.startsWith("rgp")) {
            return  wrapResultAsPromise(unauthorized("vous devez vous connectez"));
        }else{
            Map<String,String[]> map= request().body().asFormUrlEncoded();
            String str = Outil.map(map);
            final F.Promise<Result> resultPromise = WS
                    .url("http://localhost:9001/objectifRrp")
                    .setContentType("application/x-www-form-urlencoded").post(str)
                    .map(new F.Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            JsonNode json = response.asJson();
                            List<String> messages = new ArrayList<>();
                            messages.add(json.findPath("object0").asText().trim());
                            messages.add(json.findPath("object1").asText().trim());
                            messages.add(json.findPath("object2").asText().trim());
                            messages.add(json.findPath("object3").asText().trim());
                            if(messages.get(0).equals("") && messages.get(1).equals("") && messages.get(2).equals("") && messages.get(3).equals("")){
                                flash("success", "L'objectif a été saisi avec succés.");
                                return redirect(routes.Application.menu());
                            }else{
                                flash("error", "Please correct the form below.");
                                List<Produit> produits = (ArrayList<Produit>) Cache.get("produits");
                                List<Pdv> pdvs = (ArrayList<Pdv>) Cache.get("pdvs");
                                return ok(objectifRrp.render(produits, messages, pdvs));
                            }
                        }
                    });
            return resultPromise;
        }
    }

    //action pour la cnx de directeur commerciale
    public static Result cnx_dc(){
        List<String> messages = new ArrayList<>();
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        return ok(cnx_dc.render(dcForm, messages));
    }

    //action pour la cnx de chef departement direct
    public static Result cnx_cdd(){
        List<String> messages = new ArrayList<>();
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        return ok(cnx_cdd.render(cddForm, messages));
    }

    //action pour la cnx de chef departement indirect
    public static Result cnx_cdi() {
        List<String> messages = new ArrayList<>();
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        return ok(cnx_cdi.render(cdiForm, messages));
    }

    //action pour la cnx de chef service distribution
    public static Result cnx_csd(){
            List<String> messages = new ArrayList<>();
            messages.add("");
            messages.add("");
            messages.add("");
            messages.add("");
            return ok(cnx_csd.render(csdForm, messages));
    }

    //action pour la connexion de chef sercice pdv
    public static Result cnx_csp() {
        List<String> messages = new ArrayList<>();
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        return ok(cnx_csp.render(cspForm, messages));
    }

    //action pour la connexion de responsable regionale boutique
    public static Result cnx_rrb(){
        List<String> messages = new ArrayList<>();
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        return ok(cnx_rrb.render(rrbForm, messages));
    }

    //action pour la connexion de responsable regionale pdv
    public static Result cnx_rrp() {
        List<String> messages = new ArrayList<>();
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        return ok(cnx_rrp.render(rrpForm, messages));
    }

    // action pour la connexion de responsable regionale distribution
    public static Result cnx_rrd(){
        List<String> messages = new ArrayList<>();
        messages.add("");
        messages.add("");
        messages.add("");
        messages.add("");
        return ok(cnx_rrd.render(rrdForm, messages));
    }

    //action pour le traitement de connexion de directeur commerciale
    public static F.Promise<Result> traitement_dc() {
        Map<String, String[]> map = request().body().asFormUrlEncoded();
        String str = Outil.map(map);
        final F.Promise<Result> resultPromise = WS
                .url("http://localhost:9001/traitementDc")
                .setContentType("application/x-www-form-urlencoded").post(str)
                .map(new F.Function<WS.Response, Result>() {
                    public Result apply(WS.Response response) {
                        JsonNode json = response.asJson();
                        List<String> messages = new ArrayList<>();
                        Form<DirecteurCommercial> boundForm = dcForm.bind(json.findPath("object0"));
                        messages.add(json.findPath("object1").asText().trim());
                        messages.add(json.findPath("object2").asText().trim());
                        messages.add(json.findPath("object3").asText().trim());
                        messages.add(json.findPath("object4").asText().trim());
                        if (!messages.get(0).equals("") || !messages.get(1).equals("") || !messages.get(2).equals("") || !messages.get(3).equals("")) {
                            flash("error", "Please correct the form below.");
                            return ok(cnx_dc.render(boundForm, messages));
                        } else {
                            flash("success", "connexion success");
                            session("connected", boundForm.get().login);
                            return redirect(routes.Application.menu());
                        }
                    }
                });
        return resultPromise;
    }

    //action pour le traitement de connexion de chef departement direct

    public static F.Promise<Result> traitement_cdd(){
        Map<String,String[]> map= request().body().asFormUrlEncoded();
        String str = Outil.map(map);
        final F.Promise<Result> resultPromise = WS
                .url("http://localhost:9001/traitementCdd")
                .setContentType("application/x-www-form-urlencoded").post(str)
                .map(new F.Function<WS.Response, Result>() {
                    public Result apply(WS.Response response) {
                        JsonNode json = response.asJson();
                        List<String> messages = new ArrayList<>();
                        Form<ChefDepartementDirect> boundForm = cddForm.bind(json.findPath("object0"));
                        messages.add(json.findPath("object1").asText().trim());
                        messages.add(json.findPath("object2").asText().trim());
                        messages.add(json.findPath("object3").asText().trim());
                        messages.add(json.findPath("object4").asText().trim());
                        if (!messages.get(0).equals("") || !messages.get(1).equals("") || !messages.get(2).equals("") || !messages.get(3).equals("")) {
                            flash("error", "Please correct the form below.");
                            return ok(cnx_cdd.render(boundForm, messages));
                        } else {
                            flash("success", "connexion success");
                            session("connected", boundForm.get().login);
                            return redirect(routes.Application.menu());
                        }
                    }
                });
        return resultPromise;
    }

    //action pour le traitement de connexion de Chef Service Distribution

    public static F.Promise<Result> traitement_csd(){
        Map<String,String[]> map= request().body().asFormUrlEncoded();
        String str = Outil.map(map);
        final F.Promise<Result> resultPromise = WS
                .url("http://localhost:9001/traitementCsd")
                .setContentType("application/x-www-form-urlencoded").post(str)
                .map(new F.Function<WS.Response, Result>() {
                    public Result apply(WS.Response response) {
                        JsonNode json = response.asJson();
                        List<String> messages = new ArrayList<>();
                        Form<ChefServiceDistribution> boundForm = csdForm.bind(json.findPath("object0"));
                        messages.add(json.findPath("object1").asText().trim());
                        messages.add(json.findPath("object2").asText().trim());
                        messages.add(json.findPath("object3").asText().trim());
                        messages.add(json.findPath("object4").asText().trim());
                        if (!messages.get(0).equals("") || !messages.get(1).equals("") || !messages.get(2).equals("") || !messages.get(3).equals("")) {
                            flash("error", "Please correct the form below.");
                            return ok(cnx_csd.render(boundForm, messages));
                        } else {
                            flash("success", "connexion success");
                            session("connected", boundForm.get().login);
                            return redirect(routes.Application.menu());
                        }
                    }
                });
        return resultPromise;
    }

    //action pour le traitement de connexion de responsable regionale boutique
    public static F.Promise<Result> traitement_rrb(){
        Map<String,String[]> map= request().body().asFormUrlEncoded();
        String str = Outil.map(map);
        final F.Promise<Result> resultPromise = WS
                .url("http://localhost:9001/traitementRrb")
                .setContentType("application/x-www-form-urlencoded").post(str)
                .map(new F.Function<WS.Response, Result>() {
                    public Result apply(WS.Response response) {
                        JsonNode json = response.asJson();
                        List<String> messages = new ArrayList<>();
                        Form<ResponsableRegionalBoutique> boundForm = rrbForm.bind(json.findPath("object0"));
                        messages.add(json.findPath("object1").asText().trim());
                        messages.add(json.findPath("object2").asText().trim());
                        messages.add(json.findPath("object3").asText().trim());
                        messages.add(json.findPath("object4").asText().trim());
                        if (!messages.get(0).equals("") || !messages.get(1).equals("") || !messages.get(2).equals("") || !messages.get(3).equals("")) {
                            flash("error", "Please correct the form below.");
                            return ok(cnx_rrb.render(boundForm, messages));
                        } else {
                            flash("success", "connexion success");
                            session("connected", boundForm.get().login);
                            return redirect(routes.Application.menu());
                        }
                    }
                });
        return resultPromise;
    }

    //action pour le traitement de connexion de responsable regionale distribution

    public static F.Promise<Result> traitement_rrd(){
        Map<String,String[]> map= request().body().asFormUrlEncoded();
        String str = Outil.map(map);
        final F.Promise<Result> resultPromise = WS
                .url("http://localhost:9001/traitementRrd")
                .setContentType("application/x-www-form-urlencoded").post(str)
                .map(new F.Function<WS.Response, Result>() {
                    public Result apply(WS.Response response) {
                        JsonNode json = response.asJson();
                        List<String> messages = new ArrayList<>();
                        Form<ResponsableRegionalDistribution> boundForm = rrdForm.bind(json.findPath("object0"));
                        messages.add(json.findPath("object1").asText().trim());
                        messages.add(json.findPath("object2").asText().trim());
                        messages.add(json.findPath("object3").asText().trim());
                        messages.add(json.findPath("object4").asText().trim());
                        if (!messages.get(0).equals("") || !messages.get(1).equals("") || !messages.get(2).equals("") || !messages.get(3).equals("")) {
                            flash("error", "Please correct the form below.");
                            return ok(cnx_rrd.render(boundForm, messages));
                        } else {
                            flash("success", "connexion success");
                            session("connected", boundForm.get().login);
                            return redirect(routes.Application.menu());
                        }
                    }
                });
        return resultPromise;
    }

    //action du traitement de la cnx du CDI
    public static F.Promise<Result> traitement_cdi() {
        Map<String, String[]> map = request().body().asFormUrlEncoded();
        String str = Outil.map(map);
        final F.Promise<Result> resultPromise = WS
                .url("http://localhost:9001/traitementCdi")
                .setContentType("application/x-www-form-urlencoded").post(str)
                .map(new F.Function<WS.Response, Result>() {
                    public Result apply(WS.Response response) {
                        JsonNode json = response.asJson();
                        List<String> messages = new ArrayList<>();
                        Form<ChefDepartementIndirect> boundForm = cdiForm.bind(json.findPath("object0"));
                        messages.add(json.findPath("object1").asText().trim());
                        messages.add(json.findPath("object2").asText().trim());
                        messages.add(json.findPath("object3").asText().trim());
                        messages.add(json.findPath("object4").asText().trim());
                        if (!messages.get(0).equals("") || !messages.get(1).equals("") || !messages.get(2).equals("") || !messages.get(3).equals("")) {
                            flash("error", "Please correct the form below.");
                            return ok(cnx_cdi.render(boundForm, messages));
                        } else {
                            flash("success", "connexion success");
                            session("connected", boundForm.get().login);
                            return redirect(routes.Application.menu());
                        }
                    }
                });
        return resultPromise;
    }

    //action du traitement de la cnx du CSP
    public static F.Promise<Result> traitement_csp() {
        Map<String, String[]> map = request().body().asFormUrlEncoded();
        String str = Outil.map(map);
        final F.Promise<Result> resultPromise = WS
                .url("http://localhost:9001/traitementCsp")
                .setContentType("application/x-www-form-urlencoded").post(str)
                .map(new F.Function<WS.Response, Result>() {
                    public Result apply(WS.Response response) {
                        JsonNode json = response.asJson();
                        List<String> messages = new ArrayList<>();
                        Form<ChefServicePdv> boundForm = cspForm.bind(json.findPath("object0"));
                        messages.add(json.findPath("object1").asText().trim());
                        messages.add(json.findPath("object2").asText().trim());
                        messages.add(json.findPath("object3").asText().trim());
                        messages.add(json.findPath("object4").asText().trim());
                        if (!messages.get(0).equals("") || !messages.get(1).equals("") || !messages.get(2).equals("") || !messages.get(3).equals("")) {
                            flash("error", "Please correct the form below.");
                            return ok(cnx_csp.render(boundForm, messages));
                        } else {
                            flash("success", "connexion success");
                            session("connected", boundForm.get().login);
                            return redirect(routes.Application.menu());
                        }
                    }
                });
        return resultPromise;
    }

    //action du traitement de la cnx du RRP
    public static F.Promise<Result> traitement_rrp() {
        Map<String, String[]> map = request().body().asFormUrlEncoded();
        String str = Outil.map(map);
        final F.Promise<Result> resultPromise = WS
                .url("http://localhost:9001/traitementRrp")
                .setContentType("application/x-www-form-urlencoded").post(str)
                .map(new F.Function<WS.Response, Result>() {
                    public Result apply(WS.Response response) {
                        JsonNode json = response.asJson();
                        List<String> messages = new ArrayList<>();
                        Form<ResponsableRegionalPdv> boundForm = rrpForm.bind(json.findPath("object0"));
                        messages.add(json.findPath("object1").asText().trim());
                        messages.add(json.findPath("object2").asText().trim());
                        messages.add(json.findPath("object3").asText().trim());
                        messages.add(json.findPath("object4").asText().trim());
                        if (!messages.get(0).equals("") || !messages.get(1).equals("") || !messages.get(2).equals("") || !messages.get(3).equals("")) {
                            flash("error", "Please correct the form below.");
                            return ok(cnx_rrp.render(boundForm, messages));
                        } else {
                            flash("success", "connexion success");
                            session("connected", boundForm.get().login);
                            return redirect(routes.Application.menu());
                        }
                    }
                });
        return resultPromise;
    }

}







































