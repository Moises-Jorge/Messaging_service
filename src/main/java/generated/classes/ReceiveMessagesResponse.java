//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.25 at 11:30:08 AM WAT 
//


package generated.classes;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="messages" type="{http://www.classes.generated}messageList"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "messages"
})
@XmlRootElement(name = "receiveMessagesResponse")
public class ReceiveMessagesResponse {

    @XmlElement(required = true)
    protected MessageList messages;

    /**
     * Gets the value of the messages property.
     * 
     * @return
     *     possible object is
     *     {@link MessageList }
     *     
     */
    public MessageList getMessages() {
        return messages;
    }

    /**
     * Sets the value of the messages property.
     * 
     * @param list
     *     allowed object is
     *     {@link MessageList }
     *     
     */
    public void setMessages(List<MessageList> list) { // EM CASO DE BUG, ANALISAR AQUI(JUNTAMENTE COM O BUG EM "MessageList")...
        this.messages = (MessageList) list;
    }

}
