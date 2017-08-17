
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
 *         &lt;element name="UnsubscribeRecordingStopEventResult" type="{http://schemas.datacontract.org/2004/07/VCLogWebSDK}WebSDKReturn" minOccurs="0"/>
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
    "unsubscribeRecordingStopEventResult"
})
@XmlRootElement(name = "UnsubscribeRecordingStopEventResponse")
public class UnsubscribeRecordingStopEventResponse {

    @XmlElementRef(name = "UnsubscribeRecordingStopEventResult", namespace = "http://www.voicecyber.com/", type = JAXBElement.class)
    protected JAXBElement<WebSDKReturn> unsubscribeRecordingStopEventResult;

    /**
     * Gets the value of the unsubscribeRecordingStopEventResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}
     *     
     */
    public JAXBElement<WebSDKReturn> getUnsubscribeRecordingStopEventResult() {
        return unsubscribeRecordingStopEventResult;
    }

    /**
     * Sets the value of the unsubscribeRecordingStopEventResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}
     *     
     */
    public void setUnsubscribeRecordingStopEventResult(JAXBElement<WebSDKReturn> value) {
        this.unsubscribeRecordingStopEventResult = ((JAXBElement<WebSDKReturn> ) value);
    }

}
