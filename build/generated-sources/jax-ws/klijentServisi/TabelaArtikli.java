
package klijentServisi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tabelaArtikli complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tabelaArtikli">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cena" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="idArtikla" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nazivArtikla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tabelaArtikli", propOrder = {
    "cena",
    "idArtikla",
    "nazivArtikla"
})
public class TabelaArtikli {

    protected Integer cena;
    protected Integer idArtikla;
    protected String nazivArtikla;

    /**
     * Gets the value of the cena property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCena() {
        return cena;
    }

    /**
     * Sets the value of the cena property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCena(Integer value) {
        this.cena = value;
    }

    /**
     * Gets the value of the idArtikla property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdArtikla() {
        return idArtikla;
    }

    /**
     * Sets the value of the idArtikla property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdArtikla(Integer value) {
        this.idArtikla = value;
    }

    /**
     * Gets the value of the nazivArtikla property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNazivArtikla() {
        return nazivArtikla;
    }

    /**
     * Sets the value of the nazivArtikla property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNazivArtikla(String value) {
        this.nazivArtikla = value;
    }

}
