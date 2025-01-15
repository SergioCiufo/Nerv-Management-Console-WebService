package com.company.NervManagementConsoleREST.model;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.company.NervManagementConsoleREST.utils.BlobConverter;
import com.company.NervManagementConsoleREST.utils.BooleanToCharConverterUtils;
import com.company.NervManagementConsoleREST.utils.LocalDateAdapterUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@XmlRootElement(name = "mission")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@AttributeOverrides({
    @AttributeOverride(name = "level", column = @Column(name = "levelMin"))
})
@Table(name = "MISSION")
public class Mission extends Activity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "missionId")
	private Integer missionId;
	private String description;
	 
	private Blob image;
	
	private Integer participantsMax;
	
	@XmlTransient
	@JsonIgnore
	@Transient
	private List<MissionParticipants> missionParticipants;
	
	@Convert(converter = BooleanToCharConverterUtils.class) // Y or N
	private Boolean eventMission; //true la missione è un evento speciale //false è una missione normale
	
	@Convert(converter = BooleanToCharConverterUtils.class) // Y or N
	private Boolean available; //true la missione è disponibile per essere giocata //false la missione non è disponibile per essere giocata
	
	//JAXB non supporta nativamente LocalDate, 
	//quindi è necessario un XmlAdapter per serializzarlo correttamente come stringa (yyyy-MM-dd) in XML.
	@XmlJavaTypeAdapter(LocalDateAdapterUtils.class)
	private LocalDate releaseDate;
	
	public Mission() {
		super();
	}

	public Mission(Integer exp, Integer level, Integer synchronizationRate, Integer tacticalAbility,
			Integer supportAbility, String name, Integer durationTime, Integer missionId, String description,
			Integer participantsMax) {
		super(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
		this.missionId = missionId;
		this.description = description;
		this.participantsMax = participantsMax;
	}
	
	public Mission(Integer exp, Integer level, Integer synchronizationRate, Integer tacticalAbility,
			Integer supportAbility, String name, Integer durationTime, Integer missionId, String description,
			Integer participantsMax, List<MissionParticipants> missionParticipants) {
		super(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
		this.missionId = missionId;
		this.description = description;
		this.participantsMax = participantsMax;
		this.missionParticipants = missionParticipants;
	}
	
	public Mission(Integer exp, Integer level, Integer synchronizationRate, Integer tacticalAbility,
			Integer supportAbility, String name, Integer durationTime, Integer missionId, String description,
			Blob image, Integer participantsMax) {
		super(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
		this.missionId = missionId;
		this.description = description;
		this.image = image;
		this.participantsMax = participantsMax;
	}

	
	public Mission(String name, String description, Integer participantsMax, Integer level, Integer synchronizationRate,
			Integer tacticalAbility, Integer supportAbility, Integer exp, Integer durationTime) {
	super(exp, level, synchronizationRate, tacticalAbility, supportAbility, name, durationTime);
	this.description = description;
	this.participantsMax = participantsMax;
	}
	
	public Mission(Integer missionId, String description, Blob image, Integer participantsMax,
			List<MissionParticipants> missionParticipants, Boolean eventMission, Boolean available,
			LocalDate releaseDate) {
		super();
		this.missionId = missionId;
		this.description = description;
		this.image = image;
		this.participantsMax = participantsMax;
		this.missionParticipants = missionParticipants;
		this.eventMission = eventMission;
		this.available = available;
		this.releaseDate = releaseDate;
	}

	public Integer getMissionId() {
		return missionId;
	}

	public void setMissionId(Integer missionId) {
		this.missionId = missionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getParticipantsMax() {
		return participantsMax;
	}

	public void setParticipantsMax(Integer participantsMax) {
		this.participantsMax = participantsMax;
	}

	public List<MissionParticipants> getMissionParticipants() {
		return missionParticipants;
	}

	public void setMissionParticipants(List<MissionParticipants> missionParticipants) {
		this.missionParticipants = missionParticipants;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public Boolean getEventMission() {
		return eventMission;
	}

	public void setEventMission(Boolean eventMission) {
		this.eventMission = eventMission;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(description, missionId, participantsMax);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mission other = (Mission) obj;
		return Objects.equals(description, other.description) 
				&& Objects.equals(missionId, other.missionId)
				&& Objects.equals(participantsMax, other.participantsMax)
				&& Objects.equals(eventMission, other.eventMission)
				&& Objects.equals(available, other.available)
				&& Objects.equals(releaseDate, other.releaseDate);
	}

	@Override
	public String toString() {
		return "Mission [missionId=" + missionId + ", event= " + eventMission + ", description=" + description + ", image=" + image
				+ ", participantsMax=" + participantsMax + ", name=" + name + ", durationTime=" + durationTime
				+ ", getExp()=" + getExp() + ", getLevel()=" + getLevel() + ", getSynchronizationRate()="
				+ getSynchronizationRate() + ", getTacticalAbility()=" + getTacticalAbility() + ", getSupportAbility()="
				+ getSupportAbility() + "]";
	}
	
	//Metodo per ottenere l'immagine in formato Base64
	@JsonProperty("image")
	@XmlElement(name = "image")
	public String getImageAsBase64() {
		BlobConverter blobConverter = new BlobConverter();
		return blobConverter.blobToBase64(this.image);
	}
	
	//Metodo per impostare l'immagine da una stringa Base64
	@JsonProperty("image")
	@XmlElement(name = "image")
	public void setImageFromBase64(String base64Image) {
		BlobConverter blobConverter = new BlobConverter();
        this.image = blobConverter.base64ToBlob(base64Image);
    }

}
