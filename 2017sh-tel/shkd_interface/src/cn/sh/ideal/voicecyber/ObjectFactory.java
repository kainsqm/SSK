
package cn.sh.ideal.voicecyber;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.voicecyber package. 
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

    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _WebSDKReturn_QNAME = new QName("http://schemas.datacontract.org/2004/07/VCLogWebSDK", "WebSDKReturn");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _ArrayOfint_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfint");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _UpdateRecordDataByExtData_QNAME = new QName("http://www.voicecyber.com/", "Data");
    private final static QName _UpdateRecordDataByExtExtension_QNAME = new QName("http://www.voicecyber.com/", "Extension");
    private final static QName _GetChannelCountByVoiceIDResponseGetChannelCountByVoiceIDResult_QNAME = new QName("http://www.voicecyber.com/", "GetChannelCountByVoiceIDResult");
    private final static QName _GetRefIDResponseGetRefIDResult_QNAME = new QName("http://www.voicecyber.com/", "GetRefIDResult");
    private final static QName _GetFileHttpPathResponseGetFileHttpPathResult_QNAME = new QName("http://www.voicecyber.com/", "GetFileHttpPathResult");
    private final static QName _StopRecordFResponseStopRecordFResult_QNAME = new QName("http://www.voicecyber.com/", "StopRecordFResult");
    private final static QName _UpdateRecordDataByUCIDResponseUpdateRecordDataByUCIDResult_QNAME = new QName("http://www.voicecyber.com/", "UpdateRecordDataByUCIDResult");
    private final static QName _GetExtensionStatusResponseGetExtensionStatusResult_QNAME = new QName("http://www.voicecyber.com/", "GetExtensionStatusResult");
    private final static QName _UpdateRecordDataByUCIDUCID_QNAME = new QName("http://www.voicecyber.com/", "UCID");
    private final static QName _GetRecordListJsonOtherConditions_QNAME = new QName("http://www.voicecyber.com/", "JsonOtherConditions");
    private final static QName _GetRecordListEnd_QNAME = new QName("http://www.voicecyber.com/", "end");
    private final static QName _GetRecordListFrom_QNAME = new QName("http://www.voicecyber.com/", "from");
    private final static QName _SaveRecordFileRecordReference_QNAME = new QName("http://www.voicecyber.com/", "RecordReference");
    private final static QName _GetVoiceCountVoiceid_QNAME = new QName("http://www.voicecyber.com/", "voiceid");
    private final static QName _AgentLoginAgentID_QNAME = new QName("http://www.voicecyber.com/", "AgentID");
    private final static QName _StartRecordFResponseStartRecordFResult_QNAME = new QName("http://www.voicecyber.com/", "StartRecordFResult");
    private final static QName _GetRefIDByReservedFieldResponseGetRefIDByReservedFieldResult_QNAME = new QName("http://www.voicecyber.com/", "GetRefIDByReservedFieldResult");
    private final static QName _PauseRecordFResponsePauseRecordFResult_QNAME = new QName("http://www.voicecyber.com/", "PauseRecordFResult");
    private final static QName _AgentLoginResponseAgentLoginResult_QNAME = new QName("http://www.voicecyber.com/", "AgentLoginResult");
    private final static QName _StopRecordResponseStopRecordResult_QNAME = new QName("http://www.voicecyber.com/", "StopRecordResult");
    private final static QName _UpdateRecordDataByIDResponseUpdateRecordDataByIDResult_QNAME = new QName("http://www.voicecyber.com/", "UpdateRecordDataByIDResult");
    private final static QName _GetRecordArchiveStatusResponseGetRecordArchiveStatusResult_QNAME = new QName("http://www.voicecyber.com/", "GetRecordArchiveStatusResult");
    private final static QName _ResumeRecordFResponseResumeRecordFResult_QNAME = new QName("http://www.voicecyber.com/", "ResumeRecordFResult");
    private final static QName _PauseRecordResponsePauseRecordResult_QNAME = new QName("http://www.voicecyber.com/", "PauseRecordResult");
    private final static QName _GetFilePathbyReservedFieldsResponseGetFilePathbyReservedFieldsResult_QNAME = new QName("http://www.voicecyber.com/", "GetFilePathbyReservedFieldsResult");
    private final static QName _GetChannelByExtResponseGetChannelByExtResult_QNAME = new QName("http://www.voicecyber.com/", "GetChannelByExtResult");
    private final static QName _GetRefIDByReservedFieldCondition_QNAME = new QName("http://www.voicecyber.com/", "condition");
    private final static QName _GetChannelByExtExtension_QNAME = new QName("http://www.voicecyber.com/", "extension");
    private final static QName _SubscribeRecordingStopEventResponseSubscribeRecordingStopEventResult_QNAME = new QName("http://www.voicecyber.com/", "SubscribeRecordingStopEventResult");
    private final static QName _UpdateRecordDataByExtResponseUpdateRecordDataByExtResult_QNAME = new QName("http://www.voicecyber.com/", "UpdateRecordDataByExtResult");
    private final static QName _SaveRecordFileResponseSaveRecordFileResult_QNAME = new QName("http://www.voicecyber.com/", "SaveRecordFileResult");
    private final static QName _GetRecordListByStartRecordTimeResponseGetRecordListByStartRecordTimeResult_QNAME = new QName("http://www.voicecyber.com/", "GetRecordListByStartRecordTimeResult");
    private final static QName _GetVoiceCountResponseGetVoiceCountResult_QNAME = new QName("http://www.voicecyber.com/", "GetVoiceCountResult");
    private final static QName _UpdateRecordDataByIDRefID_QNAME = new QName("http://www.voicecyber.com/", "RefID");
    private final static QName _WebSDKReturnMessage_QNAME = new QName("http://schemas.datacontract.org/2004/07/VCLogWebSDK", "Message");
    private final static QName _WebSDKReturnStringValue_QNAME = new QName("http://schemas.datacontract.org/2004/07/VCLogWebSDK", "StringValue");
    private final static QName _GetRecordListResponseGetRecordListResult_QNAME = new QName("http://www.voicecyber.com/", "GetRecordListResult");
    private final static QName _GetVoiceIPByVoiceIDResponseGetVoiceIPByVoiceIDResult_QNAME = new QName("http://www.voicecyber.com/", "GetVoiceIPByVoiceIDResult");
    private final static QName _ResumeRecordResponseResumeRecordResult_QNAME = new QName("http://www.voicecyber.com/", "ResumeRecordResult");
    private final static QName _UnsubscribeRecordingStopEventResponseUnsubscribeRecordingStopEventResult_QNAME = new QName("http://www.voicecyber.com/", "UnsubscribeRecordingStopEventResult");
    private final static QName _GetFilePathbyReservedFieldsReservedFields_QNAME = new QName("http://www.voicecyber.com/", "reservedFields");
    private final static QName _AgentlogoutResponseAgentlogoutResult_QNAME = new QName("http://www.voicecyber.com/", "AgentlogoutResult");
    private final static QName _StartRecordResponseStartRecordResult_QNAME = new QName("http://www.voicecyber.com/", "StartRecordResult");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.voicecyber
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateRecordDataByExt }
     * 
     */
    public UpdateRecordDataByExt createUpdateRecordDataByExt() {
        return new UpdateRecordDataByExt();
    }

    /**
     * Create an instance of {@link GetChannelCountByVoiceIDResponse }
     * 
     */
    public GetChannelCountByVoiceIDResponse createGetChannelCountByVoiceIDResponse() {
        return new GetChannelCountByVoiceIDResponse();
    }

    /**
     * Create an instance of {@link GetRefIDResponse }
     * 
     */
    public GetRefIDResponse createGetRefIDResponse() {
        return new GetRefIDResponse();
    }

    /**
     * Create an instance of {@link GetFileHttpPathResponse }
     * 
     */
    public GetFileHttpPathResponse createGetFileHttpPathResponse() {
        return new GetFileHttpPathResponse();
    }

    /**
     * Create an instance of {@link StopRecordFResponse }
     * 
     */
    public StopRecordFResponse createStopRecordFResponse() {
        return new StopRecordFResponse();
    }

    /**
     * Create an instance of {@link UpdateRecordDataByUCIDResponse }
     * 
     */
    public UpdateRecordDataByUCIDResponse createUpdateRecordDataByUCIDResponse() {
        return new UpdateRecordDataByUCIDResponse();
    }

    /**
     * Create an instance of {@link GetExtensionStatusResponse }
     * 
     */
    public GetExtensionStatusResponse createGetExtensionStatusResponse() {
        return new GetExtensionStatusResponse();
    }

    /**
     * Create an instance of {@link UpdateRecordDataByUCID }
     * 
     */
    public UpdateRecordDataByUCID createUpdateRecordDataByUCID() {
        return new UpdateRecordDataByUCID();
    }

    /**
     * Create an instance of {@link StopRecord }
     * 
     */
    public StopRecord createStopRecord() {
        return new StopRecord();
    }

    /**
     * Create an instance of {@link GetRecordList }
     * 
     */
    public GetRecordList createGetRecordList() {
        return new GetRecordList();
    }

    /**
     * Create an instance of {@link SaveRecordFile }
     * 
     */
    public SaveRecordFile createSaveRecordFile() {
        return new SaveRecordFile();
    }

    /**
     * Create an instance of {@link GetVoiceCount }
     * 
     */
    public GetVoiceCount createGetVoiceCount() {
        return new GetVoiceCount();
    }

    /**
     * Create an instance of {@link AgentLogin }
     * 
     */
    public AgentLogin createAgentLogin() {
        return new AgentLogin();
    }

    /**
     * Create an instance of {@link ArrayOfint }
     * 
     */
    public ArrayOfint createArrayOfint() {
        return new ArrayOfint();
    }

    /**
     * Create an instance of {@link StartRecordFResponse }
     * 
     */
    public StartRecordFResponse createStartRecordFResponse() {
        return new StartRecordFResponse();
    }

    /**
     * Create an instance of {@link GetRefIDByReservedFieldResponse }
     * 
     */
    public GetRefIDByReservedFieldResponse createGetRefIDByReservedFieldResponse() {
        return new GetRefIDByReservedFieldResponse();
    }

    /**
     * Create an instance of {@link PauseRecordFResponse }
     * 
     */
    public PauseRecordFResponse createPauseRecordFResponse() {
        return new PauseRecordFResponse();
    }

    /**
     * Create an instance of {@link UnsubscribeRecordingStopEvent }
     * 
     */
    public UnsubscribeRecordingStopEvent createUnsubscribeRecordingStopEvent() {
        return new UnsubscribeRecordingStopEvent();
    }

    /**
     * Create an instance of {@link AgentLoginResponse }
     * 
     */
    public AgentLoginResponse createAgentLoginResponse() {
        return new AgentLoginResponse();
    }

    /**
     * Create an instance of {@link ResumeRecordF }
     * 
     */
    public ResumeRecordF createResumeRecordF() {
        return new ResumeRecordF();
    }

    /**
     * Create an instance of {@link GetRecordListByStartRecordTime }
     * 
     */
    public GetRecordListByStartRecordTime createGetRecordListByStartRecordTime() {
        return new GetRecordListByStartRecordTime();
    }

    /**
     * Create an instance of {@link StopRecordResponse }
     * 
     */
    public StopRecordResponse createStopRecordResponse() {
        return new StopRecordResponse();
    }

    /**
     * Create an instance of {@link StopRecordF }
     * 
     */
    public StopRecordF createStopRecordF() {
        return new StopRecordF();
    }

    /**
     * Create an instance of {@link PauseRecordF }
     * 
     */
    public PauseRecordF createPauseRecordF() {
        return new PauseRecordF();
    }

    /**
     * Create an instance of {@link GetVoiceIPByVoiceID }
     * 
     */
    public GetVoiceIPByVoiceID createGetVoiceIPByVoiceID() {
        return new GetVoiceIPByVoiceID();
    }

    /**
     * Create an instance of {@link UpdateRecordDataByIDResponse }
     * 
     */
    public UpdateRecordDataByIDResponse createUpdateRecordDataByIDResponse() {
        return new UpdateRecordDataByIDResponse();
    }

    /**
     * Create an instance of {@link GetRecordArchiveStatusResponse }
     * 
     */
    public GetRecordArchiveStatusResponse createGetRecordArchiveStatusResponse() {
        return new GetRecordArchiveStatusResponse();
    }

    /**
     * Create an instance of {@link ResumeRecordFResponse }
     * 
     */
    public ResumeRecordFResponse createResumeRecordFResponse() {
        return new ResumeRecordFResponse();
    }

    /**
     * Create an instance of {@link PauseRecordResponse }
     * 
     */
    public PauseRecordResponse createPauseRecordResponse() {
        return new PauseRecordResponse();
    }

    /**
     * Create an instance of {@link GetFilePathbyReservedFieldsResponse }
     * 
     */
    public GetFilePathbyReservedFieldsResponse createGetFilePathbyReservedFieldsResponse() {
        return new GetFilePathbyReservedFieldsResponse();
    }

    /**
     * Create an instance of {@link SubscribeRecordingStopEvent }
     * 
     */
    public SubscribeRecordingStopEvent createSubscribeRecordingStopEvent() {
        return new SubscribeRecordingStopEvent();
    }

    /**
     * Create an instance of {@link PauseRecord }
     * 
     */
    public PauseRecord createPauseRecord() {
        return new PauseRecord();
    }

    /**
     * Create an instance of {@link ResumeRecord }
     * 
     */
    public ResumeRecord createResumeRecord() {
        return new ResumeRecord();
    }

    /**
     * Create an instance of {@link GetChannelByExtResponse }
     * 
     */
    public GetChannelByExtResponse createGetChannelByExtResponse() {
        return new GetChannelByExtResponse();
    }

    /**
     * Create an instance of {@link GetChannelCountByVoiceID }
     * 
     */
    public GetChannelCountByVoiceID createGetChannelCountByVoiceID() {
        return new GetChannelCountByVoiceID();
    }

    /**
     * Create an instance of {@link StartRecordF }
     * 
     */
    public StartRecordF createStartRecordF() {
        return new StartRecordF();
    }

    /**
     * Create an instance of {@link GetRefIDByReservedField }
     * 
     */
    public GetRefIDByReservedField createGetRefIDByReservedField() {
        return new GetRefIDByReservedField();
    }

    /**
     * Create an instance of {@link GetChannelByExt }
     * 
     */
    public GetChannelByExt createGetChannelByExt() {
        return new GetChannelByExt();
    }

    /**
     * Create an instance of {@link GetExtensionStatus }
     * 
     */
    public GetExtensionStatus createGetExtensionStatus() {
        return new GetExtensionStatus();
    }

    /**
     * Create an instance of {@link SubscribeRecordingStopEventResponse }
     * 
     */
    public SubscribeRecordingStopEventResponse createSubscribeRecordingStopEventResponse() {
        return new SubscribeRecordingStopEventResponse();
    }

    /**
     * Create an instance of {@link UpdateRecordDataByExtResponse }
     * 
     */
    public UpdateRecordDataByExtResponse createUpdateRecordDataByExtResponse() {
        return new UpdateRecordDataByExtResponse();
    }

    /**
     * Create an instance of {@link SaveRecordFileResponse }
     * 
     */
    public SaveRecordFileResponse createSaveRecordFileResponse() {
        return new SaveRecordFileResponse();
    }

    /**
     * Create an instance of {@link GetRecordArchiveStatus }
     * 
     */
    public GetRecordArchiveStatus createGetRecordArchiveStatus() {
        return new GetRecordArchiveStatus();
    }

    /**
     * Create an instance of {@link StartRecord }
     * 
     */
    public StartRecord createStartRecord() {
        return new StartRecord();
    }

    /**
     * Create an instance of {@link Agentlogout }
     * 
     */
    public Agentlogout createAgentlogout() {
        return new Agentlogout();
    }

    /**
     * Create an instance of {@link GetRecordListByStartRecordTimeResponse }
     * 
     */
    public GetRecordListByStartRecordTimeResponse createGetRecordListByStartRecordTimeResponse() {
        return new GetRecordListByStartRecordTimeResponse();
    }

    /**
     * Create an instance of {@link GetVoiceCountResponse }
     * 
     */
    public GetVoiceCountResponse createGetVoiceCountResponse() {
        return new GetVoiceCountResponse();
    }

    /**
     * Create an instance of {@link UpdateRecordDataByID }
     * 
     */
    public UpdateRecordDataByID createUpdateRecordDataByID() {
        return new UpdateRecordDataByID();
    }

    /**
     * Create an instance of {@link WebSDKReturn }
     * 
     */
    public WebSDKReturn createWebSDKReturn() {
        return new WebSDKReturn();
    }

    /**
     * Create an instance of {@link GetRecordListResponse }
     * 
     */
    public GetRecordListResponse createGetRecordListResponse() {
        return new GetRecordListResponse();
    }

    /**
     * Create an instance of {@link GetVoiceIPByVoiceIDResponse }
     * 
     */
    public GetVoiceIPByVoiceIDResponse createGetVoiceIPByVoiceIDResponse() {
        return new GetVoiceIPByVoiceIDResponse();
    }

    /**
     * Create an instance of {@link ResumeRecordResponse }
     * 
     */
    public ResumeRecordResponse createResumeRecordResponse() {
        return new ResumeRecordResponse();
    }

    /**
     * Create an instance of {@link GetRefID }
     * 
     */
    public GetRefID createGetRefID() {
        return new GetRefID();
    }

    /**
     * Create an instance of {@link UnsubscribeRecordingStopEventResponse }
     * 
     */
    public UnsubscribeRecordingStopEventResponse createUnsubscribeRecordingStopEventResponse() {
        return new UnsubscribeRecordingStopEventResponse();
    }

    /**
     * Create an instance of {@link GetFilePathbyReservedFields }
     * 
     */
    public GetFilePathbyReservedFields createGetFilePathbyReservedFields() {
        return new GetFilePathbyReservedFields();
    }

    /**
     * Create an instance of {@link GetFileHttpPath }
     * 
     */
    public GetFileHttpPath createGetFileHttpPath() {
        return new GetFileHttpPath();
    }

    /**
     * Create an instance of {@link AgentlogoutResponse }
     * 
     */
    public AgentlogoutResponse createAgentlogoutResponse() {
        return new AgentlogoutResponse();
    }

    /**
     * Create an instance of {@link StartRecordResponse }
     * 
     */
    public StartRecordResponse createStartRecordResponse() {
        return new StartRecordResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VCLogWebSDK", name = "WebSDKReturn")
    public JAXBElement<WebSDKReturn> createWebSDKReturn(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_WebSDKReturn_QNAME, WebSDKReturn.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfint")
    public JAXBElement<ArrayOfint> createArrayOfint(ArrayOfint value) {
        return new JAXBElement<ArrayOfint>(_ArrayOfint_QNAME, ArrayOfint.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Data", scope = UpdateRecordDataByExt.class)
    public JAXBElement<String> createUpdateRecordDataByExtData(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtData_QNAME, String.class, UpdateRecordDataByExt.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Extension", scope = UpdateRecordDataByExt.class)
    public JAXBElement<String> createUpdateRecordDataByExtExtension(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtExtension_QNAME, String.class, UpdateRecordDataByExt.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "GetChannelCountByVoiceIDResult", scope = GetChannelCountByVoiceIDResponse.class)
    public JAXBElement<WebSDKReturn> createGetChannelCountByVoiceIDResponseGetChannelCountByVoiceIDResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_GetChannelCountByVoiceIDResponseGetChannelCountByVoiceIDResult_QNAME, WebSDKReturn.class, GetChannelCountByVoiceIDResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "GetRefIDResult", scope = GetRefIDResponse.class)
    public JAXBElement<WebSDKReturn> createGetRefIDResponseGetRefIDResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_GetRefIDResponseGetRefIDResult_QNAME, WebSDKReturn.class, GetRefIDResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "GetFileHttpPathResult", scope = GetFileHttpPathResponse.class)
    public JAXBElement<WebSDKReturn> createGetFileHttpPathResponseGetFileHttpPathResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_GetFileHttpPathResponseGetFileHttpPathResult_QNAME, WebSDKReturn.class, GetFileHttpPathResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "StopRecordFResult", scope = StopRecordFResponse.class)
    public JAXBElement<WebSDKReturn> createStopRecordFResponseStopRecordFResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_StopRecordFResponseStopRecordFResult_QNAME, WebSDKReturn.class, StopRecordFResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "UpdateRecordDataByUCIDResult", scope = UpdateRecordDataByUCIDResponse.class)
    public JAXBElement<WebSDKReturn> createUpdateRecordDataByUCIDResponseUpdateRecordDataByUCIDResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_UpdateRecordDataByUCIDResponseUpdateRecordDataByUCIDResult_QNAME, WebSDKReturn.class, UpdateRecordDataByUCIDResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "GetExtensionStatusResult", scope = GetExtensionStatusResponse.class)
    public JAXBElement<WebSDKReturn> createGetExtensionStatusResponseGetExtensionStatusResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_GetExtensionStatusResponseGetExtensionStatusResult_QNAME, WebSDKReturn.class, GetExtensionStatusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "UCID", scope = UpdateRecordDataByUCID.class)
    public JAXBElement<String> createUpdateRecordDataByUCIDUCID(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByUCIDUCID_QNAME, String.class, UpdateRecordDataByUCID.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Data", scope = UpdateRecordDataByUCID.class)
    public JAXBElement<String> createUpdateRecordDataByUCIDData(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtData_QNAME, String.class, UpdateRecordDataByUCID.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Data", scope = StopRecord.class)
    public JAXBElement<String> createStopRecordData(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtData_QNAME, String.class, StopRecord.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Extension", scope = StopRecord.class)
    public JAXBElement<String> createStopRecordExtension(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtExtension_QNAME, String.class, StopRecord.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "JsonOtherConditions", scope = GetRecordList.class)
    public JAXBElement<String> createGetRecordListJsonOtherConditions(String value) {
        return new JAXBElement<String>(_GetRecordListJsonOtherConditions_QNAME, String.class, GetRecordList.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "end", scope = GetRecordList.class)
    public JAXBElement<String> createGetRecordListEnd(String value) {
        return new JAXBElement<String>(_GetRecordListEnd_QNAME, String.class, GetRecordList.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "from", scope = GetRecordList.class)
    public JAXBElement<String> createGetRecordListFrom(String value) {
        return new JAXBElement<String>(_GetRecordListFrom_QNAME, String.class, GetRecordList.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "RecordReference", scope = SaveRecordFile.class)
    public JAXBElement<String> createSaveRecordFileRecordReference(String value) {
        return new JAXBElement<String>(_SaveRecordFileRecordReference_QNAME, String.class, SaveRecordFile.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "voiceid", scope = GetVoiceCount.class)
    public JAXBElement<ArrayOfint> createGetVoiceCountVoiceid(ArrayOfint value) {
        return new JAXBElement<ArrayOfint>(_GetVoiceCountVoiceid_QNAME, ArrayOfint.class, GetVoiceCount.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "AgentID", scope = AgentLogin.class)
    public JAXBElement<String> createAgentLoginAgentID(String value) {
        return new JAXBElement<String>(_AgentLoginAgentID_QNAME, String.class, AgentLogin.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Extension", scope = AgentLogin.class)
    public JAXBElement<String> createAgentLoginExtension(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtExtension_QNAME, String.class, AgentLogin.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "StartRecordFResult", scope = StartRecordFResponse.class)
    public JAXBElement<WebSDKReturn> createStartRecordFResponseStartRecordFResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_StartRecordFResponseStartRecordFResult_QNAME, WebSDKReturn.class, StartRecordFResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "GetRefIDByReservedFieldResult", scope = GetRefIDByReservedFieldResponse.class)
    public JAXBElement<WebSDKReturn> createGetRefIDByReservedFieldResponseGetRefIDByReservedFieldResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_GetRefIDByReservedFieldResponseGetRefIDByReservedFieldResult_QNAME, WebSDKReturn.class, GetRefIDByReservedFieldResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "PauseRecordFResult", scope = PauseRecordFResponse.class)
    public JAXBElement<WebSDKReturn> createPauseRecordFResponsePauseRecordFResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_PauseRecordFResponsePauseRecordFResult_QNAME, WebSDKReturn.class, PauseRecordFResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "AgentLoginResult", scope = AgentLoginResponse.class)
    public JAXBElement<WebSDKReturn> createAgentLoginResponseAgentLoginResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_AgentLoginResponseAgentLoginResult_QNAME, WebSDKReturn.class, AgentLoginResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Data", scope = ResumeRecordF.class)
    public JAXBElement<String> createResumeRecordFData(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtData_QNAME, String.class, ResumeRecordF.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Extension", scope = ResumeRecordF.class)
    public JAXBElement<String> createResumeRecordFExtension(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtExtension_QNAME, String.class, ResumeRecordF.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "StopRecordResult", scope = StopRecordResponse.class)
    public JAXBElement<WebSDKReturn> createStopRecordResponseStopRecordResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_StopRecordResponseStopRecordResult_QNAME, WebSDKReturn.class, StopRecordResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "end", scope = GetRecordListByStartRecordTime.class)
    public JAXBElement<String> createGetRecordListByStartRecordTimeEnd(String value) {
        return new JAXBElement<String>(_GetRecordListEnd_QNAME, String.class, GetRecordListByStartRecordTime.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "from", scope = GetRecordListByStartRecordTime.class)
    public JAXBElement<String> createGetRecordListByStartRecordTimeFrom(String value) {
        return new JAXBElement<String>(_GetRecordListFrom_QNAME, String.class, GetRecordListByStartRecordTime.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Data", scope = StopRecordF.class)
    public JAXBElement<String> createStopRecordFData(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtData_QNAME, String.class, StopRecordF.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Extension", scope = StopRecordF.class)
    public JAXBElement<String> createStopRecordFExtension(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtExtension_QNAME, String.class, StopRecordF.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Data", scope = PauseRecordF.class)
    public JAXBElement<String> createPauseRecordFData(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtData_QNAME, String.class, PauseRecordF.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Extension", scope = PauseRecordF.class)
    public JAXBElement<String> createPauseRecordFExtension(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtExtension_QNAME, String.class, PauseRecordF.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "UpdateRecordDataByIDResult", scope = UpdateRecordDataByIDResponse.class)
    public JAXBElement<WebSDKReturn> createUpdateRecordDataByIDResponseUpdateRecordDataByIDResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_UpdateRecordDataByIDResponseUpdateRecordDataByIDResult_QNAME, WebSDKReturn.class, UpdateRecordDataByIDResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "GetRecordArchiveStatusResult", scope = GetRecordArchiveStatusResponse.class)
    public JAXBElement<WebSDKReturn> createGetRecordArchiveStatusResponseGetRecordArchiveStatusResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_GetRecordArchiveStatusResponseGetRecordArchiveStatusResult_QNAME, WebSDKReturn.class, GetRecordArchiveStatusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "ResumeRecordFResult", scope = ResumeRecordFResponse.class)
    public JAXBElement<WebSDKReturn> createResumeRecordFResponseResumeRecordFResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_ResumeRecordFResponseResumeRecordFResult_QNAME, WebSDKReturn.class, ResumeRecordFResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "PauseRecordResult", scope = PauseRecordResponse.class)
    public JAXBElement<WebSDKReturn> createPauseRecordResponsePauseRecordResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_PauseRecordResponsePauseRecordResult_QNAME, WebSDKReturn.class, PauseRecordResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "GetFilePathbyReservedFieldsResult", scope = GetFilePathbyReservedFieldsResponse.class)
    public JAXBElement<WebSDKReturn> createGetFilePathbyReservedFieldsResponseGetFilePathbyReservedFieldsResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_GetFilePathbyReservedFieldsResponseGetFilePathbyReservedFieldsResult_QNAME, WebSDKReturn.class, GetFilePathbyReservedFieldsResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Data", scope = PauseRecord.class)
    public JAXBElement<String> createPauseRecordData(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtData_QNAME, String.class, PauseRecord.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Extension", scope = PauseRecord.class)
    public JAXBElement<String> createPauseRecordExtension(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtExtension_QNAME, String.class, PauseRecord.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Data", scope = ResumeRecord.class)
    public JAXBElement<String> createResumeRecordData(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtData_QNAME, String.class, ResumeRecord.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Extension", scope = ResumeRecord.class)
    public JAXBElement<String> createResumeRecordExtension(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtExtension_QNAME, String.class, ResumeRecord.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "GetChannelByExtResult", scope = GetChannelByExtResponse.class)
    public JAXBElement<WebSDKReturn> createGetChannelByExtResponseGetChannelByExtResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_GetChannelByExtResponseGetChannelByExtResult_QNAME, WebSDKReturn.class, GetChannelByExtResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Data", scope = StartRecordF.class)
    public JAXBElement<String> createStartRecordFData(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtData_QNAME, String.class, StartRecordF.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Extension", scope = StartRecordF.class)
    public JAXBElement<String> createStartRecordFExtension(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtExtension_QNAME, String.class, StartRecordF.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "condition", scope = GetRefIDByReservedField.class)
    public JAXBElement<String> createGetRefIDByReservedFieldCondition(String value) {
        return new JAXBElement<String>(_GetRefIDByReservedFieldCondition_QNAME, String.class, GetRefIDByReservedField.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "extension", scope = GetChannelByExt.class)
    public JAXBElement<String> createGetChannelByExtExtension(String value) {
        return new JAXBElement<String>(_GetChannelByExtExtension_QNAME, String.class, GetChannelByExt.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "extension", scope = GetExtensionStatus.class)
    public JAXBElement<String> createGetExtensionStatusExtension(String value) {
        return new JAXBElement<String>(_GetChannelByExtExtension_QNAME, String.class, GetExtensionStatus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "SubscribeRecordingStopEventResult", scope = SubscribeRecordingStopEventResponse.class)
    public JAXBElement<WebSDKReturn> createSubscribeRecordingStopEventResponseSubscribeRecordingStopEventResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_SubscribeRecordingStopEventResponseSubscribeRecordingStopEventResult_QNAME, WebSDKReturn.class, SubscribeRecordingStopEventResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "UpdateRecordDataByExtResult", scope = UpdateRecordDataByExtResponse.class)
    public JAXBElement<WebSDKReturn> createUpdateRecordDataByExtResponseUpdateRecordDataByExtResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_UpdateRecordDataByExtResponseUpdateRecordDataByExtResult_QNAME, WebSDKReturn.class, UpdateRecordDataByExtResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "SaveRecordFileResult", scope = SaveRecordFileResponse.class)
    public JAXBElement<WebSDKReturn> createSaveRecordFileResponseSaveRecordFileResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_SaveRecordFileResponseSaveRecordFileResult_QNAME, WebSDKReturn.class, SaveRecordFileResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "RecordReference", scope = GetRecordArchiveStatus.class)
    public JAXBElement<String> createGetRecordArchiveStatusRecordReference(String value) {
        return new JAXBElement<String>(_SaveRecordFileRecordReference_QNAME, String.class, GetRecordArchiveStatus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Data", scope = StartRecord.class)
    public JAXBElement<String> createStartRecordData(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtData_QNAME, String.class, StartRecord.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Extension", scope = StartRecord.class)
    public JAXBElement<String> createStartRecordExtension(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtExtension_QNAME, String.class, StartRecord.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Extension", scope = Agentlogout.class)
    public JAXBElement<String> createAgentlogoutExtension(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtExtension_QNAME, String.class, Agentlogout.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "GetRecordListByStartRecordTimeResult", scope = GetRecordListByStartRecordTimeResponse.class)
    public JAXBElement<WebSDKReturn> createGetRecordListByStartRecordTimeResponseGetRecordListByStartRecordTimeResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_GetRecordListByStartRecordTimeResponseGetRecordListByStartRecordTimeResult_QNAME, WebSDKReturn.class, GetRecordListByStartRecordTimeResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "GetVoiceCountResult", scope = GetVoiceCountResponse.class)
    public JAXBElement<WebSDKReturn> createGetVoiceCountResponseGetVoiceCountResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_GetVoiceCountResponseGetVoiceCountResult_QNAME, WebSDKReturn.class, GetVoiceCountResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "voiceid", scope = GetVoiceCountResponse.class)
    public JAXBElement<ArrayOfint> createGetVoiceCountResponseVoiceid(ArrayOfint value) {
        return new JAXBElement<ArrayOfint>(_GetVoiceCountVoiceid_QNAME, ArrayOfint.class, GetVoiceCountResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "RefID", scope = UpdateRecordDataByID.class)
    public JAXBElement<String> createUpdateRecordDataByIDRefID(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByIDRefID_QNAME, String.class, UpdateRecordDataByID.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "Data", scope = UpdateRecordDataByID.class)
    public JAXBElement<String> createUpdateRecordDataByIDData(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByExtData_QNAME, String.class, UpdateRecordDataByID.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VCLogWebSDK", name = "Message", scope = WebSDKReturn.class)
    public JAXBElement<String> createWebSDKReturnMessage(String value) {
        return new JAXBElement<String>(_WebSDKReturnMessage_QNAME, String.class, WebSDKReturn.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VCLogWebSDK", name = "StringValue", scope = WebSDKReturn.class)
    public JAXBElement<String> createWebSDKReturnStringValue(String value) {
        return new JAXBElement<String>(_WebSDKReturnStringValue_QNAME, String.class, WebSDKReturn.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "GetRecordListResult", scope = GetRecordListResponse.class)
    public JAXBElement<WebSDKReturn> createGetRecordListResponseGetRecordListResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_GetRecordListResponseGetRecordListResult_QNAME, WebSDKReturn.class, GetRecordListResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "GetVoiceIPByVoiceIDResult", scope = GetVoiceIPByVoiceIDResponse.class)
    public JAXBElement<WebSDKReturn> createGetVoiceIPByVoiceIDResponseGetVoiceIPByVoiceIDResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_GetVoiceIPByVoiceIDResponseGetVoiceIPByVoiceIDResult_QNAME, WebSDKReturn.class, GetVoiceIPByVoiceIDResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "ResumeRecordResult", scope = ResumeRecordResponse.class)
    public JAXBElement<WebSDKReturn> createResumeRecordResponseResumeRecordResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_ResumeRecordResponseResumeRecordResult_QNAME, WebSDKReturn.class, ResumeRecordResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "extension", scope = GetRefID.class)
    public JAXBElement<String> createGetRefIDExtension(String value) {
        return new JAXBElement<String>(_GetChannelByExtExtension_QNAME, String.class, GetRefID.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "UnsubscribeRecordingStopEventResult", scope = UnsubscribeRecordingStopEventResponse.class)
    public JAXBElement<WebSDKReturn> createUnsubscribeRecordingStopEventResponseUnsubscribeRecordingStopEventResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_UnsubscribeRecordingStopEventResponseUnsubscribeRecordingStopEventResult_QNAME, WebSDKReturn.class, UnsubscribeRecordingStopEventResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "reservedFields", scope = GetFilePathbyReservedFields.class)
    public JAXBElement<String> createGetFilePathbyReservedFieldsReservedFields(String value) {
        return new JAXBElement<String>(_GetFilePathbyReservedFieldsReservedFields_QNAME, String.class, GetFilePathbyReservedFields.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "RefID", scope = GetFileHttpPath.class)
    public JAXBElement<String> createGetFileHttpPathRefID(String value) {
        return new JAXBElement<String>(_UpdateRecordDataByIDRefID_QNAME, String.class, GetFileHttpPath.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "AgentlogoutResult", scope = AgentlogoutResponse.class)
    public JAXBElement<WebSDKReturn> createAgentlogoutResponseAgentlogoutResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_AgentlogoutResponseAgentlogoutResult_QNAME, WebSDKReturn.class, AgentlogoutResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebSDKReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.voicecyber.com/", name = "StartRecordResult", scope = StartRecordResponse.class)
    public JAXBElement<WebSDKReturn> createStartRecordResponseStartRecordResult(WebSDKReturn value) {
        return new JAXBElement<WebSDKReturn>(_StartRecordResponseStartRecordResult_QNAME, WebSDKReturn.class, StartRecordResponse.class, value);
    }

}
