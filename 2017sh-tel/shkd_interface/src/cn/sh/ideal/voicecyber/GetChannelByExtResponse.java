
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
 *         &lt;element name="GetChannelByExtResult" type="{http://schemas.datacontract.org/2004/07/VCLogWebSDK}WebSDKReturn" minOccurs="0"/>
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
    "getChannelByExtResult"
})
@XmlRootElement(name = "GetChannelByExtResponse")
public class GetChannelByExtResponse {

    @XmlElementRef(name = "GetChannelByExtResult", namespace = "http://www.voicecyber.com/", type = JAXBElement.class)
    protected JAXBElement<WebSDKReturn> getChannelByExtResult;

    /**
     * Gets the value of the getChannelByExtResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}
     *     
     */
    public JAXBElement<WebSDKReturn> getGetChannelByExtResult() {
        return getChannelByExtResult;
    }

    /**
     * Sets the value of the getChannelByExtResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}
     *     
     */
    public void setGetChannelByExtResult(JAXBElement<WebSDKReturn> value) {
        this.getChannelByExtResult = ((JAXBElement<WebSDKReturn> ) value);
    }

}
