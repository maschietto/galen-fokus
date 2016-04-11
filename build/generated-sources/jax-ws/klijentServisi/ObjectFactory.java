
package klijentServisi;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the klijentServisi package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _VratiOrtopanArtikle_QNAME = new QName("http://servisi/", "vratiOrtopanArtikle");
    private final static QName _HelloResponse_QNAME = new QName("http://servisi/", "helloResponse");
    private final static QName _VratiOrtopanArtikleResponse_QNAME = new QName("http://servisi/", "vratiOrtopanArtikleResponse");
    private final static QName _Hello_QNAME = new QName("http://servisi/", "hello");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: klijentServisi
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Hello }
     * 
     */
    public Hello createHello() {
        return new Hello();
    }

    /**
     * Create an instance of {@link VratiOrtopanArtikleResponse }
     * 
     */
    public VratiOrtopanArtikleResponse createVratiOrtopanArtikleResponse() {
        return new VratiOrtopanArtikleResponse();
    }

    /**
     * Create an instance of {@link HelloResponse }
     * 
     */
    public HelloResponse createHelloResponse() {
        return new HelloResponse();
    }

    /**
     * Create an instance of {@link VratiOrtopanArtikle }
     * 
     */
    public VratiOrtopanArtikle createVratiOrtopanArtikle() {
        return new VratiOrtopanArtikle();
    }

    /**
     * Create an instance of {@link TabelaArtikli }
     * 
     */
    public TabelaArtikli createTabelaArtikli() {
        return new TabelaArtikli();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VratiOrtopanArtikle }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servisi/", name = "vratiOrtopanArtikle")
    public JAXBElement<VratiOrtopanArtikle> createVratiOrtopanArtikle(VratiOrtopanArtikle value) {
        return new JAXBElement<VratiOrtopanArtikle>(_VratiOrtopanArtikle_QNAME, VratiOrtopanArtikle.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servisi/", name = "helloResponse")
    public JAXBElement<HelloResponse> createHelloResponse(HelloResponse value) {
        return new JAXBElement<HelloResponse>(_HelloResponse_QNAME, HelloResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VratiOrtopanArtikleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servisi/", name = "vratiOrtopanArtikleResponse")
    public JAXBElement<VratiOrtopanArtikleResponse> createVratiOrtopanArtikleResponse(VratiOrtopanArtikleResponse value) {
        return new JAXBElement<VratiOrtopanArtikleResponse>(_VratiOrtopanArtikleResponse_QNAME, VratiOrtopanArtikleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Hello }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servisi/", name = "hello")
    public JAXBElement<Hello> createHello(Hello value) {
        return new JAXBElement<Hello>(_Hello_QNAME, Hello.class, null, value);
    }

}
