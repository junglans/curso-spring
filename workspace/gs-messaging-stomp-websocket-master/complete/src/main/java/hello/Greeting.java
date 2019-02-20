package hello;

public class Greeting {

	private String id;
    private String content;

    public Greeting() {
    }

    public Greeting(String id, String content) {
        this.content = content;
        this.id = id;
    }

    public String getContent() {
        return content;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
