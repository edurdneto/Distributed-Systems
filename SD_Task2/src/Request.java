
public class Request {
	private String id;
	private int status;
	
	
	public Request(String id, int status) {
		super();
		this.id = id;
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Request [id=" + id + ", status=" + status + "]";
	}
	
	
	
	
}
