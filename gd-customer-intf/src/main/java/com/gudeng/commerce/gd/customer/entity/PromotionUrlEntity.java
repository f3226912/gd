package com.gudeng.commerce.gd.customer.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "promotion_url")
public class PromotionUrlEntity  implements java.io.Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3807198343945751598L;

	private Long id;

    private Long sourceId;

    private Integer type;

    private String url;

    private String urlImg;
    
    @Id
    @Column(name = "id")
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "sourceId")
    public Long getSourceId(){

        return this.sourceId;
    }
    public void setSourceId(Long sourceId){

        this.sourceId = sourceId;
    }
    @Column(name = "type")
    public Integer getType(){

        return this.type;
    }
    public void setType(Integer type){

        this.type = type;
    }
    @Column(name = "url")
    public String getUrl(){

        return this.url;
    }
    public void setUrl(String url){

        this.url = url;
    }
    @Column(name = "urlImg")
    public String getUrlImg(){

        return this.urlImg;
    }
    public void setUrlImg(String urlImg){

        this.urlImg = urlImg;
    }
}




