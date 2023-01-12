package si.fri.rso.samples.scrapper.services.beans;

//import si.fri.rso.samples.artikli.lib.Artikli;
//import si.fri.rso.samples.artikli.models.converters.ArtikliConverter;
//import si.fri.rso.samples.artikli.models.entities.ArtikliEntity;

import javax.enterprise.context.RequestScoped;
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.io.IOException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@RequestScoped
public class ScrapperBean {

    private Logger log = Logger.getLogger(ScrapperBean.class.getName());

    public List<Float> getScrapperPrice(String artikelName) {
        ArrayList<Float> list = new ArrayList<Float>();

        float valueTus = scrapeTus(artikelName);
        float valueJager = scrapeSN(artikelName);
        if (valueTus > 0) list.add(valueTus); else list.add((float) 0);
        if (valueJager > 0) list.add(valueJager); else list.add((float) 0);
        return list;
    }

    public static float scrapeSN(String artikelName) {
        float price = 0;
        try {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true); // enable javascript
            webClient.getOptions().setThrowExceptionOnScriptError(false); //even if there is error in js continue
            webClient.waitForBackgroundJavaScript(5000); // important! wait until javascript finishes rendering
            String url = "https://www.spletninakupi.si/iskanje?q="+artikelName+"&sort=name-asc&s=&cena-od=&cena-do=";
            System.out.println(url);
            HtmlPage page = webClient.getPage(url);

            /**
             * Here we create a document object,
             * Then we use JSoup to fetch the website.
             */
            Document doc = Jsoup.parse(page.asXml());

            Elements repositories = doc.getElementsByClass("discounted-price");
            if (!repositories.isEmpty()) {
                Element repository = repositories.first();
                price = Float.parseFloat(repository.text().replace(',','.').replace('€',' '));
                System.out.println(price);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return price;
    }

    public static float scrapeTus(String artikelName){
        float price = 0;
        try {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true); // enable javascript
            webClient.getOptions().setThrowExceptionOnScriptError(false); //even if there is error in js continue
            webClient.waitForBackgroundJavaScript(5000); // important! wait until javascript finishes rendering
            HtmlPage page = webClient.getPage("https://www.tus.si/?s="+artikelName+"&post_type=product");

            Document doc = Jsoup.parse(page.asXml());
            Elements repositories = doc.getElementsByClass("price");
            if (!repositories.isEmpty()) {
                Element repository = repositories.first();
                price = Float.parseFloat(repository.text().replace(',','.').replace('€',' '));
                System.out.println(price);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return price;
    }



//    @Inject
//    private EntityManager em;
//
//    public List<Artikli> getArtikli() {
//
//        TypedQuery<ArtikliEntity> query = em.createNamedQuery(
//                "ArtikliEntity.getAll", ArtikliEntity.class);
//
//        List<ArtikliEntity> resultList = query.getResultList();
//
//        return resultList.stream().map(ArtikliConverter::toDto).collect(Collectors.toList());
//
//    }
//
//    @Timed
//    public List<Artikli> getArtikliFilter(UriInfo uriInfo) {
//
//        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
//                .build();
//
//        return JPAUtils.queryEntities(em, ArtikliEntity.class, queryParameters).stream()
//                .map(ArtikliConverter::toDto).collect(Collectors.toList());
//    }
//
//    public Artikli getArtikli(Integer id) {
//
//        ArtikliEntity artikliEntity = em.find(ArtikliEntity.class, id);
//
//        if (artikliEntity == null) {
//            throw new NotFoundException();
//        }
//
//        Artikli artikli = ArtikliConverter.toDto(artikliEntity);
//
//        return artikli;
//    }
//
//    public Artikli createArtikli(Artikli artikli) {
//
//        ArtikliEntity artikliEntity = ArtikliConverter.toEntity(artikli);
//
//        try {
//            beginTx();
//            em.persist(artikliEntity);
//            commitTx();
//        }
//        catch (Exception e) {
//            rollbackTx();
//        }
//
//        if (artikliEntity.getId() == null) {
//            throw new RuntimeException("Entity was not persisted");
//        }
//
//        return ArtikliConverter.toDto(artikliEntity);
//    }
//
//    public Artikli putArtikli(Integer id, Artikli artikli) {
//
//        ArtikliEntity c = em.find(ArtikliEntity.class, id);
//
//        if (c == null) {
//            return null;
//        }
//
//        ArtikliEntity updatedArtikliEntity = ArtikliConverter.toEntity(artikli);
//
//        try {
//            beginTx();
//            updatedArtikliEntity.setId(c.getId());
//            updatedArtikliEntity = em.merge(updatedArtikliEntity);
//            commitTx();
//        }
//        catch (Exception e) {
//            rollbackTx();
//        }
//
//        return ArtikliConverter.toDto(updatedArtikliEntity);
//    }
//
//    public boolean deleteArtikli(Integer id) {
//
//        ArtikliEntity Artikli = em.find(ArtikliEntity.class, id);
//
//        if (Artikli != null) {
//            try {
//                beginTx();
//                em.remove(Artikli);
//                commitTx();
//            }
//            catch (Exception e) {
//                rollbackTx();
//            }
//        }
//        else {
//            return false;
//        }
//
//        return true;
//    }
//
//    private void beginTx() {
//        if (!em.getTransaction().isActive()) {
//            em.getTransaction().begin();
//        }
//    }
//
//    private void commitTx() {
//        if (em.getTransaction().isActive()) {
//            em.getTransaction().commit();
//        }
//    }
//
//    private void rollbackTx() {
//        if (em.getTransaction().isActive()) {
//            em.getTransaction().rollback();
//        }
//    }
}
