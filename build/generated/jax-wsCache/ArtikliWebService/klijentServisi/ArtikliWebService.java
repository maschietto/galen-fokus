
package klijentServisi;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10-b140803.1500
 * Generated source version: 2.1
 * 
 */
@WebService(name = "ArtikliWebService", targetNamespace = "http://servisi/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ArtikliWebService {


    /**
     * 
     * @param name
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "hello", targetNamespace = "http://servisi/", className = "klijentServisi.Hello")
    @ResponseWrapper(localName = "helloResponse", targetNamespace = "http://servisi/", className = "klijentServisi.HelloResponse")
    public String hello(
        @WebParam(name = "name", targetNamespace = "")
        String name);

    /**
     * 
     * @return
     *     returns java.util.List<klijentServisi.TabelaArtikli>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "vratiOrtopanArtikle", targetNamespace = "http://servisi/", className = "klijentServisi.VratiOrtopanArtikle")
    @ResponseWrapper(localName = "vratiOrtopanArtikleResponse", targetNamespace = "http://servisi/", className = "klijentServisi.VratiOrtopanArtikleResponse")
    public List<TabelaArtikli> vratiOrtopanArtikle();

}
