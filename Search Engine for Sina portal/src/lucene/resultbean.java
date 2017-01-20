package lucene;



public class resultbean {
	private String sword="";
	private String title="";
	private String url = "";
	private String content="";
	private String time = "";
    
	
	public resultbean(){}
	
public String setSword(String sword){
	
		return this.sword=sword; 
		
	}
	
	public String getSword(){
		return sword;
		
	}
	
	
	public String setContent(String content){
		
		content=content.subSequence(0, 200).toString();
		
		return this.content=content; 
		
	}
	
	public String getContent(){
		return content;
		
	}
	
public String setTitle(String title){
		
		return this.title=title; 
		
	}
	
	public String getTitle(){
		return title;
		
	}
	
public String setUrl(String url){
		
		return this.url=url; 
		
	}
	
	public String getUrl(){
		return url;
		
	}
	
	
	public String getTime(){
		return time;	
	}
	
	
}

