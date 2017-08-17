
package cn.sh.ideal.voicecyber;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetVoiceCountResult" type="{http://schemas.datacontract.org/2004/07/VCLogWebSDK}WebSDKReturn" minOccurs="0"/>
 *         &lt;element name="voiceid" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfint" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getVoiceCountResult",
    "voiceid"
})
@XmlRootElement(name = "GetVoiceCountResponse")
public class GetVoiceCountResponse {

    @XmlElementRef(name = "GetVoiceCountResult", namespace = "http://www.voicecyber.com/", type = JAXBElement.class)
    protected JAXBElement<WebSDKReturn> getVoiceCountResult;
    @XmlElementRef(name = "voiceid", namespace = "http://www.voicecyber.com/", type = JAXBElement.class)
    protected JAXBElement<ArrayOfint> voiceid;

    /**
     * Gets the value of the getVoiceCountResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}
     *     
     */
    public JAXBElement<WebSDKReturn> getGetVoiceCountResult() {
        return getVoiceCountResult;
    }

    /**
     * Sets the value of the getVoiceCountResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}
     *     
     */
    public void setGetVoiceCountResult(JAXBElement<WebSDKReturn> value) {
        this.getVoiceCountResult = ((JAXBElement<WebSDKReturn> ) value);
    }

    /**
     * Gets the value of the voiceid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfint }{@code >}
     *     
     */
    public JAXBElement<ArrayOfint> getVoiceid() {
        return voiceid;
    }

    /**
     * Sets the value of the voiceid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfint }{@code >}
     *     
     */
    public void setVoiceid(JAXBElement<ArrayOfint> value) {
        this.voiceid = ((JAXBElement<ArrayOfint> ) value);
    }

}
