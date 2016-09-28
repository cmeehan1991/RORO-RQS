/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author cmeehan
 */
@Entity
@Table(name = "allquotes", catalog = "kline", schema = "")
@NamedQueries({
    @NamedQuery(name = "Allquotes.findAll", query = "SELECT a FROM Allquotes a"),
    @NamedQuery(name = "Allquotes.findById", query = "SELECT a FROM Allquotes a WHERE a.id = :id"),
    @NamedQuery(name = "Allquotes.findByTradeLane", query = "SELECT a FROM Allquotes a WHERE a.tradeLane = :tradeLane"),
    @NamedQuery(name = "Allquotes.findByPol", query = "SELECT a FROM Allquotes a WHERE a.pol = :pol"),
    @NamedQuery(name = "Allquotes.findByPolUnloc", query = "SELECT a FROM Allquotes a WHERE a.polUnloc = :polUnloc"),
    @NamedQuery(name = "Allquotes.findByPolName", query = "SELECT a FROM Allquotes a WHERE a.polName = :polName"),
    @NamedQuery(name = "Allquotes.findByPolCountry", query = "SELECT a FROM Allquotes a WHERE a.polCountry = :polCountry"),
    @NamedQuery(name = "Allquotes.findByPod", query = "SELECT a FROM Allquotes a WHERE a.pod = :pod"),
    @NamedQuery(name = "Allquotes.findByPodUnloc", query = "SELECT a FROM Allquotes a WHERE a.podUnloc = :podUnloc"),
    @NamedQuery(name = "Allquotes.findByPodName", query = "SELECT a FROM Allquotes a WHERE a.podName = :podName"),
    @NamedQuery(name = "Allquotes.findByPodCountry", query = "SELECT a FROM Allquotes a WHERE a.podCountry = :podCountry"),
    @NamedQuery(name = "Allquotes.findByTshp1", query = "SELECT a FROM Allquotes a WHERE a.tshp1 = :tshp1"),
    @NamedQuery(name = "Allquotes.findByTshp1Unloc", query = "SELECT a FROM Allquotes a WHERE a.tshp1Unloc = :tshp1Unloc"),
    @NamedQuery(name = "Allquotes.findByTshp1Name", query = "SELECT a FROM Allquotes a WHERE a.tshp1Name = :tshp1Name"),
    @NamedQuery(name = "Allquotes.findByTshp1Country", query = "SELECT a FROM Allquotes a WHERE a.tshp1Country = :tshp1Country"),
    @NamedQuery(name = "Allquotes.findByTshp2", query = "SELECT a FROM Allquotes a WHERE a.tshp2 = :tshp2"),
    @NamedQuery(name = "Allquotes.findByTshp2Unloc", query = "SELECT a FROM Allquotes a WHERE a.tshp2Unloc = :tshp2Unloc"),
    @NamedQuery(name = "Allquotes.findByTshp2Name", query = "SELECT a FROM Allquotes a WHERE a.tshp2Name = :tshp2Name"),
    @NamedQuery(name = "Allquotes.findByTshp2Country", query = "SELECT a FROM Allquotes a WHERE a.tshp2Country = :tshp2Country"),
    @NamedQuery(name = "Allquotes.findByCommClass", query = "SELECT a FROM Allquotes a WHERE a.commClass = :commClass"),
    @NamedQuery(name = "Allquotes.findByHandlingInstructions", query = "SELECT a FROM Allquotes a WHERE a.handlingInstructions = :handlingInstructions"),
    @NamedQuery(name = "Allquotes.findByCommDescription", query = "SELECT a FROM Allquotes a WHERE a.commDescription = :commDescription"),
    @NamedQuery(name = "Allquotes.findByRate", query = "SELECT a FROM Allquotes a WHERE a.rate = :rate"),
    @NamedQuery(name = "Allquotes.findByRateUnit", query = "SELECT a FROM Allquotes a WHERE a.rateUnit = :rateUnit"),
    @NamedQuery(name = "Allquotes.findByBaf", query = "SELECT a FROM Allquotes a WHERE a.baf = :baf"),
    @NamedQuery(name = "Allquotes.findByEcaBaf", query = "SELECT a FROM Allquotes a WHERE a.ecaBaf = :ecaBaf"),
    @NamedQuery(name = "Allquotes.findByEcaUnit", query = "SELECT a FROM Allquotes a WHERE a.ecaUnit = :ecaUnit"),
    @NamedQuery(name = "Allquotes.findByThc", query = "SELECT a FROM Allquotes a WHERE a.thc = :thc"),
    @NamedQuery(name = "Allquotes.findByThcUnit", query = "SELECT a FROM Allquotes a WHERE a.thcUnit = :thcUnit"),
    @NamedQuery(name = "Allquotes.findByThcMinimum", query = "SELECT a FROM Allquotes a WHERE a.thcMinimum = :thcMinimum"),
    @NamedQuery(name = "Allquotes.findByThcMinimumUnit", query = "SELECT a FROM Allquotes a WHERE a.thcMinimumUnit = :thcMinimumUnit"),
    @NamedQuery(name = "Allquotes.findByThcMaximum", query = "SELECT a FROM Allquotes a WHERE a.thcMaximum = :thcMaximum"),
    @NamedQuery(name = "Allquotes.findByThcMaximumUnit", query = "SELECT a FROM Allquotes a WHERE a.thcMaximumUnit = :thcMaximumUnit"),
    @NamedQuery(name = "Allquotes.findByWfg", query = "SELECT a FROM Allquotes a WHERE a.wfg = :wfg"),
    @NamedQuery(name = "Allquotes.findByWfgUnit", query = "SELECT a FROM Allquotes a WHERE a.wfgUnit = :wfgUnit"),
    @NamedQuery(name = "Allquotes.findByWfgMinimum", query = "SELECT a FROM Allquotes a WHERE a.wfgMinimum = :wfgMinimum"),
    @NamedQuery(name = "Allquotes.findByWfgMinimumUnit", query = "SELECT a FROM Allquotes a WHERE a.wfgMinimumUnit = :wfgMinimumUnit"),
    @NamedQuery(name = "Allquotes.findByWfgMaximum", query = "SELECT a FROM Allquotes a WHERE a.wfgMaximum = :wfgMaximum"),
    @NamedQuery(name = "Allquotes.findByWfgMaximumUnit", query = "SELECT a FROM Allquotes a WHERE a.wfgMaximumUnit = :wfgMaximumUnit"),
    @NamedQuery(name = "Allquotes.findByDocFee", query = "SELECT a FROM Allquotes a WHERE a.docFee = :docFee"),
    @NamedQuery(name = "Allquotes.findByWarRisk", query = "SELECT a FROM Allquotes a WHERE a.warRisk = :warRisk"),
    @NamedQuery(name = "Allquotes.findBySpotRate", query = "SELECT a FROM Allquotes a WHERE a.spotRate = :spotRate"),
    @NamedQuery(name = "Allquotes.findByTariffRate", query = "SELECT a FROM Allquotes a WHERE a.tariffRate = :tariffRate"),
    @NamedQuery(name = "Allquotes.findByFtfSpotRate", query = "SELECT a FROM Allquotes a WHERE a.ftfSpotRate = :ftfSpotRate"),
    @NamedQuery(name = "Allquotes.findByFtfTariffRate", query = "SELECT a FROM Allquotes a WHERE a.ftfTariffRate = :ftfTariffRate"),
    @NamedQuery(name = "Allquotes.findByIndicatoryRate", query = "SELECT a FROM Allquotes a WHERE a.indicatoryRate = :indicatoryRate"),
    @NamedQuery(name = "Allquotes.findByUserID", query = "SELECT a FROM Allquotes a WHERE a.userID = :userID"),
    @NamedQuery(name = "Allquotes.findByBooked", query = "SELECT a FROM Allquotes a WHERE a.booked = :booked"),
    @NamedQuery(name = "Allquotes.findByDateBooked", query = "SELECT a FROM Allquotes a WHERE a.dateBooked = :dateBooked"),
    @NamedQuery(name = "Allquotes.findByDate", query = "SELECT a FROM Allquotes a WHERE a.date = :date"),
    @NamedQuery(name = "Allquotes.findByDateQuoted", query = "SELECT a FROM Allquotes a WHERE a.dateQuoted = :dateQuoted"),
    @NamedQuery(name = "Allquotes.findByComments", query = "SELECT a FROM Allquotes a WHERE a.comments = :comments"),
    @NamedQuery(name = "Allquotes.findByCarrierComments", query = "SELECT a FROM Allquotes a WHERE a.carrierComments = :carrierComments"),
    @NamedQuery(name = "Allquotes.findByDateUpdated", query = "SELECT a FROM Allquotes a WHERE a.dateUpdated = :dateUpdated"),
    @NamedQuery(name = "Allquotes.findByDateUpdated1", query = "SELECT a FROM Allquotes a WHERE a.dateUpdated1 = :dateUpdated1"),
    @NamedQuery(name = "Allquotes.findByDeny", query = "SELECT a FROM Allquotes a WHERE a.deny = :deny"),
    @NamedQuery(name = "Allquotes.findByCustomerName", query = "SELECT a FROM Allquotes a WHERE a.customerName = :customerName"),
    @NamedQuery(name = "Allquotes.findByCustomerID", query = "SELECT a FROM Allquotes a WHERE a.customerID = :customerID"),
    @NamedQuery(name = "Allquotes.findByUpdateUserID", query = "SELECT a FROM Allquotes a WHERE a.updateUserID = :updateUserID"),
    @NamedQuery(name = "Allquotes.findByThcIncluded", query = "SELECT a FROM Allquotes a WHERE a.thcIncluded = :thcIncluded"),
    @NamedQuery(name = "Allquotes.findByWfgIncluded", query = "SELECT a FROM Allquotes a WHERE a.wfgIncluded = :wfgIncluded"),
    @NamedQuery(name = "Allquotes.findByThcAttached", query = "SELECT a FROM Allquotes a WHERE a.thcAttached = :thcAttached"),
    @NamedQuery(name = "Allquotes.findByWfgAttached", query = "SELECT a FROM Allquotes a WHERE a.wfgAttached = :wfgAttached"),
    @NamedQuery(name = "Allquotes.findByBafIncluded", query = "SELECT a FROM Allquotes a WHERE a.bafIncluded = :bafIncluded"),
    @NamedQuery(name = "Allquotes.findByBafPerTariff", query = "SELECT a FROM Allquotes a WHERE a.bafPerTariff = :bafPerTariff"),
    @NamedQuery(name = "Allquotes.findByEcaIncluded", query = "SELECT a FROM Allquotes a WHERE a.ecaIncluded = :ecaIncluded"),
    @NamedQuery(name = "Allquotes.findByEcaPerTariff", query = "SELECT a FROM Allquotes a WHERE a.ecaPerTariff = :ecaPerTariff"),
    @NamedQuery(name = "Allquotes.findByDocumentationFeeIncluded", query = "SELECT a FROM Allquotes a WHERE a.documentationFeeIncluded = :documentationFeeIncluded"),
    @NamedQuery(name = "Allquotes.findByAlphaNumeral", query = "SELECT a FROM Allquotes a WHERE a.alphaNumeral = :alphaNumeral"),
    @NamedQuery(name = "Allquotes.findByAccessories", query = "SELECT a FROM Allquotes a WHERE a.accessories = :accessories"),
    @NamedQuery(name = "Allquotes.findByMafiMinimum", query = "SELECT a FROM Allquotes a WHERE a.mafiMinimum = :mafiMinimum"),
    @NamedQuery(name = "Allquotes.findByMafiMinimumCharge", query = "SELECT a FROM Allquotes a WHERE a.mafiMinimumCharge = :mafiMinimumCharge"),
    @NamedQuery(name = "Allquotes.findByReasonForDecline", query = "SELECT a FROM Allquotes a WHERE a.reasonForDecline = :reasonForDecline"),
    @NamedQuery(name = "Allquotes.findByContractRate", query = "SELECT a FROM Allquotes a WHERE a.contractRate = :contractRate"),
    @NamedQuery(name = "Allquotes.findByBookingNumber", query = "SELECT a FROM Allquotes a WHERE a.bookingNumber = :bookingNumber"),
    @NamedQuery(name = "Allquotes.findByBookedUserID", query = "SELECT a FROM Allquotes a WHERE a.bookedUserID = :bookedUserID"),
    @NamedQuery(name = "Allquotes.findByFeedbackType", query = "SELECT a FROM Allquotes a WHERE a.feedbackType = :feedbackType"),
    @NamedQuery(name = "Allquotes.findByFeedbackDescription", query = "SELECT a FROM Allquotes a WHERE a.feedbackDescription = :feedbackDescription"),
    @NamedQuery(name = "Allquotes.findByFeedback", query = "SELECT a FROM Allquotes a WHERE a.feedback = :feedback"),
    @NamedQuery(name = "Allquotes.findByPublishingID", query = "SELECT a FROM Allquotes a WHERE a.publishingID = :publishingID"),
    @NamedQuery(name = "Allquotes.findByCommodityNumber", query = "SELECT a FROM Allquotes a WHERE a.commodityNumber = :commodityNumber"),
    @NamedQuery(name = "Allquotes.findByDescription", query = "SELECT a FROM Allquotes a WHERE a.description = :description"),
    @NamedQuery(name = "Allquotes.findByTliNumber", query = "SELECT a FROM Allquotes a WHERE a.tliNumber = :tliNumber"),
    @NamedQuery(name = "Allquotes.findByExpiration", query = "SELECT a FROM Allquotes a WHERE a.expiration = :expiration"),
    @NamedQuery(name = "Allquotes.findByContactName", query = "SELECT a FROM Allquotes a WHERE a.contactName = :contactName"),
    @NamedQuery(name = "Allquotes.findByContactEmail", query = "SELECT a FROM Allquotes a WHERE a.contactEmail = :contactEmail"),
    @NamedQuery(name = "Allquotes.findByContactPhone", query = "SELECT a FROM Allquotes a WHERE a.contactPhone = :contactPhone"),
    @NamedQuery(name = "Allquotes.findByContactPhoneExtension", query = "SELECT a FROM Allquotes a WHERE a.contactPhoneExtension = :contactPhoneExtension"),
    @NamedQuery(name = "Allquotes.findByContactPhoneType", query = "SELECT a FROM Allquotes a WHERE a.contactPhoneType = :contactPhoneType"),
    @NamedQuery(name = "Allquotes.findByDuplicateRate", query = "SELECT a FROM Allquotes a WHERE a.duplicateRate = :duplicateRate"),
    @NamedQuery(name = "Allquotes.findByStorage", query = "SELECT a FROM Allquotes a WHERE a.storage = :storage"),
    @NamedQuery(name = "Allquotes.findByStorageUnit", query = "SELECT a FROM Allquotes a WHERE a.storageUnit = :storageUnit"),
    @NamedQuery(name = "Allquotes.findByStorageIncluded", query = "SELECT a FROM Allquotes a WHERE a.storageIncluded = :storageIncluded"),
    @NamedQuery(name = "Allquotes.findByStoragePerTariff", query = "SELECT a FROM Allquotes a WHERE a.storagePerTariff = :storagePerTariff"),
    @NamedQuery(name = "Allquotes.findByCarrierComments1", query = "SELECT a FROM Allquotes a WHERE a.carrierComments1 = :carrierComments1"),
    @NamedQuery(name = "Allquotes.findByMtdApproval", query = "SELECT a FROM Allquotes a WHERE a.mtdApproval = :mtdApproval"),
    @NamedQuery(name = "Allquotes.findBySpaceApproval", query = "SELECT a FROM Allquotes a WHERE a.spaceApproval = :spaceApproval"),
    @NamedQuery(name = "Allquotes.findByOverseasResponse", query = "SELECT a FROM Allquotes a WHERE a.overseasResponse = :overseasResponse")})
public class Allquotes implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "tradeLane")
    private String tradeLane;
    @Column(name = "pol")
    private String pol;
    @Column(name = "POL_UNLOC")
    private String polUnloc;
    @Column(name = "POL_NAME")
    private String polName;
    @Column(name = "POL_COUNTRY")
    private String polCountry;
    @Column(name = "pod")
    private String pod;
    @Column(name = "POD_UNLOC")
    private String podUnloc;
    @Column(name = "POD_NAME")
    private String podName;
    @Column(name = "POD_COUNTRY")
    private String podCountry;
    @Basic(optional = false)
    @Column(name = "tshp1")
    private String tshp1;
    @Column(name = "TSHP1_UNLOC")
    private String tshp1Unloc;
    @Column(name = "TSHP1_NAME")
    private String tshp1Name;
    @Column(name = "TSHP1_COUNTRY")
    private String tshp1Country;
    @Basic(optional = false)
    @Column(name = "tshp2")
    private String tshp2;
    @Column(name = "TSHP2_UNLOC")
    private String tshp2Unloc;
    @Column(name = "TSHP2_NAME")
    private String tshp2Name;
    @Column(name = "TSHP2_COUNTRY")
    private String tshp2Country;
    @Column(name = "comm_class")
    private String commClass;
    @Basic(optional = false)
    @Column(name = "handling_instructions")
    private String handlingInstructions;
    @Column(name = "comm_description")
    private String commDescription;
    @Basic(optional = false)
    @Column(name = "rate")
    private String rate;
    @Basic(optional = false)
    @Column(name = "rate_unit")
    private String rateUnit;
    @Basic(optional = false)
    @Column(name = "baf")
    private String baf;
    @Basic(optional = false)
    @Column(name = "eca_baf")
    private String ecaBaf;
    @Basic(optional = false)
    @Column(name = "eca_unit")
    private String ecaUnit;
    @Basic(optional = false)
    @Column(name = "thc")
    private String thc;
    @Basic(optional = false)
    @Column(name = "thc_unit")
    private String thcUnit;
    @Basic(optional = false)
    @Column(name = "thc_minimum")
    private String thcMinimum;
    @Basic(optional = false)
    @Column(name = "thc_minimum_unit")
    private String thcMinimumUnit;
    @Basic(optional = false)
    @Column(name = "thc_maximum")
    private String thcMaximum;
    @Basic(optional = false)
    @Column(name = "thc_maximum_unit")
    private String thcMaximumUnit;
    @Basic(optional = false)
    @Column(name = "wfg")
    private String wfg;
    @Basic(optional = false)
    @Column(name = "wfg_unit")
    private String wfgUnit;
    @Basic(optional = false)
    @Column(name = "wfg_minimum")
    private String wfgMinimum;
    @Basic(optional = false)
    @Column(name = "wfg_minimum_unit")
    private String wfgMinimumUnit;
    @Basic(optional = false)
    @Column(name = "wfg_maximum")
    private String wfgMaximum;
    @Basic(optional = false)
    @Column(name = "wfg_maximum_unit")
    private String wfgMaximumUnit;
    @Basic(optional = false)
    @Column(name = "doc_fee")
    private String docFee;
    @Basic(optional = false)
    @Column(name = "war_risk")
    private String warRisk;
    @Basic(optional = false)
    @Column(name = "spot_rate")
    private String spotRate;
    @Basic(optional = false)
    @Column(name = "TARIFF_RATE")
    private boolean tariffRate;
    @Basic(optional = false)
    @Column(name = "FTF_SPOT_RATE")
    private boolean ftfSpotRate;
    @Basic(optional = false)
    @Column(name = "FTF_TARIFF_RATE")
    private boolean ftfTariffRate;
    @Basic(optional = false)
    @Column(name = "INDICATORY_RATE")
    private boolean indicatoryRate;
    @Basic(optional = false)
    @Column(name = "user_ID")
    private String userID;
    @Basic(optional = false)
    @Column(name = "booked")
    private String booked;
    @Basic(optional = false)
    @Column(name = "DATE_BOOKED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBooked;
    @Column(name = "date")
    private String date;
    @Column(name = "DATE_QUOTED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateQuoted;
    @Column(name = "comments")
    private String comments;
    @Basic(optional = false)
    @Column(name = "carrier_comments")
    private String carrierComments;
    @Column(name = "dateUpdated")
    private String dateUpdated;
    @Column(name = "DATE_UPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated1;
    @Column(name = "deny")
    private String deny;
    @Column(name = "customerName")
    private String customerName;
    @Column(name = "customerID")
    private String customerID;
    @Column(name = "updateUserID")
    private String updateUserID;
    @Column(name = "thcIncluded")
    private String thcIncluded;
    @Column(name = "wfgIncluded")
    private String wfgIncluded;
    @Column(name = "thcAttached")
    private String thcAttached;
    @Column(name = "wfgAttached")
    private String wfgAttached;
    @Column(name = "bafIncluded")
    private String bafIncluded;
    @Basic(optional = false)
    @Column(name = "bafPerTariff")
    private String bafPerTariff;
    @Column(name = "ecaIncluded")
    private String ecaIncluded;
    @Basic(optional = false)
    @Column(name = "ecaPerTariff")
    private String ecaPerTariff;
    @Column(name = "documentationFeeIncluded")
    private String documentationFeeIncluded;
    @Column(name = "alpha_numeral")
    private String alphaNumeral;
    @Column(name = "accessories")
    private String accessories;
    @Column(name = "mafiMinimum")
    private String mafiMinimum;
    @Column(name = "mafiMinimumCharge")
    private String mafiMinimumCharge;
    @Column(name = "reason_for_decline")
    private String reasonForDecline;
    @Column(name = "contract_rate")
    private String contractRate;
    @Basic(optional = false)
    @Column(name = "bookingNumber")
    private String bookingNumber;
    @Basic(optional = false)
    @Column(name = "bookedUserID")
    private String bookedUserID;
    @Column(name = "feedbackType")
    private String feedbackType;
    @Column(name = "feedbackDescription")
    private String feedbackDescription;
    @Basic(optional = false)
    @Column(name = "feedback")
    private String feedback;
    @Basic(optional = false)
    @Column(name = "publishingID")
    private String publishingID;
    @Basic(optional = false)
    @Column(name = "commodityNumber")
    private String commodityNumber;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "tliNumber")
    private String tliNumber;
    @Basic(optional = false)
    @Column(name = "expiration")
    private String expiration;
    @Basic(optional = false)
    @Column(name = "contactName")
    private String contactName;
    @Basic(optional = false)
    @Column(name = "contactEmail")
    private String contactEmail;
    @Column(name = "CONTACT_PHONE")
    private String contactPhone;
    @Column(name = "CONTACT_PHONE_EXTENSION")
    private String contactPhoneExtension;
    @Column(name = "CONTACT_PHONE_TYPE")
    private String contactPhoneType;
    @Basic(optional = false)
    @Column(name = "duplicateRate")
    private String duplicateRate;
    @Basic(optional = false)
    @Column(name = "storage")
    private String storage;
    @Basic(optional = false)
    @Column(name = "storageUnit")
    private String storageUnit;
    @Basic(optional = false)
    @Column(name = "storageIncluded")
    private String storageIncluded;
    @Basic(optional = false)
    @Column(name = "storagePerTariff")
    private String storagePerTariff;
    @Column(name = "carrierComments")
    private String carrierComments1;
    @Column(name = "MTD_APPROVAL")
    private String mtdApproval;
    @Column(name = "SPACE_APPROVAL")
    private String spaceApproval;
    @Column(name = "OVERSEAS_RESPONSE")
    private String overseasResponse;

    public Allquotes() {
    }

    public Allquotes(Integer id) {
        this.id = id;
    }

    public Allquotes(Integer id, String tradeLane, String tshp1, String tshp2, String handlingInstructions, String rate, String rateUnit, String baf, String ecaBaf, String ecaUnit, String thc, String thcUnit, String thcMinimum, String thcMinimumUnit, String thcMaximum, String thcMaximumUnit, String wfg, String wfgUnit, String wfgMinimum, String wfgMinimumUnit, String wfgMaximum, String wfgMaximumUnit, String docFee, String warRisk, String spotRate, boolean tariffRate, boolean ftfSpotRate, boolean ftfTariffRate, boolean indicatoryRate, String userID, String booked, Date dateBooked, String carrierComments, String bafPerTariff, String ecaPerTariff, String bookingNumber, String bookedUserID, String feedback, String publishingID, String commodityNumber, String description, String tliNumber, String expiration, String contactName, String contactEmail, String duplicateRate, String storage, String storageUnit, String storageIncluded, String storagePerTariff) {
        this.id = id;
        this.tradeLane = tradeLane;
        this.tshp1 = tshp1;
        this.tshp2 = tshp2;
        this.handlingInstructions = handlingInstructions;
        this.rate = rate;
        this.rateUnit = rateUnit;
        this.baf = baf;
        this.ecaBaf = ecaBaf;
        this.ecaUnit = ecaUnit;
        this.thc = thc;
        this.thcUnit = thcUnit;
        this.thcMinimum = thcMinimum;
        this.thcMinimumUnit = thcMinimumUnit;
        this.thcMaximum = thcMaximum;
        this.thcMaximumUnit = thcMaximumUnit;
        this.wfg = wfg;
        this.wfgUnit = wfgUnit;
        this.wfgMinimum = wfgMinimum;
        this.wfgMinimumUnit = wfgMinimumUnit;
        this.wfgMaximum = wfgMaximum;
        this.wfgMaximumUnit = wfgMaximumUnit;
        this.docFee = docFee;
        this.warRisk = warRisk;
        this.spotRate = spotRate;
        this.tariffRate = tariffRate;
        this.ftfSpotRate = ftfSpotRate;
        this.ftfTariffRate = ftfTariffRate;
        this.indicatoryRate = indicatoryRate;
        this.userID = userID;
        this.booked = booked;
        this.dateBooked = dateBooked;
        this.carrierComments = carrierComments;
        this.bafPerTariff = bafPerTariff;
        this.ecaPerTariff = ecaPerTariff;
        this.bookingNumber = bookingNumber;
        this.bookedUserID = bookedUserID;
        this.feedback = feedback;
        this.publishingID = publishingID;
        this.commodityNumber = commodityNumber;
        this.description = description;
        this.tliNumber = tliNumber;
        this.expiration = expiration;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.duplicateRate = duplicateRate;
        this.storage = storage;
        this.storageUnit = storageUnit;
        this.storageIncluded = storageIncluded;
        this.storagePerTariff = storagePerTariff;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getTradeLane() {
        return tradeLane;
    }

    public void setTradeLane(String tradeLane) {
        String oldTradeLane = this.tradeLane;
        this.tradeLane = tradeLane;
        changeSupport.firePropertyChange("tradeLane", oldTradeLane, tradeLane);
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        String oldPol = this.pol;
        this.pol = pol;
        changeSupport.firePropertyChange("pol", oldPol, pol);
    }

    public String getPolUnloc() {
        return polUnloc;
    }

    public void setPolUnloc(String polUnloc) {
        String oldPolUnloc = this.polUnloc;
        this.polUnloc = polUnloc;
        changeSupport.firePropertyChange("polUnloc", oldPolUnloc, polUnloc);
    }

    public String getPolName() {
        return polName;
    }

    public void setPolName(String polName) {
        String oldPolName = this.polName;
        this.polName = polName;
        changeSupport.firePropertyChange("polName", oldPolName, polName);
    }

    public String getPolCountry() {
        return polCountry;
    }

    public void setPolCountry(String polCountry) {
        String oldPolCountry = this.polCountry;
        this.polCountry = polCountry;
        changeSupport.firePropertyChange("polCountry", oldPolCountry, polCountry);
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        String oldPod = this.pod;
        this.pod = pod;
        changeSupport.firePropertyChange("pod", oldPod, pod);
    }

    public String getPodUnloc() {
        return podUnloc;
    }

    public void setPodUnloc(String podUnloc) {
        String oldPodUnloc = this.podUnloc;
        this.podUnloc = podUnloc;
        changeSupport.firePropertyChange("podUnloc", oldPodUnloc, podUnloc);
    }

    public String getPodName() {
        return podName;
    }

    public void setPodName(String podName) {
        String oldPodName = this.podName;
        this.podName = podName;
        changeSupport.firePropertyChange("podName", oldPodName, podName);
    }

    public String getPodCountry() {
        return podCountry;
    }

    public void setPodCountry(String podCountry) {
        String oldPodCountry = this.podCountry;
        this.podCountry = podCountry;
        changeSupport.firePropertyChange("podCountry", oldPodCountry, podCountry);
    }

    public String getTshp1() {
        return tshp1;
    }

    public void setTshp1(String tshp1) {
        String oldTshp1 = this.tshp1;
        this.tshp1 = tshp1;
        changeSupport.firePropertyChange("tshp1", oldTshp1, tshp1);
    }

    public String getTshp1Unloc() {
        return tshp1Unloc;
    }

    public void setTshp1Unloc(String tshp1Unloc) {
        String oldTshp1Unloc = this.tshp1Unloc;
        this.tshp1Unloc = tshp1Unloc;
        changeSupport.firePropertyChange("tshp1Unloc", oldTshp1Unloc, tshp1Unloc);
    }

    public String getTshp1Name() {
        return tshp1Name;
    }

    public void setTshp1Name(String tshp1Name) {
        String oldTshp1Name = this.tshp1Name;
        this.tshp1Name = tshp1Name;
        changeSupport.firePropertyChange("tshp1Name", oldTshp1Name, tshp1Name);
    }

    public String getTshp1Country() {
        return tshp1Country;
    }

    public void setTshp1Country(String tshp1Country) {
        String oldTshp1Country = this.tshp1Country;
        this.tshp1Country = tshp1Country;
        changeSupport.firePropertyChange("tshp1Country", oldTshp1Country, tshp1Country);
    }

    public String getTshp2() {
        return tshp2;
    }

    public void setTshp2(String tshp2) {
        String oldTshp2 = this.tshp2;
        this.tshp2 = tshp2;
        changeSupport.firePropertyChange("tshp2", oldTshp2, tshp2);
    }

    public String getTshp2Unloc() {
        return tshp2Unloc;
    }

    public void setTshp2Unloc(String tshp2Unloc) {
        String oldTshp2Unloc = this.tshp2Unloc;
        this.tshp2Unloc = tshp2Unloc;
        changeSupport.firePropertyChange("tshp2Unloc", oldTshp2Unloc, tshp2Unloc);
    }

    public String getTshp2Name() {
        return tshp2Name;
    }

    public void setTshp2Name(String tshp2Name) {
        String oldTshp2Name = this.tshp2Name;
        this.tshp2Name = tshp2Name;
        changeSupport.firePropertyChange("tshp2Name", oldTshp2Name, tshp2Name);
    }

    public String getTshp2Country() {
        return tshp2Country;
    }

    public void setTshp2Country(String tshp2Country) {
        String oldTshp2Country = this.tshp2Country;
        this.tshp2Country = tshp2Country;
        changeSupport.firePropertyChange("tshp2Country", oldTshp2Country, tshp2Country);
    }

    public String getCommClass() {
        return commClass;
    }

    public void setCommClass(String commClass) {
        String oldCommClass = this.commClass;
        this.commClass = commClass;
        changeSupport.firePropertyChange("commClass", oldCommClass, commClass);
    }

    public String getHandlingInstructions() {
        return handlingInstructions;
    }

    public void setHandlingInstructions(String handlingInstructions) {
        String oldHandlingInstructions = this.handlingInstructions;
        this.handlingInstructions = handlingInstructions;
        changeSupport.firePropertyChange("handlingInstructions", oldHandlingInstructions, handlingInstructions);
    }

    public String getCommDescription() {
        return commDescription;
    }

    public void setCommDescription(String commDescription) {
        String oldCommDescription = this.commDescription;
        this.commDescription = commDescription;
        changeSupport.firePropertyChange("commDescription", oldCommDescription, commDescription);
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        String oldRate = this.rate;
        this.rate = rate;
        changeSupport.firePropertyChange("rate", oldRate, rate);
    }

    public String getRateUnit() {
        return rateUnit;
    }

    public void setRateUnit(String rateUnit) {
        String oldRateUnit = this.rateUnit;
        this.rateUnit = rateUnit;
        changeSupport.firePropertyChange("rateUnit", oldRateUnit, rateUnit);
    }

    public String getBaf() {
        return baf;
    }

    public void setBaf(String baf) {
        String oldBaf = this.baf;
        this.baf = baf;
        changeSupport.firePropertyChange("baf", oldBaf, baf);
    }

    public String getEcaBaf() {
        return ecaBaf;
    }

    public void setEcaBaf(String ecaBaf) {
        String oldEcaBaf = this.ecaBaf;
        this.ecaBaf = ecaBaf;
        changeSupport.firePropertyChange("ecaBaf", oldEcaBaf, ecaBaf);
    }

    public String getEcaUnit() {
        return ecaUnit;
    }

    public void setEcaUnit(String ecaUnit) {
        String oldEcaUnit = this.ecaUnit;
        this.ecaUnit = ecaUnit;
        changeSupport.firePropertyChange("ecaUnit", oldEcaUnit, ecaUnit);
    }

    public String getThc() {
        return thc;
    }

    public void setThc(String thc) {
        String oldThc = this.thc;
        this.thc = thc;
        changeSupport.firePropertyChange("thc", oldThc, thc);
    }

    public String getThcUnit() {
        return thcUnit;
    }

    public void setThcUnit(String thcUnit) {
        String oldThcUnit = this.thcUnit;
        this.thcUnit = thcUnit;
        changeSupport.firePropertyChange("thcUnit", oldThcUnit, thcUnit);
    }

    public String getThcMinimum() {
        return thcMinimum;
    }

    public void setThcMinimum(String thcMinimum) {
        String oldThcMinimum = this.thcMinimum;
        this.thcMinimum = thcMinimum;
        changeSupport.firePropertyChange("thcMinimum", oldThcMinimum, thcMinimum);
    }

    public String getThcMinimumUnit() {
        return thcMinimumUnit;
    }

    public void setThcMinimumUnit(String thcMinimumUnit) {
        String oldThcMinimumUnit = this.thcMinimumUnit;
        this.thcMinimumUnit = thcMinimumUnit;
        changeSupport.firePropertyChange("thcMinimumUnit", oldThcMinimumUnit, thcMinimumUnit);
    }

    public String getThcMaximum() {
        return thcMaximum;
    }

    public void setThcMaximum(String thcMaximum) {
        String oldThcMaximum = this.thcMaximum;
        this.thcMaximum = thcMaximum;
        changeSupport.firePropertyChange("thcMaximum", oldThcMaximum, thcMaximum);
    }

    public String getThcMaximumUnit() {
        return thcMaximumUnit;
    }

    public void setThcMaximumUnit(String thcMaximumUnit) {
        String oldThcMaximumUnit = this.thcMaximumUnit;
        this.thcMaximumUnit = thcMaximumUnit;
        changeSupport.firePropertyChange("thcMaximumUnit", oldThcMaximumUnit, thcMaximumUnit);
    }

    public String getWfg() {
        return wfg;
    }

    public void setWfg(String wfg) {
        String oldWfg = this.wfg;
        this.wfg = wfg;
        changeSupport.firePropertyChange("wfg", oldWfg, wfg);
    }

    public String getWfgUnit() {
        return wfgUnit;
    }

    public void setWfgUnit(String wfgUnit) {
        String oldWfgUnit = this.wfgUnit;
        this.wfgUnit = wfgUnit;
        changeSupport.firePropertyChange("wfgUnit", oldWfgUnit, wfgUnit);
    }

    public String getWfgMinimum() {
        return wfgMinimum;
    }

    public void setWfgMinimum(String wfgMinimum) {
        String oldWfgMinimum = this.wfgMinimum;
        this.wfgMinimum = wfgMinimum;
        changeSupport.firePropertyChange("wfgMinimum", oldWfgMinimum, wfgMinimum);
    }

    public String getWfgMinimumUnit() {
        return wfgMinimumUnit;
    }

    public void setWfgMinimumUnit(String wfgMinimumUnit) {
        String oldWfgMinimumUnit = this.wfgMinimumUnit;
        this.wfgMinimumUnit = wfgMinimumUnit;
        changeSupport.firePropertyChange("wfgMinimumUnit", oldWfgMinimumUnit, wfgMinimumUnit);
    }

    public String getWfgMaximum() {
        return wfgMaximum;
    }

    public void setWfgMaximum(String wfgMaximum) {
        String oldWfgMaximum = this.wfgMaximum;
        this.wfgMaximum = wfgMaximum;
        changeSupport.firePropertyChange("wfgMaximum", oldWfgMaximum, wfgMaximum);
    }

    public String getWfgMaximumUnit() {
        return wfgMaximumUnit;
    }

    public void setWfgMaximumUnit(String wfgMaximumUnit) {
        String oldWfgMaximumUnit = this.wfgMaximumUnit;
        this.wfgMaximumUnit = wfgMaximumUnit;
        changeSupport.firePropertyChange("wfgMaximumUnit", oldWfgMaximumUnit, wfgMaximumUnit);
    }

    public String getDocFee() {
        return docFee;
    }

    public void setDocFee(String docFee) {
        String oldDocFee = this.docFee;
        this.docFee = docFee;
        changeSupport.firePropertyChange("docFee", oldDocFee, docFee);
    }

    public String getWarRisk() {
        return warRisk;
    }

    public void setWarRisk(String warRisk) {
        String oldWarRisk = this.warRisk;
        this.warRisk = warRisk;
        changeSupport.firePropertyChange("warRisk", oldWarRisk, warRisk);
    }

    public String getSpotRate() {
        return spotRate;
    }

    public void setSpotRate(String spotRate) {
        String oldSpotRate = this.spotRate;
        this.spotRate = spotRate;
        changeSupport.firePropertyChange("spotRate", oldSpotRate, spotRate);
    }

    public boolean getTariffRate() {
        return tariffRate;
    }

    public void setTariffRate(boolean tariffRate) {
        boolean oldTariffRate = this.tariffRate;
        this.tariffRate = tariffRate;
        changeSupport.firePropertyChange("tariffRate", oldTariffRate, tariffRate);
    }

    public boolean getFtfSpotRate() {
        return ftfSpotRate;
    }

    public void setFtfSpotRate(boolean ftfSpotRate) {
        boolean oldFtfSpotRate = this.ftfSpotRate;
        this.ftfSpotRate = ftfSpotRate;
        changeSupport.firePropertyChange("ftfSpotRate", oldFtfSpotRate, ftfSpotRate);
    }

    public boolean getFtfTariffRate() {
        return ftfTariffRate;
    }

    public void setFtfTariffRate(boolean ftfTariffRate) {
        boolean oldFtfTariffRate = this.ftfTariffRate;
        this.ftfTariffRate = ftfTariffRate;
        changeSupport.firePropertyChange("ftfTariffRate", oldFtfTariffRate, ftfTariffRate);
    }

    public boolean getIndicatoryRate() {
        return indicatoryRate;
    }

    public void setIndicatoryRate(boolean indicatoryRate) {
        boolean oldIndicatoryRate = this.indicatoryRate;
        this.indicatoryRate = indicatoryRate;
        changeSupport.firePropertyChange("indicatoryRate", oldIndicatoryRate, indicatoryRate);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        String oldUserID = this.userID;
        this.userID = userID;
        changeSupport.firePropertyChange("userID", oldUserID, userID);
    }

    public String getBooked() {
        return booked;
    }

    public void setBooked(String booked) {
        String oldBooked = this.booked;
        this.booked = booked;
        changeSupport.firePropertyChange("booked", oldBooked, booked);
    }

    public Date getDateBooked() {
        return dateBooked;
    }

    public void setDateBooked(Date dateBooked) {
        Date oldDateBooked = this.dateBooked;
        this.dateBooked = dateBooked;
        changeSupport.firePropertyChange("dateBooked", oldDateBooked, dateBooked);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        String oldDate = this.date;
        this.date = date;
        changeSupport.firePropertyChange("date", oldDate, date);
    }

    public Date getDateQuoted() {
        return dateQuoted;
    }

    public void setDateQuoted(Date dateQuoted) {
        Date oldDateQuoted = this.dateQuoted;
        this.dateQuoted = dateQuoted;
        changeSupport.firePropertyChange("dateQuoted", oldDateQuoted, dateQuoted);
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        String oldComments = this.comments;
        this.comments = comments;
        changeSupport.firePropertyChange("comments", oldComments, comments);
    }

    public String getCarrierComments() {
        return carrierComments;
    }

    public void setCarrierComments(String carrierComments) {
        String oldCarrierComments = this.carrierComments;
        this.carrierComments = carrierComments;
        changeSupport.firePropertyChange("carrierComments", oldCarrierComments, carrierComments);
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        String oldDateUpdated = this.dateUpdated;
        this.dateUpdated = dateUpdated;
        changeSupport.firePropertyChange("dateUpdated", oldDateUpdated, dateUpdated);
    }

    public Date getDateUpdated1() {
        return dateUpdated1;
    }

    public void setDateUpdated1(Date dateUpdated1) {
        Date oldDateUpdated1 = this.dateUpdated1;
        this.dateUpdated1 = dateUpdated1;
        changeSupport.firePropertyChange("dateUpdated1", oldDateUpdated1, dateUpdated1);
    }

    public String getDeny() {
        return deny;
    }

    public void setDeny(String deny) {
        String oldDeny = this.deny;
        this.deny = deny;
        changeSupport.firePropertyChange("deny", oldDeny, deny);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        String oldCustomerName = this.customerName;
        this.customerName = customerName;
        changeSupport.firePropertyChange("customerName", oldCustomerName, customerName);
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        String oldCustomerID = this.customerID;
        this.customerID = customerID;
        changeSupport.firePropertyChange("customerID", oldCustomerID, customerID);
    }

    public String getUpdateUserID() {
        return updateUserID;
    }

    public void setUpdateUserID(String updateUserID) {
        String oldUpdateUserID = this.updateUserID;
        this.updateUserID = updateUserID;
        changeSupport.firePropertyChange("updateUserID", oldUpdateUserID, updateUserID);
    }

    public String getThcIncluded() {
        return thcIncluded;
    }

    public void setThcIncluded(String thcIncluded) {
        String oldThcIncluded = this.thcIncluded;
        this.thcIncluded = thcIncluded;
        changeSupport.firePropertyChange("thcIncluded", oldThcIncluded, thcIncluded);
    }

    public String getWfgIncluded() {
        return wfgIncluded;
    }

    public void setWfgIncluded(String wfgIncluded) {
        String oldWfgIncluded = this.wfgIncluded;
        this.wfgIncluded = wfgIncluded;
        changeSupport.firePropertyChange("wfgIncluded", oldWfgIncluded, wfgIncluded);
    }

    public String getThcAttached() {
        return thcAttached;
    }

    public void setThcAttached(String thcAttached) {
        String oldThcAttached = this.thcAttached;
        this.thcAttached = thcAttached;
        changeSupport.firePropertyChange("thcAttached", oldThcAttached, thcAttached);
    }

    public String getWfgAttached() {
        return wfgAttached;
    }

    public void setWfgAttached(String wfgAttached) {
        String oldWfgAttached = this.wfgAttached;
        this.wfgAttached = wfgAttached;
        changeSupport.firePropertyChange("wfgAttached", oldWfgAttached, wfgAttached);
    }

    public String getBafIncluded() {
        return bafIncluded;
    }

    public void setBafIncluded(String bafIncluded) {
        String oldBafIncluded = this.bafIncluded;
        this.bafIncluded = bafIncluded;
        changeSupport.firePropertyChange("bafIncluded", oldBafIncluded, bafIncluded);
    }

    public String getBafPerTariff() {
        return bafPerTariff;
    }

    public void setBafPerTariff(String bafPerTariff) {
        String oldBafPerTariff = this.bafPerTariff;
        this.bafPerTariff = bafPerTariff;
        changeSupport.firePropertyChange("bafPerTariff", oldBafPerTariff, bafPerTariff);
    }

    public String getEcaIncluded() {
        return ecaIncluded;
    }

    public void setEcaIncluded(String ecaIncluded) {
        String oldEcaIncluded = this.ecaIncluded;
        this.ecaIncluded = ecaIncluded;
        changeSupport.firePropertyChange("ecaIncluded", oldEcaIncluded, ecaIncluded);
    }

    public String getEcaPerTariff() {
        return ecaPerTariff;
    }

    public void setEcaPerTariff(String ecaPerTariff) {
        String oldEcaPerTariff = this.ecaPerTariff;
        this.ecaPerTariff = ecaPerTariff;
        changeSupport.firePropertyChange("ecaPerTariff", oldEcaPerTariff, ecaPerTariff);
    }

    public String getDocumentationFeeIncluded() {
        return documentationFeeIncluded;
    }

    public void setDocumentationFeeIncluded(String documentationFeeIncluded) {
        String oldDocumentationFeeIncluded = this.documentationFeeIncluded;
        this.documentationFeeIncluded = documentationFeeIncluded;
        changeSupport.firePropertyChange("documentationFeeIncluded", oldDocumentationFeeIncluded, documentationFeeIncluded);
    }

    public String getAlphaNumeral() {
        return alphaNumeral;
    }

    public void setAlphaNumeral(String alphaNumeral) {
        String oldAlphaNumeral = this.alphaNumeral;
        this.alphaNumeral = alphaNumeral;
        changeSupport.firePropertyChange("alphaNumeral", oldAlphaNumeral, alphaNumeral);
    }

    public String getAccessories() {
        return accessories;
    }

    public void setAccessories(String accessories) {
        String oldAccessories = this.accessories;
        this.accessories = accessories;
        changeSupport.firePropertyChange("accessories", oldAccessories, accessories);
    }

    public String getMafiMinimum() {
        return mafiMinimum;
    }

    public void setMafiMinimum(String mafiMinimum) {
        String oldMafiMinimum = this.mafiMinimum;
        this.mafiMinimum = mafiMinimum;
        changeSupport.firePropertyChange("mafiMinimum", oldMafiMinimum, mafiMinimum);
    }

    public String getMafiMinimumCharge() {
        return mafiMinimumCharge;
    }

    public void setMafiMinimumCharge(String mafiMinimumCharge) {
        String oldMafiMinimumCharge = this.mafiMinimumCharge;
        this.mafiMinimumCharge = mafiMinimumCharge;
        changeSupport.firePropertyChange("mafiMinimumCharge", oldMafiMinimumCharge, mafiMinimumCharge);
    }

    public String getReasonForDecline() {
        return reasonForDecline;
    }

    public void setReasonForDecline(String reasonForDecline) {
        String oldReasonForDecline = this.reasonForDecline;
        this.reasonForDecline = reasonForDecline;
        changeSupport.firePropertyChange("reasonForDecline", oldReasonForDecline, reasonForDecline);
    }

    public String getContractRate() {
        return contractRate;
    }

    public void setContractRate(String contractRate) {
        String oldContractRate = this.contractRate;
        this.contractRate = contractRate;
        changeSupport.firePropertyChange("contractRate", oldContractRate, contractRate);
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        String oldBookingNumber = this.bookingNumber;
        this.bookingNumber = bookingNumber;
        changeSupport.firePropertyChange("bookingNumber", oldBookingNumber, bookingNumber);
    }

    public String getBookedUserID() {
        return bookedUserID;
    }

    public void setBookedUserID(String bookedUserID) {
        String oldBookedUserID = this.bookedUserID;
        this.bookedUserID = bookedUserID;
        changeSupport.firePropertyChange("bookedUserID", oldBookedUserID, bookedUserID);
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        String oldFeedbackType = this.feedbackType;
        this.feedbackType = feedbackType;
        changeSupport.firePropertyChange("feedbackType", oldFeedbackType, feedbackType);
    }

    public String getFeedbackDescription() {
        return feedbackDescription;
    }

    public void setFeedbackDescription(String feedbackDescription) {
        String oldFeedbackDescription = this.feedbackDescription;
        this.feedbackDescription = feedbackDescription;
        changeSupport.firePropertyChange("feedbackDescription", oldFeedbackDescription, feedbackDescription);
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        String oldFeedback = this.feedback;
        this.feedback = feedback;
        changeSupport.firePropertyChange("feedback", oldFeedback, feedback);
    }

    public String getPublishingID() {
        return publishingID;
    }

    public void setPublishingID(String publishingID) {
        String oldPublishingID = this.publishingID;
        this.publishingID = publishingID;
        changeSupport.firePropertyChange("publishingID", oldPublishingID, publishingID);
    }

    public String getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(String commodityNumber) {
        String oldCommodityNumber = this.commodityNumber;
        this.commodityNumber = commodityNumber;
        changeSupport.firePropertyChange("commodityNumber", oldCommodityNumber, commodityNumber);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String oldDescription = this.description;
        this.description = description;
        changeSupport.firePropertyChange("description", oldDescription, description);
    }

    public String getTliNumber() {
        return tliNumber;
    }

    public void setTliNumber(String tliNumber) {
        String oldTliNumber = this.tliNumber;
        this.tliNumber = tliNumber;
        changeSupport.firePropertyChange("tliNumber", oldTliNumber, tliNumber);
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        String oldExpiration = this.expiration;
        this.expiration = expiration;
        changeSupport.firePropertyChange("expiration", oldExpiration, expiration);
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        String oldContactName = this.contactName;
        this.contactName = contactName;
        changeSupport.firePropertyChange("contactName", oldContactName, contactName);
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        String oldContactEmail = this.contactEmail;
        this.contactEmail = contactEmail;
        changeSupport.firePropertyChange("contactEmail", oldContactEmail, contactEmail);
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        String oldContactPhone = this.contactPhone;
        this.contactPhone = contactPhone;
        changeSupport.firePropertyChange("contactPhone", oldContactPhone, contactPhone);
    }

    public String getContactPhoneExtension() {
        return contactPhoneExtension;
    }

    public void setContactPhoneExtension(String contactPhoneExtension) {
        String oldContactPhoneExtension = this.contactPhoneExtension;
        this.contactPhoneExtension = contactPhoneExtension;
        changeSupport.firePropertyChange("contactPhoneExtension", oldContactPhoneExtension, contactPhoneExtension);
    }

    public String getContactPhoneType() {
        return contactPhoneType;
    }

    public void setContactPhoneType(String contactPhoneType) {
        String oldContactPhoneType = this.contactPhoneType;
        this.contactPhoneType = contactPhoneType;
        changeSupport.firePropertyChange("contactPhoneType", oldContactPhoneType, contactPhoneType);
    }

    public String getDuplicateRate() {
        return duplicateRate;
    }

    public void setDuplicateRate(String duplicateRate) {
        String oldDuplicateRate = this.duplicateRate;
        this.duplicateRate = duplicateRate;
        changeSupport.firePropertyChange("duplicateRate", oldDuplicateRate, duplicateRate);
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        String oldStorage = this.storage;
        this.storage = storage;
        changeSupport.firePropertyChange("storage", oldStorage, storage);
    }

    public String getStorageUnit() {
        return storageUnit;
    }

    public void setStorageUnit(String storageUnit) {
        String oldStorageUnit = this.storageUnit;
        this.storageUnit = storageUnit;
        changeSupport.firePropertyChange("storageUnit", oldStorageUnit, storageUnit);
    }

    public String getStorageIncluded() {
        return storageIncluded;
    }

    public void setStorageIncluded(String storageIncluded) {
        String oldStorageIncluded = this.storageIncluded;
        this.storageIncluded = storageIncluded;
        changeSupport.firePropertyChange("storageIncluded", oldStorageIncluded, storageIncluded);
    }

    public String getStoragePerTariff() {
        return storagePerTariff;
    }

    public void setStoragePerTariff(String storagePerTariff) {
        String oldStoragePerTariff = this.storagePerTariff;
        this.storagePerTariff = storagePerTariff;
        changeSupport.firePropertyChange("storagePerTariff", oldStoragePerTariff, storagePerTariff);
    }

    public String getCarrierComments1() {
        return carrierComments1;
    }

    public void setCarrierComments1(String carrierComments1) {
        String oldCarrierComments1 = this.carrierComments1;
        this.carrierComments1 = carrierComments1;
        changeSupport.firePropertyChange("carrierComments1", oldCarrierComments1, carrierComments1);
    }

    public String getMtdApproval() {
        return mtdApproval;
    }

    public void setMtdApproval(String mtdApproval) {
        String oldMtdApproval = this.mtdApproval;
        this.mtdApproval = mtdApproval;
        changeSupport.firePropertyChange("mtdApproval", oldMtdApproval, mtdApproval);
    }

    public String getSpaceApproval() {
        return spaceApproval;
    }

    public void setSpaceApproval(String spaceApproval) {
        String oldSpaceApproval = this.spaceApproval;
        this.spaceApproval = spaceApproval;
        changeSupport.firePropertyChange("spaceApproval", oldSpaceApproval, spaceApproval);
    }

    public String getOverseasResponse() {
        return overseasResponse;
    }

    public void setOverseasResponse(String overseasResponse) {
        String oldOverseasResponse = this.overseasResponse;
        this.overseasResponse = overseasResponse;
        changeSupport.firePropertyChange("overseasResponse", oldOverseasResponse, overseasResponse);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Allquotes)) {
            return false;
        }
        Allquotes other = (Allquotes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sales.Allquotes[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
