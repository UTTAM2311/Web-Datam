package com.rss.core.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;
import javax.validation.Valid;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"URL", "Title", "Descripton", "Category", "Tags", "Content", "Created Date and Time",
        "Modified Date andTime", "Author"})
public class MetaDataPojo {

    @JsonProperty("URL")
    private String uRL;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Descripton")
    private String descripton;
    @JsonProperty("Category")
    @Valid
    private List<String> category = new ArrayList<String>();
    @JsonProperty("Tags")
    @Valid
    private List<String> tags = new ArrayList<String>();
    @JsonProperty("Content")
    @Valid
    private List<String> content = new ArrayList<String>();
    @JsonProperty("Created Date and Time")
    private String createdDateAndTime;
    @JsonProperty("Modified Date andTime")
    private String modifiedDateAndTime;
    @JsonProperty("Author")
    @Valid
    private List<String> author = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The uRL
     */
    @JsonProperty("URL")
    public String getURL() {
        return uRL;
    }

    /**
     *
     * @param uRL The URL
     */
    @JsonProperty("URL")
    public void setURL(String uRL) {
        this.uRL = uRL;
    }

    public MetaDataPojo withURL(String uRL) {
        this.uRL = uRL;
        return this;
    }

    /**
     *
     * @return The title
     */
    @JsonProperty("Title")
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title The Title
     */
    @JsonProperty("Title")
    public void setTitle(String title) {
        this.title = title;
    }

    public MetaDataPojo withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     *
     * @return The descripton
     */
    @JsonProperty("Descripton")
    public String getDescripton() {
        return descripton;
    }

    /**
     *
     * @param descripton The Descripton
     */
    @JsonProperty("Descripton")
    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }

    public MetaDataPojo withDescripton(String descripton) {
        this.descripton = descripton;
        return this;
    }

    /**
     *
     * @return The category
     */
    @JsonProperty("Category")
    public List<String> getCategory() {
        return category;
    }

    /**
     *
     * @param category The Category
     */
    @JsonProperty("Category")
    public void setCategory(List<String> category) {
        this.category = category;
    }

    public MetaDataPojo withCategory(List<String> category) {
        this.category = category;
        return this;
    }

    /**
     *
     * @return The tags
     */
    @JsonProperty("Tags")
    public List<String> getTags() {
        return tags;
    }

    /**
     *
     * @param tags The Tags
     */
    @JsonProperty("Tags")
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public MetaDataPojo withTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    /**
     *
     * @return The content
     */
    @JsonProperty("Content")
    public List<String> getContent() {
        return content;
    }

    /**
     *
     * @param content The Content
     */
    @JsonProperty("Content")
    public void setContent(List<String> content) {
        this.content = content;
    }

    public MetaDataPojo withContent(List<String> content) {
        this.content = content;
        return this;
    }

    /**
     *
     * @return The createdDateAndTime
     */
    @JsonProperty("Created Date and Time")
    public String getCreatedDateAndTime() {
        return createdDateAndTime;
    }

    /**
     *
     * @param createdDateAndTime The Created Date and Time
     */
    @JsonProperty("Created Date and Time")
    public void setCreatedDateAndTime(String createdDateAndTime) {
        this.createdDateAndTime = createdDateAndTime;
    }

    public MetaDataPojo withCreatedDateAndTime(String createdDateAndTime) {
        this.createdDateAndTime = createdDateAndTime;
        return this;
    }

    /**
     *
     * @return The modifiedDateAndTime
     */
    @JsonProperty("Modified Date andTime")
    public String getModifiedDateAndTime() {
        return modifiedDateAndTime;
    }

    /**
     *
     * @param modifiedDateAndTime The Modified Date andTime
     */
    @JsonProperty("Modified Date andTime")
    public void setModifiedDateAndTime(String modifiedDateAndTime) {
        this.modifiedDateAndTime = modifiedDateAndTime;
    }

    public MetaDataPojo withModifiedDateAndTime(String modifiedDateAndTime) {
        this.modifiedDateAndTime = modifiedDateAndTime;
        return this;
    }

    /**
     *
     * @return The author
     */
    @JsonProperty("Author")
    public List<String> getAuthor() {
        return author;
    }

    /**
     *
     * @param author The Author
     */
    @JsonProperty("Author")
    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public MetaDataPojo withAuthor(List<String> author) {
        this.author = author;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public MetaDataPojo withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uRL).append(title).append(descripton).append(category).append(tags)
                .append(content).append(createdDateAndTime).append(modifiedDateAndTime).append(author)
                .append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MetaDataPojo) == false) {
            return false;
        }
        MetaDataPojo rhs = ((MetaDataPojo) other);
        return new EqualsBuilder().append(uRL, rhs.uRL).append(title, rhs.title).append(descripton, rhs.descripton)
                .append(category, rhs.category).append(tags, rhs.tags).append(content, rhs.content)
                .append(createdDateAndTime, rhs.createdDateAndTime).append(modifiedDateAndTime, rhs.modifiedDateAndTime)
                .append(author, rhs.author).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
